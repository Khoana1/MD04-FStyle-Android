package com.example.eu_fstyle_mobile.src.view.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.databinding.FragmentBillAdminBinding;
import com.example.eu_fstyle_mobile.src.adapter.OrderAdminAdapter;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;
import com.example.eu_fstyle_mobile.src.model.ListOrder;
import com.example.eu_fstyle_mobile.src.model.Orders;
import com.example.eu_fstyle_mobile.src.view.user.address.EditAddressFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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
        observeViewModel();
    }

    private void observeViewModel() {
        billAdminViewModel.getListOrder().observe(getViewLifecycleOwner(), new Observer<ListOrder>() {
            @Override
            public void onChanged(ListOrder listOrder) {
                if (listOrder != null) {
                    binding.rcvOrderAdmin.setAdapter(new OrderAdminAdapter(listOrder.getOrders(), BillAdminFragment.this));
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

    @Override
    public void onOrderClick(String orderID) {
        DetailBillFragment detailBillFragment = DetailBillFragment.newInstance(orderID);
        openScreenAddAdmin(detailBillFragment, true);
    }

}