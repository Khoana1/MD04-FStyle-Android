package com.example.eu_fstyle_mobile.src.view.admin;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.eu_fstyle_mobile.databinding.FragmentHomeAdminBinding;
import com.example.eu_fstyle_mobile.src.adapter.HomeAdminAdapter;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;
import com.example.eu_fstyle_mobile.src.model.ListProduct;
import com.example.eu_fstyle_mobile.src.model.Product;
import com.example.eu_fstyle_mobile.src.retrofit.ApiClient;
import com.example.eu_fstyle_mobile.src.retrofit.ApiService;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HomeAdminFragment extends BaseFragment<FragmentHomeAdminBinding> implements HomeAdminAdapter.OnclickItem{
    private HomeAdminViewModel homeAdminViewModel;
    private HomeAdminAdapter homeAdminAdapter;

    @Override
    protected FragmentHomeAdminBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentHomeAdminBinding.inflate(inflater, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showShimmerEffect();
        initListener();
        homeAdminViewModel = new ViewModelProvider(this).get(HomeAdminViewModel.class);
        homeAdminViewModel.getAllProduct();
        observeViewModel();
    }

    private void showShimmerEffect() {
        if (binding != null) {
            binding.shimmerView.setVisibility(View.VISIBLE);
            binding.dataProduct.setVisibility(View.INVISIBLE);
            binding.shimmerView.startShimmer();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (binding != null) {
                        binding.shimmerView.stopShimmer();
                        binding.shimmerView.setVisibility(View.GONE);
                        binding.dataProduct.setVisibility(View.VISIBLE);
                    }
                }
            }, 2000);
        }
    }

    private void observeViewModel() {
        homeAdminViewModel.getProductLiveData().observe(getViewLifecycleOwner(), new Observer<ListProduct>() {
            @Override
            public void onChanged(ListProduct listProduct) {
                List<Product> productList = listProduct.getArrayList();
                Collections.sort(productList, new Comparator<Product>() {
                    @Override
                    public int compare(Product p1, Product p2) {
                        if (Integer.parseInt(p1.getQuantity()) > 0 && Integer.parseInt(p2.getQuantity()) == 0) {
                            return -1;
                        }
                        if (Integer.parseInt(p1.getQuantity()) == 0 && Integer.parseInt(p2.getQuantity()) > 0) {
                            return 1;
                        }
                        return 0;
                    }
                });
                homeAdminAdapter = new HomeAdminAdapter(productList, HomeAdminFragment.this);
                binding.rcvProductAdmin.setAdapter(homeAdminAdapter);
                hideLoadingDialog();
            }
        });
        homeAdminViewModel.getErrorLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                Log.d("Huy", "onChanged: "+s);
                hideLoadingDialog();
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
        binding.swipeRefreshLayout.setOnRefreshListener(new androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.swipeRefreshLayout.setRefreshing(false);
                homeAdminViewModel.getAllProduct();
                showLoadingDialog();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = getFragmentBinding(inflater, container);
        return binding.getRoot();
    }


    @Override
    public void onClickUpdate(Product product) {
        AddProductFragment addProductFragment = AddProductFragment.getInstance(product);
        openScreenAdmin(addProductFragment, true);
    }

    @Override
    public void onChangeQuantity(Product product) {
        showDialog("Thay đổi số lượng sản phẩm", "Bạn có muốn đổi số lượng sản phầm về 0 không ?", new Runnable() {
            @Override
            public void run() {
                 ApiService apiService = ApiClient.getClient().create(ApiService.class);
                 retrofit2.Call<Product> call = apiService.updateQuantity(product.get_id());
                 call.enqueue(new retrofit2.Callback<Product>() {
                     @Override
                     public void onResponse(retrofit2.Call<Product> call, retrofit2.Response<Product> response) {
                         if(response.isSuccessful()){
                             Toast.makeText(getActivity(), "Thay đổi số lượng sản phẩm thành công!", Toast.LENGTH_SHORT).show();
                         }else {
                             Toast.makeText(getActivity(), "Lỗi thay đổi số lượng sản phẩm! Thử lại sau. ", Toast.LENGTH_SHORT).show();
                         }
                     }

                     @Override
                     public void onFailure(retrofit2.Call<Product> call, Throwable t) {
                         Toast.makeText(getActivity(), "Server error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                     }
                 });
            }
        });
    }
}
