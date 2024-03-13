package com.example.eu_fstyle_mobile.src.view.user.profile;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eu_fstyle_mobile.src.model.ListProduct;
import com.example.eu_fstyle_mobile.src.retrofit.ApiClient;
import com.example.eu_fstyle_mobile.src.retrofit.ApiService;

import retrofit2.Call;
import retrofit2.Response;

public class MyFavouriteViewModel extends ViewModel {
    private MutableLiveData<ListProduct> listProduct;
    private MutableLiveData<String> errorMessage;

    public MyFavouriteViewModel() {
        listProduct = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
    }

    public MutableLiveData<ListProduct> getListProductLiveData() {
        return listProduct;
    }

    public MutableLiveData<String> getErrorLiveData() {
        return errorMessage;
    }

    public void getListProduct(String idUser) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<ListProduct> call = apiService.getFavorite(idUser);
        call.enqueue(new retrofit2.Callback<ListProduct>() {
            @Override
            public void onResponse(Call<ListProduct> call, Response<ListProduct> response) {
                if (response.isSuccessful()) {
                    listProduct.setValue(response.body());
                } else {
                    errorMessage.setValue("Lỗi lấy danh sách sản phẩm yêu thích!");
                }
            }

            @Override
            public void onFailure(Call<ListProduct> call, Throwable t) {
                errorMessage.setValue("Server error: " + t.getMessage());
            }
        });
    }

}
