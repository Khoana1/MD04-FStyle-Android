package com.example.eu_fstyle_mobile.src.view;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.databinding.ChooseAvatarBottomsheetBinding;
import com.example.eu_fstyle_mobile.databinding.FragmentProfileBinding;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;
import com.example.eu_fstyle_mobile.ultilties.SharedPrefManager;

public class ProfileFragment extends BaseFragment<FragmentProfileBinding> {

    @Override
    protected FragmentProfileBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentProfileBinding.inflate(inflater, container, false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = getFragmentBinding(inflater, container);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        binding.icAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAvatarBottomSheet();
            }
        });
        binding.llYourOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScreen(new MyOrderFragment(), true);
            }
        });
        binding.llFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScreen(new MyFavouriteFragment(), true);
            }
        });
        binding.llLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("Đăng xuất", "Bạn có chắc chắn muốn đăng xuất không?", new Runnable() {
                    @Override
                    public void run() {
                        SharedPrefManager.getInstance(getActivity()).setLoggedIn(false);
                        openScreen(new LoginFragment(), false);
                    }
                });
            }
        });
    }

    private void showAvatarBottomSheet() {
        final Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ChooseAvatarBottomsheetBinding binding = ChooseAvatarBottomsheetBinding.inflate(getLayoutInflater());
        dialog.setContentView(binding.getRoot());
        binding.icDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        binding.lnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(requireContext(), "Camera", Toast.LENGTH_SHORT).show();
            }
        });
        binding.lnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(requireContext(), "Take Photo", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogBottomSheetAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
}