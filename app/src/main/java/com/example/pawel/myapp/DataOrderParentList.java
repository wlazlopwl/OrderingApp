package com.example.pawel.myapp;

import java.util.ArrayList;

public class DataOrderParentList {

    private String id, name, date;
    private ArrayList<DataOrderParentList> dataProductList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<DataOrderParentList> getDataProductList() {
        return dataProductList;
    }

    public void setDataProductList(ArrayList<DataOrderParentList> dataProductList) {
        this.dataProductList = dataProductList;
    }
}
