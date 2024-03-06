package com.example.eu_fstyle_mobile.src.view.admin;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eu_fstyle_mobile.src.model.ListProduct;
import com.example.eu_fstyle_mobile.src.retrofit.ApiClient;
import com.example.eu_fstyle_mobile.src.retrofit.ApiService;

import retrofit2.Call;
import retrofit2.Response;

public class HomeAdminViewModel extends ViewModel {
    private MutableLiveData<ListProduct> productData;
    private MutableLiveData<String> errorMessage;

    public HomeAdminViewModel() {
        productData = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
    }

    public MutableLiveData<ListProduct> getProductLiveData() {
        return productData;
    }

    public MutableLiveData<String> getErrorLiveData() {
        return errorMessage;
    }

    public void getAllProduct() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<ListProduct> call = apiService.getAllProducts();
        call.enqueue(new retrofit2.Callback<ListProduct>() {
            @Override
            public void onResponse(Call<ListProduct> call, Response<ListProduct> response) {
                if (response.isSuccessful()) {
                    productData.setValue(response.body());
                } else {
                    errorMessage.setValue("Lỗi lấy danh sách sản phẩm!");
                }
            }

            @Override
            public void onFailure(Call<ListProduct> call, Throwable t) {
                errorMessage.setValue("Server error: " + t.getMessage());
            }
        });
    }

}
