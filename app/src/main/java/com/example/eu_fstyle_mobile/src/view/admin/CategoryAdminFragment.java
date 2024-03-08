package com.example.eu_fstyle_mobile.src.view.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.databinding.FragmentCategoryAdminBinding;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;

public class CategoryAdminFragment extends BaseFragment<FragmentCategoryAdminBinding> {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = getFragmentBinding(inflater, container);
        return binding.getRoot();
    }

    @Override
    protected FragmentCategoryAdminBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentCategoryAdminBinding.inflate(inflater, container, false);
    }
}