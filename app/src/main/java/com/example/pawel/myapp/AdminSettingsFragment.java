package com.example.pawel.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class AdminSettingsFragment extends Fragment {
    Button mChangeTime, mLogout, mChangeMyData, mChangePassBtn;
    SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mChangeTime = view.findViewById(R.id.admin_setting_change_time);
        mLogout = view.findViewById(R.id.admin_logout_btn);
        mChangeMyData = view.findViewById(R.id.admin_change_mydata_btn);
        mChangePassBtn =  view.findViewById(R.id.admin_setting_change_mypassword_btn);

        sessionManager = new SessionManager(getActivity());


        mChangeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.container, new AdminSettingsChangeTimeFragment());
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();

                Intent intent = new Intent(getActivity(), AdminSettingChangeTime.class);
                startActivity(intent);

            }
        });

        mChangeMyData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AdminSettingChangeMyData.class);
                startActivity(intent);
            }
        });

        mChangePassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingsChangeMyPassword.class);
                startActivity(intent);
            }
        });

        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logout();
                getActivity().finish();

            }
        });

    }


}
