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
}
