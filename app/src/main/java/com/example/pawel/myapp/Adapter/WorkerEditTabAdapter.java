package com.example.pawel.myapp.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.pawel.myapp.Worker.WorkerEditTab1Fragment;
import com.example.pawel.myapp.Worker.WorkerEditTab2Fragment;
import com.example.pawel.myapp.Worker.WorkerEditTab3Fragment;

import java.util.ArrayList;
import java.util.List;

public class WorkerEditTabAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> fragmentList = new ArrayList<>();
    private String[] tabTitles = new String[]{"Aktualne", "Raporty", "Adresy"};

    public WorkerEditTabAdapter(FragmentManager fm) {
        super(fm);

    }
    @Override
    public int getCount() {
        return fragmentList.size();
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                WorkerEditTab1Fragment tab1 = new WorkerEditTab1Fragment();
                return tab1;
            case 1:
                WorkerEditTab2Fragment tab2 = new WorkerEditTab2Fragment();
                return tab2;
            case 2:
                WorkerEditTab3Fragment tab3 = new WorkerEditTab3Fragment();
                return tab3;
            default:
                return null;
        }


    }

    public void addFragment(Fragment fragment){
        fragmentList.add(fragment);
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

}
