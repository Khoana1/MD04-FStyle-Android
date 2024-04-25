package com.example.eu_fstyle_mobile.src.view.admin;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
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
import com.example.eu_fstyle_mobile.ultilties.UserPrefManager;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
        binding.tvCancelFilterOrder.setOnClickListener(v -> {
            binding.rltFilterOrder.setVisibility(View.GONE);
            binding.llSearch.setVisibility(View.VISIBLE);
            billAdminViewModel.getAllOrder();
            if (binding.llSearchDate.getVisibility() == View.VISIBLE) {
                binding.llSearchDate.setVisibility(View.GONE);
                binding.llSearchOrder.setVisibility(View.VISIBLE);
            } else {
                binding.llSearchOrder.setVisibility(View.VISIBLE);
            }
        });
        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            billAdminViewModel.getAllOrder();
            binding.swipeRefreshLayout.setRefreshing(false);
            binding.rltFilterOrder.setVisibility(View.GONE);
            if (binding.llSearchDate.getVisibility() == View.VISIBLE) {
                binding.llSearchDate.setVisibility(View.GONE);
                binding.llSearchOrder.setVisibility(View.VISIBLE);
            } else {
                binding.llSearchOrder.setVisibility(View.VISIBLE);
            }
        });
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
                            binding.llSearch.setVisibility(View.VISIBLE);
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
                            binding.llSearch.setVisibility(View.GONE);
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
                            binding.llSearch.setVisibility(View.GONE);
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
                            binding.llSearch.setVisibility(View.GONE);
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
                            binding.llSearch.setVisibility(View.GONE);
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
                            binding.llSearch.setVisibility(View.GONE);
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
                            binding.llSearch.setVisibility(View.GONE);
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
                            binding.llSearch.setVisibility(View.GONE);
                            bottomSheetDialog.dismiss();
                        });
                        bottomSheetDialog.show();
                    });
                    binding.imgDeleteSearch.setVisibility(View.GONE);
                    binding.imgDeleteSearch.setOnClickListener(v -> {
                        binding.searchEditText.setText("");
                    });
                    binding.imgDeleteSearchDate.setOnClickListener(v -> {
                        binding.searchDateEditText.setText("");
                        updateOrder(listOrder.getOrders());
                    });
                    binding.imgDatePicker.setOnClickListener(v -> {
                        final Calendar c = Calendar.getInstance();
                        int mYear = c.get(Calendar.YEAR);
                        int mMonth = c.get(Calendar.MONTH);
                        int mDay = c.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                        String selectedDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                        binding.searchDateEditText.setText(selectedDate);

                                        List<Orders> filteredOrders = new ArrayList<>();
                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                        for (Orders order : listOrder.getOrders()) {
                                            String orderDate = order.getTimeOrder().split("T")[0]; // Extract date from "2024-04-25T07:52:58.710Z"
                                            try {
                                                Date date1 = sdf.parse(selectedDate);
                                                Date date2 = sdf.parse(orderDate);
                                                if (date1 != null && date1.equals(date2)) {
                                                    filteredOrders.add(order);
                                                }
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }
                                        }

                                        if (filteredOrders.isEmpty()) {
                                            binding.viewEmpty.setVisibility(View.VISIBLE);
                                            binding.rcvOrderAdmin.setVisibility(View.GONE);
                                        } else {
                                            updateOrder(filteredOrders);
                                        }
                                    }
                                }, mYear, mMonth, mDay);
                        datePickerDialog.show();
                    });
                    binding.searchEditText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            String searchText = s.toString();
                            if (searchText.length() > 0) {
                                binding.imgDeleteSearch.setVisibility(View.VISIBLE);
                            } else {
                                binding.imgDeleteSearch.setVisibility(View.GONE);
                            }
                            List<Orders> filteredOrders = new ArrayList<>();
                            for (Orders order : listOrder.getOrders()) {
                                if (order.get_id().contains(searchText)) {
                                    filteredOrders.add(order);
                                }
                            }
                            updateOrder(filteredOrders);
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                        }
                    });
                    binding.icSwitch.setOnClickListener(v -> {
                        if (binding.llSearchOrder.getVisibility() == View.VISIBLE) {
                            binding.llSearchOrder.setVisibility(View.GONE);
                            binding.llSearchDate.setVisibility(View.VISIBLE);
                        } else if (binding.llSearchDate.getVisibility() == View.VISIBLE) {
                            binding.llSearchDate.setVisibility(View.GONE);
                            binding.llSearchOrder.setVisibility(View.VISIBLE);
                        }
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
