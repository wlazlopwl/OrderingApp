package com.example.pawel.myapp.Admin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pawel.myapp.Adapter.AllUserListAdapter;
import com.example.pawel.myapp.Const;
import com.example.pawel.myapp.Model.UserModel;
import com.example.pawel.myapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdminUserList extends AppCompatActivity {

    private RecyclerView mAllUserListRV;
    public static ArrayList AllUserList;
    public static AllUserListAdapter allUserListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_list);

        mAllUserListRV = findViewById(R.id.all_user_list_RV);

        getAllUser();


    }

    public void getAllUser() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Const.URL_GET_ALL_USER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {

                    AllUserList = new ArrayList();


                    JSONArray jsonArray = new JSONArray(response);


                    for (int i = 0; i < jsonArray.length(); i++) {
                        UserModel user = new UserModel();
                        JSONObject object = jsonArray.getJSONObject(i);
                        user.setName(object.getString("name").trim());
                        user.setSurname(object.getString("surname").trim());
                        user.setId(object.getString("id").trim());


//

                        AllUserList.add(user);


                    }
//

                    mAllUserListRV.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));


                    mAllUserListRV.setHasFixedSize(true);
                    mAllUserListRV.setClickable(true);
                    allUserListAdapter = new AllUserListAdapter(AllUserList);
                    allUserListAdapter.notifyDataSetChanged();
                    mAllUserListRV.setAdapter(allUserListAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AdminUserList.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(AdminUserList.this);

        requestQueue.add(stringRequest);


    }
}
