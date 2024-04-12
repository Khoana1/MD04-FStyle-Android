package com.example.eu_fstyle_mobile.src.view.user.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.eu_fstyle_mobile.databinding.FragmentOrderStatusBinding;
import com.example.eu_fstyle_mobile.src.adapter.MyOrderStatusAdapter;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;
import com.example.eu_fstyle_mobile.src.model.ListOrder;
import com.example.eu_fstyle_mobile.src.model.User;
import com.example.eu_fstyle_mobile.src.view.admin.DetailBillFragment;
import com.example.eu_fstyle_mobile.ultilties.UserPrefManager;

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
        orderStatusViewModel = new ViewModelProvider(this).get(OrderStatusViewModel.class);
        User user = UserPrefManager.getInstance(getActivity()).getUser();
        orderStatusViewModel.getOrderByUserID(user.get_id());
        observeViewModel();
    }

    private void observeViewModel() {
        orderStatusViewModel.getListOrder().observe(getViewLifecycleOwner(), new Observer<ListOrder>() {
            @Override
            public void onChanged(ListOrder listOrder) {
                binding.rcvOrderStatus.setAdapter(new MyOrderStatusAdapter(listOrder.getOrders(), OrderStatusFragment.this));
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

    @Override
    public void onOrderClick(String orderId) {
        DetailOrderStatusFragment detailOrderStatusFragment = DetailOrderStatusFragment.newInstance(orderId);
        openScreen(detailOrderStatusFragment, true);
    }
}