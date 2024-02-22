package com.example.eu_fstyle_mobile.src.model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("_id")
    private String _id;
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("tokenDevice")
    private String tokenDevice;
    @SerializedName("avatar")
    private String avatar;
    @SerializedName("phone")
    private String phone;
    @SerializedName("consigneeName")
    private String consigneeName;
    @SerializedName("isAdmin")
    private Boolean isAdmin;

    public User(String _id, String name, String email, String password, String tokenDevice, String avatar, String phone, String consigneeName, Boolean isAdmin) {
        this._id = _id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.tokenDevice = tokenDevice;
        this.avatar = avatar;
        this.phone = phone;
        this.consigneeName = consigneeName;
        this.isAdmin = isAdmin;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTokenDevice() {
        return tokenDevice;
    }

    public void setTokenDevice(String tokenDevice) {
        this.tokenDevice = tokenDevice;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }
}
