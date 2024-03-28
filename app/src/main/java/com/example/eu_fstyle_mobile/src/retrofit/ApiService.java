package com.example.eu_fstyle_mobile.src.retrofit;

import com.example.eu_fstyle_mobile.src.model.Address;
import com.example.eu_fstyle_mobile.src.model.AddressRespone;
import com.example.eu_fstyle_mobile.src.model.Cart;
import com.example.eu_fstyle_mobile.src.model.Categories;
import com.example.eu_fstyle_mobile.src.model.Favourite;
import com.example.eu_fstyle_mobile.src.model.ListCategories;
import com.example.eu_fstyle_mobile.src.model.ListProduct;
import com.example.eu_fstyle_mobile.src.model.Product;
import com.example.eu_fstyle_mobile.src.model.User;
import com.example.eu_fstyle_mobile.src.request.RequestAddAddress;
import com.example.eu_fstyle_mobile.src.request.RequestCategory;
import com.example.eu_fstyle_mobile.src.request.RequestCreateCart;
import com.example.eu_fstyle_mobile.src.request.RequestCreateFavourite;
import com.example.eu_fstyle_mobile.src.request.RequestCreateProduct;
import com.example.eu_fstyle_mobile.src.request.RequestCreateUser;
import com.example.eu_fstyle_mobile.src.request.RequestLoginUser;
import com.example.eu_fstyle_mobile.src.request.RequestUpdateAddress;
import com.example.eu_fstyle_mobile.src.request.RequestUpdateUser;
import com.example.eu_fstyle_mobile.ultilties.ApiEndpoint;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    @POST(ApiEndpoint.createUser)
    Call<User> createUser(@Body RequestCreateUser createUser);

    @POST(ApiEndpoint.signin)
    Call<User> signin(@Body RequestLoginUser loginUser);

    @GET(ApiEndpoint.getUser)
    Call<User> getUser(@Path("id") String id);

    @PUT(ApiEndpoint.updateUser)
    Call<User> updateUser(@Path("id") String id, @Body RequestUpdateUser requestUpdateUser);

    @POST(ApiEndpoint.addAddress)
    Call<Address> addAddress(@Body RequestAddAddress requestAddAddress, @Path("id") String id);

    @POST(ApiEndpoint.getAddress)
    Call<AddressRespone> getAddress(@Path("id") String id);

    @PUT(ApiEndpoint.updateAddress)
    Call<Address> updateAddress(@Path("id") String id, @Path("id_address") String id_address, @Body RequestUpdateAddress requestUpdateAddress);

    @PUT(ApiEndpoint.deleteAddress)
    Call<Address> deleteAddress(@Path("id") String id, @Path("id_address") String id_address);

    @GET(ApiEndpoint.getAllProducts)
    Call<ListProduct> getAllProducts();

    @POST(ApiEndpoint.createProduct)
    Call<Product> createProduct(@Body RequestCreateProduct requestCreateProduct, @Path("id") String id);

    @PUT(ApiEndpoint.createCart)
    Call<Product> createCart(@Path("id") String id, @Body RequestCreateCart requestCreateCart);

    @GET(ApiEndpoint.getFavorite)
    Call<Favourite> getFavorite(@Path("id") String id);

    @POST(ApiEndpoint.createFavorite)
    Call<Product> createFavorite(@Path("id") String id, @Body RequestCreateFavourite requestCreateFavourite);

    @GET(ApiEndpoint.getAllCategories)
    Call<ListCategories> getAllCategories();

    @POST(ApiEndpoint.createCategories)
    Call<Categories> createCategory(@Body RequestCategory requestCategory);

    @GET(ApiEndpoint.getCart)
    Call<Cart> getCart(@Path("id") String id);

    @GET(ApiEndpoint.deleteCart)
    Call<Cart> deleteCart(@Path("id") String id, @Path("id_product") String id_product);

}