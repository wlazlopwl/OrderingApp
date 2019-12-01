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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WorkerPeopleFragment extends Fragment {
    private TextView mUserCount, mCountWorker;
    public ArrayList<UserModel> PeopleListForWorker;
    RecyclerView recyclerView;
    private com.example.pawel.myapp.Adapter.PeopleListAdapter peopleListAdapter;
    View view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_worker_people, container, false);


        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        getUser();


    }


    public void getUser() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Const.URL_GET_WORKER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {

                    PeopleListForWorker = new ArrayList();


                    JSONArray jsonArray = new JSONArray(response);


                    for (int i = 0; i < jsonArray.length(); i++) {
                        UserModel user = new UserModel();
                        JSONObject object = jsonArray.getJSONObject(i);
                        user.setName(object.getString("name").trim());
                        user.setSurname(object.getString("surname").trim());
                        user.setId(object.getString("id").trim());


//

                        PeopleListForWorker.add(user);


                    }
//


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
                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        requestQueue.add(stringRequest);


    }


}