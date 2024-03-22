package com.example.eu_fstyle_mobile.src.view.user.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.databinding.FragmentResultSearchBinding;
import com.example.eu_fstyle_mobile.src.adapter.ProductHomeAdapter;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;
import com.example.eu_fstyle_mobile.src.model.Product;
import com.example.eu_fstyle_mobile.src.model.User;
import com.example.eu_fstyle_mobile.src.request.RequestCreateFavourite;
import com.example.eu_fstyle_mobile.src.retrofit.ApiClient;
import com.example.eu_fstyle_mobile.src.retrofit.ApiService;
import com.example.eu_fstyle_mobile.ultilties.UserPrefManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ResultSearchFragment extends BaseFragment<FragmentResultSearchBinding> implements ProductHomeAdapter.onClickItem{
    FragmentResultSearchBinding binding;
    ArrayList<Product> arrayList;
    ProductHomeAdapter adapter;
    String title;
    public static ResultSearchFragment newInstance(ArrayList<Product> list,String tilte){
        ResultSearchFragment result = new ResultSearchFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("listResult", list);
        bundle.putString("title", tilte);
        result.setArguments(bundle);
        return result;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentResultSearchBinding.inflate(inflater,container,false);
        getData();
        Back();
        return binding.getRoot();
    }

    private void Back() {
        binding.resultBack.setOnClickListener(v -> getActivity().onBackPressed());
    }

    @Override
    protected FragmentResultSearchBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentResultSearchBinding.inflate(inflater,container,false);
    }

    private void getData() {
        arrayList = (ArrayList<Product>) getArguments().getSerializable("listResult");
        title = getArguments().getString("title");
        binding.resultTitle.setText(title);
        adapter = new ProductHomeAdapter(getActivity(),arrayList, ResultSearchFragment.this);
        binding.recycleResultSearch.setLayoutManager(new GridLayoutManager(getActivity(),2));
        binding.recycleResultSearch.setAdapter(adapter);
    }

    @Override
    public void onClick(Product product) {
        DetailProductFragment detailProductFragment = DetailProductFragment.newInstance(product);
        openScreenHome(detailProductFragment, true);
    }

    @Override
    public void onClickFavourite(Product product) {
        User user = UserPrefManager.getInstance(getActivity()).getUser();
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        RequestCreateFavourite requestCreateFavourite = new RequestCreateFavourite(product.getName(), product.getQuantity(), product.getPrice().toString(), product.getImage64()[0]);
        Call<Product> call = apiService.createFavorite(user.get_id(), requestCreateFavourite);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getActivity(), "Thêm vào yêu thích thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Thêm vào yêu thích thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(getActivity(), "Server error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}