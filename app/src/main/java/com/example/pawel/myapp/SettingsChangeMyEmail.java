package com.example.pawel.myapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsChangeMyEmail extends AppCompatActivity {
    EditText mMyCurrentEmail, mNewEmail;
    Button mChangeEmailBtn;
    String currentEmail, newEmail;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_change_my_email);
        sessionManager = new SessionManager(getApplicationContext());

        mMyCurrentEmail = findViewById(R.id.myCurrentEmail);
        mNewEmail = findViewById(R.id.myNewEmail);
        mChangeEmailBtn = findViewById(R.id.setting_change_myEmail_btn);

        mMyCurrentEmail.setEnabled(false);
        mMyCurrentEmail.setText(sessionManager.getUserInfo().get("login"));
        //ZMIEN NA EMAIL


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
        } else if (TextUtils.equals(currentEmail,newEmail)) {
            Toast.makeText(this, "Nowy E-mail jest identyczny do poprzedniego", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Poprawnie", Toast.LENGTH_SHORT).show();
//            funkcja aktualizacji email
        }

    }
}
