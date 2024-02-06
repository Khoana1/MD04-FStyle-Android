package com.example.eu_fstyle_mobile.src.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.databinding.FragmentLoginBinding;
import com.example.eu_fstyle_mobile.databinding.FragmentRegisterBinding;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;

import java.util.regex.Pattern;

public class RegisterFragment extends BaseFragment<FragmentRegisterBinding> {
    @Override
    protected FragmentRegisterBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentRegisterBinding.inflate(inflater, container, false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        setStatusHelperText();
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateRegister();
            }
        });
        binding.icGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    private void validateRegister() {
        validateEmail();
        validatePhone();
        validatePassword();
        validateRePass();
    }

    private void setStatusHelperText() {
        binding.emailLayout.setHelperText("");
        binding.phoneLayout.setHelperText("");
        binding.passwordLayout.setHelperText("");
        binding.newPassLayout.setHelperText("");
    }

    private void validateEmail() {
        String helperText = "";
        String email = binding.edtEmail.getText().toString();

        if (email.isEmpty()) {
            helperText = "Không được để trống Email";
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            helperText = "Vui lòng nhập đúng định dạng Email (ví dụ: example@gmail.com) để tiếp tục";
        }

        binding.emailLayout.setHelperText(helperText);
    }

    private void validatePassword() {
        String helperText = "";
        String password = binding.edtPass.getText().toString();
        Pattern upperCasePattern = Pattern.compile(".*[A-Z].*");
        Pattern lowerCasePattern = Pattern.compile(".*[a-z].*");
        Pattern specialCharPattern = Pattern.compile(".*[@#\\$%^&+=].*");

        if (password.isEmpty()) {
            helperText = "Không được để trống mật khẩu";
        } else if (password.length() < 8) {
            helperText = "Mật khẩu phải có ít nhất 8 ký tự";
        } else if (!upperCasePattern.matcher(password).matches()) {
            helperText = "Mật khẩu phải có ít nhất 1 ký tự viết hoa";
        } else if (!lowerCasePattern.matcher(password).matches()) {
            helperText = "Mật khẩu phải có ít nhất 1 ký tự viết thường";
        } else if (!specialCharPattern.matcher(password).matches()) {
            helperText = "Mật khẩu phải có ít nhất 1 ký tự đặc biệt";
        }

        binding.passwordLayout.setHelperText(helperText);

    }

    private void validatePhone() {
        String helperText = "";
        String phone = binding.edtPhone.getText().toString();
        if (phone.isEmpty()) {
            helperText = "Không được để trống số điện thoại";
        } else if (!phone.matches(".*[0-9].*")) {
            helperText = "Số điện thoại phải là số";
        } else if (phone.length() != 10) {
            helperText = "Số điện thoại phải có 10 số";
        }

        binding.phoneLayout.setHelperText(helperText);

    }

    private void validateRePass() {
        String helperText = "";
        String rePassword = binding.edtNewPass.getText().toString();
        String password = binding.edtPass.getText().toString();
        Pattern upperCasePattern = Pattern.compile(".*[A-Z].*");
        Pattern lowerCasePattern = Pattern.compile(".*[a-z].*");
        Pattern specialCharPattern = Pattern.compile(".*[@#\\$%^&+=].*");

        if (rePassword.isEmpty()) {
            helperText = "Không được để trống mật khẩu";
        } else if (rePassword.length() < 8) {
            helperText = "Mật khẩu phải có ít nhất 8 ký tự";
        } else if (!upperCasePattern.matcher(rePassword).matches()) {
            helperText = "Mật khẩu phải có ít nhất 1 ký tự viết hoa";
        } else if (!lowerCasePattern.matcher(rePassword).matches()) {
            helperText = "Mật khẩu phải có ít nhất 1 ký tự viết thường";
        } else if (!specialCharPattern.matcher(rePassword).matches()) {
            helperText = "Mật khẩu phải có ít nhất 1 ký tự đặc biệt";
        } else if (!rePassword.equals(password)) {
            helperText = "Mật khẩu không trùng khớp! Vui lòng nhập lại";
        }

        binding.newPassLayout.setHelperText(helperText);
    }

}