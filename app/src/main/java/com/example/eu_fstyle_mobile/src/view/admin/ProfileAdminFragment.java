package com.example.eu_fstyle_mobile.src.view.admin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eu_fstyle_mobile.MainActivity;
import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.databinding.FragmentProfileAdminBinding;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;
import com.example.eu_fstyle_mobile.src.model.User;
import com.example.eu_fstyle_mobile.src.view.user.SplashFragment;
import com.example.eu_fstyle_mobile.src.view.user.login.LoginFragment;
import com.example.eu_fstyle_mobile.src.view.user.profile.ProfileViewModel;
import com.example.eu_fstyle_mobile.ultilties.UserPrefManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileAdminFragment extends BaseFragment<FragmentProfileAdminBinding> {
    private ProfileAdminViewModel profileAdminViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = getFragmentBinding(inflater, container);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profileAdminViewModel = new ViewModelProvider(this).get(ProfileAdminViewModel.class);
        observeViewModel();
        initListener();
    }

    private void observeViewModel() {
        profileAdminViewModel.getUserData().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                binding.tvName.setText(user.getName());
                binding.tvEmail.setText(user.getEmail());
            }
        });
        profileAdminViewModel.getErrorData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String error) {
                showAlertDialog(error);
            }
        });
        User user = UserPrefManager.getInstance(getActivity()).getUser();
        profileAdminViewModel.fetchUserData(user.get_id());
    }

    private void initListener() {
        binding.icBack.setOnClickListener(v -> {
            showAlertDialog("Hãy đảm bảo rằng thông tin đăng nhập của bạn được bảo mật và không chia sẻ với bất kỳ ai khác. Việc bảo vệ tài khoản quản lý là chìa khóa cho sự ổn định và tăng trưởng của cửa hàng.");
        });
        binding.llBill.setOnClickListener(v -> {
            openScreenAdmin(new BillAdminFragment(), true);
        });

        binding.constraintLogOut.setOnClickListener(v -> {
            showDialog("Đăng xuất", "Bạn có chắc chắn muốn đăng xuất không?", new Runnable() {
                @Override
                public void run() {
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginPreferences", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("isLoggedIn", false);
                    editor.apply();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
            });
        });
    }

    @Override
    protected FragmentProfileAdminBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentProfileAdminBinding.inflate(inflater, container, false);
    }
}