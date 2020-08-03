package com.example.pawel.myapp.User;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.pawel.myapp.R;

public class supportContactDialogFragment extends BottomSheetDialogFragment {
    private ImageView mEmail, mPhone;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.layout_contact_support, container, false);
        mEmail = (ImageView) view.findViewById(R.id.contact_email_iv);
        mPhone = (ImageView) view.findViewById(R.id.contact_phone_iv);

        mEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "appMail@gmail.com", null));
                startActivity(Intent.createChooser(intent, "Send email..."));
            }
        });

        mPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "123 456 789", null));
                startActivity(intent);
            }
        });

        return view;
    }


}
