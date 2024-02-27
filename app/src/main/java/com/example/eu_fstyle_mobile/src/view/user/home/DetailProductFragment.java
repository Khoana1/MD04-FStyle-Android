package com.example.eu_fstyle_mobile.src.view.user.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.databinding.FragmentDetailProductBinding;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;


public class DetailProductFragment extends BaseFragment<FragmentDetailProductBinding> {
    FragmentDetailProductBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDetailProductBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    protected FragmentDetailProductBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentDetailProductBinding.inflate(inflater,container,false);
    }
}