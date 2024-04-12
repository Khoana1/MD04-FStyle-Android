package com.example.eu_fstyle_mobile.src.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListOrder {
    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String message;
    @SerializedName("orders")
    private List<Orders> orders;

    public ListOrder(String status, String message, List<Orders> orders) {
        this.status = status;
        this.message = message;
        this.orders = orders;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }
}