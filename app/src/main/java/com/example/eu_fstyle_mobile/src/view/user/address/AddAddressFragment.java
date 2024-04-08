package com.example.eu_fstyle_mobile.src.view.user.address;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.eu_fstyle_mobile.databinding.FragmentAddAddressBinding;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;
import com.example.eu_fstyle_mobile.src.model.Address;
import com.example.eu_fstyle_mobile.src.model.User;
import com.example.eu_fstyle_mobile.src.view.user.home.HomeFragment;
import com.example.eu_fstyle_mobile.src.view.user.login.LoginViewModel;
import com.example.eu_fstyle_mobile.ultilties.UserPrefManager;

public class AddAddressFragment extends BaseFragment<FragmentAddAddressBinding> {
    private String consigneeName;
    private String homeNumber;
    private String street;
    private String district;
    private String city;
    private String phoneNumber;
    private AddAddressViewModel addAddressViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = getFragmentBinding(inflater, container);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        addAddressViewModel = new ViewModelProvider(this).get(AddAddressViewModel.class);
        observeViewModel();
    }

    private void observeViewModel() {
        addAddressViewModel.getAddressLiveData().observe(getViewLifecycleOwner(), new Observer<Address>() {
            @Override
            public void onChanged(Address address) {
                binding.edtConsineeName.setText("");
                binding.edtHomeNumber.setText("");
                binding.edtStreet.setText("");
                binding.edtDistrict.setText("");
                binding.edtCity.setText("");
                binding.edtPhone.setText("");
                Toast.makeText(getActivity(), "Thêm địa chỉ thành công", Toast.LENGTH_SHORT).show();
                openScreen(new MyAddressFragment(), false);
            }
        });
        addAddressViewModel.getErrorLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String error) {
                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
            }
        });
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
                if (binding.consigneeNameContainer.getHelperText() == null || binding.consigneeNameContainer.getHelperText().toString().isEmpty() &&
                        binding.homeNumberContainer.getHelperText() == null || binding.homeNumberContainer.getHelperText().toString().isEmpty() &&
                        binding.streetContainer.getHelperText() == null || binding.streetContainer.getHelperText().toString().isEmpty() &&
                        binding.districtContainer.getHelperText() == null || binding.districtContainer.getHelperText().toString().isEmpty() &&
                        binding.cityContainer.getHelperText() == null || binding.cityContainer.getHelperText().toString().isEmpty() &&
                        binding.phoneContainer.getHelperText() == null || binding.phoneContainer.getHelperText().toString().isEmpty()) {
                    User user = UserPrefManager.getInstance(getActivity()).getUser();
                    addAddressViewModel.addAddress(user.get_id(), homeNumber, street, district, city, phoneNumber, consigneeName);
                }
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
        }  else  {
            binding.phoneContainer.setHelperText("");
        }
    }

    @Override
    protected FragmentAddAddressBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentAddAddressBinding.inflate(inflater, container, false);
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