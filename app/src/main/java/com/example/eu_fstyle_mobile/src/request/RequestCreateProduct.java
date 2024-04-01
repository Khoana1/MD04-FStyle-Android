package com.example.eu_fstyle_mobile.src.request;

public class RequestCreateProduct {
    private String name;
    private String[] image64;
    private String brand;
    private Number price;
    private int size;
    private String color;
    private int quantity;
    private String description;
    private String categoryId;


    public RequestCreateProduct(String name, String[] image64, String brand, Number price,int size, String color, int quantity, String description, String categoryId) {
        this.name = name;
        this.image64 = image64;
        this.brand = brand;
        this.price = price;
        this.size = size;
        this.color = color;
        this.quantity = quantity;
        this.categoryId = categoryId;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getImage64() {
        return image64;
    }

    public void setImage64(String[] image64) {
        this.image64 = image64;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Number getPrice() {
        return price;
    }

    public void setPrice(Number price) {
        this.price = price;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getIdCategory() {
        return categoryId;
    }

    public void setIdCategory(String idCategory) {
        this.categoryId = idCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
