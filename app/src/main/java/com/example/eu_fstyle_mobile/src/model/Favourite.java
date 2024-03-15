package com.example.eu_fstyle_mobile.src.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Favourite {
    @SerializedName("_id")
    private String _id;

    @SerializedName("listProduct")
    private List<ProductFavourite> listProduct;

    @SerializedName("__v")
    private String __v;

    public Favourite(String _id, List<ProductFavourite> listProduct, String __v) {
        this._id = _id;
        this.listProduct = listProduct;
        this.__v = __v;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<ProductFavourite> getListProduct() {
        return listProduct;
    }

    public void setListProduct(List<ProductFavourite> listProduct) {
        this.listProduct = listProduct;
    }

    public String get__v() {
        return __v;
    }

    public void set__v(String __v) {
        this.__v = __v;
    }
}
