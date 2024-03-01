package com.example.eu_fstyle_mobile.src.view.user.address;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.eu_fstyle_mobile.databinding.FragmentEditAddressBinding;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;
import com.example.eu_fstyle_mobile.src.model.Address;
import com.example.eu_fstyle_mobile.src.model.User;
import com.example.eu_fstyle_mobile.src.retrofit.ApiClient;
import com.example.eu_fstyle_mobile.src.retrofit.ApiService;
import com.example.eu_fstyle_mobile.src.view.user.profile.EditInfoViewModel;
import com.example.eu_fstyle_mobile.src.view.user.profile.ProfileFragment;
import com.example.eu_fstyle_mobile.ultilties.UserPrefManager;

import java.io.Serializable;

import retrofit2.Call;

public class EditAddressFragment extends BaseFragment<FragmentEditAddressBinding> {
    private static final String ADDRESS = "ADDRESS";
    private String consigneeName;
    private String homeNumber;
    private String street;
    private String district;
    private String city;
    private String phoneNumber;
    private Address selectedAddress;
    private EditAddressViewModel editAddressViewModel;
    private User user;

    public static EditAddressFragment newInstance(Address address) {
        EditAddressFragment fragment = new EditAddressFragment();
        Bundle args = new Bundle();
        args.putSerializable(ADDRESS, address);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = getFragmentBinding(inflater, container);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editAddressViewModel = new ViewModelProvider(this).get(EditAddressViewModel.class);
        fillData();
        intiView();
        observeViewModel();
    }

    private void fillData() {
        user = UserPrefManager.getInstance(getActivity()).getUser();
        if (getArguments() != null) {
            selectedAddress = (Address) getArguments().getSerializable(ADDRESS);
            if (selectedAddress != null) {
                binding.edtConsineeName.setText(selectedAddress.getConsigneeName());
                binding.edtHomeNumber.setText(selectedAddress.getHomeNumber());
                binding.edtStreet.setText(selectedAddress.getStreet());
                binding.edtDistrict.setText(selectedAddress.getDistrict());
                binding.edtCity.setText(selectedAddress.getCity());
                binding.edtPhone.setText(selectedAddress.getPhoneNumber());
            }
        }
    }

    private void intiView() {
        setStatusHelperText();
        binding.icBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        binding.btnChangeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAddress();
                if ((binding.consigneeNameContainer.getHelperText() == null || binding.consigneeNameContainer.getHelperText().toString().isEmpty()) &&
                        (binding.homeNumberContainer.getHelperText() == null || binding.homeNumberContainer.getHelperText().toString().isEmpty()) &&
                        (binding.streetContainer.getHelperText() == null || binding.streetContainer.getHelperText().toString().isEmpty()) &&
                        (binding.districtContainer.getHelperText() == null || binding.districtContainer.getHelperText().toString().isEmpty() &&
                                binding.cityContainer.getHelperText() == null || binding.cityContainer.getHelperText().toString().isEmpty() &&
                                binding.phoneContainer.getHelperText() == null || binding.phoneContainer.getHelperText().toString().isEmpty())) {
                    showLoadingDialog();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            hideLoadingDialog();
                            editAddressViewModel.updateAddress(user.get_id(), selectedAddress.get_id(), consigneeName, homeNumber, street, district, city, phoneNumber);
                        }
                    }, 2000);

                }
            }
        });
        binding.icDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("Xác nhận", "Bạn có chắc chắn muốn xóa địa chỉ này không?", new Runnable() {
                    @Override
                    public void run() {
                        ApiService apiService = ApiClient.getClient().create(ApiService.class);
                        Call<Address> call = apiService.deleteAddress(user.get_id(), selectedAddress.get_id());
                        call.enqueue(new retrofit2.Callback<Address>() {
                            @Override
                            public void onResponse(Call<Address> call, retrofit2.Response<Address> response) {
                                if (response.isSuccessful()) {
                                    showSuccessDialog("Xóa địa chỉ thành công");
                                    getActivity().onBackPressed();
                                } else {
                                    showAlertDialog("Xóa địa chỉ thất bại, vui lòng thử lại sau");
                                }
                            }

                            @Override
                            public void onFailure(Call<Address> call, Throwable t) {
                                showAlertDialog("Server error: " + t.getMessage());
                            }
                        });
                    }
                });
            }
        });
        binding.parentLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, android.view.MotionEvent event) {
                hideKeyboard();
                clearFocusEditText();
                return false;
            }
        });
    }

    private void setStatusHelperText() {
        binding.consigneeNameContainer.setHelperText("");
        binding.homeNumberContainer.setHelperText("");
        binding.streetContainer.setHelperText("");
        binding.districtContainer.setHelperText("");
        binding.cityContainer.setHelperText("");
        binding.phoneContainer.setHelperText("");
    }

    private void validateAddress() {
        String helperText = "";
        consigneeName = binding.edtConsineeName.getText().toString();
        homeNumber = binding.edtHomeNumber.getText().toString();
        street = binding.edtStreet.getText().toString();
        district = binding.edtDistrict.getText().toString();
        city = binding.edtCity.getText().toString();
        phoneNumber = binding.edtPhone.getText().toString();
        if (consigneeName.isEmpty()) {
            helperText = "Không được để trống tên người nhận hàng";
            binding.consigneeNameContainer.setHelperText(helperText);
        } else {
            binding.consigneeNameContainer.setHelperText("");
        }
        if (homeNumber.isEmpty()) {
            helperText = "Không được để trống số nhà";
            binding.homeNumberContainer.setHelperText(helperText);
        } else {
            binding.homeNumberContainer.setHelperText("");
        }
        if (street.isEmpty()) {
            helperText = "Không được để trống tên đường";
            binding.streetContainer.setHelperText(helperText);
        } else {
            binding.streetContainer.setHelperText("");
        }
        if (district.isEmpty()) {
            helperText = "Không được để trống quận/huyện";
            binding.districtContainer.setHelperText(helperText);
        } else {
            binding.districtContainer.setHelperText("");
        }
        if (city.isEmpty()) {
            helperText = "Không được để trống thành phố";
            binding.cityContainer.setHelperText(helperText);
        } else {
            binding.cityContainer.setHelperText("");
        }
        if (phoneNumber.isEmpty()) {
            helperText = "Không được để trống số điện thoại";
            binding.phoneContainer.setHelperText(helperText);
        } else if (phoneNumber.length() < 10) {
            helperText = "Số điện thoại phải có 10 số";
            binding.phoneContainer.setHelperText(helperText);
        } else {
            binding.phoneContainer.setHelperText("");
        }
    }

    private void observeViewModel() {
        editAddressViewModel.getAddressData().observe(getViewLifecycleOwner(), address -> {
            if (address != null) {
                showSuccessDialog("Cập nhật địa chỉ thành công");
            }
        });
        editAddressViewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                showAlertDialog(error);
            }
        });
    }


    @Override
    protected FragmentEditAddressBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentEditAddressBinding.inflate(inflater, container, false);
    }

    private void clearFocusEditText() {
        binding.edtConsineeName.clearFocus();
        binding.edtHomeNumber.clearFocus();
        binding.edtStreet.clearFocus();
        binding.edtDistrict.clearFocus();
        binding.edtCity.clearFocus();
        binding.edtPhone.clearFocus();
    }
}