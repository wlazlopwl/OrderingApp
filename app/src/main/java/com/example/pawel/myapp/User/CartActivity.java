package com.example.pawel.myapp.User;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.pawel.myapp.Adapter.CartAdapter;
import com.example.pawel.myapp.SettingsChangeMyData;
import com.example.pawel.myapp.Const;
import com.example.pawel.myapp.Model.DataProduct;
import com.example.pawel.myapp.R;
import com.example.pawel.myapp.SessionManager;
import com.example.pawel.myapp.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartActivity extends AppCompatActivity {


    private static DataProduct data;
    private static com.example.pawel.myapp.Adapter.CartAdapter CartAdapter;
    public static ArrayList<DataProduct> dataCartArrayList;
    private RecyclerView recyclerView;
    static SessionManager sessionManager;
    public static Context ctx;
    public static String userId;
    public Button btnNewOrder;
    public TextView mTextEmptyCart;
    public static View view;
    private int cartCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        view = getWindow().getDecorView();


        ctx = getApplicationContext();
        mTextEmptyCart = (TextView) findViewById(R.id.textEmptyCart);

        sessionManager = new SessionManager(this);
        btnNewOrder = (Button) findViewById(R.id.btn_order);

        Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbara);
        toolbar.setTitle("Koszyk");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        userId = sessionManager.getUserInfo().get("id");
        if (sessionManager.getUserInfo().get("cartCount")!="null") {
            cartCount = Integer.parseInt(sessionManager.getUserInfo().get("cartCount")) ;

        }
        else{
            cartCount=0;
        }

        if (cartCount==0) {
            btnNewOrder.setVisibility(View.INVISIBLE);
        }



        getProduct(userId);


        btnNewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkAddress() == true) {
                    sendNewOrder("1");
                } else {
                    alert();
                }


            }
        });


    }


    private void alert() {
        new AlertDialog.Builder(CartActivity.this).setTitle("Uwaga!.")
                .setMessage("Aby złożyć zamówienie, uzupełnij adres dostawy." +
                        " Czy chcesz to zrobić teraz?").setPositiveButton("Chcę uzupełnić", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(CartActivity.this, SettingsChangeMyData.class);
                startActivity(intent);


            }
        }).setNegativeButton("Zrobię to później", null)
                .setIcon(android.R.drawable.ic_dialog_alert).show();


    }

    private void alertDate() {
        new AlertDialog.Builder(CartActivity.this).setTitle("Uwaga!.")
                .setMessage("W dniu dzisiejszym nie możesz już złożyć zamówienia. \n" +
                        "Czy chcesz złożyc zamówienie z datą następnego dnia roboczego?").setPositiveButton("Tak, chcę!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendNewOrder("2");


            }
        }).setNegativeButton("Nie, dziękuję", null)
                .setIcon(android.R.drawable.ic_dialog_alert).show();


    }

    private Boolean checkAddress() {
        boolean enteredAddress;

        SessionManager sessionManager;
        sessionManager = new SessionManager(this);

        if ((sessionManager.getUserInfo().get("street").isEmpty()) ||
                (sessionManager.getUserInfo().get("city").isEmpty()) ||
                (sessionManager.getUserInfo().get("phone").isEmpty()) ||
                (sessionManager.getUserInfo().get("postcode").isEmpty())) {

            enteredAddress = false;
        } else enteredAddress = true;


        return enteredAddress;

    }

    private void sendNewOrder(final String type) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL_NEW_ORDER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                if (response.contains("Zaktualizowano")) {
                    Toast.makeText(ctx, "Złożono zamówienie", Toast.LENGTH_LONG).show();
                    sessionManager.updateCartCountForUser("0");
                    CartAdapter.notifyDataSetChanged();
                    dataCartArrayList.clear();
                    mTextEmptyCart.setVisibility(View.VISIBLE);



                } else {
                    alertDate();
                }
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
                params.put("type", type);


                return params;
            }
        };
        VolleySingleton.getInstance(ctx).addToRequestQueue(stringRequest);


    }


    public void setupProductCartRecycler() {


        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewProductCart);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setClickable(true);
        CartAdapter = new CartAdapter(dataCartArrayList);
        recyclerView.setAdapter(CartAdapter);
    }


    private void getProduct(final String userId) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL_GET_CART, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                if (!response.contains("Brak")) {
                    try {


                        dataCartArrayList = new ArrayList<>();
                        JSONArray dataArray = new JSONArray(response);

                        for (int i = 0; i < dataArray.length(); i++) {

                            DataProduct playerModel = new DataProduct();

                            JSONObject dataobj = dataArray.getJSONObject(i);

                            playerModel.setName(dataobj.getString("name"));
                            playerModel.setId(dataobj.getString("id"));
                            playerModel.setQuantity(dataobj.getString("quantity"));
                            playerModel.setDescription(dataobj.getString("description"));
                            playerModel.setImgUrl(dataobj.getString("img"));


                            dataCartArrayList.add(playerModel);

                        }

                        setupProductCartRecycler();


                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                } else {
                    mTextEmptyCart.setVisibility(View.VISIBLE);
                    btnNewOrder.setEnabled(false);
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
                params.put("user_id", userId);

                return params;
            }
        };

        VolleySingleton.getInstance(ctx).addToRequestQueue(stringRequest);

    }

    public static void deleteProduct(final String idProduct, final int position) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL_DELETE_CART_ITEM, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

//                Toast.makeText(ctx, response, Toast.LENGTH_LONG).show();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String countCart = jsonObject.getString("actualCartCount");



                    sessionManager.updateCartCountForUser(countCart);
                    Snackbar snackbar = Snackbar
                            .make(view, "Wybrany produkt został usunięty z koszyka", Snackbar.LENGTH_SHORT);
                    snackbar.show();


                } catch (JSONException e) {
                    e.printStackTrace();
                }


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

                params.put("product_id", idProduct);


                return params;
            }
        };
        VolleySingleton.getInstance(ctx).addToRequestQueue(stringRequest);


    }

    public static void updateCart(final String p, final String check, final String quantity) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL_UPDATE_CART, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

//                Toast.makeText(ctx, response, Toast.LENGTH_LONG).show();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String countCart = jsonObject.getString("actualCartCount");
                    Log.d("asd", countCart);


                    if (check == "1") {
                        sessionManager.updateCartCountForUser(String.valueOf(Integer.parseInt(sessionManager.getUserInfo().get("cartCount")) + 1));
                    }
                    if (check == "2") {
                        sessionManager.updateCartCountForUser(String.valueOf(Integer.parseInt(sessionManager.getUserInfo().get("cartCount")) - 1));

                    }
                    if (check == "3") {
                        sessionManager.updateCartCountForUser(countCart);


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


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
                if (check == "3") {
                    params.put("quantity", quantity);
                }


                return params;
            }
        };

        VolleySingleton.getInstance(ctx).addToRequestQueue(stringRequest);


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}
