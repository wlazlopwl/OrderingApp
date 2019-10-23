package com.example.pawel.myapp;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;

    public SessionManager(Context context){
        this.context=context;
        sharedPreferences=context.getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();

    }

    public void createSession(String login, String password, String value){
        editor.putBoolean("LOGIN", true);
        editor.putString("login", login);
        editor.putString("password", password);
        editor.putString("value", value);
        editor.apply();
    }


}
