package com.example.pawel.myapp;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

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
                while (progressBarStatus < 50) {
                    progressBarStatus += 5;
                    // Update the progress bar and display the+pr
                    //current value in the text view
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressBarStatus);




                            if (progressBarStatus>=50) {

                                progressBar.setVisibility(View.INVISIBLE);
                                sessionManager= new SessionManager(WelcomeActivity.this);
                                sessionManager.checkLoginAccount();
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



