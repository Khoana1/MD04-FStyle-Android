package com.example.eu_fstyle_mobile.src.model;

import com.google.gson.annotations.SerializedName;

public class ProductOrder {
    @SerializedName("idProduct")
    private String idProduct;
    @SerializedName("name")
    private String name;
    @SerializedName("quantity")
    private Number quantity;
    @SerializedName("price")
    private Number price;
    @SerializedName("size")
    private String size;
    @SerializedName("imageDefault")
    private String imageDefault;
    @SerializedName("_id")
    private String _id;

    public ProductOrder(String idProduct, String name, Number quantity, Number price, String size, String imageDefault, String _id) {
        this.idProduct = idProduct;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.size = size;
        this.imageDefault = imageDefault;
        this._id = _id;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getImageDefault() {
        return imageDefault;
    }

    public void setImageDefault(String imageDefault) {
        this.imageDefault = imageDefault;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Number getQuantity() {
        return quantity;
    }

    public Number getPrice() {
        return price;
    }
}

