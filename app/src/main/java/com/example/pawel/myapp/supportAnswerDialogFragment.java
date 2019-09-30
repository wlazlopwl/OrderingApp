package com.example.pawel.myapp;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

public class supportAnswerDialogFragment extends BottomSheetDialogFragment {

    @Override

    public Dialog onCreateDialog(Bundle savedInstanceState){

        Bundle mArgs = getArguments();
        String answer= mArgs.getString("answer");



        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setMessage(answer);
        AlertDialog alert = builder.create();
        return alert;

    }
}
