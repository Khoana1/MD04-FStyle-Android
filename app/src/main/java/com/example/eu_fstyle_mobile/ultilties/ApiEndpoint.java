package com.example.eu_fstyle_mobile.ultilties;

public class ApiEndpoint {
    public static final String createUser = "/api/user/post";
    public static final String signin = "/api/user/signin";

    public static final String getUser = "api/user/{id}";
    public static final String updateUser = "/api/user/update/{id}";
    public static final String addAddress = "/api/user/address/post/{id}";
    public static final String updateAddress = "/api/user/address/update/{id}/{id_address}";
    public static final String deleteAddress = "/api/user/address/del/{id}/{id_address}";
    public static final String getAddress = "/api/user/address/list/{id}";
    public static final String getAllProducts = "/api/products";
    public static final String createProduct = "/api/products/post/{id}";
    public static final String getCart = "/api/cart/{id}";
    public static final String createCart = "/api/add/product/cart/{id}";
    public static final String deleteCart = "/api/del/cart/{id}/{id_product}";
    public static final String getFavorite = "/api/favorite/get/{id}";
    public static final String createFavorite = "/api/favorite/post/{id}";
    public static final String updateFavorite = "/api/favorite/put/{id}/{id_product}";
    public static final String getAllCategories = "/api/get/categories";
    public static final String createCategories = "/api/post/categories";
}
