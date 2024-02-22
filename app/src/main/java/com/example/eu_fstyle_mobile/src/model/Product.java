package com.example.eu_fstyle_mobile.src.model;

import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("name")
    private String name;
    @SerializedName("brand")
    private String brand;
    @SerializedName("price")
    private String price;
    @SerializedName("size")
    private String size;
    @SerializedName("color")
    private String color;
    @SerializedName("status")
    private String status;
    @SerializedName("type")
    private String type;
    @SerializedName("description")
    private String description;
}
