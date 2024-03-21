package com.example.eu_fstyle_mobile.src.model;

import com.google.gson.annotations.SerializedName;

public class ProductCart {
    @SerializedName("idProduct")
    private String idProduct;
    @SerializedName("name")
    private String name;
    @SerializedName("soLuong")
    private Number soLuong;
    @SerializedName("price")
    private Number price;
    @SerializedName("size")
    private String size;
    @SerializedName("imageDefault")
    private String imageDefault;

    public ProductCart(String idProduct, String name, Number soLuong, Number price, String size, String imageDefault) {
        this.idProduct = idProduct;
        this.name = name;
        this.soLuong = soLuong;
        this.price = price;
        this.size = size;
        this.imageDefault = imageDefault;
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

    public Number getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Number soLuong) {
        this.soLuong = soLuong;
    }

    public Number getPrice() {
        return price;
    }

    public void setPrice(Number price) {
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
}
