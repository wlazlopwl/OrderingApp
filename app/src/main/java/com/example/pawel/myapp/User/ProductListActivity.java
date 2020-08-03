package com.example.pawel.myapp.User;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.example.pawel.myapp.Const;
import com.example.pawel.myapp.Model.DataProduct;
import com.example.pawel.myapp.R;
import com.example.pawel.myapp.SessionManager;

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
    static SessionManager sessionManager;
    static String userId;
    public EditText actualQuantity;
    public static View view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        sessionManager = new SessionManager(this);
        userId = sessionManager.getUserInfo().get("id");

        ctx = getApplicationContext();
        view = getWindow().getDecorView();

        Intent i = getIntent();
        String position = i.getStringExtra("position");

        Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbara);
        toolbar.setTitle("Produkty");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        recyclerView = findViewById(R.id.recyclerViewProduct);

        getProduct(position, "0", userId, "2");


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

                        JSONObject dataobj = dataArray.getJSONObject(i);

                        playerModel.setName(dataobj.getString("name"));
                        playerModel.setId(dataobj.getString("id"));
                        playerModel.setDescription(dataobj.getString("description"));
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

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String countCart = jsonObject.getString("actualCartCount");
                    Log.d("asd", countCart);
                    sessionManager.updateCartCountForUser(countCart);
                    ;


                    Snackbar snackbar = Snackbar
                            .make(view, "Dodano do koszyka", Snackbar.LENGTH_LONG).setAction("Przejdź do koszyka", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(ctx, CartActivity.class);
                                    ctx.startActivity(intent);

                                }
                            });
                    snackbar.show();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                Toast.makeText(ctx, "Dodano do koszyka", Toast.LENGTH_LONG).show();


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
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(ctx);

        requestQueue.add(stringRequest);


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


}
