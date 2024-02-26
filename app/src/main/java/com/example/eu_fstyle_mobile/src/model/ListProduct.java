package com.example.eu_fstyle_mobile.src.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ListProduct {
    @SerializedName("status")
    int status;
    @SerializedName("products")
    ArrayList<Product> arrayList;

    public int getStatus() {
        return status;
    }

    public ArrayList<Product> getArrayList() {
        return arrayList;
    }
}
