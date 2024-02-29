package com.example.eu_fstyle_mobile.src.view.user.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eu_fstyle_mobile.src.model.User;
import com.example.eu_fstyle_mobile.src.request.RequestUpdateUser;
import com.example.eu_fstyle_mobile.src.retrofit.ApiClient;
import com.example.eu_fstyle_mobile.src.retrofit.ApiService;

import retrofit2.Call;

public class EditInfoViewModel extends ViewModel {
    private MutableLiveData<User> user = new MutableLiveData<>();

    private MutableLiveData<String> error = new MutableLiveData<>();

    public MutableLiveData<User> getUser() {
        return user;
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    public void updateUser(String userId, String avatar, String name, String email, String password, String phone) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<User> call = apiService.updateUser(userId, new RequestUpdateUser(avatar, name, email, password, phone));
        call.enqueue(new retrofit2.Callback<User>() {
            @Override
            public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                if (response.isSuccessful()) {
                    user.setValue(response.body());
                } else {
                    error.setValue("Cập nhật thông tin thất bại, vui lòng thử lại sau");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                error.setValue("Server error: " + t.getMessage());
            }
        });

    }

}
