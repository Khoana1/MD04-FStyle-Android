package com.example.eu_fstyle_mobile.src.model;

import com.airbnb.lottie.L;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Address implements Serializable {
    @SerializedName("_id")
    private String _id;
    @SerializedName("idUser")
    private String idUser;
    @SerializedName("consigneeName")
    private String consigneeName;
    @SerializedName("homeNumber")
    private String homeNumber;
    @SerializedName("street")
    private String street;
    @SerializedName("district")
    private String district;
    @SerializedName("city")
    private String city;
    @SerializedName("phoneNumber")
    private String phoneNumber;
    @SerializedName("__v")
    private int __v;
    public Address(String _id,String idUser, String consigneeName, String homeNumber, String street, String district, String city, String phoneNumber) {
        this._id= _id;
        this.idUser = idUser;
        this.consigneeName = consigneeName;
        this.homeNumber = homeNumber;
        this.street = street;
        this.district = district;
        this.city = city;
        this.phoneNumber = phoneNumber;
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

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    public String getHomeNumber() {
        return homeNumber;
    }

    public void setHomeNumber(String homeNumber) {
        this.homeNumber = homeNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
