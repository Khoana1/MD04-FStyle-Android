package com.example.eu_fstyle_mobile.src.view.user.profile;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eu_fstyle_mobile.src.model.User;
import com.example.eu_fstyle_mobile.src.retrofit.ApiClient;
import com.example.eu_fstyle_mobile.src.retrofit.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileViewModel extends ViewModel {
    private MutableLiveData<User> userData = new MutableLiveData<>();
    private MutableLiveData<String> errorData = new MutableLiveData<>();

    public MutableLiveData<User> getUserData() {
        return userData;
    }

    public MutableLiveData<String> getErrorData() {
        return errorData;
    }

    public void fetchUserData(String userId) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<User> call = apiService.getUser(userId);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    userData.setValue(response.body());
                } else {
                    errorData.setValue("Gặp lỗi lấy dữ liệu người dùng");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                errorData.setValue("Server error: " + t.getMessage());
            }
        });
    }
}
