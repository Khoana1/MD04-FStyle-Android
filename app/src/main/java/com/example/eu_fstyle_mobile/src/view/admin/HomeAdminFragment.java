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

import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.databinding.FragmentHomeAdminBinding;
import com.example.eu_fstyle_mobile.src.adapter.HomeAdminAdapter;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;
import com.example.eu_fstyle_mobile.src.model.ListProduct;
import com.example.eu_fstyle_mobile.src.model.Product;

import java.util.List;

public class HomeAdminFragment extends BaseFragment<FragmentHomeAdminBinding> {
    private HomeAdminViewModel homeAdminViewModel;
    private HomeAdminAdapter homeAdminAdapter;

    @Override
    protected FragmentHomeAdminBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentHomeAdminBinding.inflate(inflater, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListener();
        homeAdminViewModel = new ViewModelProvider(this).get(HomeAdminViewModel.class);
        homeAdminViewModel.getAllProduct();
        observeViewModel();
    }

    private void observeViewModel() {
        homeAdminViewModel.getProductLiveData().observe(getViewLifecycleOwner(), new Observer<ListProduct>() {
            @Override
            public void onChanged(ListProduct listProduct) {
                List<Product> productList = listProduct.getArrayList();
                homeAdminAdapter = new HomeAdminAdapter(productList);
                binding.rcvProductAdmin.setAdapter(homeAdminAdapter);
            }
        });
        homeAdminViewModel.getErrorLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initListener() {
        binding.icAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScreenAddAdmin(new AddProductFragment(), true);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = getFragmentBinding(inflater, container);
        return binding.getRoot();
    }


}
