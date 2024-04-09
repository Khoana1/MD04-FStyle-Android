package com.example.eu_fstyle_mobile.src.view.user.payment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.databinding.BottomSheetChoosePaymentBinding;
import com.example.eu_fstyle_mobile.databinding.BottomSheetHinhthucvcBinding;
import com.example.eu_fstyle_mobile.databinding.FragmentPaymentBinding;
import com.example.eu_fstyle_mobile.src.adapter.PaymentProductAdapter;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;
import com.example.eu_fstyle_mobile.src.model.Address;
import com.example.eu_fstyle_mobile.src.model.Cart;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PaymentFragment extends BaseFragment<FragmentPaymentBinding> implements AddressSelectionListener {
    public static final String CART = "CART";
    private AddressBottomSheetDialogFragment bottomSheet;
    private BottomSheetHinhthucvcBinding binding1;
    private Cart cart;
    private String totalPayment;
    private String paymentMethod = "COD";
    private String paymentAddress;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = getFragmentBinding(inflater, container);
        return binding.getRoot();
    }

    public static PaymentFragment newInstance(Cart cart) {
        PaymentFragment fragment = new PaymentFragment();
        Bundle args = new Bundle();
        args.putSerializable(CART, cart);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        cart = (Cart) getArguments().getSerializable(CART);
        binding.tvDetailQuantumPayment.setText(String.valueOf(cart.getListProduct().size()));
        binding.tvTotalPayment.setText(cart.getTotalCart().toString() + " VNĐ");
        getTotalPaymentDetail();
        binding.rcvPayment.setAdapter(new PaymentProductAdapter(cart.getListProduct()));
        binding.tvEditItemAddress.setOnClickListener(v -> {
            List<Address> addressList = new ArrayList<>();
            bottomSheet = new AddressBottomSheetDialogFragment(this, addressList);
            bottomSheet.show(getChildFragmentManager(), "AddressBottomSheetDialog");
        });
        binding.rltSelectShip.setOnClickListener(v -> {
            binding1 = BottomSheetHinhthucvcBinding.inflate(getLayoutInflater());
            BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
            View bottomView = binding1.getRoot();
            dialog.setContentView(bottomView);

            ViewGroup.LayoutParams params = bottomView.getLayoutParams();
            params.height = (int) (getResources().getDisplayMetrics().heightPixels * 0.5);
            bottomView.setLayoutParams(params);

            binding1.constraintLayoutNhanh.setOnClickListener(v1 -> {
                binding.tvTitleShip.setText("Nhanh");
                binding.tvPriceShip.setText("15,000");
                binding.tvTimeShip.setText("3 ngày");
                getTotalPaymentDetail();
                dialog.dismiss();
            });
            binding1.constraintLayoutTietkiem.setOnClickListener(v1 -> {
                binding.tvTitleShip.setText("Tiết kiệm");
                binding.tvPriceShip.setText("5,000");
                binding.tvTimeShip.setText("7 ngày");
                getTotalPaymentDetail();
                dialog.dismiss();
            });
            dialog.show();
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogBottomSheetAnimation;
            dialog.getWindow().setGravity(Gravity.BOTTOM);
        });
        binding.rltSelectPayment.setOnClickListener(v -> {
            showSelectPaymentBottomSheet();
        });
        binding.btnPayment.setOnClickListener(v -> {
            if(binding.tvConsigneeName.getText() == "Tên người nhận hàng" || binding.tvPhone.getText() == "Số điện thoại người nhận hàng" || binding.tvAddress.getText() == "Địa chỉ người nhận hàng") {
                showAlertDialog("Vui lòng chọn địa chỉ giao hàng");
            } else {
                showLoadingDialog();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideLoadingDialog();
                        Intent intent = new Intent(requireContext(), ConfirmPaymentActivity.class);
                        intent.putExtra(CART, cart);
                        intent.putExtra("PAYMENT_METHOD", paymentMethod);
                        intent.putExtra("TOTAL", totalPayment);
                        intent.putExtra("PAYMENT_ADDRESS", paymentAddress);
                        startActivity(intent);
                    }
                }, 2000);

            }

        });
    }

    @Override
    protected FragmentPaymentBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentPaymentBinding.inflate(inflater, container, false);
    }

    private void initData() {
        binding.tvConsigneeName.setText("Tên người nhận hàng");
        binding.tvPhone.setText("Số điện thoại người nhận hàng");
        binding.tvAddress.setText("Địa chỉ người nhận hàng");
        binding.tvEditItemAddress.setText("Chọn địa chỉ");
    }

    @Override
    public void onAddressSelected(Address address) {
        binding.tvConsigneeName.setText(address.getConsigneeName());
        binding.tvPhone.setText(address.getPhoneNumber());
        String completeAddress = address.getHomeNumber() + " " + address.getStreet() + ", " + address.getDistrict() + ", " + address.getCity();
        binding.tvAddress.setText(completeAddress);
        paymentAddress = completeAddress;
        bottomSheet.dismiss();
        binding.tvEditItemAddress.setText("Đổi địa chỉ");
    }

    private void selectZaloPayment() {
        binding.tvSelectPayment.setText("ZaloPay");
        binding.imgSelectPayment.setImageDrawable(requireContext().getDrawable(R.drawable.ic_zalo_pay));
        paymentMethod = "ZALO";
    }

    private void selectShipCodPayment() {
        binding.tvSelectPayment.setText("COD");
        binding.imgSelectPayment.setImageDrawable(requireContext().getDrawable(R.drawable.ic_payment_money));
        paymentMethod = "COD";
    }

    private void showSelectPaymentBottomSheet() {
        final Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        BottomSheetChoosePaymentBinding binding = BottomSheetChoosePaymentBinding.inflate(getLayoutInflater());
        dialog.setContentView(binding.getRoot());
        binding.lnZalo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             selectZaloPayment();
             dialog.dismiss();
            }
        });
        binding.lnShipCod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectShipCodPayment();
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogBottomSheetAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void getTotalPaymentDetail() {
        String shippingFeeStr = binding.tvPriceShip.getText().toString().replaceAll("[^0-9]", "");
        int shippingFee = Integer.parseInt(shippingFeeStr);
        int totalCart = cart.getTotalCart().intValue();
        int totalPayment = totalCart + shippingFee;
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        binding.tvTotalPaymentDetail.setText(decimalFormat.format(totalPayment) + " VNĐ");
        this.totalPayment = String.valueOf(totalPayment);
    }


}