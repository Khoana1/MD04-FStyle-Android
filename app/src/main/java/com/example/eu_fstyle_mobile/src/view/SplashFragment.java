package com.example.eu_fstyle_mobile.src.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eu_fstyle_mobile.databinding.FragmentSplashFragmetBinding;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;

public class SplashFragment extends BaseFragment<FragmentSplashFragmetBinding> {

    @Override
    protected FragmentSplashFragmetBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentSplashFragmetBinding.inflate(inflater, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}