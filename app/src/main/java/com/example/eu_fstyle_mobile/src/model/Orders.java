package com.example.eu_fstyle_mobile.src.model;

import com.google.gson.annotations.SerializedName;

public class Orders {
    @SerializedName("address")
    private String address;
    @SerializedName("listProduct")
    private ProductCart listProduct;
    @SerializedName("idUser")
    private String idUser;
    @SerializedName("quantity")
    private Number quantity;
    @SerializedName("totalPrice")
    private Number totalPrice;
    @SerializedName("phone")
    private String phone;
    @SerializedName("paymentMethods")
    private String paymentMethods;
    @SerializedName("timeOrder")
    private String timeOrder;
    @SerializedName("timeDelivery")
    private String timeDelivery;
    @SerializedName("timeCancel")
    private String timeCancel;
    @SerializedName("thoiGianDangGiao")
    private String thoiGianDangGiao;
    @SerializedName("status")
    private String status;

    public Orders(String address, ProductCart listProduct, String idUser, Number quantity, Number totalPrice, String phone, String paymentMethods, String timeOrder, String timeDelivery, String timeCancel, String thoiGianDangGiao, String status) {
        this.address = address;
        this.listProduct = listProduct;
        this.idUser = idUser;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.phone = phone;
        this.paymentMethods = paymentMethods;
        this.timeOrder = timeOrder;
        this.timeDelivery = timeDelivery;
        this.timeCancel = timeCancel;
        this.thoiGianDangGiao = thoiGianDangGiao;
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public ProductCart getListProduct() {
        return listProduct;
    }

    public void setListProduct(ProductCart listProduct) {
        this.listProduct = listProduct;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public Number getQuantity() {
        return quantity;
    }

    public void setQuantity(Number quantity) {
        this.quantity = quantity;
    }

    public Number getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Number totalPrice) {
        this.totalPrice = totalPrice;
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

    public String getTimeOrder() {
        return timeOrder;
    }

    public void setTimeOrder(String timeOrder) {
        this.timeOrder = timeOrder;
    }

    public String getTimeDelivery() {
        return timeDelivery;
    }

    public void setTimeDelivery(String timeDelivery) {
        this.timeDelivery = timeDelivery;
    }

    public String getTimeCancel() {
        return timeCancel;
    }

    public void setTimeCancel(String timeCancel) {
        this.timeCancel = timeCancel;
    }

    public String getThoiGianDangGiao() {
        return thoiGianDangGiao;
    }

    public void setThoiGianDangGiao(String thoiGianDangGiao) {
        this.thoiGianDangGiao = thoiGianDangGiao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
