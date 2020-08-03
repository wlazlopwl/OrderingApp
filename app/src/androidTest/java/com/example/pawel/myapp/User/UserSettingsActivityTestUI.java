package com.example.pawel.myapp.User;

import android.support.test.rule.ActivityTestRule;

import com.example.pawel.myapp.R;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class UserSettingsActivityTestUI {


    @Rule
    public ActivityTestRule<UserSettingsActivity> SettingsActivityRue =
            new ActivityTestRule<>(UserSettingsActivity.class);

    @Test
    public void btnClickChangePassword() {
        onView((withId(R.id.user_setting_change_mypassword))).perform(click());
        onView((withId(R.id.admin_fragment_settings_password))).check(matches(isDisplayed()));

    }

    @Test
    public void btnClickChangeEmail() {
        onView((withId(R.id.user_setting_change_myEmail))).perform(click());
        onView((withId(R.id.admin_fragment_change_email))).check(matches(isDisplayed()));
    }

    @Test
    public void btnClickChangeData() {
        onView((withId(R.id.user_setting_change_mydata))).perform(click());
        onView((withId(R.id.admin_fragment_settings_data))).check(matches(isDisplayed()));
    }

    @Test
    public void btnClickLogout() {
        onView((withId(R.id.user_logout_btn))).perform(click());
        onView(withText("Czy na pewno chcesz się wylogować?")).check(matches(isDisplayed()));
    }
}