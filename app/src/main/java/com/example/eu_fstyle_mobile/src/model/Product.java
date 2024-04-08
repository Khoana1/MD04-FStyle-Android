package com.example.eu_fstyle_mobile.src.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Product implements Serializable {

    @SerializedName("_id")
    private String _id;
    @SerializedName("name")
    private String name;
    @SerializedName("image64")
    private String[] image64;
    @SerializedName("brand")
    private String brand;
    @SerializedName("price")
    private Number price;
    @SerializedName("size")
    private ArrayList<Integer> size;
    @SerializedName("color")
    private String color;
    @SerializedName("quantity")
    private String quantity;
    @SerializedName("status")
    private String status;
    @SerializedName("category")
    private String idCategory;
    @SerializedName("description")
    private String description;

    public Product(String _id, String name, String[] image64, String brand, Number price, ArrayList<Integer> size, String color, String quantity, String status, String idCategory, String description) {
        this._id = _id;
        this.name = name;
        this.image64 = image64;
        this.brand = brand;
        this.price = price;
        this.size = size;
        this.color = color;
        this.quantity = quantity;
        this.status = status;
        this.idCategory = idCategory;
        this.description = description;
    }


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

    public ArrayList<Integer> getSize() {
        return size;
    }

    public void setSize(ArrayList<Integer> size) {
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

    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
