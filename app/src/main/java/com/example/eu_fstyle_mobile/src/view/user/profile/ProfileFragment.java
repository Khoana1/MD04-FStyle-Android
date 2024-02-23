package com.example.eu_fstyle_mobile.src.view.user.profile;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.databinding.ChooseAvatarBottomsheetBinding;
import com.example.eu_fstyle_mobile.databinding.FragmentProfileBinding;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;
import com.example.eu_fstyle_mobile.src.model.User;
import com.example.eu_fstyle_mobile.src.view.user.ContactFragment;
import com.example.eu_fstyle_mobile.src.view.user.EditInfoFragment;
import com.example.eu_fstyle_mobile.src.view.user.address.MyAddressFragment;
import com.example.eu_fstyle_mobile.src.view.user.MyFavouriteFragment;
import com.example.eu_fstyle_mobile.src.view.user.MyOrderFragment;
import com.example.eu_fstyle_mobile.src.view.user.login.LoginFragment;
import com.example.eu_fstyle_mobile.ultilties.UserPrefManager;

public class ProfileFragment extends BaseFragment<FragmentProfileBinding> {
    private ProfileViewModel profileViewModel;

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
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        observeViewModel();
        initView();
    }

    private void observeViewModel() {
        profileViewModel.getUserData().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                binding.tvName.setText(user.getName());
                binding.tvPhone.setText(user.getPhone());
            }
        });

        profileViewModel.getErrorData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String error) {
                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
            }
        });

        User user = UserPrefManager.getInstance(getActivity()).getUser();
        profileViewModel.fetchUserData(user.get_id());
    }

    private void initView() {
        binding.icBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        binding.icAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAvatarBottomSheet();
            }
        });
        binding.tvEditInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScreen(new EditInfoFragment(), true);
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
        binding.llAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScreen(new MyAddressFragment(), true);
            }
        });
        binding.llContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScreen(new ContactFragment(), true);
            }
        });
        binding.llLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("Đăng xuất", "Bạn có chắc chắn muốn đăng xuất không?", new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginPreferences", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("isLoggedIn", false);
                        editor.apply();
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
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogBottomSheetAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
}