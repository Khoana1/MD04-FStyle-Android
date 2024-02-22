package com.example.eu_fstyle_mobile.src.model;

import com.google.gson.annotations.SerializedName;

public class Address {
    @SerializedName("idUser")
    private String _idUser;
    @SerializedName("consigneeName")
    private String consigneeName;
    @SerializedName("homeNumber")
    private String homeNumber;
    @SerializedName("stress")
    private String stress;
    @SerializedName("district")
    private String district;
    @SerializedName("city")
    private String city;
    @SerializedName("phoneNumber")
    private String phoneNumber;
}
