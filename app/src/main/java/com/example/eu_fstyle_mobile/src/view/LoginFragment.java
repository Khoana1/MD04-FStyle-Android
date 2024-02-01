package com.example.eu_fstyle_mobile.src.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.databinding.FragmentLoginBinding;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;

public class LoginFragment extends BaseFragment<FragmentLoginBinding> {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    protected FragmentLoginBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentLoginBinding.inflate(inflater, container, false);
    }


}