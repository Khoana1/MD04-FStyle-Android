package com.example.eu_fstyle_mobile.src.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ListCategories {
    @SerializedName("status")
    int status;
    @SerializedName("categories")
    ArrayList<Categories> arrayList;

    public int getStatus() {
        return status;
    }

    public ArrayList<Categories> getArrayList() {
        return arrayList;
    }
}
