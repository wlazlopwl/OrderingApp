package com.example.pawel.myapp;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {

    private static VolleySingleton instance;
    private RequestQueue requestQueue;
    private static Context context;

    private VolleySingleton(Context context) {
        this.context = context;
        requestQueue = getRequestQueue();


    }

    private RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized VolleySingleton getInstance(Context context) {
        // If Instance is null then initialize new Instance
        if (instance == null) {
            instance = new VolleySingleton(context);
        }
        // Return MySingleton new Instance
        return instance;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        // Add the specified request to the request queue
        getRequestQueue().add(request);
    }
}
