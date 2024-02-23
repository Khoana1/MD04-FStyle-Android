package com.example.eu_fstyle_mobile.src.view.user.address;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eu_fstyle_mobile.src.model.Address;
import com.example.eu_fstyle_mobile.src.request.RequestAddAddress;
import com.example.eu_fstyle_mobile.src.retrofit.ApiClient;
import com.example.eu_fstyle_mobile.src.retrofit.ApiService;

import retrofit2.Call;

public class AddAddressViewModel extends ViewModel {
    private MutableLiveData<Address> addressData;
    private MutableLiveData<String> errorMessage;

    public AddAddressViewModel() {
        addressData = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
    }
    public MutableLiveData<Address> getAddressLiveData() {
        return addressData;
    }
    public MutableLiveData<String> getErrorLiveData() {
        return errorMessage;
    }
    public void addAddress(String idUser, String homeNumber, String street, String district, String city, String phoneNumber, String consigneeName) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        RequestAddAddress requestAddAddress = new RequestAddAddress(homeNumber, street, district, city, phoneNumber, consigneeName);
        Call<Address> call = apiService.addAddress(requestAddAddress, idUser);
        call.enqueue(new retrofit2.Callback<Address>() {
            @Override
            public void onResponse(Call<Address> call, retrofit2.Response<Address> response) {
                if (response.isSuccessful()) {
                    addressData.setValue(response.body());
                } else {
                    errorMessage.setValue("Thêm địa chỉ thất bại, vui lòng kiểm tra lại thông tin!");
                }
            }

            @Override
            public void onFailure(Call<Address> call, Throwable t) {
                errorMessage.setValue("Server error: " + t.getMessage());
            }
        });

    }
}
