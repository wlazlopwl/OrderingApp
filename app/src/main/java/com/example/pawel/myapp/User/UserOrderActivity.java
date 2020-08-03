package com.example.pawel.myapp.User;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.pawel.myapp.Adapter.UserOrderTabAdapter;
import com.example.pawel.myapp.R;

public class UserOrderActivity extends AppCompatActivity {
    private UserOrderTabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order);
        tabLayout = (TabLayout) findViewById(R.id.userOrderTabLayout);
        viewPager = (ViewPager) findViewById(R.id.userOrderViewPager);
        Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbara);
        toolbar.setTitle("Zamówienia");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        adapter = new UserOrderTabAdapter(getSupportFragmentManager());
        adapter.addFragment(new UserActualOrderFragment());
        adapter.addFragment(new UserArchivalOrderFragment());


        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
