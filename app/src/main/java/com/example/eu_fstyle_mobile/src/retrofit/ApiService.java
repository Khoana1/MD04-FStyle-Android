package com.example.eu_fstyle_mobile.src.retrofit;

import com.example.eu_fstyle_mobile.src.model.User;
import com.example.eu_fstyle_mobile.ultilties.ApiPath;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET(ApiPath.user)
    Call<User> getUser(@Path("id") String postId);  //API mẫu
}
