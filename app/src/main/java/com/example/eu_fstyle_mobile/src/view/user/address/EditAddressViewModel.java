package com.example.eu_fstyle_mobile.src.view.user.address;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eu_fstyle_mobile.src.model.Address;
import com.example.eu_fstyle_mobile.src.request.RequestUpdateAddress;
import com.example.eu_fstyle_mobile.src.retrofit.ApiClient;
import com.example.eu_fstyle_mobile.src.retrofit.ApiService;

import retrofit2.Call;

public class EditAddressViewModel extends ViewModel {

    private MutableLiveData<Address> addressData = new MutableLiveData<>();

    private MutableLiveData<String> error = new MutableLiveData<>();

    public MutableLiveData<Address> getAddressData() {
        return addressData;
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    public void updateAddress(String userId, String id, String consigneeName, String homeNumber, String street, String district,String city, String phoneNumber) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<Address> call = apiService.updateAddress(userId, id, new RequestUpdateAddress(consigneeName, homeNumber, street, district, city, phoneNumber));
        call.enqueue(new retrofit2.Callback<Address>() {
            @Override
            public void onResponse(Call<Address> call, retrofit2.Response<Address> response) {
                if (response.isSuccessful()) {
                    addressData.setValue(response.body());
                } else {
                    error.setValue("Cập nhật địa chỉ thất bại, vui lòng thử lại sau");
                }
            }

            @Override
            public void onFailure(Call<Address> call, Throwable t) {
                error.setValue("Server error: " + t.getMessage());
            }
        });


    }
}
