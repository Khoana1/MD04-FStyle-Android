package com.example.eu_fstyle_mobile.src.view.user.register;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eu_fstyle_mobile.src.model.User;
import com.example.eu_fstyle_mobile.src.request.RequestCreateUser;
import com.example.eu_fstyle_mobile.src.retrofit.ApiClient;
import com.example.eu_fstyle_mobile.src.retrofit.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterViewModel extends ViewModel {

    private MutableLiveData<User> userData;
    private MutableLiveData<String> errorMessage;

    public RegisterViewModel() {
        userData = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
    }
    public MutableLiveData<User> getUserLiveData() {
        return userData;
    }
    public MutableLiveData<String> getErrorLiveData() {
        return errorMessage;
    }

    public void registerUser(String name, String email, String password, String phone) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        RequestCreateUser requestCreateUser = new RequestCreateUser(name, email, password, phone);
        Call<User> call = apiService.createUser(requestCreateUser);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    userData.setValue(response.body());
                } else {
                    errorMessage.setValue("Đăng ký thất bại, vui lòng thử lại sau");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                errorMessage.setValue("Server error: " + t.getMessage());
            }
        });
    }

}
