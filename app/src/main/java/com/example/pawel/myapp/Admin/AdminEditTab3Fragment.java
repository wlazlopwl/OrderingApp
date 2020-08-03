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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pawel.myapp.Const;
import com.example.pawel.myapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminEditTab3Fragment extends Fragment {


    List<String> id = new ArrayList<String>();
    EditText mProductName, mProductDesc;
    Button mAddProductBtn;
    ProgressDialog progressDialog;
    ImageView imageView;
    Bitmap bitmap;
    Boolean isPhoto, isData;
    String name, desc;
    Spinner mCategorySpinner;
    RadioGroup mRadioGroup;
    RadioButton mRadioBtnSzt, mRadioBtnG;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin_edit_tab3, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mProductName = view.findViewById(R.id.admin_edit_tab3_product_name);
        mProductDesc = view.findViewById(R.id.admin_edit_tab3_product_desc);
        mAddProductBtn = view.findViewById(R.id.admin_edit_tab3_add_btn);
        progressDialog = new ProgressDialog(getContext());
        imageView = view.findViewById(R.id.photo_add_product);
        mCategorySpinner = view.findViewById(R.id.category_spinner);


        isPhoto = false;

        setCategorySpinner();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickPhotoFromGallery();
            }
        });
        mAddProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmptyData();

                if (isData && isPhoto) {
                    addProduct();

                } else if (!isData && isPhoto) {
                    Toast.makeText(getContext(), "Wprowadź brakujące dane", Toast.LENGTH_SHORT).show();
                } else if (isData && !isPhoto) {
                    Toast.makeText(getContext(), "Dodaj zdjęcie", Toast.LENGTH_SHORT).show();
                } else if (!isData && !isPhoto) {

                    Toast.makeText(getContext(), "Dodaj zdjęcie i wprowadź brakujące dane", Toast.LENGTH_SHORT).show();
                }


            }
        });

//        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch(checkedId) {
//                    case R.id.radio_btn_szt:
//                        quantityTypeId=1;
//                            break;
//                    case R.id.radio_btn_g:
//                        quantityTypeId=2;
//
//
//                        break;
//                }
//
//            }
//        });

    }


    public void pickPhotoFromGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
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

    public void checkEmptyData() {
        name = mProductName.getText().toString().trim();
        desc = mProductDesc.getText().toString().trim();

        if (TextUtils.isEmpty(name) || (TextUtils.isEmpty(desc))) {
            isData = false;
        } else {
            isData = true;
        }


    }

    private void setCategorySpinner() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Const.URL_GET_CATEGORY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {

                    List<String> items = new ArrayList<String>();


                    JSONArray jsonArray = new JSONArray(response);


                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String name = object.getString("name").trim();

                        String idList = object.getString("id").trim();
                        items.add(name);
                        id.add(idList);

                    }


                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
                    mCategorySpinner.setAdapter(adapter);
                    mCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            Log.v("item", (String) parent.getItemAtPosition(position));


                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // TODO Auto-generated method stub
                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        requestQueue.add(stringRequest);


    }

    public void addProduct() {
        progressDialog.setMessage("Proszę czekać");
        progressDialog.show();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL_ADD_PRODUCT, new Response.Listener<String>() {
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

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("name", name);
                params.put("img", imageString);
                params.put("description", desc);
                String categoryId = (String) id.get(mCategorySpinner.getSelectedItemPosition());
                params.put("category_id", categoryId);


                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        requestQueue.add(stringRequest);


    }


}
