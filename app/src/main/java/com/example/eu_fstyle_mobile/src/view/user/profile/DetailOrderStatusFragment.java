package com.example.eu_fstyle_mobile.src.view.user.profile;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.databinding.DialogUpdateStatusOrderBinding;
import com.example.eu_fstyle_mobile.databinding.FragmentDetailOrderStatusBinding;
import com.example.eu_fstyle_mobile.src.adapter.OrderAdminProductAdapter;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;
import com.example.eu_fstyle_mobile.src.model.ListOrderID;
import com.example.eu_fstyle_mobile.src.model.Orders;
import com.example.eu_fstyle_mobile.src.request.RequestUpdateStatus;
import com.example.eu_fstyle_mobile.src.retrofit.ApiClient;
import com.example.eu_fstyle_mobile.src.retrofit.ApiService;
import com.example.eu_fstyle_mobile.src.view.admin.DetailBillFragment;
import com.example.eu_fstyle_mobile.src.view.admin.DetailBillViewModel;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailOrderStatusFragment extends BaseFragment<FragmentDetailOrderStatusBinding> {
    private static final String ORDERS = "ORDERS";
    private DetailBillViewModel detailBillViewModel;
    private String orders;

    public static DetailOrderStatusFragment newInstance(String orders) {
        DetailOrderStatusFragment fragment = new DetailOrderStatusFragment();
        Bundle args = new Bundle();
        args.putString(ORDERS, orders);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailOrderStatusBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showHintOrderDialog();
        detailBillViewModel = new ViewModelProvider(this).get(DetailBillViewModel.class);
        orders = getArguments().getString(ORDERS);
        detailBillViewModel.getOrderID(orders);
        observeViewModel();
        initView();
    }

    private void initView() {
        binding.icBack.setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });
        binding.icHint.setOnClickListener(v -> {
            getHintOrderDialog();
        });
    }

    private void observeViewModel() {
        detailBillViewModel.getListOrderID().observe(getViewLifecycleOwner(), new Observer<ListOrderID>() {
            @Override
            public void onChanged(ListOrderID listOrderID) {
                Orders order = listOrderID.getOrder();
                binding.tvOrderQuantum.setText(String.valueOf(order.getListProduct().size()));
                binding.tvOrderId.setText(order.get_id());
                if (order.getStatus().equals("active")) {
                    binding.tvOrderStatus.setText("Xác nhận");
                    binding.tvOrderStatus.setTextColor(Color.parseColor("#00CC00"));
                    binding.tvOrderStatus.setBackgroundResource(R.drawable.bg_order_active);
                } else if (order.getStatus().equals("deactive")) {
                    binding.tvOrderStatus.setText("Đã hủy");
                    binding.tvOrderStatus.setTextColor(Color.parseColor("#000000"));
                    binding.tvOrderStatus.setBackgroundResource(R.drawable.bg_order_deactive);
                } else if (order.getStatus().equals("pending")) {
                    binding.tvOrderStatus.setText("Chờ xác nhận");
                    binding.tvOrderStatus.setTextColor(Color.parseColor("#C67C4E"));
                    binding.tvOrderStatus.setBackgroundResource(R.drawable.bg_order_pending);
                } else if (order.getStatus().equals("trading")) {
                    binding.tvOrderStatus.setText("Đang giao hàng");
                    binding.tvOrderStatus.setTextColor(Color.parseColor("#FFA500"));
                    binding.tvOrderStatus.setBackgroundResource(R.drawable.bg_order_trading);
                } else if (order.getStatus().equals("success")) {
                    binding.tvOrderStatus.setText("Đã giao hàng");
                    binding.tvOrderStatus.setTextColor(Color.parseColor("#FF0000"));
                    binding.tvOrderStatus.setBackgroundResource(R.drawable.bg_order_success);
                }
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
                inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                Date date = null;
                try {
                    date = inputFormat.parse(order.getTimeOrder());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
                String formattedDate = outputFormat.format(date);

                binding.tvOrderTime.setText(formattedDate);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                double totalPrice = Double.parseDouble(order.getTotalPrice());
                binding.tvOrderTotalPrice.setText(decimalFormat.format(totalPrice) + " VNĐ");
                binding.tvOrderConsigneeName.setText(order.getCustomerName());
                binding.tvOrderPhone.setText(order.getPhone());
                binding.tvOrderAddress.setText(order.getAddress());
                if (order.getPaymentMethods().equals("COD")) {
                    binding.tvOrderPaymentMethod.setText("Thanh toán khi nhận hàng");
                    binding.imgSelectPayment.setImageDrawable(requireContext().getDrawable(R.drawable.ic_payment_money));
                }
                if (order.getPaymentMethods().equals("Sandbox")) {
                    binding.tvOrderPaymentMethod.setText("Thanh toán khi nhận hàng");
                    binding.imgSelectPayment.setImageDrawable(requireContext().getDrawable(R.drawable.ic_payment_money));
                }
                if (order.getShippingMethod().equals("express")) {
                    binding.tvOrderShippingMethod.setText("Giao hàng nhanh");
                    binding.tvOrderShipPrice.setText("15.000 VNĐ");
                }
                if (order.getShippingMethod().equals("standard")) {
                    binding.tvOrderShippingMethod.setText("Giao hàng tiết kiệm");
                    binding.tvOrderShipPrice.setText("5.000 VNĐ");
                }
                binding.rcvOrderProduct.setAdapter(new OrderAdminProductAdapter(order.getListProduct()));
                binding.tvDetailOrderTotalPrice.setText(decimalFormat.format(totalPrice) + " VNĐ");
                if (order.getStatus().equals("deactive") || order.getStatus().equals("trading") || order.getStatus().equals("delivered")) {
                    setDisableCancelOrder();
                } else {
                    setEnableCancelOrder();
                }
                double productPrice = calculateProductPrice(order);
                binding.tvOrderProductPrice.setText(decimalFormat.format(productPrice) + " VNĐ");
                binding.btnCancelOrder.setOnClickListener(v -> {
                    showDialog("Hủy đơn hàng", "Bạn có chắc chắn muốn hủy đơn hàng này không?", new Runnable() {
                        @Override
                        public void run() {
                            ApiService apiService = ApiClient.getClient().create(ApiService.class);
                            RequestUpdateStatus requestUpdateStatus = new RequestUpdateStatus("deactive");
                            Call<Orders> call = apiService.updateOrderStatus(order.get_id(), requestUpdateStatus);
                            call.enqueue(new Callback<Orders>() {
                                @Override
                                public void onResponse(Call<Orders> call, Response<Orders> response) {
                                    if (response.isSuccessful()) {
                                        Toast.makeText(getActivity(), "Đã hủy đơn hàng thành công", Toast.LENGTH_SHORT).show();
                                        binding.tvOrderStatus.setText("Đã hủy");
                                        binding.tvOrderStatus.setTextColor(Color.parseColor("#000000"));
                                        binding.tvOrderStatus.setBackgroundResource(R.drawable.bg_order_deactive);
                                        setDisableCancelOrder();
                                    } else {
                                        Toast.makeText(getActivity(), "Thay đổi trạng thái đơn hàng thất bại", Toast.LENGTH_SHORT).show();
                                        setEnableCancelOrder();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Orders> call, Throwable t) {
                                    Toast.makeText(getActivity(), "Server error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                });
            }
        });
        detailBillViewModel.getErrorMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null) {
                    Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void showHintOrderDialog() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("app_prefs", MODE_PRIVATE);
        boolean isFirstRun = sharedPreferences.getBoolean("isFirstHintOrder", true);

        if (isFirstRun) {
            getHintOrderDialog();
            sharedPreferences.edit().putBoolean("isFirstHintOrder", false).apply();
        }
    }

    private void getHintOrderDialog() {
        Dialog dialog = new Dialog(getContext());
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.setContentView(R.layout.hint_order_layout);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_dialog_background));

        Button btnConfirm = dialog.findViewById(R.id.btn_hint_ok);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void setDisableCancelOrder() {
        binding.btnCancelOrder.setEnabled(false);
        binding.btnCancelOrder.setAlpha(0.4f);
    }

    private void setEnableCancelOrder() {
        binding.btnCancelOrder.setEnabled(true);
        binding.btnCancelOrder.setAlpha(1f);
    }

    private double calculateProductPrice(Orders order) {
        double totalPrice = Double.parseDouble(order.getTotalPrice());
        double shippingCost = 0.0;

        if (order.getShippingMethod().equals("express")) {
            shippingCost = 15000.0;
        } else if (order.getShippingMethod().equals("standard")) {
            shippingCost = 5000.0;
        }

        return totalPrice - shippingCost;
    }

    @Override
    protected FragmentDetailOrderStatusBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentDetailOrderStatusBinding.inflate(inflater, container, false);
    }
}