package com.example.pawel.myapp;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.pawel.myapp.Adapter.UserOrderTabAdapter;

public class UserOrderActivity extends AppCompatActivity {
    private UserOrderTabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order);
        tabLayout=(TabLayout) findViewById(R.id.userOrderTabLayout) ;
        viewPager=(ViewPager)findViewById(R.id.userOrderViewPager);

        adapter= new UserOrderTabAdapter(getSupportFragmentManager());
        adapter.addFragment(new UserActualOrderFragment());
        adapter.addFragment(new UserArchivalOrderFragment());





        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
