package com.example.eu_fstyle_mobile.src.view.admin;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eu_fstyle_mobile.src.model.ListOrder;
import com.example.eu_fstyle_mobile.src.retrofit.ApiClient;
import com.example.eu_fstyle_mobile.src.retrofit.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillAdminViewModel extends ViewModel {
    private MutableLiveData<ListOrder> listOrder;

    private MutableLiveData<String> errorMessage;

    public BillAdminViewModel() {
        listOrder = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
    }

    public MutableLiveData<ListOrder> getListOrder() {
        return listOrder;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void getAllOrder() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<ListOrder> call = apiService.getAllOrder();
        call.enqueue(new Callback<ListOrder>() {
            @Override
            public void onResponse(Call<ListOrder> call, Response<ListOrder> response) {
                if (response.isSuccessful()) {
                    listOrder.setValue(response.body());
                } else {
                    errorMessage.setValue("Lỗi lấy danh sách đơn hàng!");
                }
            }

            @Override
            public void onFailure(Call<ListOrder> call, Throwable t) {
                errorMessage.setValue("Server error: " + t.getMessage());

            }
        });
    }
}
