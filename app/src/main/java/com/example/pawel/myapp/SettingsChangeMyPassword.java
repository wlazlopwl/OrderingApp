package com.example.pawel.myapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class SettingsChangeMyPassword extends AppCompatActivity {
    EditText mMyCurrentPass, mFirstNewPass, mSecondNewPass;
    Button mChangePassBtn;
    String currentPass, firstNewPass, secondNewPass, idUser;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_change_my_password);
        sessionManager = new SessionManager(getApplicationContext());
        idUser = sessionManager.getUserInfo().get("id");

        Toolbar toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.toolbara);
        toolbar.setTitle("Zmiana hasła");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        mMyCurrentPass = findViewById(R.id.myCurrentPass);
        mFirstNewPass = findViewById(R.id.myFirstNewPass);
        mSecondNewPass = findViewById(R.id.mySecondNewPass);
        mChangePassBtn = findViewById(R.id.admin_setting_change_myPass_btn);

//        mChangePassBtn.setEnabled(false);

        mChangePassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkData();
            }
        });


    }


    private void checkData() {
        currentPass = mMyCurrentPass.getText().toString().trim();
        firstNewPass = mFirstNewPass.getText().toString().trim();
        secondNewPass = mSecondNewPass.getText().toString().trim();

        if ((TextUtils.isEmpty(currentPass)) || (TextUtils.isEmpty(firstNewPass)) || (TextUtils.isEmpty(secondNewPass))) {
            Toast.makeText(this, "Uzupełnij brakujące pola", Toast.LENGTH_SHORT).show();
        } else if (!((TextUtils.isEmpty(currentPass)) && (TextUtils.isEmpty(firstNewPass)) && (TextUtils.isEmpty(secondNewPass)))) {


            if (TextUtils.equals(firstNewPass, secondNewPass)) {
                updatePassword( firstNewPass);
            } else {
                Toast.makeText(this, "Wpisane hasła różnią się", Toast.LENGTH_SHORT).show();
            }

        }


    }

    private void updatePassword( final String firstNewPass) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL_UPDATE_PASSWORD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(SettingsChangeMyPassword.this, response, Toast.LENGTH_LONG).show();
                ;

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(SettingsChangeMyPassword.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("id", idUser);
                params.put("password", firstNewPass);


                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);

    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
