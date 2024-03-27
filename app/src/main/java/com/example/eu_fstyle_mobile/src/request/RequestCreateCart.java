package com.example.eu_fstyle_mobile.src.request;

public class RequestCreateCart {
    private String idProduct;
    private String name;
    private String soLuong;
    private String size;
    private Number price;
    private String imageDefault;

    public RequestCreateCart(String idProduct, String name, String soLuong, String size, Number price, String imageDefault) {
        this.idProduct = idProduct;
        this.name = name;
        this.soLuong = soLuong;
        this.size = size;
        this.price = price;
        this.imageDefault = imageDefault;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(String soLuong) {
        this.soLuong = soLuong;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Number getPrice() {
        return price;
    }

    public void setPrice(Number price) {
        this.price = price;
    }

    public String getImageDefault() {
        return imageDefault;
    }

    public void setImageDefault(String imageDefault) {
        this.imageDefault = imageDefault;
    }
}
