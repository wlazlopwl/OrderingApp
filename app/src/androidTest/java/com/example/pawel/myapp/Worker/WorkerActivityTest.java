package com.example.pawel.myapp.Worker;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import com.example.pawel.myapp.Admin.RecyclerViewItemCountAssertion;
import com.example.pawel.myapp.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class WorkerActivityTest {
    @Rule
    public ActivityTestRule<WorkerActivity> testRule =
            new ActivityTestRule<>(WorkerActivity.class);

    @Before
    public void init() {
        testRule.getActivity()
                .getSupportFragmentManager().beginTransaction();
    }

    @Test
    public void testIsButtonHome(){
        onView(withId(R.id.worker_navigation_home)).perform(click());
        onView(withId(R.id.worker_home_to_order_btn)).check(matches(isDisplayed()));

    }

    @Test
    public void testOpenSettings(){
        onView(withId(R.id.worker_navigation_setting)).perform(click());
        onView(withId(R.id.worker_logout_btn)).check(matches(isDisplayed()));

    }

    @Test
    public void testOpenUserList() throws InterruptedException {
        onView(withId(R.id.worker_navigation_people)).perform(click());
        Thread.sleep(3000);
        onView(withId(R.id.worker_people_list_rv)).check(new RecyclerViewItemCountAssertion(7));

    }
    @Test
    public void testCheckUserDetail() throws InterruptedException {
        onView(withId(R.id.worker_navigation_people)).perform(click());
        Thread.sleep(3000);
        onView(withId(R.id.worker_people_list_rv))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.worker_user_detail_phoneIV)).check(matches(isDisplayed()));

    }

    @Test
    public void testOpenActualOrderList() throws InterruptedException {
        onView(withId(R.id.worker_navigation_order)).perform(click());
        Thread.sleep(3000);
        onView(withId(R.id.fragment_worker_edit_tab1)).check(matches(isDisplayed()));


    }




}