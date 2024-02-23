package com.example.eu_fstyle_mobile.src.model;

import com.google.gson.annotations.SerializedName;

public class Address {
    @SerializedName("idUser")
    private String idUser;
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

    public Address(String idUser, String consigneeName, String homeNumber, String stress, String district, String city, String phoneNumber) {
        this.idUser = idUser;
        this.consigneeName = consigneeName;
        this.homeNumber = homeNumber;
        this.stress = stress;
        this.district = district;
        this.city = city;
        this.phoneNumber = phoneNumber;
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

    public String getStress() {
        return stress;
    }

    public void setStress(String stress) {
        this.stress = stress;
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
