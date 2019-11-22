package com.example.pawel.myapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        Button mChangeMyData= (Button) findViewById(R.id.user_setting_change_mydata_btn);
        Button mChangeMyPass = (Button) findViewById(R.id.user_setting_change_mypassword_btn);
        Button mChangeMyEmail = (Button) findViewById(R.id.user_setting_change_myEmail_btn);
        Button mLogout= (Button) findViewById(R.id.user_logout_btn);

        mChangeMyData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserSettingsActivity.this,  AdminSettingChangeMyData.class);
                startActivity(i);

            }
        });
        mChangeMyPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserSettingsActivity.this,  SettingsChangeMyPassword.class);
                startActivity(i);

            }
        });

        mChangeMyEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserSettingsActivity.this,  SettingsChangeMyEmail.class);
                startActivity(i);
            }
        });

        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
}
