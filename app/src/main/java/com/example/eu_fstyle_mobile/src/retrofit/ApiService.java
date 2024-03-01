package com.example.eu_fstyle_mobile.src.retrofit;

import com.example.eu_fstyle_mobile.src.model.Address;
import com.example.eu_fstyle_mobile.src.model.AddressRespone;
import com.example.eu_fstyle_mobile.src.model.ListProduct;
import com.example.eu_fstyle_mobile.src.model.User;
import com.example.eu_fstyle_mobile.src.request.RequestAddAddress;
import com.example.eu_fstyle_mobile.src.request.RequestCreateUser;
import com.example.eu_fstyle_mobile.src.request.RequestLoginUser;
import com.example.eu_fstyle_mobile.src.request.RequestUpdateAddress;
import com.example.eu_fstyle_mobile.src.request.RequestUpdateUser;
import com.example.eu_fstyle_mobile.ultilties.ApiEndpoint;

import retrofit2.Call;
import retrofit2.http.Body;
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
}
