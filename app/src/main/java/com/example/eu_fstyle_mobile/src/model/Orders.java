package com.example.eu_fstyle_mobile.src.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Orders implements Serializable {
    @SerializedName("_id")
    private String _id;
    @SerializedName("address")
    private String address;
    @SerializedName("listProduct")
    private List<ProductOrder> listProduct;
    @SerializedName("idUser")
    private String idUser;
    @SerializedName("quantity")
    private int quantity;
    @SerializedName("totalPrice")
    private String totalPrice;
    @SerializedName("phone")
    private String phone;
    @SerializedName("paymentMethods")
    private String paymentMethods;
    @SerializedName("shippingMethod")
    private String shippingMethod;
    @SerializedName("status")
    private String status;
    @SerializedName("timeOrder")
    private String timeOrder;
    @SerializedName("__v")
    private int __v;

    public Orders(String _id, String address, List<ProductOrder> listProduct, String idUser, int quantity, String totalPrice, String phone, String paymentMethods, String shippingMethod, String status, String timeOrder, int __v) {
        this._id = _id;
        this.address = address;
        this.listProduct = listProduct;
        this.idUser = idUser;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.phone = phone;
        this.paymentMethods = paymentMethods;
        this.shippingMethod = shippingMethod;
        this.status = status;
        this.timeOrder = timeOrder;
        this.__v = __v;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<ProductOrder> getListProduct() {
        return listProduct;
    }

    public void setListProduct(List<ProductOrder> listProduct) {
        this.listProduct = listProduct;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
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

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimeOrder() {
        return timeOrder;
    }

    public void setTimeOrder(String timeOrder) {
        this.timeOrder = timeOrder;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }
}
