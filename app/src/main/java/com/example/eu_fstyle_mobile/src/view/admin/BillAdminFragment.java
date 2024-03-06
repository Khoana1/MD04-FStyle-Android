package com.example.eu_fstyle_mobile.src.view.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eu_fstyle_mobile.databinding.FragmentBillAdminBinding;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;

public class BillAdminFragment extends BaseFragment<FragmentBillAdminBinding> {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = getFragmentBinding(inflater, container);
        return binding.getRoot();
    }

    @Override
    protected FragmentBillAdminBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentBillAdminBinding.inflate(inflater, container, false);
    }
}