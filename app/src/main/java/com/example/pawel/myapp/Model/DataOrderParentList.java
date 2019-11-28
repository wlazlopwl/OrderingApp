package com.example.pawel.myapp.Model;

import com.example.pawel.myapp.Model.DataProduct;

import java.util.ArrayList;

public class DataOrderParentList {

    private String id, name, date;
    private ArrayList<DataProduct> ChildOrderList;

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

    public ArrayList<DataProduct> getDataProductChildList() {
        return ChildOrderList;
    }

    public void setDataProductList(ArrayList<DataProduct> ChildOrderList) {
        this.ChildOrderList = ChildOrderList;
    }
}
