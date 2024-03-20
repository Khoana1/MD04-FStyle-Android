package com.example.eu_fstyle_mobile.src.model;

import com.google.gson.annotations.SerializedName;

public class ProductFavourite {
    @SerializedName("name")
    private String name;
    @SerializedName("quantity")
    private String quantity;
    @SerializedName("price")
    private Number price;
    @SerializedName("defaultImage")
    private String defaultImage;
    @SerializedName("_id")
    private String _id;

    public ProductFavourite(String name, String quantity, Number price, String defaultImage, String _id) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.defaultImage = defaultImage;
        this._id = _id;
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

    public Number getPrice() {
        return price;
    }

    public void setPrice(Number price) {
        this.price = price;
    }

    public String getDefaultImage() {
        return defaultImage;
    }

    public void setDefaultImage(String defaultImage) {
        this.defaultImage = defaultImage;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

}
