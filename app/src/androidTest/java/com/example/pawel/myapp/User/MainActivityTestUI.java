package com.example.pawel.myapp.User;

import android.support.test.rule.ActivityTestRule;

import com.example.pawel.myapp.Admin.RecyclerViewItemCountAssertion;
import com.example.pawel.myapp.R;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class MainActivityTestUI {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void btnCategoryList() throws InterruptedException {
        onView((withId(R.id.category_btn_user))).perform(click());
        Thread.sleep(3000);
        onView(withId(R.id.category_list_listview)).check(new RecyclerViewItemCountAssertion(7));


    }

}