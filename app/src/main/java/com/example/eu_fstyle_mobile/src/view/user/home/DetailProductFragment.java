package com.example.eu_fstyle_mobile.src.view.user.home;

import android.icu.text.DecimalFormat;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eu_fstyle_mobile.databinding.FragmentDetailProductBinding;
import com.example.eu_fstyle_mobile.src.adapter.ViewPager_detail_Adapter;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;
import com.example.eu_fstyle_mobile.src.model.Product;

public class DetailProductFragment extends BaseFragment<FragmentDetailProductBinding> {
    public static final String Products = "product";
    FragmentDetailProductBinding binding;
    ViewPager_detail_Adapter adapter;
    public static DetailProductFragment newInstance(Product product){
        DetailProductFragment detail = new DetailProductFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Products, product);
        detail.setArguments(bundle);
        return detail;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDetailProductBinding.inflate(inflater, container, false);
       getData();
        return binding.getRoot();
    }

    private void getData() {
        Product product = (Product) getArguments().getSerializable(Products);
        Log.d("Huy", "onCreateView: "+product.getName());
        adapter = new ViewPager_detail_Adapter(getActivity(), product.getImage64());
        binding.viewpagerDetail.setAdapter(adapter);
        binding.detailCircleIndicator.setViewPager(binding.viewpagerDetail);
        binding.tenDetail.setText(product.getName());
        DecimalFormat def = new DecimalFormat("###,###,###");
        binding.giaDetail.setText(def.format(product.getPrice())+ " VNĐ");
    }

    @Override
    protected FragmentDetailProductBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentDetailProductBinding.inflate(inflater,container,false);
    }
}