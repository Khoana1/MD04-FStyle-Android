package com.example.eu_fstyle_mobile.src.retrofit;

import com.example.eu_fstyle_mobile.src.model.User;
import com.example.eu_fstyle_mobile.src.request.RequestCreateUser;
import com.example.eu_fstyle_mobile.src.request.RequestLoginUser;
import com.example.eu_fstyle_mobile.ultilties.ApiEndpoint;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @POST(ApiEndpoint.createUser)
    Call<User> createUser(@Body RequestCreateUser createUser);
    @POST(ApiEndpoint.signin)
    Call<User> signin(@Body RequestLoginUser loginUser);
    @GET(ApiEndpoint.getUser)
    Call<User> getUser(@Path("id") String id);
    @GET(ApiEndpoint.updateUser)
    Call<User> updateUser(@Path("id") int id);
}
