package com.example.pawel.myapp.Admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pawel.myapp.Const;
import com.example.pawel.myapp.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AdminEditTab2Fragment extends Fragment {

    EditText mCategoryName;
    Button mAddCategoryBtn;
    Boolean isData;
    Boolean isPhoto;
    ProgressDialog progressDialog;
    ImageView imageView;
    Bitmap bitmap;

    private static int GALLERY = 1;

    String name;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin_edit_tab2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAddCategoryBtn = view.findViewById(R.id.admin_edit_tab2_add_btn);
        mCategoryName = view.findViewById(R.id.admin_edit_tab2_category_name);
        progressDialog = new ProgressDialog(getContext());
        imageView = view.findViewById(R.id.photo_add_category);
        isPhoto = false;

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickPhotoFromGallery();
            }
        });


        mAddCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmptyData();

                if (isData && isPhoto) {
                    addCategory();
                } else if (!isData && isPhoto) {
                    Toast.makeText(getContext(), "Wprowadź nazwę kategorii", Toast.LENGTH_SHORT).show();
                } else if (isData && !isPhoto) {
                    Toast.makeText(getContext(), "Dodaj zdjęcie", Toast.LENGTH_SHORT).show();
                } else if (!isData && !isPhoto) {

                    Toast.makeText(getContext(), "Dodaj zdjęcie i nazwę kategorii", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    public void checkEmptyData() {
        name = mCategoryName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            isData = false;
        } else {
            isData = true;

        }
    }

    public void addCategory() {
        progressDialog.setMessage("Proszę czekać");
        progressDialog.show();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 25, outputStream);
        byte[] imageBytes = outputStream.toByteArray();
        final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL_ADD_CATEGORY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();
                Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();


                params.put("name", name);
                params.put("image", imageString);


                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        requestQueue.add(stringRequest);


    }

    public void pickPhotoFromGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY);


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), contentURI);
                    isPhoto = true;
                    imageView.setImageBitmap(bitmap);


                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        }

    }
}
