package com.example.pawel.myapp.User;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.pawel.myapp.R;

public class supportAnswerDialogFragment extends BottomSheetDialogFragment {
TextView mAnswer;
Button mOk;
//    Bundle mArgs = getArguments();
//    String answer= mArgs.getString("answer");
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.layout_answer_support, container, false);
//        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


       mAnswer= (TextView) view.findViewById(R.id.answer_textview);
       mOk =(Button) view.findViewById(R.id.answer_support_btn_ok);

       mOk.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               getDialog().dismiss();
           }
       });

        Bundle mArgs = getArguments();
        String test = mArgs.getString("answer");
        mAnswer.setText(test);


        return view;
    }


   public void setAnswer(String answer){
//       View sheetView = getView();
//
//       mAnswer.setText(answer);
    }


}
