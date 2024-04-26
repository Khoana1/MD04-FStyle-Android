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
    public static final String getProductsById = "/api/get/productsById/{id}";
    public static final String createProduct = "/api/products";
    public static final String updateQuantity = "/api/products/update/quantity/{id}";
    public static final String getCart = "/api/cart/{id}";
    public static final String createCart = "/api/add/product/cart/{id}";
    public static final String deleteCart = "/api/del/cart/{id}/{id_product}";
    public static final String getFavorite = "/api/favorite/get/{id}";
    public static final String createFavorite = "/api/favorite/post/{id}";
    public static final String deleteFavorite = "/api/favorite/delete/{id}/{id_product}";
    public static final String getAllCategories = "/api/get/categories";
    public static final String createCategories = "/api/post/categories";
    public static final String deleteCategories = "/api/delete/categories/{id}";
    public static final String updateCategories = "/api/put/categories/{id}";
    public static final String reduceCart = "/api/cart/reduce/{id}/{id_product}";
    public static final String increaseCart = "/api/cart/increase/{id}/{id_product}";
    public static final String clearCart = "/api/cart/clear/order/{id}/{id_product}";
    public static final String createOrder = "/api/orders/{id}";
    public static final String getAllOrder = "/api/orders";
    public static final String getOrderByID = "/api/orders/{orderId}";
    public static final String updateOrderStatus = "/api/orders/{id}";
    public static final String getOrderByUserID = "/api/orders/getbyuser/{id}";
}
