package com.example.eu_fstyle_mobile.src.view.user.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.eu_fstyle_mobile.databinding.FragmentEditInfoBinding;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;
import com.example.eu_fstyle_mobile.src.model.User;

import java.util.regex.Pattern;

public class EditInfoFragment extends BaseFragment<FragmentEditInfoBinding> {
    public static final String USER = "USER";
    private User currentUser;
    private String name;
    private String phoneNumber;
    private String oldPassword;
    private String newPassword;

    private String renewPassword;
    private EditInfoViewModel editInfoViewModel;

    public static EditInfoFragment newInstance(User user) {
        EditInfoFragment fragment = new EditInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable(USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    private User getUserFromArguments() {
        Bundle args = getArguments();
        if (args != null) {
            return (User) args.getSerializable(USER);
        }
        return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = getFragmentBinding(inflater, container);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editInfoViewModel = new ViewModelProvider(this).get(EditInfoViewModel.class);
        observeViewModel();
        currentUser = getUserFromArguments();
        initView();
        initListener();
    }

    private void observeViewModel() {
        editInfoViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                showAlertDialog("Cập nhật thông tin thành công, vân tay bị vô hiệu hóa sau khi đổi thông tin");
            }
        });
        editInfoViewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                showAlertDialog(error);
            }
        });
    }

    private void initView() {
        setStatusHelperText();
        if (currentUser != null) {
            binding.edtName.setText(currentUser.getName());
            binding.edtPhoneNumber.setText(currentUser.getPhone());
        }
    }

    private void initListener() {
        binding.icBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        binding.btnChangeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateEditInfo();
                if ((binding.nameContainer.getHelperText() == null || binding.nameContainer.getHelperText().toString().isEmpty()) &&
                        (binding.phoneContainer.getHelperText() == null || binding.phoneContainer.getHelperText().toString().isEmpty()) &&
                        (binding.oldPassContainer.getHelperText() == null || binding.oldPassContainer.getHelperText().toString().isEmpty()) &&
                        (binding.newPassContainer.getHelperText() == null || binding.newPassContainer.getHelperText().toString().isEmpty() &&
                                binding.renewPassContainer.getHelperText() == null || binding.renewPassContainer.getHelperText().toString().isEmpty())) {
                    if (!newPassword.equals(renewPassword)) {
                        binding.renewPassContainer.setHelperText("Mật khẩu không trùng khớp! Vui lòng nhập lại");
                    } else {
                        showLoadingDialog();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                editInfoViewModel.updateUser(currentUser.get_id(), currentUser.getAvatar(), name, currentUser.getEmail(), newPassword, phoneNumber);
                                hideLoadingDialog();
                                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginPreferences", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean("isLoggedIn", false);
                                editor.putBoolean("isFingerprintEnabled", false);
                                editor.putBoolean("isVisibleSwitch", false);
                                editor.apply();
                                openScreen(new ProfileFragment(), false);
                                showSuccessDialog("Cập nhật thông tin thành công, Vân tay bị vô hiệu hóa sau khi đổi thông tin. Vui lòng đăng nhập lại để sử dụng");
                            }
                        }, 2000);
                    }

                }
            }
        });
    }

    @Override
    protected FragmentEditInfoBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentEditInfoBinding.inflate(inflater, container, false);
    }

    private void setStatusHelperText() {
        binding.nameContainer.setHelperText("");
        binding.phoneContainer.setHelperText("");
        binding.oldPassContainer.setHelperText("");
        binding.newPassContainer.setHelperText("");
        binding.renewPassContainer.setHelperText("");
    }

    private void validateEditInfo() {
        String helperText = "";
        Pattern upperCasePattern = Pattern.compile(".*[A-Z].*");
        Pattern lowerCasePattern = Pattern.compile(".*[a-z].*");
        Pattern specialCharPattern = Pattern.compile(".*[@#\\$%^&+=].*");
        name = binding.edtName.getText().toString();
        phoneNumber = binding.edtPhoneNumber.getText().toString();
        oldPassword = binding.edtOldPass.getText().toString();
        newPassword = binding.edtNewPass.getText().toString();
        renewPassword = binding.edtReNewPass.getText().toString();
        if (name.isEmpty()) {
            helperText = "Không được để trống tên cần sửa";
            binding.nameContainer.setHelperText(helperText);
        } else {
            binding.nameContainer.setHelperText("");
        }
        if (phoneNumber.isEmpty()) {
            helperText = "Không được để trống số điện thoại";
            binding.phoneContainer.setHelperText(helperText);
        } else if (phoneNumber.length() < 10) {
            helperText = "Số điện thoại phải có 10 số";
            binding.phoneContainer.setHelperText(helperText);
        } else {
            binding.phoneContainer.setHelperText("");
        }
        if (oldPassword.isEmpty()) {
            helperText = "Không được để trống mật khẩu cũ";
            binding.oldPassContainer.setHelperText(helperText);
        } else if (oldPassword.length() < 8) {
            helperText = "Mật khẩu phải có ít nhất 8 ký tự";
            binding.oldPassContainer.setHelperText(helperText);
        } else if (!upperCasePattern.matcher(oldPassword).matches()) {
            helperText = "Mật khẩu phải có ít nhất 1 ký tự viết hoa";
            binding.oldPassContainer.setHelperText(helperText);
        } else if (!lowerCasePattern.matcher(oldPassword).matches()) {
            helperText = "Mật khẩu phải có ít nhất 1 ký tự viết thường";
            binding.oldPassContainer.setHelperText(helperText);
        } else if (!specialCharPattern.matcher(oldPassword).matches()) {
            helperText = "Mật khẩu phải có ít nhất 1 ký tự đặc biệt";
            binding.oldPassContainer.setHelperText(helperText);
        } else if (!oldPassword.equals(currentUser.getPassword())) {
            helperText = "Mật khẩu cũ không trùng khớp với mật khẩu hiện tại";
            binding.oldPassContainer.setHelperText(helperText);
        } else {
            binding.oldPassContainer.setHelperText("");
        }
        if (newPassword.isEmpty()) {
            helperText = "Không được để trống mật khẩu mới";
            binding.newPassContainer.setHelperText(helperText);
        } else if (newPassword.length() < 8) {
            helperText = "Mật khẩu phải có ít nhất 8 ký tự";
            binding.newPassContainer.setHelperText(helperText);
        } else if (!upperCasePattern.matcher(newPassword).matches()) {
            helperText = "Mật khẩu phải có ít nhất 1 ký tự viết hoa";
            binding.newPassContainer.setHelperText(helperText);
        } else if (!lowerCasePattern.matcher(newPassword).matches()) {
            helperText = "Mật khẩu phải có ít nhất 1 ký tự viết thường";
            binding.newPassContainer.setHelperText(helperText);
        } else if (!specialCharPattern.matcher(newPassword).matches()) {
            helperText = "Mật khẩu phải có ít nhất 1 ký tự đặc biệt";
            binding.newPassContainer.setHelperText(helperText);
        } else if (newPassword.equals(oldPassword)) {
            helperText = "Mật khẩu mới không được trùng với mật khẩu cũ";
            binding.newPassContainer.setHelperText(helperText);
        } else {
            binding.newPassContainer.setHelperText("");
        }
        validateRePass();
    }


    private void validateRePass() {
        String helperText = "";
        renewPassword = binding.edtReNewPass.getText().toString();
        newPassword = binding.edtNewPass.getText().toString();
        Pattern upperCasePattern = Pattern.compile(".*[A-Z].*");
        Pattern lowerCasePattern = Pattern.compile(".*[a-z].*");
        Pattern specialCharPattern = Pattern.compile(".*[@#\\$%^&+=].*");

        if (renewPassword.isEmpty()) {
            helperText = "Không được để trống mật khẩu";
        } else if (renewPassword.length() < 8) {
            helperText = "Mật khẩu phải có ít nhất 8 ký tự";
        } else if (!upperCasePattern.matcher(renewPassword).matches()) {
            helperText = "Mật khẩu phải có ít nhất 1 ký tự viết hoa";
        } else if (!lowerCasePattern.matcher(renewPassword).matches()) {
            helperText = "Mật khẩu phải có ít nhất 1 ký tự viết thường";
        } else if (!specialCharPattern.matcher(renewPassword).matches()) {
            helperText = "Mật khẩu phải có ít nhất 1 ký tự đặc biệt";
        } else if (!renewPassword.equals(newPassword)) {
            helperText = "Mật khẩu không trùng khớp! Vui lòng nhập lại";
        }

        binding.renewPassContainer.setHelperText(helperText);
    }

}
