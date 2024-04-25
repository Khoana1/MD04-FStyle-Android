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


public class ResultSearchFragment extends BaseFragment<FragmentResultSearchBinding> implements ProductHomeAdapter.onClickItem {
    FragmentResultSearchBinding binding;
    ArrayList<Product> arrayList;
    ProductHomeAdapter adapter;
    String title;
    private String titleFilter;
    private String idFilter;
    public static final String TITLE_FILTER = "TITLE_FILTER";
    public static final String LIST_FILTER = "LIST_FILTER";
    public static final String ID_FILTER = "ID_FILTER";

    public static ResultSearchFragment newInstance(ArrayList<Product> list, String tilte) {
        ResultSearchFragment result = new ResultSearchFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("listResult", list);
        bundle.putString("title", tilte);
        result.setArguments(bundle);
        return result;
    }

    public static ResultSearchFragment newInstance(String titleFilter, ArrayList<Product> listFilter, String idFilter) {
        ResultSearchFragment result = new ResultSearchFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(LIST_FILTER, listFilter);
        bundle.putString(TITLE_FILTER, titleFilter);
        bundle.putString(ID_FILTER, idFilter);
        result.setArguments(bundle);
        return result;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentResultSearchBinding.inflate(inflater, container, false);
        getData();
        getDataFilter();
        Back();
        return binding.getRoot();
    }

    private void getDataFilter() {
        titleFilter = getArguments().getString(TITLE_FILTER);
        if (titleFilter != null) {
            binding.tvFilterTitle.setText("Lọc sản phẩm theo thể loại");
            binding.resultTitle.setText(titleFilter);
            idFilter = getArguments().getString(ID_FILTER);
            ArrayList<Product> products = (ArrayList<Product>) getArguments().getSerializable(LIST_FILTER);
            ArrayList<Product> filteredProducts = new ArrayList<>();
            for (Product product : products) {
                if (product.getIdCategory().equals(idFilter)) {
                    filteredProducts.add(product);
                }
            }
            if (filteredProducts.isEmpty()) {
                binding.viewEmpty.setVisibility(View.VISIBLE);
                binding.recycleResultSearch.setVisibility(View.GONE);
            } else {
                adapter = new ProductHomeAdapter(getActivity(), filteredProducts, ResultSearchFragment.this);
                binding.recycleResultSearch.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                binding.recycleResultSearch.setAdapter(adapter);
            }
        }
    }

    private void Back() {
        binding.resultBack.setOnClickListener(v -> getActivity().onBackPressed());
    }

    @Override
    protected FragmentResultSearchBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentResultSearchBinding.inflate(inflater, container, false);
    }

    private void getData() {
        arrayList = (ArrayList<Product>) getArguments().getSerializable("listResult");
        title = getArguments().getString("title");
        binding.resultTitle.setText(title);
        adapter = new ProductHomeAdapter(getActivity(), arrayList, ResultSearchFragment.this);
        binding.recycleResultSearch.setLayoutManager(new GridLayoutManager(getActivity(), 2));
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
        RequestCreateFavourite requestCreateFavourite = new RequestCreateFavourite(product.get_id(), product.getName(), product.getQuantity(), product.getPrice().toString(), product.getImage64()[0]);
        Call<Product> call = apiService.createFavorite(user.get_id(), requestCreateFavourite);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    showSuccessDialog("Thêm vào yêu thích thành công");
                } else {
                    showAlertDialog("Thêm vào yêu thích thất bại");
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(getActivity(), "Server error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}