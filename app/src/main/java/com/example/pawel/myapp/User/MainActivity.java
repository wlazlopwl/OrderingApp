package com.example.pawel.myapp.User;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.example.pawel.myapp.Adapter.Adapter;
import com.example.pawel.myapp.Const;
import com.example.pawel.myapp.Model.DataModel;
import com.example.pawel.myapp.R;
import com.example.pawel.myapp.RecyclerViewClickListener;
import com.example.pawel.myapp.SessionManager;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, RecyclerViewClickListener {


    public static ArrayList<DataModel> dataModelArrayList;
    private com.example.pawel.myapp.Adapter.Adapter Adapter;
    private RecyclerView recyclerView;
    SessionManager sessionManager;
    public TextView textCartItemCount, mHour, mMinute, mActualOrder, mMyWorkerName, mMonthOrder;
    public int mCartItemCount = 0;
    int pHour, pMinute, pSecond;
    private Button mCategoryBtn;
    public String userId;
    LineChart mChart;

    public android.app.SearchManager searchManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.support_toolbar);

        setSupportActionBar(toolbar);
        sessionManager = new SessionManager(getApplicationContext());
        mCategoryBtn = (Button) findViewById(R.id.category_btn);
        mActualOrder = (TextView) findViewById(R.id.user_home_actual_order);
        mMyWorkerName = (TextView) findViewById(R.id.user_home_my_worker_name);
        mMonthOrder = (TextView) findViewById(R.id.user_home_all_month_order);
        mChart = (LineChart) findViewById(R.id.user_home_statistic);
        mChart.setTouchEnabled(true);
        mChart.setPinchZoom(true);

        ArrayList<Entry> values = new ArrayList<>();


        for (int i = 0; i <30 ; i++) {
            int a;
            if (i%2==0) {
                a=5;
            }
            else{
                a=2;
            }
            values.add(new Entry(i+1, a));
        }
        LineDataSet set1;
        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(values, "Sample Data");
            set1.setDrawIcons(false);
            set1.enableDashedLine(10f, 5f, 0f);
            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(Color.DKGRAY);
            set1.setCircleColor(Color.DKGRAY);
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
            set1.setDrawFilled(true);
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);
            if (Utils.getSDKInt() >= 18) {
                Drawable drawable = ContextCompat.getDrawable(this, R.color.colorAccent);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.DKGRAY);
            }
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            LineData data = new LineData(dataSets);
            mChart.setData(data);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        View headerView = navigationView.getHeaderView(0);
        TextView menuName = (TextView) headerView.findViewById(R.id.menu_name);
        TextView menuEmail = (TextView) headerView.findViewById(R.id.menu_email);

        menuName.setText(sessionManager.getUserInfo().get("login") + " " + sessionManager.getUserInfo().get("surname"));
        menuEmail.setText(sessionManager.getUserInfo().get("password"));
        userId = sessionManager.getUserInfo().get("id");

        recyclerView = findViewById(R.id.recyclerVievCategory);

        getCategory();
        getUserData();


        mHour = (TextView) findViewById(R.id.main_hour_textView);
        mMinute = (TextView) findViewById(R.id.main_minute_textView);
        getActualTime();


        mCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CategoryListActivity.class);
                startActivity(intent);
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
                        mMonthOrder.setText(obj.getString("countLastMonthOrder"));
//                        String cartCount = "10";
//                        mCartItemCount = Integer.parseInt(cartCount);
//                        mCartItemCount=5;
                        String count =obj.getString("countCart");
                        if (count=="null") {
                            textCartItemCount.setText("0");
                        }
                        sessionManager.updateCartCountForUser(textCartItemCount.getText().toString());
                        Log.d("countCart", response);


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
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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


                    dataModelArrayList = new ArrayList<>();
                    JSONArray dataArray = new JSONArray(response);

                    for (int i = 0; i < dataArray.length(); i++) {

                        DataModel playerModel = new DataModel();
                        JSONObject dataobj = dataArray.getJSONObject(i);
                        playerModel.setId(dataobj.getString("id"));
                        playerModel.setName(dataobj.getString("name"));
                        playerModel.setImgUrl(dataobj.getString("img"));


                        dataModelArrayList.add(playerModel);

                    }

                    setupRecycler();


                } catch (JSONException e) {


                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // request queue

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);


    }

    public void setupRecycler() {


        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.recyclerVievCategory);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setClickable(true);
        Adapter = new Adapter(this, dataModelArrayList);
        recyclerView.setAdapter(Adapter);


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(int position) {
        Intent i = new Intent(MainActivity.this, ProductListActivity.class);
        String id = dataModelArrayList.get(position).getId();
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

