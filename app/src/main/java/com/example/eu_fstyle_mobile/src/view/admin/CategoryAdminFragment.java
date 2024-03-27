package com.example.eu_fstyle_mobile.src.view.admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.databinding.FragmentCategoryAdminBinding;
import com.example.eu_fstyle_mobile.src.adapter.CategoriesAdminAdapter;
import com.example.eu_fstyle_mobile.src.adapter.HomeAdminAdapter;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;
import com.example.eu_fstyle_mobile.src.model.Categories;
import com.example.eu_fstyle_mobile.src.model.ListCategories;

import java.util.List;

public class CategoryAdminFragment extends BaseFragment<FragmentCategoryAdminBinding> {
    private CategoriesViewModel categoriesViewModel;
    private CategoriesAdminAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = getFragmentBinding(inflater, container);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         initListioner();
         showShimmerEffect();
         categoriesViewModel = new ViewModelProvider(this).get(CategoriesViewModel.class);
         categoriesViewModel.getAllCategories();
         observeViewModel();
    }
    private void showShimmerEffect() {
        if (binding != null) {
            binding.shimmerViewCategory.setVisibility(View.VISIBLE);
            binding.dataCategory.setVisibility(View.INVISIBLE);
            binding.shimmerViewCategory.startShimmer();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (binding != null) {
                        binding.shimmerViewCategory.stopShimmer();
                        binding.shimmerViewCategory.setVisibility(View.GONE);
                        binding.dataCategory.setVisibility(View.VISIBLE);
                    }
                }
            }, 2000);
        }
    }
    private void observeViewModel() {
        categoriesViewModel.getCategorieData().observe(getViewLifecycleOwner(), new Observer<ListCategories>() {
            @Override
            public void onChanged(ListCategories listCategories) {
                List<Categories> categoriesList = listCategories.getArrayList();
                 adapter= new CategoriesAdminAdapter(categoriesList);
                 binding.rcvCategoryAdmin.setAdapter(adapter);
                 hideLoadingDialog();
            }
        });
        categoriesViewModel.getErrorMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                hideLoadingDialog();
            }
        });
    }
    private void initListioner(){
        binding.icAddCategory.setOnClickListener(v -> {
            openScreenAdmin(new AddCategoriesFragment(), true);
        });
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.swipeRefreshLayout.setRefreshing(false);
                categoriesViewModel.getAllCategories();
                showLoadingDialog();
            }
        });
    }
    @Override
    protected FragmentCategoryAdminBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentCategoryAdminBinding.inflate(inflater, container, false);
    }
}