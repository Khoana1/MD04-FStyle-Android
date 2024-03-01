package com.example.eu_fstyle_mobile.src.request;

public class RequestLoginUser {
    private String email;
    private String password;
    private String tokenDevice;

    public RequestLoginUser(String email, String password, String tokenDevice) {
        this.email = email;
        this.password = password;
        this.tokenDevice = tokenDevice;
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
}
