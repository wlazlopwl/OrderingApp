package com.example.pawel.myapp.User;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.Button;

import com.example.pawel.myapp.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testActivity() {
        MainActivity activity = mainActivityRule.getActivity();
        assertNotNull(activity);
    }

    @Test
    public void testButton() {
        assertNotNull(mainActivityRule.getActivity().findViewById(R.id.category_btn));
    }

    @Test
    public void testTextButton() {
        Button button = mainActivityRule.getActivity().findViewById(R.id.category_btn);
        assertEquals("Lista kategorii", button.getText());
    }


}

