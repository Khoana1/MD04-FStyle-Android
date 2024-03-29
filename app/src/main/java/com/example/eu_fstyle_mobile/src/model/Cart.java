package com.example.eu_fstyle_mobile.src.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Cart {
    @SerializedName("_id")
    private String _id;
    @SerializedName("idUser")
    private String idUser;
    @SerializedName("listProduct")
    private List<ProductCart> listProduct;
    @SerializedName("__v")
    private String __v;
    @SerializedName("totalCart")
    private Number totalCart;

    public Cart(String _id, String idUser, List<ProductCart> listProduct, String __v, Number totalCart) {
        this._id = _id;
        this.idUser = idUser;
        this.listProduct = listProduct;
        this.__v = __v;
        this.totalCart = totalCart;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public List<ProductCart> getListProduct() {
        return listProduct;
    }

    public void setListProduct(List<ProductCart> listProduct) {
        this.listProduct = listProduct;
    }

    public String get__v() {
        return __v;
    }

    public void set__v(String __v) {
        this.__v = __v;
    }

    public Number getTotalCart() {
        return totalCart;
    }

    public void setTotalCart(Number totalCart) {
        this.totalCart = totalCart;
    }
}
