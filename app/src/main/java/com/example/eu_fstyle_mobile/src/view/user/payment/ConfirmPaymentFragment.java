package com.example.eu_fstyle_mobile.src.view.user.payment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.databinding.FragmentConfirmPaymentBinding;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;

public class ConfirmPaymentFragment extends BaseFragment<FragmentConfirmPaymentBinding> {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = getFragmentBinding(inflater, container);
        return binding.getRoot();
    }

    @Override
    protected FragmentConfirmPaymentBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentConfirmPaymentBinding.inflate(inflater, container, false);
    }
}