package com.example.eu_fstyle_mobile.src.view.admin;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.databinding.FragmentProfileAdminBinding;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileAdminFragment extends BaseFragment<FragmentProfileAdminBinding> {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = getFragmentBinding(inflater, container);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected FragmentProfileAdminBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentProfileAdminBinding.inflate(inflater, container, false);
    }
}