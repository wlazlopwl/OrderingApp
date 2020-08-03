package com.example.pawel.myapp;

import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.Button;
import android.widget.EditText;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


@RunWith(AndroidJUnit4.class)

public class LogInActivityTest {
    LogInActivity activity;
    EditText email, password;
    Button button;

    @Rule
    public ActivityTestRule<LogInActivity> LoginTestRule = new ActivityTestRule<>(LogInActivity.class);


    @Before
    public void setUp() throws Exception {
        activity = LoginTestRule.getActivity();
        email = activity.findViewById(R.id.pass_login);
        password = activity.findViewById(R.id.pass_login);
        button = activity.findViewById(R.id.btn_login);
    }

    @Test
    @UiThreadTest
    public void matchingData() throws InterruptedException {

        email.setText("b");
        password.setText("u");
        button.performClick();
        Thread.sleep(5000);
        assertNull(email.getError());


    }

    @Test
    @UiThreadTest
    public void emptyEmail() throws InterruptedException {

        button.performClick();
        Thread.sleep(5000);
        assertEquals("brak", email.getError().toString().trim());

    }


}