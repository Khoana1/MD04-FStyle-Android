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

import java.util.ArrayList;


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
}