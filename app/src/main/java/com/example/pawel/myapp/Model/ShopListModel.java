package com.example.pawel.myapp.Model;


public class ShopListModel {

    String name;
    String quantity;
    String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public ShopListModel() {
        this.name = name;
        this.quantity = quantity;
        this.description=description;
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
        return quantity +" x  "+ name+" "+description;
    }
}
