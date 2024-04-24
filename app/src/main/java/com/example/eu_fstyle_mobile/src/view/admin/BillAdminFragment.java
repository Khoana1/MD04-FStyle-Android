package com.example.eu_fstyle_mobile.src.view.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.eu_fstyle_mobile.databinding.BottomSheetFilterOrderBinding;
import com.example.eu_fstyle_mobile.databinding.FragmentBillAdminBinding;
import com.example.eu_fstyle_mobile.src.adapter.MyOrderStatusAdapter;
import com.example.eu_fstyle_mobile.src.adapter.OrderAdminAdapter;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;
import com.example.eu_fstyle_mobile.src.model.ListOrder;
import com.example.eu_fstyle_mobile.src.model.Orders;
import com.example.eu_fstyle_mobile.src.view.user.profile.OrderStatusFragment;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BillAdminFragment extends BaseFragment<FragmentBillAdminBinding> implements OrderAdminAdapter.OnItemOrderClickListener{
    private BillAdminViewModel billAdminViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = getFragmentBinding(inflater, container);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        billAdminViewModel = new ViewModelProvider(this).get(BillAdminViewModel.class);
        billAdminViewModel.getAllOrder();
        binding.rltFilterOrder.setVisibility(View.GONE);
        observeViewModel();
    }

    private void observeViewModel() {
        billAdminViewModel.getListOrder().observe(getViewLifecycleOwner(), new Observer<ListOrder>() {
            @Override
            public void onChanged(ListOrder listOrder) {
                if (listOrder != null) {
                    updateOrder(listOrder.getOrders());
                    binding.ivFilter.setOnClickListener(v -> {
                        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
                        BottomSheetFilterOrderBinding bottomSheetFilterOrderBinding = BottomSheetFilterOrderBinding.inflate(LayoutInflater.from(getContext()));
                        bottomSheetDialog.setContentView(bottomSheetFilterOrderBinding.getRoot());
                        bottomSheetFilterOrderBinding.tvOrderAll.setOnClickListener(v1 -> {
                            updateOrder(listOrder.getOrders());
                            binding.rltFilterOrder.setVisibility(View.GONE);
                            bottomSheetDialog.dismiss();
                        });
                        bottomSheetFilterOrderBinding.tvOrderActive.setOnClickListener(v1 -> {
                            List<Orders> activeOrders = new ArrayList<>();
                            for (Orders order : listOrder.getOrders()) {
                                if (order.getStatus().equals("active")) {
                                    activeOrders.add(order);
                                }
                            }
                            updateOrder(activeOrders);
                            binding.rltFilterOrder.setVisibility(View.VISIBLE);
                            binding.tvFilterOrder.setText("Lọc theo: Đơn hàng đã xác nhận");
                            bottomSheetDialog.dismiss();
                        });
                        bottomSheetFilterOrderBinding.tvOrderDeactive.setOnClickListener(v1 -> {
                            List<Orders> cancelOrders = new ArrayList<>();
                            for (Orders order : listOrder.getOrders()) {
                                if (order.getStatus().equals("deactive")) {
                                    cancelOrders.add(order);
                                }
                            }
                            updateOrder(cancelOrders);
                            binding.rltFilterOrder.setVisibility(View.VISIBLE);
                            binding.tvFilterOrder.setText("Lọc theo: Đơn hàng đã hủy");
                            bottomSheetDialog.dismiss();
                        });
                        bottomSheetFilterOrderBinding.tvOrderPending.setOnClickListener(v1 -> {
                            List<Orders> cancelOrders = new ArrayList<>();
                            for (Orders order : listOrder.getOrders()) {
                                if (order.getStatus().equals("pending")) {
                                    cancelOrders.add(order);
                                }
                            }
                            updateOrder(cancelOrders);
                            binding.rltFilterOrder.setVisibility(View.VISIBLE);
                            binding.tvFilterOrder.setText("Lọc theo: Đơn hàng chờ xác nhận");
                            bottomSheetDialog.dismiss();
                        });
                        bottomSheetFilterOrderBinding.tvOrderTrading.setOnClickListener(v1 -> {
                            List<Orders> cancelOrders = new ArrayList<>();
                            for (Orders order : listOrder.getOrders()) {
                                if (order.getStatus().equals("trading")) {
                                    cancelOrders.add(order);
                                }
                            }
                            updateOrder(cancelOrders);
                            binding.rltFilterOrder.setVisibility(View.VISIBLE);
                            binding.tvFilterOrder.setText("Lọc theo: Đơn hàng đang giao");
                            bottomSheetDialog.dismiss();
                        });
                        bottomSheetFilterOrderBinding.tvOrderDelivered.setOnClickListener(v1 -> {
                            List<Orders> cancelOrders = new ArrayList<>();
                            for (Orders order : listOrder.getOrders()) {
                                if (order.getStatus().equals("delivered")) {
                                    cancelOrders.add(order);
                                }
                            }
                            updateOrder(cancelOrders);
                            binding.rltFilterOrder.setVisibility(View.VISIBLE);
                            binding.tvFilterOrder.setText("Lọc theo: Đơn hàng đã giao");
                            bottomSheetDialog.dismiss();
                        });
                        bottomSheetFilterOrderBinding.btnNew.setOnClickListener(v1 -> {
                            List<Orders> sortedOrders = new ArrayList<>(listOrder.getOrders());
                            Collections.sort(sortedOrders, new Comparator<Orders>() {
                                @Override
                                public int compare(Orders o1, Orders o2) {
                                    return o2.getTimeOrder().compareTo(o1.getTimeOrder());
                                }
                            });
                            binding.rcvOrderAdmin.setAdapter(new OrderAdminAdapter(sortedOrders, BillAdminFragment.this));
                            binding.rltFilterOrder.setVisibility(View.VISIBLE);
                            binding.tvFilterOrder.setText("Lọc theo: Mới nhất → Cũ nhất");
                            bottomSheetDialog.dismiss();
                        });
                        bottomSheetFilterOrderBinding.btnOld.setOnClickListener(v1 -> {
                            List<Orders> sortedOrders = new ArrayList<>(listOrder.getOrders());
                            Collections.sort(sortedOrders, new Comparator<Orders>() {
                                @Override
                                public int compare(Orders o1, Orders o2) {
                                    return o1.getTimeOrder().compareTo(o2.getTimeOrder());
                                }
                            });
                            binding.rcvOrderAdmin.setAdapter(new OrderAdminAdapter(sortedOrders, BillAdminFragment.this));
                            binding.rltFilterOrder.setVisibility(View.VISIBLE);
                            binding.tvFilterOrder.setText("Lọc theo: Cũ nhất → Mới nhất");
                            bottomSheetDialog.dismiss();
                        });
                        bottomSheetDialog.show();
                    });
                }
            }
        });
        billAdminViewModel.getErrorMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null) {
                    Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected FragmentBillAdminBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentBillAdminBinding.inflate(inflater, container, false);
    }

    private void updateOrder(List<Orders> orders) {
        if (orders.isEmpty()) {
            binding.viewEmpty.setVisibility(View.VISIBLE);
            binding.rcvOrderAdmin.setVisibility(View.GONE);
        } else {
            binding.viewEmpty.setVisibility(View.GONE);
            binding.rcvOrderAdmin.setVisibility(View.VISIBLE);
            binding.rcvOrderAdmin.setAdapter(new OrderAdminAdapter(orders, BillAdminFragment.this));
        }
    }

    @Override
    public void onOrderClick(String orderID) {
        DetailBillFragment detailBillFragment = DetailBillFragment.newInstance(orderID);
        openScreenAddAdmin(detailBillFragment, true);
    }

}
