package com.example.eu_fstyle_mobile.src.view.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.eu_fstyle_mobile.databinding.FragmentAddAddressBinding;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;

public class AddAddressFragment extends BaseFragment<FragmentAddAddressBinding> {
    private String consigneeName;
    private String homeNumber;
    private String stress;
    private String district;
    private String city;
    private String phoneNumber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = getFragmentBinding(inflater, container);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        setStatusHelperText();
        binding.icBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        binding.btnAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAddress();
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
        stress = binding.edtStreet.getText().toString();
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
        if (stress.isEmpty()) {
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
        } else if (phoneNumber.matches(".*[0-9].*")) {
            helperText = "Số điện thoại phải là số";
            binding.phoneContainer.setHelperText(helperText);
        } else  {
            binding.phoneContainer.setHelperText("");
        }
    }

    @Override
    protected FragmentAddAddressBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentAddAddressBinding.inflate(inflater, container, false);
    }
}