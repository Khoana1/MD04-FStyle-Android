package com.example.eu_fstyle_mobile.src.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.databinding.FragmentIntroduceBinding;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;

public class IntroduceFragment extends BaseFragment<FragmentIntroduceBinding> {

    @Override
    protected FragmentIntroduceBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentIntroduceBinding.inflate(inflater, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.tvSkipIntro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new SplashFragment());
                transaction.commit();
            }
        });
    }
}