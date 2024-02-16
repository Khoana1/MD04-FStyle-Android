package com.example.eu_fstyle_mobile.src.view.user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.databinding.FragmentEditAddressBinding;
import com.example.eu_fstyle_mobile.databinding.FragmentEditInfoBinding;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;

public class EditInfoFragment extends BaseFragment<FragmentEditInfoBinding> {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = getFragmentBinding(inflater, container);
        return binding.getRoot();
    }

    @Override
    protected FragmentEditInfoBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentEditInfoBinding.inflate(inflater, container, false);
    }
}