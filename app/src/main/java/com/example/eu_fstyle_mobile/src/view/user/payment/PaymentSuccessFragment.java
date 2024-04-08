package com.example.eu_fstyle_mobile.src.view.user.payment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.eu_fstyle_mobile.databinding.FragmentConfirmPaymentBinding;
import com.example.eu_fstyle_mobile.databinding.FragmentPaymentSuccessBinding;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;
import com.example.eu_fstyle_mobile.src.model.Cart;
import com.example.eu_fstyle_mobile.src.view.user.home.HomeFragment;

public class PaymentSuccessFragment extends BaseFragment<FragmentPaymentSuccessBinding> {
    public static final String CART = "CART";
    public static PaymentSuccessFragment newInstance(Cart cart) {
        PaymentSuccessFragment fragment = new PaymentSuccessFragment();
        Bundle args = new Bundle();
        args.putSerializable(CART, cart);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected FragmentPaymentSuccessBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentPaymentSuccessBinding.inflate(inflater, container, false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = getFragmentBinding(inflater, container);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initListener();
    }

    private void initListener() {
        binding.btnBackToHome.setOnClickListener(v -> {
            openScreenHome(new HomeFragment(), false);
        });
        binding.tvGoCart.setOnClickListener(v -> {
            openScreenHome(new CartFragment(), false);
        });
    }

    private void initData() {
        Cart cart = (Cart) getArguments().getSerializable(CART);
        binding.tvTotalPaymentSuccess.setText(String.format(cart.getTotalCart().toString()+" VNĐ"));
    }
}
