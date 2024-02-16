package com.example.eu_fstyle_mobile.src.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.databinding.FragmentMyFavouriteBinding;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;

public class MyFavouriteFragment extends BaseFragment<FragmentMyFavouriteBinding> {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_favourite, container, false);
    }

    @Override
    protected FragmentMyFavouriteBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return null;
    }
}