package com.example.eu_fstyle_mobile.src.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddressRespone {
    @SerializedName("list_address")
    private List<Address> listAddress;

    public AddressRespone(List<Address> listAddress) {
        this.listAddress = listAddress;
    }

    public List<Address> getListAddress() {
        return listAddress;
    }

    public void setListAddress(List<Address> listAddress) {
        this.listAddress = listAddress;
    }
}
