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

import com.example.eu_fstyle_mobile.databinding.FragmentMyFavouriteBinding;
import com.example.eu_fstyle_mobile.src.adapter.MyFavouriteAdapter;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;
import com.example.eu_fstyle_mobile.src.model.Favourite;
import com.example.eu_fstyle_mobile.src.model.Product;
import com.example.eu_fstyle_mobile.src.model.ProductFavourite;
import com.example.eu_fstyle_mobile.src.model.User;
import com.example.eu_fstyle_mobile.src.retrofit.ApiClient;
import com.example.eu_fstyle_mobile.src.retrofit.ApiService;
import com.example.eu_fstyle_mobile.ultilties.UserPrefManager;

import java.util.List;

import retrofit2.Call;

public class MyFavouriteFragment extends BaseFragment<FragmentMyFavouriteBinding> implements MyFavouriteAdapter.onItemFavouriteClickListener {
    private MyFavouriteAdapter myFavouriteAdapter;

    private MyFavouriteViewModel myFavouriteViewModel;

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
        User user = UserPrefManager.getInstance(getActivity()).getUser();
        myFavouriteViewModel = new ViewModelProvider(this).get(MyFavouriteViewModel.class);
        myFavouriteViewModel.getFavourite(user.get_id());
        observeViewModel();
    }

    private void observeViewModel() {
        myFavouriteViewModel.getFavouriteLiveData().observe(getViewLifecycleOwner(), new Observer<Favourite>() {
            @Override
            public void onChanged(Favourite favourite) {
                if (favourite != null) {
                    List<ProductFavourite> productList = favourite.getListProduct();
                    myFavouriteAdapter = new MyFavouriteAdapter(productList, MyFavouriteFragment.this);
                    binding.rcvFavourite.setAdapter(myFavouriteAdapter);
                }
            }
        });
        myFavouriteViewModel.getErrorLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        binding.icBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        binding.swipeRefreshLayout.setOnRefreshListener(new androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                User user = UserPrefManager.getInstance(getActivity()).getUser();
                myFavouriteViewModel.getFavourite(user.get_id());
                binding.swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected FragmentMyFavouriteBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentMyFavouriteBinding.inflate(inflater, container, false);
    }

    @Override
    public void onItemFavouriteClick(String productId) {
        showDialog("Xóa sản phẩm khỏi danh sách", "Bạn có chắc chắn muốn xóa sản phẩm khỏi danh sách yêu thích không?", new Runnable() {
            @Override
            public void run() {
                ApiService apiService = ApiClient.getClient().create(ApiService.class);
                User user = UserPrefManager.getInstance(getActivity()).getUser();
                Call<Product> call = apiService.deleteFavorite(user.get_id(), productId);
                call.enqueue(new retrofit2.Callback<Product>() {
                    @Override
                    public void onResponse(Call<Product> call, retrofit2.Response<Product> response) {
                        if (response.isSuccessful()) {
                            myFavouriteViewModel.getFavourite(user.get_id());
                            showSuccessDialog("Xóa sản phẩm khỏi danh sách yêu thích thành công");
                        } else {
                            myFavouriteViewModel.getFavourite(user.get_id());
                            showAlertDialog("Xóa sản phẩm khỏi danh sách yêu thích thất bại");
                        }
                    }

                    @Override
                    public void onFailure(Call<Product> call, Throwable t) {
                        Toast.makeText(getActivity(), "Server error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}