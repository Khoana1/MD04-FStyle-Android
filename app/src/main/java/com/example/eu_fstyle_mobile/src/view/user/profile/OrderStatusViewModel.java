package com.example.eu_fstyle_mobile.src.view.user.profile;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eu_fstyle_mobile.src.model.ListOrder;
import com.example.eu_fstyle_mobile.src.model.ListOrderID;
import com.example.eu_fstyle_mobile.src.retrofit.ApiClient;
import com.example.eu_fstyle_mobile.src.retrofit.ApiService;

import retrofit2.Call;

public class OrderStatusViewModel extends ViewModel {
    private MutableLiveData<ListOrder> listOrder;
    private MutableLiveData<String> errorMessage;

    public OrderStatusViewModel() {
        listOrder = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
    }

    public MutableLiveData<ListOrder> getListOrder() {
        return listOrder;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void getOrderByUserID(String id) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<ListOrder> call = apiService.getOrderByUserID(id);
        call.enqueue(new retrofit2.Callback<ListOrder>() {
            @Override
            public void onResponse(Call<ListOrder> call, retrofit2.Response<ListOrder> response) {
                if (response.isSuccessful()) {
                    listOrder.setValue(response.body());
                } else {
                    errorMessage.setValue("Lỗi lấy dang sách sản phẩm đâ đặt, vui lòng thử lại sau!");
                }
            }

            @Override
            public void onFailure(Call<ListOrder> call, Throwable t) {
                errorMessage.setValue(t.getMessage());
            }
        });
    }
}
