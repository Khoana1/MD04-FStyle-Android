package com.example.eu_fstyle_mobile.src.request;

public class RequestUpdateStatus {
    private String status;

    public RequestUpdateStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}