package com.example.pawel.myapp.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.pawel.myapp.UserActualOrderFragment;
import com.example.pawel.myapp.UserArchivalOrderFragment;

import java.util.ArrayList;
import java.util.List;

public class UserOrderTabAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> fragmentList = new ArrayList<>();
    private String[] tabTitles = new String[]{"Aktualne", "Zakończone"};

    public UserOrderTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                UserActualOrderFragment tab1 = new UserActualOrderFragment();
                return tab1;
            case 1:
                UserArchivalOrderFragment tab2 = new UserArchivalOrderFragment();
                return tab2;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public void addFragment(Fragment fragment){
        fragmentList.add(fragment);
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
