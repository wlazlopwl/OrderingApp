package com.example.pawel.myapp.Worker;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.pawel.myapp.Const;
import com.example.pawel.myapp.R;
import com.example.pawel.myapp.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class WorkerHomeFragment extends Fragment {

    private TextView mActualOrderTime, mCountUser, mCountActualOrder;
    private Button mGoToOrderBtn;
    View view;
    SessionManager sessionManager;
    public String userId;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_worker_home, container, false);

        mActualOrderTime = (TextView) view.findViewById(R.id.actual_time_order_admin);
        mCountUser = (TextView) view.findViewById(R.id.worker_home_count_user);
        mCountActualOrder=(TextView) view.findViewById(R.id.worker_home_count_actual_order);
        mGoToOrderBtn=(Button) view.findViewById(R.id.worker_home_to_order_btn);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sessionManager = new SessionManager(getContext());
        userId = sessionManager.getUserInfo().get("id");

        getActualTime();
        getWorkerHomeData();

        mGoToOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkerEditFragment workerEditFragment = new WorkerEditFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.worker_fragment_container, workerEditFragment).commit();
            }
        });









    }

    public void getActualTime(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Const.URL_GET_TIME, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONArray jsonArray  = new JSONArray(response);




                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object=jsonArray.getJSONObject(i);
                        String count = object.getString("hour").trim();
                        String hourFromDatabase = count.substring(0,2);
                        String minuteFromDatabase = count.substring(3,5);

                        mActualOrderTime.setText(hourFromDatabase+":"+minuteFromDatabase);







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
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        requestQueue.add(stringRequest);


    }
    private void getWorkerHomeData(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL_GET_WORKER_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("aa", response);

                try {


                    JSONArray dataArray = new JSONArray(response);

                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject obj = dataArray.getJSONObject(i);

                        mCountUser.setText(obj.getString("countUser"));
                        mCountActualOrder.setText(obj.getString("countActualOrder"));
                        Log.d("cu", obj.getString("countUser"));
                        Log.d("cau", obj.getString("countActualOrder"));




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
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("id", userId);


                return params;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        requestQueue.add(stringRequest);


    }


}