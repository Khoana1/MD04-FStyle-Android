package com.example.eu_fstyle_mobile.src.view.user.payment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eu_fstyle_mobile.databinding.FragmentPaymentBinding;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;

public class PaymentFragment extends BaseFragment<FragmentPaymentBinding> {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = getFragmentBinding(inflater, container);
        return binding.getRoot();
    }

    @Override
    protected FragmentPaymentBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentPaymentBinding.inflate(inflater, container, false);
    }
}