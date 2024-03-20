package com.example.eu_fstyle_mobile.src.view.user;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.databinding.FragmentSplashFragmetBinding;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;
import com.example.eu_fstyle_mobile.src.view.user.login.LoginFragment;

public class SplashFragment extends BaseFragment<FragmentSplashFragmetBinding> {

    @Override
    protected FragmentSplashFragmetBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentSplashFragmetBinding.inflate(inflater, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ObjectAnimator animator = ObjectAnimator.ofFloat(binding.imgLogo, "translationY", -100f, 0f);
        animator.setDuration(2000);
        animator.setRepeatCount(0);
        animator.start();
        Animation animationLogo = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha);
        binding.imgLogo.startAnimation(animationLogo);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (isNetworkConnected()) {
                openScreen(new HomeFragment(), false);
            } else {
                showAlertNoConnectionDialog(new Runnable() {
                    @Override
                    public void run() {
                        if (isNetworkConnected()) {
                            Toast.makeText(getContext(), "Đã có kết nối mạng", Toast.LENGTH_SHORT).show();
                            openScreen(new LoginFragment(), false);
                        } else {
                            showAlertNoConnectionDialog(this);
                        }
                    }
                });
            }
        }, 3000); // vào ứng dụng sau 3s
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public void showAlertNoConnectionDialog(Runnable onConfirm) {
        Dialog dialog = new Dialog(getContext());
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.setContentView(R.layout.dialog_no_connection);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_dialog_background));
        Button btnConfirm = dialog.findViewById(R.id.btn_ok);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onConfirm.run();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}