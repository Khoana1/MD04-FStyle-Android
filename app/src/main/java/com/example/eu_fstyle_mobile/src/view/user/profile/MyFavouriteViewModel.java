package com.example.eu_fstyle_mobile.src.view.user.profile;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eu_fstyle_mobile.src.model.Favourite;
import com.example.eu_fstyle_mobile.src.model.ListProduct;
import com.example.eu_fstyle_mobile.src.retrofit.ApiClient;
import com.example.eu_fstyle_mobile.src.retrofit.ApiService;

import retrofit2.Call;
import retrofit2.Response;

public class MyFavouriteViewModel extends ViewModel {
    private MutableLiveData<Favourite> favouriteData;
    private MutableLiveData<String> errorMessage;

    public MyFavouriteViewModel() {
        favouriteData = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
    }

    public MutableLiveData<Favourite> getFavouriteLiveData() {
        return favouriteData;
    }

    public MutableLiveData<String> getErrorLiveData() {
        return errorMessage;
    }

    public void getFavourite(String idUser) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<Favourite> call = apiService.getFavorite(idUser);
        call.enqueue(new retrofit2.Callback<Favourite>() {
            @Override
            public void onResponse(Call<Favourite> call, Response<Favourite> response) {
                if (response.isSuccessful()) {
                    favouriteData.setValue(response.body());
                } else {
                    errorMessage.setValue("Lỗi lấy danh sách yêu thích!");
                }
            }

            @Override
            public void onFailure(Call<Favourite> call, Throwable t) {
                errorMessage.setValue("Server error: " + t.getMessage());
            }
        });
    }

}
