package com.example.pawel.myapp;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LogInActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button btn_login;
    private static String URL_LOGIN="http://192.168.0.165/app2/login.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        email=findViewById(R.id.email_login);
        password=findViewById(R.id.pass_login);
        btn_login=findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eMail=email.getText().toString().trim();
                String mPass=password.getText().toString().trim();

                if (!eMail.isEmpty() || !mPass.isEmpty()) {
                   Login(eMail,mPass);
                }
                else
                {
                    email.setError("Proszę wpisać email");
                    password.setError("Proszę wpisać hasło");
                }

            }
        });
    }

    private void Login(final String email, final String password) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String success=jsonObject.getString("success");
                            JSONArray jsonArray=jsonObject.getJSONArray("login");


                            if (success.equals("1")){
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject object=jsonArray.getJSONObject(i);
                                String name = object.getString("name").trim();
                                String email = object.getString("email").trim();


                                Intent intent=new Intent(LogInActivity.this,MainActivity.class);
                                intent.putExtra("name",name);
                                intent.putExtra("email",email);
                                startActivity(intent);


                            }
                            }
                            if (success.equals("2")){
                                for(int i=0;i<jsonArray.length();i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    Toast.makeText(LogInActivity.this, "Zalogowany ADMIN", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(LogInActivity.this,AdminActivity.class);
                                    startActivity(intent);

                                }}


                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(LogInActivity.this, "Błąd logowania", Toast.LENGTH_SHORT).show();


                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LogInActivity.this, "Błąd" +error.toString(), Toast.LENGTH_SHORT).show();


                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params=new HashMap<>();
                params.put("email",email);
                params.put("password",password);
                return params;
            }
        };

        RequestQueue requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


}
