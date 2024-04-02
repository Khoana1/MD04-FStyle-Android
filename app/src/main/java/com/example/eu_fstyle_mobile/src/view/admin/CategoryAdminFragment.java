package com.example.eu_fstyle_mobile.src.view.admin;

import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
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
import com.example.eu_fstyle_mobile.src.model.ListProduct;
import com.example.eu_fstyle_mobile.src.model.Product;
import com.example.eu_fstyle_mobile.src.request.RequestString;
import com.example.eu_fstyle_mobile.src.retrofit.ApiClient;
import com.example.eu_fstyle_mobile.src.retrofit.ApiService;

import java.util.ArrayList;
import java.util.List;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryAdminFragment extends BaseFragment<FragmentCategoryAdminBinding> implements CategoriesAdminAdapter.onClickItem{
    private CategoriesViewModel categoriesViewModel;
    private HomeAdminViewModel homeAdminViewModel;
    private CategoriesAdminAdapter adapter;
    private ArrayList<Product> productArrayList;
    private ItemTouchHelper itemTouchHelper;
    boolean isHaslinkedProduct= false;
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
         homeAdminViewModel = new ViewModelProvider(this).get(HomeAdminViewModel.class);
         categoriesViewModel.getAllCategories();
         homeAdminViewModel.getAllProduct();
         getAllProduct();
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
                 adapter= new CategoriesAdminAdapter(categoriesList, CategoryAdminFragment.this);
                 binding.rcvCategoryAdmin.setAdapter(adapter);
                 binding.rcvCategoryAdmin.setLayoutManager(new LinearLayoutManager(getActivity()));
                 hideLoadingDialog();
                 //
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

    @Override
    public void onClick(Categories categories) {
            AddCategoriesFragment addCategoriesFragment = AddCategoriesFragment.getInstance(categories);
            openScreenAdmin(addCategoriesFragment, true);
    }

    @Override
    public void onDelete(Categories categories) {
        for(int i=0;i<productArrayList.size();i++) {
            if (categories.getId().equals(productArrayList.get(i).getIdCategory())) {
                isHaslinkedProduct = true;
                break;
            }
        }
        if(isHaslinkedProduct){
            showAlertDialog("Hãy xóa sản phẩm chứa thể loại này");
        }else {
            showDialog("Nhắc nhở", "Bạn có muốn xóa không?", new Runnable() {
                @Override
                public void run() {
                    ApiService apiService = ApiClient.getClient().create(ApiService.class);
                    Call<RequestString> call = apiService.deleteCategory(categories.getId());
                    call.enqueue(new Callback<RequestString>() {
                        @Override
                        public void onResponse(Call<RequestString> call, Response<RequestString> response) {
                            if(response.isSuccessful()){
                                Toast.makeText(getActivity(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                                categoriesViewModel.getAllCategories();
                            }else {
                                Toast.makeText(getActivity(), "Xóa thất bại", Toast.LENGTH_SHORT).show();
                                categoriesViewModel.getAllCategories();
                            }
                        }

                        @Override
                        public void onFailure(Call<RequestString> call, Throwable t) {
                            Log.d("Huy_erro", "onFailure: "+t.getMessage());
                        }
                    });
                }
            });
        }
    }
    private void getAllProduct(){
        homeAdminViewModel.getProductLiveData().observe(getViewLifecycleOwner(), new Observer<ListProduct>() {
            @Override
            public void onChanged(ListProduct listProduct) {
                productArrayList = listProduct.getArrayList();
            }
        });
     }
 }