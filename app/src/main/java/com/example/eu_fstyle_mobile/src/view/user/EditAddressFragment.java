package com.example.eu_fstyle_mobile.src.view.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.eu_fstyle_mobile.databinding.FragmentEditAddressBinding;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;

public class EditAddressFragment extends BaseFragment<FragmentEditAddressBinding> {
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
    }

    private void initView() {
        binding.icBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    @Override
    protected FragmentEditAddressBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentEditAddressBinding.inflate(inflater, container, false);
    }
}