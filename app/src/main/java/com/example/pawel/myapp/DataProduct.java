package com.example.pawel.myapp;

import java.util.ArrayList;

class DataProduct {
    private String id, name, description, imgUrl, categoryId,quantityTypeId, quantity;
    private ArrayList<DataProduct> dataProductList;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getQuantityTypeId() {
        return quantityTypeId;
    }

    public void setQuantityTypeId(String quantityTypeId) {
        this.quantityTypeId = quantityTypeId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
    public ArrayList<DataProduct> getDataProductList() {
        return dataProductList;
    }

    public void setDataProductList(ArrayList<DataProduct> dataProductList) {
        this.dataProductList = dataProductList;
    }
}
