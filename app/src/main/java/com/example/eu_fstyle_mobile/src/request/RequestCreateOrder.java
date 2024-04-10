package com.example.eu_fstyle_mobile.src.request;

import com.example.eu_fstyle_mobile.src.model.ProductCart;

import java.util.List;

public class RequestCreateOrder {
    private String address;
    private List<ProductCart> listProduct;
    private String idUser;
    private String phone;
    private String paymentMethods;
    private String totalPrice;
    private String status;

    public RequestCreateOrder(String address, List<ProductCart> listProduct, String idUser, String phone, String paymentMethods, String totalPrice, String status) {
        this.address = address;
        this.listProduct = listProduct;
        this.idUser = idUser;
        this.phone = phone;
        this.paymentMethods = paymentMethods;
        this.totalPrice = totalPrice;
        this.status = status;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<ProductCart> getListProduct() {
        return listProduct;
    }

    public void setListProduct(List<ProductCart> listProduct) {
        this.listProduct = listProduct;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(String paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
