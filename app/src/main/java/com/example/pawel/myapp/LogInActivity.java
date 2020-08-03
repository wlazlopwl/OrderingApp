package com.example.pawel.myapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pawel.myapp.Admin.AdminActivity;
import com.example.pawel.myapp.User.MainActivity;
import com.example.pawel.myapp.Worker.WorkerActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LogInActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button btn_login;

    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        sessionManager = new SessionManager(this);


        email = findViewById(R.id.email_login);
        password = findViewById(R.id.pass_login);
        btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eMail = email.getText().toString().trim();
                String mPass = password.getText().toString().trim();

                checkValue(eMail, mPass);

            }
        });
    }

    public boolean checkValue(String mEmail, String mPass) {
        if (!mEmail.isEmpty() || !mPass.isEmpty()) {
            Login(mEmail, mPass);
            return true;
        } else {
            email.setError("Proszę wpisać email");
            password.setError("Proszę wpisać hasło");
            return false;
        }
    }

    private void Login(final String email, final String password) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("login");


                    if (success.equals("1")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String name = object.getString("name").trim();
                            String email = object.getString("email").trim();
                            String surname = object.getString("surname").trim();
                            String street = object.getString("street").trim();
                            String city = object.getString("city").trim();
                            String postcode = object.getString("postcode").trim();
                            String phone = object.getString("phone").trim();
                            String id = object.getString("id").trim();


                            sessionManager.createSession(name, email, success, surname, street, city, postcode, phone, id);

                            Intent intent = new Intent(LogInActivity.this, MainActivity.class);

                            startActivity(intent);


                        }
                    }
                    if (success.equals("2")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String name = object.getString("name").trim();
                            String email = object.getString("email").trim();
                            String surname = object.getString("surname").trim();
                            String street = object.getString("street").trim();
                            String city = object.getString("city").trim();
                            String postcode = object.getString("postcode").trim();
                            String phone = object.getString("phone").trim();
                            String id = object.getString("id").trim();


                            sessionManager.createSession(name, email, success, surname, street, city, postcode, phone, id);
                            Intent intent = new Intent(LogInActivity.this, AdminActivity.class);
                            startActivity(intent);

                        }
                    }
                    if (success.equals("3")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String name = object.getString("name").trim();
                            String email = object.getString("email").trim();
                            String surname = object.getString("surname").trim();
                            String street = object.getString("street").trim();
                            String city = object.getString("city").trim();
                            String postcode = object.getString("postcode").trim();
                            String phone = object.getString("phone").trim();
                            String id = object.getString("id").trim();


                            sessionManager.createSession(name, email, success, surname, street, city, postcode, phone, id);
                            Intent intent = new Intent(LogInActivity.this, WorkerActivity.class);
                            startActivity(intent);

                        }
                    }
                    LogInActivity.this.finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(LogInActivity.this, "Błąd logowania", Toast.LENGTH_SHORT).show();


                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LogInActivity.this, "Błąd" + error.toString(), Toast.LENGTH_SHORT).show();


                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


}
