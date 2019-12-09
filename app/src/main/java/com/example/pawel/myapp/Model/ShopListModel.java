package com.example.pawel.myapp.Model;

import android.support.annotation.NonNull;

public class ShopListModel {

    String name, quantity;

    public ShopListModel() {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }


    @Override
    public String toString() {
        return quantity +" x  "+ name;
    }
}
