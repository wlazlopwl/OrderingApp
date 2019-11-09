package com.example.pawel.myapp;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.HashMap;

public class WelcomeActivity extends AppCompatActivity {
    ProgressBar progressBar;
    private int progressBarStatus=0;
    private Handler handler = new Handler();
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        progressBar = (ProgressBar) findViewById(R.id.welcomeProgressbar);

        new Thread(new Runnable() {
            public void run() {
                while (progressBarStatus < 100) {
                    progressBarStatus += 5;
                    Log.d("s",""+progressBarStatus);
                    // Update the progress bar and display the+pr
                    //current value in the text view
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressBarStatus);




                            if (progressBarStatus>=100) {
                                Toast.makeText(WelcomeActivity.this, "działa", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.INVISIBLE);
                                sessionManager= new SessionManager(WelcomeActivity.this);
                                sessionManager.checkLogin();
                                WelcomeActivity.this.finish();




                            }
                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


    }





    }



