package com.example.eu_fstyle_mobile.src.request;

public class RequestCreateCart {
    private String image64;
    private String name;
    private String quantity;
    private Number price;

    public RequestCreateCart(String image64, String name, String quantity, Number price) {
        this.image64 = image64;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getImage64() {
        return image64;
    }

    public void setImage64(String image64) {
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

    public Number getPrice() {
        return price;
    }

    public void setPrice(Number price) {
        this.price = price;
    }
}
