package com.example.eu_fstyle_mobile.src.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListOrderID  {
    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String message;
    @SerializedName("order")
    private Orders order;

    public ListOrderID(String status, String message, Orders order) {
        this.status = status;
        this.message = message;
        this.order = order;
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

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }
}
