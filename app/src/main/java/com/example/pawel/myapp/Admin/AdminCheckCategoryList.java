package com.example.pawel.myapp.Admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ListView;
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

public class AdminCheckCategoryList extends AppCompatActivity {
        private ListView mCategoryListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_check_category_list);

        mCategoryListView=(ListView)findViewById(R.id.admin_check_category_listView);





    }

    private void getAllCategoryName(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Const.URL_GET_CATEGORY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {

                    String[] category = new String[5];

                    JSONArray jsonArray = new JSONArray(response);


                    for (int i = 0; i < jsonArray.length(); i++) {
                        UserModel user = new UserModel();
                        JSONObject object = jsonArray.getJSONObject(i);
                        user.setName(object.getString("name").trim());
                        user.setSurname(object.getString("surname").trim());
                        user.setId(object.getString("id").trim());


//

//                        AllUserList.add(user);


                    }
//

//                    mAllUserListRV.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
//
//
//                    mAllUserListRV.setHasFixedSize(true);
//                    mAllUserListRV.setClickable(true);
//                    allUserListAdapter = new AllUserListAdapter(AllUserList);
//                    allUserListAdapter.notifyDataSetChanged();
//                    mAllUserListRV.setAdapter(allUserListAdapter);



                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);


    }
}
