package com.example.eu_fstyle_mobile.src.view.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eu_fstyle_mobile.databinding.FragmentHomeAdminBinding;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;

public class HomeAdminFragment extends BaseFragment<FragmentHomeAdminBinding> {
    @Override
    protected FragmentHomeAdminBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentHomeAdminBinding.inflate(inflater, container, false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = getFragmentBinding(inflater, container);
        return binding.getRoot();
    }
}
