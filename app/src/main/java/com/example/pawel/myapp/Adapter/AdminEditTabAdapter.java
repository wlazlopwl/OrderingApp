package com.example.pawel.myapp.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.pawel.myapp.Admin.AdminEditTab1Fragment;
import com.example.pawel.myapp.Admin.AdminEditTab2Fragment;
import com.example.pawel.myapp.Admin.AdminEditTab3Fragment;

import java.util.ArrayList;
import java.util.List;

public class AdminEditTabAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> fragmentList = new ArrayList<>();
    private String[] tabTitles = new String[]{"Dodaj użytk.", "Kategoria", "Produkt"};

    public AdminEditTabAdapter(FragmentManager fm) {
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
                AdminEditTab1Fragment tab1 = new AdminEditTab1Fragment();
                return tab1;
            case 1:
                AdminEditTab2Fragment tab2 = new AdminEditTab2Fragment();
                return tab2;
            case 2:
                AdminEditTab3Fragment tab3 = new AdminEditTab3Fragment();
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
