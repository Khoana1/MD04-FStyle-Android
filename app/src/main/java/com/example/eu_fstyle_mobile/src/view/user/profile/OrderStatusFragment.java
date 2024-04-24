package com.example.eu_fstyle_mobile.src.view.user.profile;

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
import com.example.eu_fstyle_mobile.databinding.FragmentOrderStatusBinding;
import com.example.eu_fstyle_mobile.src.adapter.MyOrderStatusAdapter;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;
import com.example.eu_fstyle_mobile.src.model.ListOrder;
import com.example.eu_fstyle_mobile.src.model.Orders;
import com.example.eu_fstyle_mobile.src.model.User;
import com.example.eu_fstyle_mobile.ultilties.UserPrefManager;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class OrderStatusFragment extends BaseFragment<FragmentOrderStatusBinding> implements MyOrderStatusAdapter.OnItemOrderClickListener {
    private OrderStatusViewModel orderStatusViewModel;

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
        orderStatusViewModel = new ViewModelProvider(this).get(OrderStatusViewModel.class);
        User user = UserPrefManager.getInstance(getActivity()).getUser();
        orderStatusViewModel.getOrderByUserID(user.get_id());
        observeViewModel();
    }

    private void initView() {
        binding.icBack.setOnClickListener(v -> {
            getActivity().onBackPressed();
        });
        binding.rltFilterOrder.setVisibility(View.GONE);
        binding.tvCancelFilterOrder.setOnClickListener(v -> {
            binding.rltFilterOrder.setVisibility(View.GONE);
            orderStatusViewModel.getOrderByUserID(UserPrefManager.getInstance(getActivity()).getUser().get_id());
        });
    }

    private void observeViewModel() {
        orderStatusViewModel.getListOrder().observe(getViewLifecycleOwner(), new Observer<ListOrder>() {
            @Override
            public void onChanged(ListOrder listOrder) {
                updateOrder(listOrder.getOrders());
                binding.icFilter.setOnClickListener(v -> {
                    BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
                    BottomSheetFilterOrderBinding bottomSheetFilterOrderBinding = BottomSheetFilterOrderBinding.inflate(LayoutInflater.from(getContext()));
                    bottomSheetDialog.setContentView(bottomSheetFilterOrderBinding.getRoot());
                    bottomSheetFilterOrderBinding.tvOrderAll.setOnClickListener(v1 -> {
                        binding.rcvOrderStatus.setAdapter(new MyOrderStatusAdapter(listOrder.getOrders(), OrderStatusFragment.this));
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
                        binding.tvFilterOrder.setText("Lọc theo: Đơn hàng Đã xác nhận");
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
                        binding.tvFilterOrder.setText("Lọc theo: Đơn hàng Đã hủy");
                        bottomSheetDialog.dismiss();
                    });
                    bottomSheetFilterOrderBinding.tvOrderPending.setOnClickListener(v1 -> {
                        List<Orders> pendingOrders = new ArrayList<>();
                        for (Orders order : listOrder.getOrders()) {
                            if (order.getStatus().equals("pending")) {
                                pendingOrders.add(order);
                            }
                        }
                        updateOrder(pendingOrders);
                        binding.rltFilterOrder.setVisibility(View.VISIBLE);
                        binding.tvFilterOrder.setText("Lọc theo: Đơn hàng Chờ xác nhận");
                        bottomSheetDialog.dismiss();
                    });
                    bottomSheetFilterOrderBinding.tvOrderTrading.setOnClickListener(v1 -> {
                        List<Orders> tradingOrders = new ArrayList<>();
                        for (Orders order : listOrder.getOrders()) {
                            if (order.getStatus().equals("trading")) {
                                tradingOrders.add(order);
                            }
                        }
                        updateOrder(tradingOrders);
                        binding.rltFilterOrder.setVisibility(View.VISIBLE);
                        binding.tvFilterOrder.setText("Lọc theo: Đơn hàng Đang giao");
                        bottomSheetDialog.dismiss();
                    });
                    bottomSheetFilterOrderBinding.tvOrderDelivered.setOnClickListener(v1 -> {
                        List<Orders> deliveredOrders = new ArrayList<>();
                        for (Orders order : listOrder.getOrders()) {
                            if (order.getStatus().equals("delivered")) {
                                deliveredOrders.add(order);
                            }
                        }
                        updateOrder(deliveredOrders);
                        binding.rltFilterOrder.setVisibility(View.VISIBLE);
                        binding.tvFilterOrder.setText("Lọc theo: Đơn hàng Đã giao");
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
                        binding.rcvOrderStatus.setAdapter(new MyOrderStatusAdapter(sortedOrders, OrderStatusFragment.this));
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
                        binding.rcvOrderStatus.setAdapter(new MyOrderStatusAdapter(sortedOrders, OrderStatusFragment.this));
                        binding.rltFilterOrder.setVisibility(View.VISIBLE);
                        binding.tvFilterOrder.setText("Lọc theo: Cũ nhất → Mới nhất");
                        bottomSheetDialog.dismiss();
                    });
                    bottomSheetDialog.show();
                });
            }
        });

        orderStatusViewModel.getErrorMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected FragmentOrderStatusBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentOrderStatusBinding.inflate(inflater, container, false);
    }

    private void updateOrder(List<Orders> orders) {
        if (orders.isEmpty()) {
            binding.viewEmpty.setVisibility(View.VISIBLE);
            binding.rcvOrderStatus.setVisibility(View.GONE);
        } else {
            binding.viewEmpty.setVisibility(View.GONE);
            binding.rcvOrderStatus.setVisibility(View.VISIBLE);
            binding.rcvOrderStatus.setAdapter(new MyOrderStatusAdapter(orders, OrderStatusFragment.this));
        }
    }

    @Override
    public void onOrderClick(String orderId) {
        DetailOrderStatusFragment detailOrderStatusFragment = DetailOrderStatusFragment.newInstance(orderId);
        openScreen(detailOrderStatusFragment, true);
    }
}