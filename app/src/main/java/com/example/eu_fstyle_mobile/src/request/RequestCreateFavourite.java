package com.example.eu_fstyle_mobile.src.request;

public class RequestCreateFavourite {
    private String name;
    private String quantity;
    private String price;
    private String image64;

    public RequestCreateFavourite(String name, String quantity, String price, String image64) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.image64 = image64;
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

    public String getImage64() {
        return image64;
    }

    public void setImage64(String image64) {
        this.image64 = image64;
    }
}
