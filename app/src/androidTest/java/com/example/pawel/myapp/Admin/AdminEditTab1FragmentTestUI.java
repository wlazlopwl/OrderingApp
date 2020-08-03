package com.example.pawel.myapp.Admin;

import android.support.test.rule.ActivityTestRule;

import com.example.pawel.myapp.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class AdminEditTab1FragmentTestUI {

    @Rule
    public ActivityTestRule<AdminActivity> testRule =
            new ActivityTestRule<>(AdminActivity.class);

    @Before
    public void init() {
        testRule.getActivity()
                .getSupportFragmentManager().beginTransaction();
    }

    @Test
    public void testLaunchSettingsFragment(){
        onView(withId(R.id.navigation_setting)).perform(click());
        onView(withId(R.id.admin_fragment_change_email)).check(matches(isDisplayed()));

    }
    @Test
    public void testLaunchEditFragment(){

        onView(withId(R.id.navigation_order)).perform(click());
        onView(withId(R.id.admin_fragment_edit)).check(matches(isDisplayed()));

    }

    @Test
    public void testLaunchEditUserData(){
        onView(withId(R.id.navigation_setting)).perform(click());
        onView(withId(R.id.admin_edit_profile)).perform(click());

        onView(withId(R.id.admin_fragment_settings_data)).check(matches(isDisplayed()));

    }
    @Test
    public void testLaunchEditUserEmail(){
        onView(withId(R.id.navigation_setting)).perform(click());
        onView(withId(R.id.admin_setting_change_myEmail)).perform(click());

        onView(withId(R.id.admin_fragment_change_email)).check(matches(isDisplayed()));

    }

    @Test
    public void testEnterEmailToEditText(){
        onView(withId(R.id.navigation_setting)).perform(click());
        onView(withId(R.id.admin_setting_change_myEmail)).perform(click());
        onView(withId(R.id.myNewEmail)).perform(typeText("Test@email.pl"));
        onView(withId(R.id.myNewEmail)).check(matches(withText("Test@email.pl")));

    }
    @Test
    public void testEnterNewNameToEditText(){
        onView(withId(R.id.navigation_setting)).perform(click());
        onView(withId(R.id.admin_edit_profile)).perform(click());
        onView(withId(R.id.myName)).perform(clearText(),typeText("TestName"));
        onView(withId(R.id.mySurname)).perform(clearText(),typeText("TestSurname"));
        onView(withId(R.id.myName)).check(matches(withText("TestName")));
        onView(withId(R.id.mySurname)).check(matches(withText("TestSurname")));
    }
    @Test
    public void testUpdateNameAndSurname(){
        onView(withId(R.id.navigation_setting)).perform(click());
        onView(withId(R.id.admin_edit_profile)).perform(click());
        onView(withId(R.id.myName)).perform(clearText(),typeText("TestName"));
        onView(withId(R.id.mySurname)).perform(clearText(),typeText("TestSurname"));
        closeSoftKeyboard();
        onView(withId(R.id.admin_setting_change_mydata_btn)).perform(click());
        onView(withText("Twoje dane zostały zmienione")).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));


    }

    @Test
    public void testUpdateTime(){
        onView(withId(R.id.navigation_setting)).perform(click());
        onView(withId(R.id.admin_setting_change_time)).perform(click());
        onView(withId(R.id.update_time)).perform(click());
        onView(withId(android.R.id.button1)).perform(click());


    }
    @Test
    public void testCheckUserList() throws InterruptedException {
        onView(withId(R.id.navigation_setting)).perform(click());
        onView(withId(R.id.admin_setting_all_user)).perform(click());
        Thread.sleep(3000);
        onView(withId(R.id.all_user_list_RV)).check(new RecyclerViewItemCountAssertion(15));
    }

}





