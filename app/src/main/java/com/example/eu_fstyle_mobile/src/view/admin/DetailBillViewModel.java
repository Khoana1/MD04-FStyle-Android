package com.example.eu_fstyle_mobile.src.view.admin;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eu_fstyle_mobile.src.model.ListOrderID;
import com.example.eu_fstyle_mobile.src.retrofit.ApiClient;
import com.example.eu_fstyle_mobile.src.retrofit.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailBillViewModel extends ViewModel {
    private MutableLiveData<ListOrderID> listOrderID;

    private MutableLiveData<String> errorMessage;

    public DetailBillViewModel() {
        listOrderID = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
    }

    public MutableLiveData<ListOrderID> getListOrderID() {
        return listOrderID;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void getOrderID(String id) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<ListOrderID> call = apiService.getOrderByID(id);
        call.enqueue(new Callback<ListOrderID>() {
            @Override
            public void onResponse(Call<ListOrderID> call, Response<ListOrderID> response) {
                if (response.isSuccessful()) {
                    listOrderID.setValue(response.body());
                } else {
                    errorMessage.setValue("Lỗi lấy thông tin đơn hàng!");
                }
            }

            @Override
            public void onFailure(Call<ListOrderID> call, Throwable t) {
                errorMessage.setValue("Server error: " + t.getMessage());

            }
        });
    }
}
