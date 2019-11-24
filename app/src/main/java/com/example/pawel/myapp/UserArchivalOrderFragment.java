package com.example.pawel.myapp;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pawel.myapp.Adapter.ActualOrderAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class UserArchivalOrderFragment extends Fragment {
    private ActualOrderAdapter actualAdapter;
    public ArrayList<DataOrderParentList> ArchivalOrderArrayList;
    public ArrayList<DataProduct> ChildOrderList;
    SessionManager sessionManager;
    SwipeRefreshLayout mSwipeRefreshLayout;
    String userStatus, userId;

    public UserArchivalOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_archival_order, container, false);
        ArchivalOrderArrayList = new ArrayList<>();
        ChildOrderList = new ArrayList<>();
        sessionManager = new SessionManager(getContext());
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.archival_order_refresh);

        userStatus = String.valueOf(Integer.parseInt(String.valueOf(sessionManager.getUserInfo().get("value"))) - 1);
        userId = sessionManager.getUserInfo().get("id");

        getProduct("1", userStatus, userId, "1");


        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.parent_actual_order_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        recyclerView.setHasFixedSize(true);
        recyclerView.setClickable(true);
        actualAdapter = new ActualOrderAdapter(ArchivalOrderArrayList);
        recyclerView.setAdapter(actualAdapter);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                actualAdapter.notifyDataSetChanged();
                ArchivalOrderArrayList.clear();
                getProduct("1", userStatus, userId, "1");
                mSwipeRefreshLayout.setRefreshing(false);


            }
        });


    }


    private void getProduct(final String position, final String userStatus, final String userId, final String type) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL_GET_PRODUCT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONArray dataArray = new JSONArray(response);
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject idOrder = dataArray.getJSONObject(i);
                        DataOrderParentList datamodel = new DataOrderParentList();
                        datamodel.setName(idOrder.getString("id"));
                        datamodel.setDate(idOrder.getString("date_order"));
                        ArrayList<DataProduct> ChildOrderList = new ArrayList<>();


                        JSONArray product = idOrder.getJSONArray("product");

                        for (int j = 0; j < product.length(); j++) {

                            DataProduct dataProduct = new DataProduct();
                            JSONObject dataobj = product.getJSONObject(j);

                            dataProduct.setName(dataobj.getString("name"));
                            dataProduct.setDescription(dataobj.getString("desc"));
                            dataProduct.setQuantity(dataobj.getString("quantity"));
//                                dataProduct.setId(dataobj.getString("id"));
//                                dataProduct.setImgUrl(dataobj.getString("img"));


                            ChildOrderList.add(dataProduct);

                        }
                        datamodel.setDataProductList(ChildOrderList);
//
//
                        ArchivalOrderArrayList.add(datamodel);
//


                    }
                    actualAdapter.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("position", position);
                params.put("user_status", userStatus);
                params.put("user_id", userId);
                params.put("type", type);

                return params;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        requestQueue.add(stringRequest);

    }


}



