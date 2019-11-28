package com.example.pawel.myapp.Admin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.pawel.myapp.R;

public class AdminActivity extends AppCompatActivity {

    private TextView mTextMessage, mCountUser;
    public String dane;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);



        BottomNavigationView navigation =  findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);





    }






    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        Fragment selectedFragment = null;

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    selectedFragment = new AdminHomeFragment();

                    break;
                case R.id.navigation_order:
                    selectedFragment = new AdminEditFragment();
                    break;
                case R.id.navigation_setting:
                    selectedFragment = new AdminSettingsFragment();

            }

            getSupportFragmentManager().beginTransaction().replace(R.id.admin_fragment_container, selectedFragment).commit();
            return true;
        }
    };




}
