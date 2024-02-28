package com.example.eu_fstyle_mobile.src.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Product implements Serializable {
    @SerializedName("name")
    private String name;
    @SerializedName("image64")
    private String[] image64;
    @SerializedName("brand")
    private String brand;
    @SerializedName("price")
    private Number price;
    @SerializedName("size")
    private Number size;
    @SerializedName("color")
    private String color;
    @SerializedName("quantity")
    private String quantity;
    @SerializedName("status")
    private String status;
    @SerializedName("type")
    private String type;
    @SerializedName("description")
    private String description;

    public Product(String name, String[] image64, String brand, Number price, Number size, String color, String quantity, String status, String type, String description) {
        this.name = name;
        this.image64 = image64;
        this.brand = brand;
        this.price = price;
        this.size = size;
        this.color = color;
        this.quantity = quantity;
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

    public String[] getImage64() {
        return image64;
    }

    public void setImage64(String[] image64) {
        this.image64 = image64;
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
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