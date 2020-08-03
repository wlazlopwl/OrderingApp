package com.example.pawel.myapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class LogInActivityTestUI {

    @Rule
    public ActivityTestRule<LogInActivity> loginTestRule =
            new ActivityTestRule<>(LogInActivity.class);

    @Test
    public void logIn() {
        onView(withId(R.id.email_login)).perform(typeText("Test@test.pl"));
        closeSoftKeyboard();
        onView(withId(R.id.pass_login)).perform(typeText("Test"));
        closeSoftKeyboard();


    }

}