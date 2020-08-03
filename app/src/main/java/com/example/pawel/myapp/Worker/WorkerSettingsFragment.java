package com.example.pawel.myapp.Worker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.pawel.myapp.R;
import com.example.pawel.myapp.SessionManager;
import com.example.pawel.myapp.SettingsChangeMyData;
import com.example.pawel.myapp.SettingsChangeMyEmail;
import com.example.pawel.myapp.SettingsChangeMyPassword;

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
        LinearLayout mChangeMyData = (LinearLayout) view.findViewById(R.id.worker_change_mydata);
        LinearLayout mChangePassBtn = (LinearLayout) view.findViewById(R.id.worker_setting_change_mypassword);
        LinearLayout mChangeEmailBtn = (LinearLayout) view.findViewById(R.id.worker_setting_change_myEmail);
        sessionManager = new SessionManager(getActivity());

        mChangeMyData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingsChangeMyData.class);
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
                logoutAlert();
            }
        });


    }

    private void logoutAlert() {
        AlertDialog.Builder logoutDialog = new AlertDialog.Builder(getContext());
        logoutDialog.setTitle("UWAGA!");
        logoutDialog.setIcon(R.drawable.ic_sad_face);
        logoutDialog.setMessage("Czy na pewno chcesz się wylogować?").setCancelable(false).setPositiveButton("TAK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sessionManager.logout();
                getActivity().finish();

            }
        }).setNegativeButton("NIE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = logoutDialog.create();
        alertDialog.show();
    }
}
