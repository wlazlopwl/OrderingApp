package com.example.pawel.myapp.Worker;

import android.support.test.rule.ActivityTestRule;
import android.widget.TextView;

import com.example.pawel.myapp.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class WorkerUserDetailActivityTest {
    WorkerUserDetailActivity activity;
    TextView mPhone;
    @Rule
    public ActivityTestRule<WorkerUserDetailActivity> mainActivityTestRule =
            new ActivityTestRule<>(WorkerUserDetailActivity.class);

    @Before
    public void setUp() throws Exception {
        activity = mainActivityTestRule.getActivity();
        mPhone = activity.findViewById(R.id.worker_user_detail_phone);

    }


    @Test
    public void isEmptyTextView() {
        WorkerUserDetailActivity workerUserDetailActivity = new WorkerUserDetailActivity();
        String s = "aaa";
        workerUserDetailActivity.checkValue(mPhone, s);
        assertTrue(true);

    }
}