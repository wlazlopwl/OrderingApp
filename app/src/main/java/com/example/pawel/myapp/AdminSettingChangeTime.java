package com.example.pawel.myapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AdminSettingChangeTime extends AppCompatActivity {
    private static String URL_UPDATE_TIME = "http://s34787.s.pwste.edu.pl/app/updateTime.php";
    private static String URL_GET_TIME = "http://s34787.s.pwste.edu.pl/app/getTime.php";
    private TimePicker mTimePicker;
    private Button mUpdateTimeBtn;
    ProgressDialog progressDialog;
    String fullHour;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_admin_settings_change_time);

        mTimePicker = findViewById(R.id.time_picker);
        mUpdateTimeBtn = findViewById(R.id.update_time);
        progressDialog = new ProgressDialog(this);





        mUpdateTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getTime();


            }
        });


    }






    private void getTime() {
        int h = mTimePicker.getCurrentHour();
        int m = mTimePicker.getCurrentMinute();
        String hour = String.valueOf(h);
        String min = String.valueOf(m);
        fullHour = hour + ":" + min;
        alert();

    }

    private void alert() {
        new AlertDialog.Builder(AdminSettingChangeTime.this).setTitle("Potwierdź zmiany.").setMessage("Potwierdź, aby zaktualizować godzinę na " + fullHour).setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateTime();

            }
        }).setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert).show();


    }

    private void updateTime() {

        progressDialog.setMessage("Proszę czekać");
        progressDialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPDATE_TIME, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();
                Toast.makeText(AdminSettingChangeTime.this, response, Toast.LENGTH_LONG).show();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(AdminSettingChangeTime.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("hour", fullHour);


                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);


    }
    private void getActualTime(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_GET_TIME, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONArray jsonArray  = new JSONArray(response);




                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object=jsonArray.getJSONObject(i);
                        String count = object.getString("hour").trim();
                        String hourFromDatabase = count.substring(0,2);
                        String minuteFromDatabase = count.substring(3,5);
                        mTimePicker.setCurrentHour(Integer.parseInt(hourFromDatabase));
                        mTimePicker.setCurrentMinute(Integer.parseInt(minuteFromDatabase));
                        Log.d("h", hourFromDatabase);
                        Log.d("m", minuteFromDatabase);

                        Toast.makeText(AdminSettingChangeTime.this, ""+count, Toast.LENGTH_SHORT).show();




                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(AdminSettingChangeTime.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);


    }

    }



