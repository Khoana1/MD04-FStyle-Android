package com.example.eu_fstyle_mobile.src.model;

import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("image")
    private String image;
    @SerializedName("name")
    private String name;
    @SerializedName("brand")
    private String brand;
    @SerializedName("price")
    private Number price;
    @SerializedName("size")
    private Number size;
    @SerializedName("color")
    private String color;
    @SerializedName("status")
    private String status;
    @SerializedName("type")
    private String type;
    @SerializedName("description")
    private String description;

    public Product(String name, String brand, Number price, Number size, String color, String status, String type, String description) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.size = size;
        this.color = color;
        this.status = status;
        this.type = type;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Number getPrice() {
        return price;
    }

    public void setPrice(Number price) {
        this.price = price;
    }

    public Number getSize() {
        return size;
    }

    public void setSize(Number size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}