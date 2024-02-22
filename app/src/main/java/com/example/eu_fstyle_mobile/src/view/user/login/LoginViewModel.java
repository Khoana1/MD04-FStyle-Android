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
    private MutableLiveData<User> userData;
    private MutableLiveData<String> errorMessage;

    public LoginViewModel() {
        userData = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
    }
    public MutableLiveData<User> getUserLiveData() {
        return userData;
    }
    public MutableLiveData<String> getErrorLiveData() {
        return errorMessage;
    }

    public void loginUser(String email, String password) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        RequestLoginUser requestLoginUser = new RequestLoginUser(email, password);
        Call<User> call = apiService.signin(requestLoginUser);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    userData.setValue(response.body());
                } else {
                    errorMessage.setValue("Đăng nhập thất bại, vui lòng kiểm tra lại thông tin đăng nhập!");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                errorMessage.setValue("Server error: " + t.getMessage());
            }
        });
    }

}
