package com.example.pawel.myapp.Worker;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pawel.myapp.Const;
import com.example.pawel.myapp.R;
import com.example.pawel.myapp.User.UserArchivalOrderFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class WorkerUserDetailActivity extends AppCompatActivity {
    private TextView test, mName, mSurname, mAllOrder, mActualOrder, mStreet, mCity, mPhone, mPostcode, mEmail, mFirstStreet;
    private ImageView mNavIV, mPhoneIV, mEmailIV;
    LinearLayout linearLayout;
    String idUser, city, street, phone, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_details);

        Toolbar toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.toolbara);
        toolbar.setTitle("Szczegóły");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        mName = (TextView) findViewById(R.id.worker_user_detail_name);
        mSurname = (TextView) findViewById(R.id.worker_user_detail_surname);
        mNavIV = (ImageView) findViewById(R.id.worker_user_detail_navIV);
        mPhoneIV = (ImageView) findViewById(R.id.worker_user_detail_phoneIV);
        mEmailIV = (ImageView) findViewById(R.id.worker_user_detail_emailIV);
        mAllOrder = (TextView) findViewById(R.id.worker_user_detail_all_order);
        mActualOrder = (TextView) findViewById(R.id.worker_user_detail_current_order);
        mStreet = (TextView) findViewById(R.id.worker_user_detail_street);
        mCity = (TextView) findViewById(R.id.worker_user_detail_city);
        mPhone = (TextView) findViewById(R.id.worker_user_detail_phone);
        mPostcode = (TextView) findViewById(R.id.worker_user_detail_postcode);
        mEmail = (TextView) findViewById(R.id.worker_user_detail_email);
        mFirstStreet=(TextView) findViewById(R.id.textView_street);
//        linearLayout = (LinearLayout) findViewById(R.id.linear);


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            idUser = extras.getString("idUser");

        }
        Log.d("idUser", idUser);

        getUserInfo();


        mNavIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(isEmptyTextView(mStreet))&&!(isEmptyTextView(mCity))) {
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse("google.navigation:q=" + street + ",+" + city + "+Poland"));

                startActivity(intent);
                }
                else {
                    Toast.makeText(WorkerUserDetailActivity.this, "Adres jest niepełny. Nawigowanie niemożliwe.", Toast.LENGTH_SHORT).show();
                }

            }
        });
        mPhoneIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(isEmptyTextView(mPhone))) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                    startActivity(intent);
                }
                else{
                    Toast.makeText(WorkerUserDetailActivity.this, "Użytkownik nie podał numeru. ", Toast.LENGTH_SHORT).show();
                }

            }
        });
        mEmailIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email, null));
                startActivity(Intent.createChooser(intent, "Send email..."));

            }
        });

//        mAllOrder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                Bundle bundle = new Bundle();
//                bundle.putString("idUser", idUser);
//
//
//                FragmentManager fm = getSupportFragmentManager();
//                Fragment order = new UserArchivalOrderFragment();
//                order.setArguments(bundle);
//                FragmentTransaction transaction = fm.beginTransaction();
//                transaction.replace(R.id.frame, order).addToBackStack(null).commit();
//
//
//            }
//        });


    }

private Boolean isEmptyTextView(TextView textView){
    String value = textView.getText().toString();
    if (value.contains("Nie podano")){
        return true;
    }
    else{
        return false;
    }
        
}
    private void checkValue(TextView textView, String name) {
        String value = textView.getText().toString();
        if (value.isEmpty()) {
            textView.setText(name + "Nie podano");
            textView.setTextColor(Color.RED);


        }

    }

    public void getUserInfo() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL_GET_USER_INFO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONArray dataArray = new JSONArray(response);
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject jsonObject = dataArray.getJSONObject(i);
                        mName.setText(jsonObject.getString("name") + " ");
                        mSurname.setText(jsonObject.getString("surname"));
//                        mAllOrder.setText(jsonObject.getString("finishedOrderCount"));
//                        mActualOrder.setText(jsonObject.getString("orderCount"));
                        mCity.setText(jsonObject.getString("city"));
                        mStreet.setText(jsonObject.getString("street"));
                        mPostcode.setText(jsonObject.getString("postcode")+" ");
                        mPhone.setText(jsonObject.getString("phone"));
                        mEmail.setText(jsonObject.getString("email"));
                        checkValue(mCity, "Miasto: ");
                        checkValue(mStreet, "Ulica: ");
                        checkValue(mPostcode, "Kod pocztowy: ");
                        checkValue(mPhone, "Telefon: ");
                        checkValue(mEmail,"E-mail: ");

                        city = mCity.getText().toString();
                        street = mStreet.getText().toString();
                        if (isEmptyTextView(mPostcode)) {
                            mPostcode.setVisibility(View.GONE);
                        }
                        if (isEmptyTextView(mStreet)) {
                            mFirstStreet.setVisibility(View.GONE);
                        }
                        phone = mPhone.getText().toString();
                        email = mEmail.getText().toString();

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
                        Toast.makeText(WorkerUserDetailActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("user_id", idUser);

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
