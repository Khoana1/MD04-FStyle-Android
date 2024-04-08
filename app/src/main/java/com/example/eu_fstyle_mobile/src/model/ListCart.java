package com.example.eu_fstyle_mobile.src.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListCart {
    @SerializedName("cart")
    private List<Cart> listCart;

    public ListCart(List<Cart> listCart) {
        this.listCart = listCart;
    }

    public List<Cart> getListCart() {
        return listCart;
    }

    public void setListCart(List<Cart> listCart) {
        this.listCart = listCart;
    }
}
