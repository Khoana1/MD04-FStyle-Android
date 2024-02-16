package com.example.eu_fstyle_mobile.src.view.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eu_fstyle_mobile.databinding.FragmentEditAddressBinding;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;

public class EditAddressFragment extends BaseFragment<FragmentEditAddressBinding> {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = getFragmentBinding(inflater, container);
        return binding.getRoot();
    }

    @Override
    protected FragmentEditAddressBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentEditAddressBinding.inflate(inflater, container, false);
    }
}