package com.example.pawel.myapp.User;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pawel.myapp.Adapter.CategoryAdapter;
import com.example.pawel.myapp.Const;
import com.example.pawel.myapp.Model.CategoryModel;
import com.example.pawel.myapp.R;
import com.example.pawel.myapp.RecyclerViewClickListener;
import com.example.pawel.myapp.SessionManager;
import com.example.pawel.myapp.VolleySingleton;
import com.github.mikephil.charting.charts.LineChart;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, RecyclerViewClickListener {


    public static ArrayList<CategoryModel> categoryModelArrayList;
    private CategoryAdapter CategoryAdapter;
    private RecyclerView recyclerView;
    SessionManager sessionManager;
    public TextView textCartItemCount, mHour, mMinute, mActualOrder, mMyWorkerName, mMyWorkerSurname, mMonthOrder, mMonthOrderProduct;
    public int mCartItemCount = 0;
    int pHour, pMinute, pSecond;
    private Button mCategoryBtn;
    public String userId;
    LineChart mChart;
    Button refreshBtn;
    public android.app.SearchManager searchManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.support_toolbar);

        setSupportActionBar(toolbar);
        sessionManager = new SessionManager(getApplicationContext());
        mCategoryBtn = findViewById(R.id.category_btn_user);
        mActualOrder = findViewById(R.id.user_home_actual_order);
        mMyWorkerName = findViewById(R.id.user_home_my_worker_name);
        mMyWorkerSurname = findViewById(R.id.user_home_my_worker_surname);
        mMonthOrder = findViewById(R.id.user_home_all_month_order);
        mMonthOrderProduct = findViewById(R.id.all_order_product_count);
        refreshBtn = findViewById(R.id.refreshButton);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        View headerView = navigationView.getHeaderView(0);
        TextView menuName = headerView.findViewById(R.id.menu_name);
        TextView menuEmail = headerView.findViewById(R.id.menu_email);

        menuName.setText(sessionManager.getUserInfo().get("name") + " " + sessionManager.getUserInfo().get("surname"));
        menuEmail.setText(sessionManager.getUserInfo().get("password"));
        userId = sessionManager.getUserInfo().get("id");

        recyclerView = findViewById(R.id.recyclerVievCategory);

        getCategory();
        getUserData();


        mHour = findViewById(R.id.main_hour_textView);
        mMinute = findViewById(R.id.main_minute_textView);
        getActualTime();


        mCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CategoryListActivity.class);
                startActivity(intent);
            }
        });

        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getCategory();
                getActualTime();
                getUserData();
                refreshBtn.setVisibility(View.INVISIBLE);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        invalidateOptionsMenu();
    }

    private void getUserData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL_GET_USER_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {


                    JSONArray dataArray = new JSONArray(response);

                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject obj = dataArray.getJSONObject(i);

                        mActualOrder.setText(obj.getString("countActualOrder"));
                        mMyWorkerName.setText(obj.getString("name"));
                        mMyWorkerSurname.setText(obj.getString("surname"));
                        mMonthOrder.setText(obj.getString("countLastMonthOrder"));
                        mMonthOrderProduct.setText(obj.getString("countLastMonthOrderProduct"));

                        String count = obj.getString("countCart");
                        if (count == "null") {
                            textCartItemCount.setText("0");
                        } else {
                            textCartItemCount.setText(count);

                        }
                        sessionManager.updateCartCountForUser(textCartItemCount.getText().toString());


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
                        Toast.makeText(MainActivity.this, "Wystąpił problem z połączeniem internetowym." +
                                " Sprawdź połączenie i spróbuj ponownie", Toast.LENGTH_LONG).show();

                        refreshBtn.setVisibility(View.VISIBLE);


                    }
                }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("id", userId);


                return params;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);


    }

    public void refreshActivity() {
        finish();
        startActivity(getIntent());
    }

    public void getActualTime() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Const.URL_GET_TIME, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONArray jsonArray = new JSONArray(response);


                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);
                        String count = object.getString("hour").trim();
                        String hourFromDatabase = count.substring(0, 2);
                        String minuteFromDatabase = count.substring(3, 5);

                        mHour.setText(hourFromDatabase);
                        mMinute.setText(minuteFromDatabase);


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        requestQueue.add(stringRequest);


    }


    public void getCategory() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Const.URL_GET_CATEGORY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    categoryModelArrayList = new ArrayList<>();
                    JSONArray dataArray = new JSONArray(response);

                    for (int i = 0; i < dataArray.length(); i++) {

                        CategoryModel categoryModel = new CategoryModel();
                        JSONObject dataobj = dataArray.getJSONObject(i);
                        categoryModel.setId(dataobj.getString("id"));
                        categoryModel.setName(dataobj.getString("name"));
                        categoryModel.setImgUrl(dataobj.getString("img"));
                        categoryModelArrayList.add(categoryModel);

                    }

                    setupRecycler();


                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });


        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);


    }

    public void setupRecycler() {


        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.recyclerVievCategory);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setClickable(true);
        CategoryAdapter = new CategoryAdapter(this, categoryModelArrayList);
        recyclerView.setAdapter(CategoryAdapter);


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        final MenuItem menuItem = menu.findItem(R.id.action_cart);


        View actionView = MenuItemCompat.getActionView(menuItem);
        textCartItemCount = actionView.findViewById(R.id.cart_badge);
//        setupBadge();
        searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });


        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {


        textCartItemCount.setText(sessionManager.getUserInfo().get("cartCount"));


        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_category) {
            Intent i = new Intent(MainActivity.this, CategoryListActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_cart) {
            Intent i = new Intent(MainActivity.this, CartActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_setting) {
            Intent i = new Intent(MainActivity.this, UserSettingsActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_help) {
            Intent i = new Intent(MainActivity.this, SupportActivity.class);
            startActivity(i);


        } else if (id == R.id.nav_order) {
            Intent i = new Intent(MainActivity.this, UserOrderActivity.class);
            startActivity(i);


        } else if (id == R.id.nav_about) {
            aboutApp();
        } else if (id == R.id.nav_logout) {

            logoutAlert();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(int position) {
        Intent i = new Intent(MainActivity.this, ProductListActivity.class);
        String id = categoryModelArrayList.get(position).getId();
        i.putExtra("position", id);
        startActivity(i);
    }

    private void setupBadge() {

        if (textCartItemCount != null) {
            if (mCartItemCount == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void aboutApp() {
        LayoutInflater layoutInflater = getLayoutInflater();
        View alertLayout = layoutInflater.inflate(R.layout.layout_user_about_app, null);
        TextView title = alertLayout.findViewById(R.id.about_title);
//TODO add close dialog "zamknnij"
//TODO fix problem witch switch menu to dialog
        title.setText("O aplikacji");
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(alertLayout);

        AlertDialog alertDialog = alert.create();
        alertDialog.show();

    }

    public void logoutAlert() {
        AlertDialog.Builder logoutDialog = new AlertDialog.Builder(this);
        logoutDialog.setTitle("UWAGA!");
        logoutDialog.setIcon(R.drawable.ic_sad_face);
        logoutDialog.setMessage("Czy na pewno chcesz się wylogować?").setCancelable(false).setPositiveButton("TAK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sessionManager.logout();
                finish();

            }
        }).setNegativeButton("NIE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = logoutDialog.create();
        alertDialog.show();
    }
}

