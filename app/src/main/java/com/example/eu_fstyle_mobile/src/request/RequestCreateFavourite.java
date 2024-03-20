package com.example.eu_fstyle_mobile.src.request;

public class RequestCreateFavourite {
    private String name;
    private String quantity;
    private String price;
    private String defaultImage;


    public RequestCreateFavourite(String name, String quantity, String price, String defaultImage) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.defaultImage = defaultImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDefaultImage() {
        return defaultImage;
    }

    public void setDefaultImage(String defaultImage) {
        this.defaultImage = defaultImage;
    }
}
