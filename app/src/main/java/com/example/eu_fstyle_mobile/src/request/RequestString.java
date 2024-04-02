package com.example.eu_fstyle_mobile.src.request;

import com.google.gson.annotations.SerializedName;

public class RequestString {
    @SerializedName("message")
    String mesage;

    public String getMesage() {
        return mesage;
    }
}
