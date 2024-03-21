package com.example.eu_fstyle_mobile.src.view.user.payment;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eu_fstyle_mobile.src.model.Cart;
import com.example.eu_fstyle_mobile.src.retrofit.ApiClient;
import com.example.eu_fstyle_mobile.src.retrofit.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartViewModel extends ViewModel {
    private MutableLiveData<Cart> cartData;
    private MutableLiveData<String> errorMessage;

    public CartViewModel() {
        cartData = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
    }

    public MutableLiveData<Cart> getCartData() {
        return cartData;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void getCart(String idUser) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<Cart> call = apiService.getCart(idUser);
        call.enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                if (response.isSuccessful()) {
                    cartData.setValue(response.body());
                } else {
                    errorMessage.setValue("Lỗi lấy danh sách giỏ hàng!");
                }
            }

            @Override
            public void onFailure(Call<Cart> call, Throwable t) {
                errorMessage.setValue("Server error: " + t.getMessage());
            }
        });
    }
}
