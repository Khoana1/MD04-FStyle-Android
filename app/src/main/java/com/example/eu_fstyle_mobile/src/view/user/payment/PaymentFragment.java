package com.example.eu_fstyle_mobile.src.view.user.payment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.databinding.FragmentPaymentBinding;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;


public class PaymentFragment extends BaseFragment<FragmentPaymentBinding> {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPaymentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "zzzzz", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected FragmentPaymentBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentPaymentBinding.inflate(inflater, container, false);
    }
}