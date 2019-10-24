package com.example.pawel.myapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AdminHomeFragment extends Fragment {
    private TextView mUserCount;
    private static String URL_GET_ADMIN="http://s34787.s.pwste.edu.pl/app/getAdminData.php";
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_admin_home, container, false);


        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mUserCount = view.findViewById(R.id.count_user);



        Log.d("fragment","sa");

        AdminActivity activity = (AdminActivity) getActivity();
        getAdminData();


    }


    private void getAdminData(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_GET_ADMIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONArray jsonArray  = new JSONArray(response);




                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object=jsonArray.getJSONObject(i);
                        String count = object.getString("COUNT(id)").trim();
                        Log.d("ilosc", count);
                        mUserCount.setText(count);




                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        requestQueue.add(stringRequest);


    }

}