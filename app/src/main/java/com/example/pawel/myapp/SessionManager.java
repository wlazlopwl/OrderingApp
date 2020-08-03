package com.example.pawel.myapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.pawel.myapp.Admin.AdminActivity;
import com.example.pawel.myapp.User.MainActivity;
import com.example.pawel.myapp.Worker.WorkerActivity;

import java.util.HashMap;

public class SessionManager {
    private static final String PREF_NAME = "LOGIN";
    private static final String LOGIN = "IS_LOGIN";
    private static final String NAME = "NAME";
    private static final String EMAIL = "EMAIL";
    private static final String VALUE = "VALUE";
    private static final String SURNAME = "SURNAME";
    private static final String STREET = "STREET";
    private static final String CITY = "CITY";
    private static final String POSTCODE = "POSTCODE";
    private static final String PHONE = "PHONE";
    private static final String ID_USER = "ID_USER";
    private static final String COUNT_CART_USER = "COUNT_CART_USER";
    public SharedPreferences.Editor editor;
    public Context context;
    SharedPreferences sharedPreferences;


    public SessionManager(Context context) {
        this.context = context;

        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }

    public void createSession(String name, String email, String value, String surname, String street,
                              String city, String postcode, String phone, String id) {
        editor.putBoolean(LOGIN, true);
        editor.putString(NAME, name);
        editor.putString(EMAIL, email);
        editor.putString(VALUE, value);
        editor.putString(SURNAME, surname);
        editor.putString(STREET, street);
        editor.putString(CITY, city);
        editor.putString(POSTCODE, postcode);
        editor.putString(PHONE, phone);
        editor.putString(ID_USER, id);

        editor.commit();
    }


    public boolean isLoginAccount() {
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    public void checkLoginAccount() {
        if (!this.isLoginAccount()) {
            Intent intent = new Intent(context, LogInActivity.class);
            context.startActivity(intent);
        } else {
            int userValue = Integer.parseInt(getUserInfo().get("value")) - 1;

            if (userValue == 0) {
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }
            if (userValue == 1) {
                Intent intent = new Intent(context, AdminActivity.class);
                context.startActivity(intent);
            }
            if (userValue == 2) {
                Intent intent = new Intent(context, WorkerActivity.class);
                context.startActivity(intent);
            }


        }
    }

    public HashMap<String, String> getUserInfo() {
        HashMap<String, String> user = new HashMap<>();
        user.put("name", sharedPreferences.getString(NAME, null));
        user.put("password", sharedPreferences.getString(EMAIL, null));
        user.put("value", sharedPreferences.getString(VALUE, null));
        user.put("surname", sharedPreferences.getString(SURNAME, null));
        user.put("street", sharedPreferences.getString(STREET, null));
        user.put("city", sharedPreferences.getString(CITY, null));
        user.put("postcode", sharedPreferences.getString(POSTCODE, null));
        user.put("phone", sharedPreferences.getString(PHONE, null));
        user.put("id", sharedPreferences.getString(ID_USER, null));
        user.put("cartCount", sharedPreferences.getString(COUNT_CART_USER, "0"));


        return user;
    }


    public void updateDataInSession(String name, String surname, String street, String city, String postcode, String phone) {

        editor.putString(NAME, name);
        editor.putString(SURNAME, surname);
        editor.putString(STREET, street);
        editor.putString(CITY, city);
        editor.putString(POSTCODE, postcode);
        editor.putString(PHONE, phone);
        editor.commit();
    }

    public void updateEmailInSession(String email) {

        editor.putString(EMAIL, email);

        editor.commit();
    }

    public void updateCartCountForUser(String cartCount) {
        editor.putString(COUNT_CART_USER, cartCount);
        editor.commit();
    }


    public void logout() {
        editor.clear();
        editor.commit();
        Intent intent = new Intent(context, LogInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);


    }


}
