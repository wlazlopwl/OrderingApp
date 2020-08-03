package com.example.pawel.myapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
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

public class SettingsChangeMyData extends AppCompatActivity {

    EditText mName, mSurname, mStreet, mCity, mPostcode, mPhone;
    SessionManager sessionManager;
    ProgressDialog progressDialog;
    String name, surname, street, city, postcode, phone, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_setting_change_my_data);
        Toolbar toolbar = findViewById(R.id.toolbara);
        toolbar.setTitle("Zmiana danych");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        sessionManager = new SessionManager(getApplicationContext());
        Button mChangeDataBtn = (Button) findViewById(R.id.admin_setting_change_mydata_btn);

        mName = findViewById(R.id.myName);
        mSurname = findViewById(R.id.mySurname);
        mStreet = findViewById(R.id.myStreet);
        mCity = findViewById(R.id.myCity);
        mPostcode = findViewById(R.id.myPostcode);
        mPhone = findViewById(R.id.myPhone);


        id = sessionManager.getUserInfo().get("id");


        progressDialog = new ProgressDialog(this);
        setDefaultData();
        mPostcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().contains("-") && s.length() > 2) {
                    s.insert(2, "-");
                }

            }
        });

        mChangeDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkNewData(mName, mSurname, mStreet, mCity, mPostcode, mPhone)) {
                    updateData();
                }

            }
        });


    }


    private void setDefaultData() {


        String name = sessionManager.getUserInfo().get("name");
        String surname = sessionManager.getUserInfo().get("surname");
        String street = sessionManager.getUserInfo().get("street");
        String city = sessionManager.getUserInfo().get("city");
        String postcode = sessionManager.getUserInfo().get("postcode");
        String phone = sessionManager.getUserInfo().get("phone");


        mName.setText(name);
        mSurname.setText(surname);
        mStreet.setText(street);
        mCity.setText(city);
        mPostcode.setText(postcode);
        mPhone.setText(phone);


    }

    //    , EditText surname, EditText street, EditText city, EditText postcode, EditText phone
    private boolean checkNewData(EditText name, EditText surname, EditText street, EditText city, EditText postcode, EditText phone) {
        String newName = name.getText().toString().trim();
        String newSurname = surname.getText().toString().trim();
        String newStreet = street.getText().toString().trim();
        String newCity = city.getText().toString().trim();
        String newPostcode = postcode.getText().toString().trim();
        String newPhone = phone.getText().toString().trim();
        int lengthName = newName.length();
        int lengthSurname = newSurname.length();
        int lengthStreet = newStreet.length();
        int lengthCity = newCity.length();
        int lengthPostcode = newPostcode.length();
        int lengthPhone = newPhone.length();


        if ((lengthName < 3) || (lengthSurname < 3) || (lengthCity > 0 && lengthCity < 4) || (lengthStreet > 0 && lengthStreet < 5)
                || (lengthPostcode > 0 && lengthPostcode < 6) || (lengthPhone > 0 && lengthPhone < 9)) {

            if (lengthName == 0) {
                name.setError("Pole jest puste!");
            } else if (lengthName <= 2) {
                name.setError("Uzupełnij lub zostaw puste");
            }
            if (lengthSurname == 0) {
                surname.setError("Pole jest puste!");
            } else if (lengthSurname <= 2) {
                surname.setError("Uzupełnij lub zostaw puste");
            }
            if (lengthStreet > 0 && lengthStreet < 5) {
                street.setError("Uzupełnij lub zostaw puste");
            }
            if (lengthCity > 0 && lengthCity < 4) {
                city.setError("Uzupełnij lub zostaw puste");
            }
            if (lengthPostcode > 0 && lengthPostcode < 6) {
                postcode.setError("Uzupełnij lub zostaw puste");
            }
            if (lengthPhone > 0 && lengthPhone < 9) {
                phone.setError("Uzupełnij 9 cyfr lub zostaw puste");

            }
            return false;

        } else {
            return true;
        }


    }


    private void updateData() {
        name = mName.getText().toString().trim();
        surname = mSurname.getText().toString().trim();
        street = mStreet.getText().toString().trim();
        city = mCity.getText().toString().trim();
        postcode = mPostcode.getText().toString().trim();
        phone = mPhone.getText().toString().trim();
        progressDialog.setMessage("Trwa zmiana danych");
        progressDialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL_UPDATE_MYDATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();
                Toast.makeText(SettingsChangeMyData.this, "Twoje dane zostały zmienione", Toast.LENGTH_LONG).show();
                sessionManager.updateDataInSession(name, surname, street, city, postcode, phone);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(SettingsChangeMyData.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();


                params.put("id", id);
                params.put("name", name);
                params.put("surname", surname);
                params.put("street", street);
                params.put("city", city);
                params.put("postcode", postcode);
                params.put("phone", phone);


                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}
