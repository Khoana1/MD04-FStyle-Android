package com.example.eu_fstyle_mobile.src.view.user.address;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eu_fstyle_mobile.src.model.Address;
import com.example.eu_fstyle_mobile.src.model.AddressRespone;
import com.example.eu_fstyle_mobile.src.retrofit.ApiClient;
import com.example.eu_fstyle_mobile.src.retrofit.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class MyAddressViewModel extends ViewModel {
    private MutableLiveData<AddressRespone> addressData;
    private MutableLiveData<String> errorMessage;

    public MyAddressViewModel() {
        addressData = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
    }

    public MutableLiveData<AddressRespone> getAddressLiveData() {
        return addressData;
    }

    public MutableLiveData<String> getErrorLiveData() {
        return errorMessage;
    }

    public void getAddress(String idUser) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<AddressRespone> call = apiService.getAddress(idUser);
        call.enqueue(new retrofit2.Callback<AddressRespone>() {
            @Override
            public void onResponse(Call<AddressRespone> call, Response<AddressRespone> response) {
                if (response.isSuccessful()) {
                    addressData.setValue(response.body());
                } else {
                    errorMessage.setValue("Lỗi lấy danh sách địa chỉ!");
                }
            }

            @Override
            public void onFailure(Call<AddressRespone> call, Throwable t) {
                errorMessage.setValue("Server error: " + t.getMessage());
            }
        });
    }
}
