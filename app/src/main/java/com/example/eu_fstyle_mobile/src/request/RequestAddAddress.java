package com.example.eu_fstyle_mobile.src.request;

public class RequestAddAddress {
    private String homeNumber;
    private String street;
    private String district;
    private String city;
    private String phoneNumber;
    private String consigneeName;

    public RequestAddAddress(String homeNumber, String street, String district, String city, String phoneNumber, String consigneeName) {
        this.homeNumber = homeNumber;
        this.street = street;
        this.district = district;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.consigneeName = consigneeName;
    }

    public String getHomeNumber() {
        return homeNumber;
    }

    public void setHomeNumber(String homeNumber) {
        this.homeNumber = homeNumber;
    }

    public String getstreet() {
        return street;
    }

    public void setstreet(String street) {
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

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }
}
