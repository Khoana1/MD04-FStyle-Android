package com.example.eu_fstyle_mobile.src.view.user.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.databinding.FragmentHomeBinding;
import com.example.eu_fstyle_mobile.src.adapter.CategoryHomeAdapter;
import com.example.eu_fstyle_mobile.src.adapter.ProductHomeAdapter;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;
import com.example.eu_fstyle_mobile.src.model.Category;
import com.example.eu_fstyle_mobile.src.model.Product;

import java.util.ArrayList;


public class HomeFragment extends BaseFragment<FragmentHomeBinding> {
    FragmentHomeBinding binding;
    ArrayList<Category> arrayList;
    ArrayList<Product> listProduct;
    ProductHomeAdapter productAdapter;
    CategoryHomeAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        getCategory();
        getProduct();
        return binding.getRoot();
    }

    @Override
    protected FragmentHomeBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentHomeBinding.inflate(inflater,container,false);
    }


    private void getProduct() {
        listProduct = new ArrayList<>();
        listProduct.add(new Product(1,"Nike SB Dunk","https://i.pinimg.com/564x/87/e7/b1/87e7b1ecc2ef1580841e7a0d23ed49a0.jpg",50000));
        listProduct.add(new Product(2,"Men's National Trendy","https://i.pinimg.com/564x/01/9e/a4/019ea44da5222ed4e5f838fd3d11e77f.jpg",20000));
        productAdapter = new ProductHomeAdapter(getActivity(), listProduct);
        binding.recycleProductHame.setLayoutManager(new GridLayoutManager(getActivity(),2));
        binding.recycleProductHame.setAdapter(productAdapter);
    }

    private void getCategory() {
        arrayList = new ArrayList<>();
        arrayList.add(new Category(1, "https://i.pinimg.com/564x/70/6d/1e/706d1e17ddb9188407952985c83c4ab7.jpg","shoes"));
        arrayList.add(new Category(2, "https://i.pinimg.com/564x/87/e7/b1/87e7b1ecc2ef1580841e7a0d23ed49a0.jpg", "shoes2"));
        adapter = new CategoryHomeAdapter(getActivity(), arrayList);
        binding.recycleCategoryHome.setLayoutManager(new GridLayoutManager(getActivity(),2));
        binding.recycleCategoryHome.setAdapter(adapter);
    }


    @NonNull
    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }
}