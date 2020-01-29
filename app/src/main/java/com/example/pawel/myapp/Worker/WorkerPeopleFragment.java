package com.example.pawel.myapp.Worker;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pawel.myapp.Adapter.PeopleListAdapter;
import com.example.pawel.myapp.Const;
import com.example.pawel.myapp.Model.UserModel;
import com.example.pawel.myapp.R;
import com.example.pawel.myapp.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WorkerPeopleFragment extends Fragment {
    private TextView mUserCount, mCountWorker, mTextEmptyUser;
    public ArrayList<UserModel> PeopleListForWorker;
    RecyclerView recyclerView;
    private com.example.pawel.myapp.Adapter.PeopleListAdapter peopleListAdapter;
    View view;
    public String userId;
    SessionManager sessionManager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_worker_people, container, false);

        mTextEmptyUser = (TextView) view.findViewById(R.id.textEmptyUser);

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sessionManager = new SessionManager(getContext());
        userId = sessionManager.getUserInfo().get("id");


        getUser();


    }


    private void getUser() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL_GET_USER_NAME, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    PeopleListForWorker = new ArrayList();


                    JSONArray jsonArray = new JSONArray(response);
                    if (jsonArray.length() == 0) {
                        mTextEmptyUser.setVisibility(View.VISIBLE);
                    } else {

                        for (int i = 0; i < jsonArray.length(); i++) {
                            UserModel user = new UserModel();
                            JSONObject object = jsonArray.getJSONObject(i);
                            user.setName(object.getString("name").trim());
                            user.setSurname(object.getString("surname").trim());
                            user.setId(object.getString("id").trim());


                            PeopleListForWorker.add(user);


                        }
                    }


                    recyclerView = (RecyclerView) view.findViewById(R.id.worker_people_list_rv);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));


                    recyclerView.setHasFixedSize(true);
                    recyclerView.setClickable(true);
                    peopleListAdapter = new PeopleListAdapter(PeopleListForWorker);
                    peopleListAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(peopleListAdapter);


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

                params.put("workerId", userId);


                return params;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        requestQueue.add(stringRequest);

    }


}