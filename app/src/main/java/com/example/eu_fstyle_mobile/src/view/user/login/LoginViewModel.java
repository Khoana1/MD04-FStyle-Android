package com.example.eu_fstyle_mobile.src.view.user.login;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eu_fstyle_mobile.src.model.User;
import com.example.eu_fstyle_mobile.src.request.RequestLoginUser;
import com.example.eu_fstyle_mobile.src.retrofit.ApiClient;
import com.example.eu_fstyle_mobile.src.retrofit.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {
    private final MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<User> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public void loginUser(String email, String password) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        RequestLoginUser requestLoginUser = new RequestLoginUser(email, password);
        Call<User> call = apiService.signin(requestLoginUser);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    userMutableLiveData.setValue(response.body());
                } else {
                    userMutableLiveData.setValue(null);
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                userMutableLiveData.setValue(null);
            }
        });
    }
}