package com.example.pawel.myapp.Worker;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.pawel.myapp.R;

public class WorkerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker);


        BottomNavigationView bottomNavigationView = findViewById(R.id.worker_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        bottomNavigationView.setSelectedItemId(R.id.worker_navigation_home);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                Fragment selectedFragment = null;
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.worker_navigation_home:
                            selectedFragment = new WorkerHomeFragment();

                            break;
                        case R.id.worker_navigation_order:
                            selectedFragment = new WorkerEditFragment();

                            break;
                        case R.id.worker_navigation_people:
                            selectedFragment =  new WorkerPeopleFragment();
                            break;
                        case R.id.worker_navigation_setting:
                            selectedFragment = new WorkerSettingsFragment();


                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.worker_fragment_container, selectedFragment).commit();
                    return true;
                }
            };

}
