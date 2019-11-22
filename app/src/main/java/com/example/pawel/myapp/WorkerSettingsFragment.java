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

public class WorkerSettingsFragment extends Fragment {
    SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_worker_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button mLogout = (Button) view.findViewById(R.id.worker_logout_btn);
        Button mChangeMyData = (Button) view.findViewById(R.id.worker_change_mydata_btn);
        Button mChangePassBtn = (Button) view.findViewById(R.id.worker_setting_change_mypassword_btn);
        Button mChangeEmailBtn = (Button) view.findViewById(R.id.worker_setting_change_myEmail_btn);
        sessionManager = new SessionManager(getActivity());

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

        mChangeEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingsChangeMyEmail.class);
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
