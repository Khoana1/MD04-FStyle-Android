package com.example.eu_fstyle_mobile.src.view.user.payment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.databinding.FragmentConfirmPaymentBinding;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;
import com.example.eu_fstyle_mobile.src.model.Cart;

import java.util.HashMap;
import java.util.Map;

public class ConfirmPaymentFragment extends BaseFragment<FragmentConfirmPaymentBinding> {
    public static final String CART = "CART";
    public static final String PAYMENT_METHOD = "PAYMENT";
    public static ConfirmPaymentFragment newInstance(Cart cart, String paymentMethod) {
        ConfirmPaymentFragment fragment = new ConfirmPaymentFragment();
        Bundle args = new Bundle();
        args.putSerializable(CART, cart);
        args.putString(PAYMENT_METHOD, paymentMethod);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = getFragmentBinding(inflater, container);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        Cart cart = (Cart) getArguments().getSerializable(CART);
        binding.tvTotalPrice.setText(String.format(cart.getTotalCart().toString()+" VNĐ"));
        String paymentMethod = getArguments().getString(PAYMENT_METHOD);
        switch (paymentMethod) {
            case "COD":
                binding.tvPaymentMethod.setText("COD");
                binding.icPaymentMethod.setImageDrawable(getResources().getDrawable(R.drawable.ic_payment_money));
                break;
            case "Momo":
                binding.tvPaymentMethod.setText("Thanh toán qua Momo");
                binding.icPaymentMethod.setImageDrawable(getResources().getDrawable(R.drawable.ic_momo));
                break;
            default:
                break;
        }
        binding.btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (paymentMethod) {
                    case "COD":
                        showLoadingDialog();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                hideLoadingDialog();
                                PaymentSuccessFragment paymentSuccessFragment = PaymentSuccessFragment.newInstance(cart);
                                openScreenHome(paymentSuccessFragment, false);
                            }
                        }, 2000);
                        break;
                    case "Momo":
//                        AppMoMoLib.getInstance().setEnvironment(AppMoMoLib.ENVIRONMENT.DEBUG);
//                        Map<String, Object> eventValue = new HashMap<>();
//                        eventValue.put("merchantname", "Tên đối tác");
//                        eventValue.put("merchantcode", "Mã đối tác");
//                        eventValue.put("amount", cart.getTotalCart());
//                        eventValue.put("orderId", "ID Đơn hàng");
//                        eventValue.put("orderLabel", "Nhãn Đơn hàng");
//                        eventValue.put("fee", 0);
//                        eventValue.put("description", "Mô tả");
//
//                        AppMoMoLib.getInstance().requestMoMoCallBack(getActivity(), eventValue);
                        break;
                    default:
                        break;

                }
            }
        });
    }

    @Override
    protected FragmentConfirmPaymentBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentConfirmPaymentBinding.inflate(inflater, container, false);
    }
}