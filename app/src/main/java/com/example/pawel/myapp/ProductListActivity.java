package com.example.pawel.myapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pawel.myapp.Adapter.ProductAdapter;
import com.example.pawel.myapp.Model.DataProduct;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductListActivity extends AppCompatActivity {


    private com.example.pawel.myapp.Adapter.ProductAdapter ProductAdapter;
    ArrayList<DataProduct> dataProductArrayList;
    private RecyclerView recyclerView;
    public static Context ctx;
    SessionManager sessionManager;
    static String userId;
    public EditText actualQuantity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        sessionManager = new SessionManager(this);
        userId = sessionManager.getUserInfo().get("id");

        ctx = getApplicationContext();
        Intent i = getIntent();
        String position = i.getStringExtra("position");


        recyclerView = findViewById(R.id.recyclerViewProduct);

        getProduct(position, "0", "55", "2");

        Toast.makeText(ctx, "" + userId, Toast.LENGTH_SHORT).show();


    }


    private void getProduct(final String position, final String userStatus, final String userId, final String type) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL_GET_PRODUCT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {


                    dataProductArrayList = new ArrayList<>();
                    JSONArray dataArray = new JSONArray(response);

                    for (int i = 0; i < dataArray.length(); i++) {

                        DataProduct playerModel = new DataProduct();

                        Log.i("tagconvertstr2", "[" + response + "]");
                        JSONObject dataobj = dataArray.getJSONObject(i);

                        playerModel.setName(dataobj.getString("name"));
                        playerModel.setId(dataobj.getString("id"));
                        playerModel.setImgUrl(dataobj.getString("img"));


                        dataProductArrayList.add(playerModel);

                    }

                    setupProductRecycler();


                } catch (JSONException e) {
                    e.printStackTrace();
//                    DataProduct playerModell = new DataProduct();
//                    playerModell.setId("2");
//                    playerModell.setName("Test");
//                    dataProductArrayList.add(playerModell);
//                    setupProductRecycler();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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


        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);

    }


    public void setupProductRecycler() {

        //LinearLayoutManager layoutManager= new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewProduct);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setClickable(true);
        ProductAdapter = new ProductAdapter(dataProductArrayList);
        recyclerView.setAdapter(ProductAdapter);
    }


    public static void updateCart(final String p, final String check) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL_UPDATE_CART, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(ctx, response, Toast.LENGTH_LONG).show();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(ctx, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("user_id", userId);
                params.put("check", check);
                params.put("product_id", p);
                params.put("quantity", "3");


                return params;
            }
        };
        Log.d("liczba q ", "" + check);
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(ctx);

        requestQueue.add(stringRequest);


    }


}
