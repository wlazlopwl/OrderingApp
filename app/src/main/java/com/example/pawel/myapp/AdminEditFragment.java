package com.example.pawel.myapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AdminEditFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_edit, container, false);
        TabLayout tabLayout= (TabLayout) view.findViewById(R.id.admin_edit_tab);
        ViewPager viewPager= (ViewPager) view.findViewById(R.id.admin_edit_viewPager);
        AdminEditTabAdapter adapter = new AdminEditTabAdapter(getFragmentManager());

        //add fragment
        adapter.addFragment(new AdminEditTab1Fragment());
        adapter.addFragment(new AdminEditTab2Fragment());
        adapter.addFragment(new AdminEditTab3Fragment());

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


        return view;
    }


}
