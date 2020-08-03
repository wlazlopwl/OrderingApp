package com.example.pawel.myapp.User;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.pawel.myapp.R;
import com.example.pawel.myapp.SessionManager;
import com.example.pawel.myapp.SettingsChangeMyData;
import com.example.pawel.myapp.SettingsChangeMyEmail;
import com.example.pawel.myapp.SettingsChangeMyPassword;

public class UserSettingsActivity extends AppCompatActivity {
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);
        sessionManager = new SessionManager(getApplicationContext());

        Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbara);
        toolbar.setTitle("Ustawienia");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        LinearLayout mChangeMyData = (LinearLayout) findViewById(R.id.user_setting_change_mydata);
        LinearLayout mChangeMyPass = (LinearLayout) findViewById(R.id.user_setting_change_mypassword);
        LinearLayout mChangeMyEmail = (LinearLayout) findViewById(R.id.user_setting_change_myEmail);

        Button mLogout = (Button) findViewById(R.id.user_logout_btn);

        mChangeMyData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserSettingsActivity.this, SettingsChangeMyData.class);
                startActivity(i);

            }
        });
        mChangeMyPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserSettingsActivity.this, SettingsChangeMyPassword.class);
                startActivity(i);

            }
        });

        mChangeMyEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserSettingsActivity.this, SettingsChangeMyEmail.class);
                startActivity(i);
            }
        });

        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutAlert();
            }
        });


    }

    public void logoutAlert() {
        AlertDialog.Builder logoutDialog = new AlertDialog.Builder(this);
        logoutDialog.setTitle("UWAGA!");
        logoutDialog.setIcon(R.drawable.ic_sad_face);
        logoutDialog.setMessage("Czy na pewno chcesz się wylogować?").setCancelable(false).setPositiveButton("TAK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sessionManager.logout();
                finish();

            }
        }).setNegativeButton("NIE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = logoutDialog.create();
        alertDialog.show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
