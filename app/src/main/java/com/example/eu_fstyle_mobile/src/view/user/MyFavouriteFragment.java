package com.example.eu_fstyle_mobile.src.view.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eu_fstyle_mobile.databinding.FragmentMyFavouriteBinding;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;

public class MyFavouriteFragment extends BaseFragment<FragmentMyFavouriteBinding> {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = getFragmentBinding(inflater, container);
        return binding.getRoot();
    }

    @Override
    protected FragmentMyFavouriteBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentMyFavouriteBinding.inflate(inflater, container, false);
    }
}