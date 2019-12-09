package com.example.pawel.myapp.Worker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pawel.myapp.Const;
import com.example.pawel.myapp.Model.ShopListModel;
import com.example.pawel.myapp.R;
import com.example.pawel.myapp.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class WorkerEditTab2Fragment extends Fragment {
    private ImageView mOpenDatePicker, mShareRaport;
    TextView mDate, mEmptyListInfo;
    Calendar c;
    DatePickerDialog datePickerDialog;
    SessionManager sessionManager;
    String user_id;
    ListView mShopListListView;
    String dateFromPicker;
    ArrayList<ShopListModel> shopList;
    CardView mListContent;
    ArrayAdapter<ShopListModel> adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_worker_edit_tab2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mOpenDatePicker = (ImageView) view.findViewById(R.id.calennar_raport);
        mDate = (TextView) view.findViewById(R.id.worker_edit_tab2_date);
        sessionManager = new SessionManager(getActivity());
        mShopListListView = (ListView) view.findViewById(R.id.raport_shop_list);
        mEmptyListInfo = (TextView) view.findViewById(R.id.raport_empty_list_inf);
        mShareRaport = (ImageView) view.findViewById(R.id.raport_shareIV);
        mListContent = (CardView) view.findViewById(R.id.raport_shop_list_content);
        shopList = new ArrayList<>();
        adapter = new ArrayAdapter<ShopListModel>(getContext(), android.R.layout.simple_list_item_1, shopList);
        mListContent.setVisibility(View.INVISIBLE);
        user_id = sessionManager.getUserInfo().get("id");
// getRaport();
        mOpenDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);

                int month = c.get(Calendar.MONTH);

                int year = c.get(Calendar.YEAR);
                dateFromPicker = year + "-" + month + "-" + day;
                Log.d("d1", dateFromPicker);


                datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mDayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        mMonth = mMonth + 1;

                        Log.d("month", "" + mMonth);

                        calendar.set(mYear, mMonth, mDayOfMonth);
                        mDate.setText(mDayOfMonth + "." + mMonth + "." + mYear);
                        Log.d("d2", mDate.getText().toString());
                        dateFromPicker = mYear + "-" + mMonth + "-" + mDayOfMonth;
                        int actualDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                        Log.d("d3", "" + actualDayOfWeek);
                        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {


                            mEmptyListInfo.setText("Wybrany dzień jest niedzielą. Brak raportu.");

                            shopList.clear();
                            adapter.notifyDataSetChanged();


                        } else {
                            getRaport();
                        }
                        mListContent.setVisibility(View.VISIBLE);

                    }
                }, year, month, day);
                datePickerDialog.show();

            }
        });

        mShareRaport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareRaport = new Intent();
                shareRaport.setAction(Intent.ACTION_SEND);
                shareRaport.setType("text/plain");
//TODO: usuń share icon and method

                Intent share = Intent.createChooser(shareRaport, null);
                startActivity(share);
            }
        });

    }


    private void getRaport() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL_GET_RAPORT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("Brak")) {
                    mShopListListView.setVisibility(View.GONE);
                    mShareRaport.setVisibility(View.INVISIBLE);
                    mEmptyListInfo.setText("Brak raportu w wybranym dniu");
                } else {
                    mEmptyListInfo.setText("Lista zakupów w tym dniu:");
                    shopList.clear();

                    try {
                        mShareRaport.setVisibility(View.VISIBLE);

                        Log.d("e", "" + response);
                        JSONArray dataArray = new JSONArray(response);
                        for (int i = 0; i < dataArray.length(); i++) {
                            ShopListModel item = new ShopListModel();
                            JSONObject jsonObject = dataArray.getJSONObject(i);

                            item.setName(jsonObject.getString("name").trim());
                            item.setQuantity(jsonObject.getString("q").trim());

                            shopList.add(item);


                        }
                        mShopListListView.setVisibility(View.VISIBLE);

                        adapter.notifyDataSetChanged();
                        mShopListListView.setAdapter(adapter);


                    } catch (JSONException e) {
                        e.printStackTrace();


                    }
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

                params.put("user_id", user_id);
                params.put("date", dateFromPicker);

                return params;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        requestQueue.add(stringRequest);


    }
}
