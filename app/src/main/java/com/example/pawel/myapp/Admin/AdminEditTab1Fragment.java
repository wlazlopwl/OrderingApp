package com.example.pawel.myapp.Admin;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminEditTab1Fragment extends Fragment {


    Spinner spinner, workerSpinner;
    EditText mName, mSurname, mEmail, mPassword, mStreet, mCity, mPostcode, mPhone;
    String name, surname, email, password, street, city, postcode, phone;
    String toastMessage = null;
    Button mAddUser;
    Boolean isData;
    ProgressDialog progressDialog;
    LinearLayout linearLayoutForWorkerSpinner;
    List<String> id = new ArrayList<String>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin_edit_tab1, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spinner = view.findViewById(R.id.status_spinner);
        workerSpinner = view.findViewById(R.id.worker_spinner);
        mName = view.findViewById(R.id.addUserName);
        mSurname = view.findViewById(R.id.addUserSurname);
        mEmail = view.findViewById(R.id.addUserEmail);
        mPassword = view.findViewById(R.id.addUserPassword);
        mStreet = view.findViewById(R.id.addStreet);
        mCity = view.findViewById(R.id.addCity);
        mPostcode = view.findViewById(R.id.addPostcode);
        mPhone = view.findViewById(R.id.addPhone);
        progressDialog = new ProgressDialog(getContext());
        linearLayoutForWorkerSpinner = view.findViewById(R.id.worker_spinner_row);

        mAddUser = view.findViewById(R.id.btn_addUser);
        setStatusSpinner();


        mAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkEmptyData();
                if (isData) {

                    addUser();

                } else {
                    Toast.makeText(getContext(), toastMessage, Toast.LENGTH_SHORT).show();


                }


            }
        });

        setWorkerSpinner();


    }

    private void setStatusSpinner() {
        String[] items = new String[]{
                "User", "Admin", " Worker",
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                if (position != 0) {
                    linearLayoutForWorkerSpinner.setVisibility(View.INVISIBLE);
                } else linearLayoutForWorkerSpinner.setVisibility(View.VISIBLE);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }


    public void setWorkerSpinner() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Const.URL_GET_WORKER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {

                    List<String> items = new ArrayList<String>();


                    JSONArray jsonArray = new JSONArray(response);


                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String name = object.getString("name").trim();
                        String surname = object.getString("surname").trim();
                        String idList = object.getString("id").trim();
                        String fullName = surname + " " + name;
                        items.add(fullName);
                        id.add(idList);

                    }


                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
                    workerSpinner.setAdapter(adapter);
                    workerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    public String checkEmptyData() {


        name = mName.getText().toString().trim();
        surname = mSurname.getText().toString().trim();
        email = mEmail.getText().toString().trim();
        password = mPassword.getText().toString().trim();
        toastMessage = null;
        if (TextUtils.isEmpty(name) && TextUtils.isEmpty(surname) && TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
            isData = false;
            toastMessage = "Uzupełnij wszystkie wymagane pola";
        } else if (TextUtils.isEmpty(name)) {
            isData = false;
            toastMessage = "Wprowadź imię";

        } else if (TextUtils.isEmpty(surname)) {
            isData = false;
            toastMessage = "Wprowadź nazwisko";
        } else if (TextUtils.isEmpty(email)) {
            isData = false;
            toastMessage = "Wprowadź email";
        } else if (TextUtils.isEmpty(password)) {
            isData = false;
            toastMessage = "Wprowadź hasło";
        } else {
            isData = true;
            toastMessage = "Poprawne dane";

        }

        return toastMessage;
    }

    public void clearUserData() {
        mName.getText().clear();
        mSurname.getText().clear();
        mEmail.getText().clear();
        mPassword.getText().clear();
        mStreet.getText().clear();
        mCity.getText().clear();
        mPostcode.getText().clear();
        mPhone.getText().clear();
    }

    public void addUser() {

        street = mStreet.getText().toString().trim();
        city = mCity.getText().toString().trim();
        postcode = mPostcode.getText().toString().trim();
        phone = mPhone.getText().toString().trim();

        progressDialog.setMessage("Proszę czekać");
        progressDialog.show();
        final int intUserStatusPosition = spinner.getSelectedItemPosition();
        final String StringUserStatusPosition = String.valueOf(intUserStatusPosition);
        final int workerId = workerSpinner.getSelectedItemPosition();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL_ADD_USER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();
                Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                clearUserData();
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
                params.put("surname", surname);
                params.put("email", email);
                params.put("password", password);
                params.put("status", StringUserStatusPosition);
                params.put("street", street);
                params.put("city", city);
                params.put("postcode", postcode);
                params.put("phone", phone);
                if (intUserStatusPosition == 0) {

                    String workerId = (String) id.get(workerSpinner.getSelectedItemPosition());
                    params.put("workerId", workerId);
                }

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        requestQueue.add(stringRequest);


    }


}



