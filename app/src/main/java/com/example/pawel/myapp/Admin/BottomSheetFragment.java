package com.example.pawel.myapp.Admin;


import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pawel.myapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BottomSheetFragment extends BottomSheetDialogFragment {
    TextView header;

    public BottomSheetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle mArgs = getArguments();
        String answer = mArgs.getString("answer");

        View v = inflater.inflate(R.layout.fragment_bottom_support_sheet, container, false);
        header = (TextView) v.findViewById(R.id.support_answer);

        header.setText(answer);


        return v;


        //return inflater.inflate(R.layout.fragment_bottom_support_sheet, container, false);

    }

}
