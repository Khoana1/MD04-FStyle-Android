package com.example.eu_fstyle_mobile.src.view.admin;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eu_fstyle_mobile.src.model.Product;
import com.example.eu_fstyle_mobile.src.request.RequestCreateProduct;
import com.example.eu_fstyle_mobile.src.retrofit.ApiClient;
import com.example.eu_fstyle_mobile.src.retrofit.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductViewModel extends ViewModel {
    private MutableLiveData<Product> productData;

    private MutableLiveData<String> errorMessage;

    public AddProductViewModel() {
        productData = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
    }

    public MutableLiveData<Product> getProductLiveData() {
        return productData;
    }

    public MutableLiveData<String> getErrorLiveData() {
        return errorMessage;
    }

    public void createProduct(String name, String[] image64, String brand, Number price, List<Integer> size, String color, int quantity, String Idtype, String description) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        RequestCreateProduct requestCreateProduct = new RequestCreateProduct(name, image64, brand, price, size, color, quantity, description, Idtype);
        Call<Product> call = apiService.createProduct(requestCreateProduct);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    productData.setValue(response.body());
                } else {
                    errorMessage.setValue("Thêm sản phẩm thất bại, vui lòng kiểm tra lại thông tin!");
                    Log.d("Huy_erro", "onResponse: "+response.message());
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                errorMessage.setValue("Server error: " + t.getMessage());
            }
        });
    }
}
