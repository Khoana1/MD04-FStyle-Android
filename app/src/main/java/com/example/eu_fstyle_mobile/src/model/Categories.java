package com.example.eu_fstyle_mobile.src.model;

import com.google.gson.annotations.SerializedName;

public class Categories {
    @SerializedName("id")
    int id;
    @SerializedName("name")
    String name;
    @SerializedName("image")
    String image;

    public Categories(int id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }
}
