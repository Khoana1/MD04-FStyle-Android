package com.example.eu_fstyle_mobile.src.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.databinding.FragmentSplashFragmetBinding;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;

public class SplashFragment extends BaseFragment<FragmentSplashFragmetBinding> {

    @Override
    protected FragmentSplashFragmetBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentSplashFragmetBinding.inflate(inflater, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ObjectAnimator animator = ObjectAnimator.ofFloat(binding.imgLogo,"translationY",-100f,0f);
        animator.setDuration(2000);
        animator.setRepeatCount(0);
        animator.start();
        Animation animationLogo = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha);
        binding.imgLogo.startAnimation(animationLogo);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (isNetworkConnected()) {
                openScreen(new LoginFragment(), false);
            } else {
                Toast.makeText(requireActivity(), "Không có kết nối mạng, thử lại sau!", Toast.LENGTH_SHORT).show();
            }
        }, 3000); // vào ứng dụng sau 3s
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}