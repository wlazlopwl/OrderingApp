package com.example.pawel.myapp;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class SettingsChangeMyEmail extends AppCompatActivity {
    EditText mMyCurrentEmail, mNewEmail;
    Button mChangeEmailBtn;
    String currentEmail, newEmail, userId;
    SessionManager sessionManager;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_change_my_email);
        sessionManager = new SessionManager(getApplicationContext());
        Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbara);
        toolbar.setTitle("Zmiana e-maila");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mMyCurrentEmail = findViewById(R.id.myCurrentEmail);
        mNewEmail = findViewById(R.id.myNewEmail);
        mChangeEmailBtn = findViewById(R.id.setting_change_myEmail_btn);
        relativeLayout = findViewById(R.id.admin_fragment_change_email);
        mMyCurrentEmail.setEnabled(false);
        mMyCurrentEmail.setText(sessionManager.getUserInfo().get("password"));
        userId = sessionManager.getUserInfo().get("id");



        mChangeEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndUpdate();
            }
        });
    }

    private void checkAndUpdate() {
        currentEmail = mMyCurrentEmail.getText().toString().trim();
        newEmail = mNewEmail.getText().toString().trim();

        if (TextUtils.isEmpty(newEmail)) {
            Toast.makeText(this, "Wprowadź nowy E-mail", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.equals(currentEmail, newEmail)) {
            Toast.makeText(this, "Nowy E-mail jest identyczny do poprzedniego", Toast.LENGTH_SHORT).show();
        } else {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            updateEmail();
        }

    }

    private void updateEmail() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL_UPDATE_EMAIL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("Zaktualizowano")) {
                    Snackbar.make(relativeLayout, "E-mail został zmieniony.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    sessionManager.updateEmailInSession(newEmail);
                }


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(SettingsChangeMyEmail.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("id", userId);
                params.put("email", newEmail);


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
