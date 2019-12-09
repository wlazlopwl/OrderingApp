package com.example.pawel.myapp.User;

import android.content.Intent;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pawel.myapp.Adapter.Adapter;
import com.example.pawel.myapp.Const;
import com.example.pawel.myapp.Model.DataModel;
import com.example.pawel.myapp.ProductListActivity;
import com.example.pawel.myapp.R;
import com.example.pawel.myapp.RecyclerViewClickListener;
import com.example.pawel.myapp.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, RecyclerViewClickListener {

    ArrayList<DataModel> dataModelArrayList;
    private com.example.pawel.myapp.Adapter.Adapter Adapter;
    private RecyclerView recyclerView;
    SessionManager sessionManager;
    TextView textCartItemCount, mHour, mMinute, mSecond;
    int mCartItemCount = 1;
    int pHour, pMinute, pSecond;
    Boolean timeFromDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sessionManager = new SessionManager(getApplicationContext());


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        View headerView = navigationView.getHeaderView(0);
        TextView menuName = (TextView) headerView.findViewById(R.id.menu_name);

        menuName.setText(sessionManager.getUserInfo().get("login") + " " + sessionManager.getUserInfo().get("surname"));


        recyclerView = findViewById(R.id.recyclerVievCategory);

        getCategory();


        mHour = (TextView) findViewById(R.id.main_hour_textView);
        mMinute = (TextView) findViewById(R.id.main_minute_textView);
        mSecond = (TextView) findViewById(R.id.main_second_textView);
        timeFromDatabase=false;
//        getTime();
        timmer();











            CountDownTimer timer = new CountDownTimer(30000000, 1000) {


                @Override
                public void onTick(long millisUntilFinished) {

                    int aHour = Integer.parseInt(mHour.getText().toString());
                    int aMinute = Integer.parseInt(mMinute.getText().toString());
                    int aSecond = Integer.parseInt(mSecond.getText().toString());



                    if (aSecond <= 60) {
                        aSecond = aSecond - 1;


                        if (aSecond <0) {
                            aMinute = aMinute - 1;

                            aSecond = 59;

                        }
                        if (aMinute<0) {
                            aMinute=59;
                            aHour = aHour - 1;
                            if (aHour<=0) {
                                aHour=23;
                            }
                        }




                    }

                    if (aSecond<10){
                        mSecond.setText( "0"+String.valueOf(aSecond));
                    }
                    else{
                        mSecond.setText( String.valueOf(aSecond));
                    }
                    if (aMinute<10){
                        mMinute.setText( "0"+String.valueOf(aMinute));
                    }
                    else{
                        mMinute.setText( String.valueOf(aMinute));
                    }





                    mHour.setText( String.valueOf(aHour));





                }

                @Override
                public void onFinish() {

                }
            };

            timer.start();
        }





    public void timmer() {
        getTime();
        int dbMinute = Integer.valueOf(mMinute.getText().toString());
        int dbHour = Integer.valueOf(mHour.getText().toString());
        int dbSecond = Integer.valueOf(mSecond.getText().toString());

        Calendar calendar = Calendar.getInstance();
        //TODO check winter time / summer time
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+1"));
        int cHour = calendar.get(Calendar.HOUR_OF_DAY);
        int cMinute = calendar.get(Calendar.MINUTE);
        int cSecond = calendar.get(Calendar.SECOND);

        pHour = dbHour - cHour;
        pMinute = dbMinute - cMinute;
        pSecond = dbSecond - cSecond;
        mHour.setText(String.valueOf(pHour));
        mMinute.setText(String.valueOf(pMinute));
        mSecond.setText(String.valueOf(pSecond));

        Log.d("dbHour",""+dbHour);
        Log.d("cHour",""+cHour);
        Log.d("pHour",""+pHour);



    }


    public void getTime() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Const.URL_GET_TIME, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONArray jsonArray = new JSONArray(response);


                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);
                        String count = object.getString("hour").trim();
                        mHour.setText(count.substring(0,2));
                        mMinute.setText(count.substring(3,5));
                        mSecond.setText(count.substring(6,7));



                        Log.d("mHourget",""+mHour.getText().toString().trim());
                        Log.d("mMinuteget",""+mMinute);
                        Log.d("Msecget",""+mSecond);


                    }

                    timeFromDatabase=true;


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
                });

        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);

    }

    public void getCategory() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Const.URL_GET_CATEGORY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("strrrrr", ">>" + response);
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
                    e.printStackTrace();
                    DataModel playerModell = new DataModel();
                    playerModell.setId("1");
                    playerModell.setImgUrl("https://acegif.com/wp-content/gifs/apple-81-gap.jpg");
                    playerModell.setName("Test");
                    dataModelArrayList.add(playerModell);
                    setupRecycler();

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

        setupBadge();

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onOptionsItemSelected(menuItem);
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

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
            sessionManager.logout();
            this.finish();

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
}

