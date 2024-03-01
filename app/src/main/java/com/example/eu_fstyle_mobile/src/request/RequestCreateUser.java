package com.example.eu_fstyle_mobile.src.request;

public class RequestCreateUser {
    private String avatar;
    private String name;
    private String email;
    private String password;
    private String phone;

    public RequestCreateUser(String avatar, String name, String email, String password, String phone) {
        this.avatar = avatar;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
