package com.example.eu_fstyle_mobile.src.view.user.address;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.eu_fstyle_mobile.databinding.FragmentEditAddressBinding;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;
import com.example.eu_fstyle_mobile.src.model.Address;

import java.io.Serializable;

public class EditAddressFragment extends BaseFragment<FragmentEditAddressBinding> {
    private static final String ADDRESS = "ADDRESS";
    private String consigneeName;
    private String homeNumber;
    private String street;
    private String district;
    private String city;
    private String phoneNumber;

    private Serializable address;

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
        address = getArguments().getSerializable(ADDRESS);
        intiView();
        fillData();
    }

    private void fillData() {
        if (getArguments() != null) {
            Address selectedAddress = (Address) getArguments().getSerializable(ADDRESS);
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
        }  else  {
            binding.phoneContainer.setHelperText("");
        }
    }

    @Override
    protected FragmentEditAddressBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentEditAddressBinding.inflate(inflater, container, false);
    }
}