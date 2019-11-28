package com.example.pawel.myapp.Worker;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pawel.myapp.Adapter.WorkerEditTabAdapter;
import com.example.pawel.myapp.R;

public class WorkerEditFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_worker_edit, container, false);

    TabLayout tabLayout = (TabLayout) view.findViewById(R.id.worker_edit_tab);
    ViewPager viewPager = (ViewPager) view.findViewById(R.id.worker_edit_viewPager);
        WorkerEditTabAdapter adapter = new WorkerEditTabAdapter(getFragmentManager());

        adapter.addFragment(new WorkerEditTab1Fragment());
        adapter.addFragment(new WorkerEditTab2Fragment());
        adapter.addFragment(new WorkerEditTab3Fragment());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }


}
