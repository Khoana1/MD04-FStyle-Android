package com.example.eu_fstyle_mobile.src.view.user.register;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.eu_fstyle_mobile.databinding.FragmentRegisterBinding;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;
import com.example.eu_fstyle_mobile.src.model.User;
import com.example.eu_fstyle_mobile.src.request.RequestCreateUser;
import com.example.eu_fstyle_mobile.src.retrofit.ApiClient;
import com.example.eu_fstyle_mobile.src.retrofit.ApiService;
import com.example.eu_fstyle_mobile.src.view.user.login.LoginFragment;
import com.example.eu_fstyle_mobile.src.view.user.login.LoginViewModel;
import com.example.eu_fstyle_mobile.src.view.user.register.RegisterViewModel;
import com.example.eu_fstyle_mobile.ultilties.UserPrefManager;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends BaseFragment<FragmentRegisterBinding> {
    private String email;
    private String phone;
    private String password;
    private String rePassword;
    private String name;
    private String avatar = "/9j/4AAQSkZJRgABAQAAAQABAAD/4gIoSUNDX1BST0ZJTEUAAQEAAAIYAAAAAAQwAABtbnRyUkdC\n" +
            "IFhZWiAAAAAAAAAAAAAAAABhY3NwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAQAA9tYAAQAA\n" +
            "AADTLQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAlk\n" +
            "ZXNjAAAA8AAAAHRyWFlaAAABZAAAABRnWFlaAAABeAAAABRiWFlaAAABjAAAABRyVFJDAAABoAAA\n" +
            "AChnVFJDAAABoAAAAChiVFJDAAABoAAAACh3dHB0AAAByAAAABRjcHJ0AAAB3AAAADxtbHVjAAAA\n" +
            "AAAAAAEAAAAMZW5VUwAAAFgAAAAcAHMAUgBHAEIAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\n" +
            "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAFhZWiAA\n" +
            "AAAAAABvogAAOPUAAAOQWFlaIAAAAAAAAGKZAAC3hQAAGNpYWVogAAAAAAAAJKAAAA+EAAC2z3Bh\n" +
            "cmEAAAAAAAQAAAACZmYAAPKnAAANWQAAE9AAAApbAAAAAAAAAABYWVogAAAAAAAA9tYAAQAAAADT\n" +
            "LW1sdWMAAAAAAAAAAQAAAAxlblVTAAAAIAAAABwARwBvAG8AZwBsAGUAIABJAG4AYwAuACAAMgAw\n" +
            "ADEANv/bAEMAUDc8RjwyUEZBRlpVUF94yIJ4bm549a+5kcj/////////////////////////////\n" +
            "///////////////////////bAEMBVVpaeGl464KC6///////////////////////////////////\n" +
            "///////////////////////////////////////AABEIAN0A7AMBIgACEQEDEQH/xAAZAAEAAwEB\n" +
            "AAAAAAAAAAAAAAAAAQIDBAX/xAA1EAADAAIABAQEBQIGAwEAAAAAAQIDERIhMVEEQWFxEyIygTNC\n" +
            "UmKRcrEjkqHB0eE08PFT/8QAFwEBAQEBAAAAAAAAAAAAAAAAAAECA//EABsRAQEBAAMBAQAAAAAA\n" +
            "AAAAAAABEQIDMSES/9oADAMBAAIRAxEAPwDtAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\n" +
            "AAAAAAAAAAAAAADO8nC1KTqmtpAaAxWW9brFX2ey05op63p9nyA0AAAEGefI8cbXVsLJvxoRNKlt\n" +
            "NNHHearhS+5bwrSt7pL07k1q8Mm12AArAAAAAAAAAAAAAAAACADgd06229kta48f0Xkqq22zsw07\n" +
            "xpvqcB34Fw4p/kkdOySRoADTiGFc/Ef0z/c3OeXusld3pfYC8XquF+fQvUTa1STXqYWtrrprmmaR\n" +
            "mip3VKX5psCvDeLnG7n9LfNe3/BrFzc7l7RHxcf65/kytJV8TDSdec7+oDczztLG+JbLRauVS8ya\n" +
            "lUtNbQWevOL48dW/lR034eXGp5PuWw4vhrrtszjreyZ8agA04qVcy9VSQu5idt8jk8Rv4z2Z7etb\n" +
            "JrrOvZrvjJOTfCyxzeEl8Try6HSWMcplyJAAZAAAAAAAAQY34eae02mbEhZbPHMvCrfOn/B0a0CK\n" +
            "pSt09IFtvqxS8kY/qpIy+JWadw+Ce76nDfEqfFvfnsI7/jVa3jjS/VTKLG+HVZHrtPIjCmsUpmhF\n" +
            "U+Fj/Tv3eyeCF0if4DuU9b59hxfsv/KBPDP6Z/gh44fWJ/gK53p8n2a0WAosUz9DqfZkp5p6UrXa\n" +
            "losAE+IXTInD9ehsmmtp7Meq0+hRS4e8T1+19GVHUDPFlWTaac0ussvtb1vn2ApkxTkXzLn3Mp8K\n" +
            "uLnW0dAGLOVngkpWktIkAIAAAAAAAAApkvgnetveku5T4tLrir7PYGwMX4iV1i/8pnk8XKlqU+L1\n" +
            "QGuXKsa6cVdkcGTLeV7p/Y68bThNfm5tvzOXLicVtLckVfwtc6n7nQ5Te2k37HN4aXx8WuSR1AQ3\n" +
            "pbZS6Uyqval9JXVl5njyJPoub/2NcmOck6pbQEYXDxqoWkzQiZUyklpIkqK1KpapJr1Mal4qS3uH\n" +
            "09DoMvEfg16a0BUBJt8i6mV1e2RVCdPszRNPoWKjluXdKVypc+LsX8PS5zSSyLr6+psYZpyb+JKS\n" +
            "cdO7QHQDPE3UKnSpNdtGgAAAAAAAAAAAZZOeXHPq3/BGWaXzzt66z/wTf/kY/Z/7GgHO8j4U5e3X\n" +
            "Q5M01ORqnt9+51Y5XFVro2+H/cs5VdUmRWfh01iWzUrxLepTp9kT8/8A+b/lASCqpN65p9mWAnD9\n" +
            "WT3X9jYwh8OXn0v+5uVAENpJt8kjndVl57cx5JdWBvVzK+akvcxqnma0vkT37/8ARCiZ5qVsvvp7\n" +
            "hZEp6eu5VdZ92J6z7Cfy/cNC/L7msvcpspM716GgSpIJAZc2Onju8Sh0k9rXZmjzKfri59dbX+hF\n" +
            "/L4nG/1Joi81LiqYTmeTbfX2A1mppblpruKpTLpvkjKPDzrd/NT6+SMdKM7mNuU0+Bc//dAdoKxc\n" +
            "3vXVdU/IsAAAAAAY5OWbE990T4imsfCutPSI8Ryxql+VplafH4hdonf3YE6SSS6LkVe6mq58E9vP\n" +
            "/oVvSS609HRKUykuiIrHwuX4kP5VOu3Q3ISSXJaJKimTGrns10fYxVrg3T15Nep0NpLbekjkcXWR\n" +
            "3jl8LfJvl/7sLEuptcMqm35a19zVLOp51DfsR4aFKp/mb012NgjmdVlpzfLX5e5opb8itcs+Nrz2\n" +
            "n7HQBk5S5FWtJfc2a2V4Q1KpK5r2JmS6kkJolokAIAADDxHK8TX6iNzLvFl5Km2n32Tn55cSX6tm\n" +
            "1JUtNJr1Ax+HkaSeVufRaf8AJSG8NVCluqrk+/39DSEseVxO+FraXYnLjdNOXpp7TArk3LjI+T3q\n" +
            "tdmbnPmVLw1Onuuu0bgSAAAAAikqlp9HyObDLlZNvb4tb9jqOXD+FvvTAsvxo+7Og52+G4ryT0/u\n" +
            "dAAAxzW3Xw5en1p9kAtq8s497S5tf2NjlSWKppL5Vyev7mvxoa2qWvcA3wZd/lrr6MrXiISfPl7E\n" +
            "RM5lVPnzan0NYn/DU0l000Bljmqvjpab5JdkdBzpPFkU/kf0+np/wbp7QEgAAAAAAAAFafIDF/N4\n" +
            "qf2ps6DDAuK7yd3peyNMlrHDp/8A0Cqe/Ev9s6/k1MsMuZdV9VPbJrLMvW0BXxP4an9VJGxzy/i+\n" +
            "I35Y1/qzoAAAAAABy4fwn6UzqObHyvLP7t/yBLSaafRkzkqFqk6Xk11+5KhstwJdWRVXmbXyRW/3\n" +
            "ckVmeHm3tt82WetrRHVL+oq4tNKdr1FYYfPhn+Cut7/qNl0CVl4fhUcOtUuVI2Mcsb3S5UujJnLu\n" +
            "JpLqthF7hXDl9GUwU2nNfVL0y80qMr+TxE15X8r/ANgNwAAAAAAADDxF6nS6vkjTJaiW29Iw+C80\n" +
            "u75N/Sn5f/QLRkUyseJO2vPy/ktONp8eWuKl07L2LYaVY1pcOuTXZlbrm9vkgFZU3wppM5vEQ6yT\n" +
            "w82+Winwry5acc1v6vI7cWFY+bfFT6tkVOHGsWNSuvm+5oAVAAAAAAMKajxO65Kp8/Q3ObNNVPxf\n" +
            "OHtL0A6JapbXQpkelr0LTc0lprmtoUtoLGf5pIS5L3L8LJUhdJnzLkEhlWvpZ5u8j5J00umj0reo\n" +
            "b7I4I8Rwwp4d69QsdGHiULj6l/Er/BdfpaZSaVyqXRmub8C/6SC6e0mSUxPeKfYuVAAACl3wru/J\n" +
            "IuQBjOJ3XHl566T5I3AA58qqMqcVwrJyb1vn/wBkrw873bdv1L5p48TS69V7k4648c13QFkklpLR\n" +
            "IAAAAAAAAAAgkgDm8O1FcLWlfOX6djqM8mKbjh1rXRryM5y1ifDm+1eTA6AVVJk7XcCQRtdyHXYD\n" +
            "PxNaxcK63yRnWDG/y9OxeJeTNx0vlnlPr6mjxryYGcSlqUuRbxD1gv2LzKky8R83BC/M+fsgNMa1\n" +
            "jS9C5E9CQAAAAAAAABjg+V5Mf6a/0ZsY/T4r+qf7AbAAAAAAAAAAAAABFSqWqSa9SQBg/D8PPFbn\n" +
            "06opNZntJRWnrsdFUpl0+iMIxq1xPaque0+gEpZ2/pifdmsy19T2/YrOOk/xL+7NEtLrsASAAKcO\n" +
            "6325FwAAAAAAAAAAAAxy8s2J+rRsY+IX4b/egNgAAAAAAAAAAAAAAAY+Je5mF+Z6+xpCMsnPxMLt\n" +
            "LZtPQCQAAAAAAAAAAAAAAAAAAMfEdMf9aNjHxD54l+9AbAAAAAAAAAAAAAAAArwS64tc9a2SSAAA\n" +
            "AAAAAAAAAAAAAAAAAFXKbTa5roWAAAAAAAAAH//Z";
    private RegisterViewModel registerViewModel;

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
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        observeViewModel();
        initView();
    }

    private void observeViewModel() {
        registerViewModel.getUserLiveData().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                hideLoadingDialog();
                Toast.makeText(getActivity(), "Đăng ký thành công, vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
                openScreen(new LoginFragment(), false);
            }
        });

        registerViewModel.getErrorLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String error) {
                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        setStatusHelperText();
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateRegister();
                binding.btnRegister.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        validateRegister();
                        if ((binding.emailLayout.getHelperText() == null || binding.emailLayout.getHelperText().toString().isEmpty()) &&
                                (binding.phoneLayout.getHelperText() == null || binding.phoneLayout.getHelperText().toString().isEmpty()) &&
                                (binding.passwordLayout.getHelperText() == null || binding.passwordLayout.getHelperText().toString().isEmpty()) &&
                                (binding.newPassLayout.getHelperText() == null || binding.newPassLayout.getHelperText().toString().isEmpty() &&
                                        binding.nameLayout.getHelperText() == null || binding.nameLayout.getHelperText().toString().isEmpty())) {
                            showLoadingDialog();
                            registerViewModel.registerUser(avatar, name, email, password, phone);
                        }
                    }
                });
            }
        });
        binding.icGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        binding.parentLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, android.view.MotionEvent event) {
                hideKeyboard();
                clearFocusEditText();
                return false;
            }
        });
    }

    private void validateRegister() {
        validateEmail();
        validatePhone();
        validatePassword();
        validateRePass();
        validateName();
    }

    private void setStatusHelperText() {
        binding.emailLayout.setHelperText("");
        binding.phoneLayout.setHelperText("");
        binding.passwordLayout.setHelperText("");
        binding.newPassLayout.setHelperText("");
        binding.nameLayout.setHelperText("");
    }

    private void validateEmail() {
        String helperText = "";
        email = binding.edtEmail.getText().toString();

        if (email.isEmpty()) {
            helperText = "Không được để trống Email";
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            helperText = "Vui lòng nhập đúng định dạng Email (ví dụ: example@gmail.com) để tiếp tục";
        }

        binding.emailLayout.setHelperText(helperText);
    }

    private void validatePassword() {
        String helperText = "";
        password = binding.edtPass.getText().toString();
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
        phone = binding.edtPhone.getText().toString();
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
        rePassword = binding.edtNewPass.getText().toString();
        password = binding.edtPass.getText().toString();
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

    private void validateName() {
        String helperText = "";
        name = binding.edtName.getText().toString();
        if (name.isEmpty()) {
            helperText = "Không được để trống tên";
        }
        binding.nameLayout.setHelperText(helperText);
    }

    private void clearFocusEditText() {
        binding.edtEmail.clearFocus();
        binding.edtPass.clearFocus();
        binding.edtNewPass.clearFocus();
        binding.edtPhone.clearFocus();
        binding.edtName.clearFocus();
    }
}