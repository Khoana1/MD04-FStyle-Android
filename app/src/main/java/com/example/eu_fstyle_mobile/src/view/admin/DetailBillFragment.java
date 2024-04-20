package com.example.eu_fstyle_mobile.src.view.admin;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
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
import com.example.eu_fstyle_mobile.databinding.FragmentDetailBillBinding;
import com.example.eu_fstyle_mobile.src.adapter.OrderAdminProductAdapter;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;
import com.example.eu_fstyle_mobile.src.model.Address;
import com.example.eu_fstyle_mobile.src.model.ListOrderID;
import com.example.eu_fstyle_mobile.src.model.Orders;
import com.example.eu_fstyle_mobile.src.request.RequestUpdateStatus;
import com.example.eu_fstyle_mobile.src.retrofit.ApiClient;
import com.example.eu_fstyle_mobile.src.retrofit.ApiService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailBillFragment extends BaseFragment<FragmentDetailBillBinding> {
    private static final String ORDERS = "ORDERS";
    private DetailBillViewModel detailBillViewModel;
    private String orders;

    public static DetailBillFragment newInstance(String orders) {
        DetailBillFragment fragment = new DetailBillFragment();
        Bundle args = new Bundle();
        args.putString(ORDERS, orders);
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
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), onBackPressedCallback);
        showHintOrderDialog();
        detailBillViewModel = new ViewModelProvider(this).get(DetailBillViewModel.class);
        orders = getArguments().getString(ORDERS);
        detailBillViewModel.getOrderID(orders);
        observeViewModel();
        initView();
    }

    private void showHintOrderDialog() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("app_prefs", MODE_PRIVATE);
        boolean isFirstRun = sharedPreferences.getBoolean("isFirstHintOrder", true);

        if (isFirstRun) {
            getHintOrderDialog();
            sharedPreferences.edit().putBoolean("isFirstHintOrder", false).apply();
        }
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
                binding.tvOrderTotalPrice.setText(order.getTotalPrice() + " VNĐ");
                binding.tvOrderConsigneeName.setText(order.getIdUser());
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
                binding.btnChangeOrderStatus.setOnClickListener(v -> {
                    Dialog dialog = new Dialog(getContext());
                    DialogUpdateStatusOrderBinding dialogBinding = DialogUpdateStatusOrderBinding.inflate(LayoutInflater.from(getContext()));
                    dialogBinding.tvOrderActive.setOnClickListener(v1 -> {
                        ApiService apiService = ApiClient.getClient().create(ApiService.class);
                        RequestUpdateStatus requestUpdateStatus = new RequestUpdateStatus("active");
                        Call<Orders> call = apiService.updateOrderStatus(order.get_id(), requestUpdateStatus);
                        call.enqueue(new Callback<Orders>() {
                            @Override
                            public void onResponse(Call<Orders> call, Response<Orders> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(getActivity(), "Đã thay đổi trạng thái đơn hàng thành công", Toast.LENGTH_SHORT).show();
                                    binding.tvOrderStatus.setText("Xác nhận");
                                    binding.tvOrderStatus.setTextColor(Color.parseColor("#00CC00"));
                                    binding.tvOrderStatus.setBackgroundResource(R.drawable.bg_order_active);
                                } else {
                                    Toast.makeText(getActivity(), "Thay đổi trạng thái đơn hàng thất bại", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Orders> call, Throwable t) {
                                Toast.makeText(getActivity(), "Server error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        dialog.dismiss();
                    });
                    dialogBinding.tvOrderDeactive.setOnClickListener(v1 -> {
                        ApiService apiService = ApiClient.getClient().create(ApiService.class);
                        RequestUpdateStatus requestUpdateStatus = new RequestUpdateStatus("deactive");
                        Call<Orders> call = apiService.updateOrderStatus(order.get_id(), requestUpdateStatus);
                        call.enqueue(new Callback<Orders>() {
                            @Override
                            public void onResponse(Call<Orders> call, Response<Orders> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(getActivity(), "Đã thay đổi trạng thái đơn hàng thành công", Toast.LENGTH_SHORT).show();
                                    binding.tvOrderStatus.setText("Đã hủy");
                                    binding.tvOrderStatus.setTextColor(Color.parseColor("#000000"));
                                    binding.tvOrderStatus.setBackgroundResource(R.drawable.bg_order_deactive);
                                } else {
                                    Toast.makeText(getActivity(), "Thay đổi trạng thái đơn hàng thất bại", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Orders> call, Throwable t) {
                                Toast.makeText(getActivity(), "Server error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        dialog.dismiss();
                    });
                    dialogBinding.tvOrderPending.setOnClickListener(v1 -> {
                        ApiService apiService = ApiClient.getClient().create(ApiService.class);
                        RequestUpdateStatus requestUpdateStatus = new RequestUpdateStatus("pending");
                        Call<Orders> call = apiService.updateOrderStatus(order.get_id(), requestUpdateStatus);
                        call.enqueue(new Callback<Orders>() {
                            @Override
                            public void onResponse(Call<Orders> call, Response<Orders> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(getActivity(), "Đã thay đổi trạng thái đơn hàng thành công", Toast.LENGTH_SHORT).show();
                                    binding.tvOrderStatus.setText("Chờ xác nhận");
                                    binding.tvOrderStatus.setTextColor(Color.parseColor("#C67C4E"));
                                    binding.tvOrderStatus.setBackgroundResource(R.drawable.bg_order_pending);
                                } else {
                                    Toast.makeText(getActivity(), "Thay đổi trạng thái đơn hàng thất bại", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Orders> call, Throwable t) {
                                Toast.makeText(getActivity(), "Server error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        dialog.dismiss();
                    });
                    dialogBinding.tvOrderTrading.setOnClickListener(v1 -> {
                        ApiService apiService = ApiClient.getClient().create(ApiService.class);
                        RequestUpdateStatus requestUpdateStatus = new RequestUpdateStatus("trading");
                        Call<Orders> call = apiService.updateOrderStatus(order.get_id(), requestUpdateStatus);
                        call.enqueue(new Callback<Orders>() {
                            @Override
                            public void onResponse(Call<Orders> call, Response<Orders> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(getActivity(), "Đã thay đổi trạng thái đơn hàng thành công", Toast.LENGTH_SHORT).show();
                                    binding.tvOrderStatus.setText("Đang giao hàng");
                                    binding.tvOrderStatus.setTextColor(Color.parseColor("#FFA500"));
                                    binding.tvOrderStatus.setBackgroundResource(R.drawable.bg_order_trading);
                                } else {
                                    Toast.makeText(getActivity(), "Thay đổi trạng thái đơn hàng thất bại", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Orders> call, Throwable t) {
                                Toast.makeText(getActivity(), "Server error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        dialog.dismiss();
                    });
                    dialogBinding.tvOrderDelivered.setOnClickListener(
                            v1 -> {
                                ApiService apiService = ApiClient.getClient().create(ApiService.class);
                                RequestUpdateStatus requestUpdateStatus = new RequestUpdateStatus("delivered");
                                Call<Orders> call = apiService.updateOrderStatus(order.get_id(), requestUpdateStatus);
                                call.enqueue(new Callback<Orders>() {
                                    @Override
                                    public void onResponse(Call<Orders> call, Response<Orders> response) {
                                        if (response.isSuccessful()) {
                                            Toast.makeText(getActivity(), "Đã thay đổi trạng thái đơn hàng thành công", Toast.LENGTH_SHORT).show();
                                            binding.tvOrderStatus.setText("Đã giao hàng");
                                            binding.tvOrderStatus.setTextColor(Color.parseColor("#FF0000"));
                                            binding.tvOrderStatus.setBackgroundResource(R.drawable.bg_order_success);
                                        } else {
                                            Toast.makeText(getActivity(), "Thay đổi trạng thái đơn hàng thất bại", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Orders> call, Throwable t) {
                                        Toast.makeText(getActivity(), "Server error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                                dialog.dismiss();
                            });
                    dialog.setContentView(dialogBinding.getRoot());
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                    dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_dialog_background));
                    dialog.show();

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

    private void initView() {
        binding.icBack.setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });
        binding.icHint.setOnClickListener(v -> {
            getHintOrderDialog();
        });
    }

    @Override
    protected FragmentDetailBillBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentDetailBillBinding.inflate(inflater, container, false);
    }

    private final OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNavigation);
            bottomNavigationView.setVisibility(View.VISIBLE);
            openScreenAdmin(new BillAdminFragment(), false);
        }
    };

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

}