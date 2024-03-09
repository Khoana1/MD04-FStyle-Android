package com.example.eu_fstyle_mobile.src.view.user.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.eu_fstyle_mobile.databinding.FragmentForgotPasswordBinding;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;
import com.example.eu_fstyle_mobile.src.model.User;
import com.example.eu_fstyle_mobile.src.request.RequestUpdateUser;
import com.example.eu_fstyle_mobile.src.retrofit.ApiClient;
import com.example.eu_fstyle_mobile.src.retrofit.ApiService;
import com.example.eu_fstyle_mobile.ultilties.UserPrefManager;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Response;

public class ForgotPasswordFragment extends BaseFragment<FragmentForgotPasswordBinding> {
    private String email;

    private String phone;

    private String password;

    private String rePassword;

    private User user;

    @Override
    protected FragmentForgotPasswordBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentForgotPasswordBinding.inflate(inflater, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        user = UserPrefManager.getInstance(getActivity()).getUser();
        initView();
    }

    private void initView() {
        setStatusHelperText();
        binding.icGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        binding.btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateChangePass();
                if ((binding.emailLayout.getHelperText() == null || binding.emailLayout.getHelperText().toString().isEmpty()) &&
                        (binding.phoneLayout.getHelperText() == null || binding.phoneLayout.getHelperText().toString().isEmpty()) &&
                        (binding.passwordLayout.getHelperText() == null || binding.passwordLayout.getHelperText().toString().isEmpty()) &&
                        (binding.newPassLayout.getHelperText() == null || binding.newPassLayout.getHelperText().toString().isEmpty())) {
                    ApiService apiService = ApiClient.getClient().create(ApiService.class);
                    if (phone != user.getPhone()) {
                        showAlertDialog("Vui lòng nhập đúng số điện thoại đã đăng ký cùng email để đổi mật khẩu");
                    } else {
                        RequestUpdateUser requestUpdateUser = new RequestUpdateUser(user.getAvatar(), user.getName(), email, password, phone);
                        Call<User> call = apiService.updateUser(user.get_id(), requestUpdateUser);
                        call.enqueue(new retrofit2.Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(getActivity(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                    openScreenHome(new LoginFragment(), false);
                                }
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                showAlertDialog("Có lỗi xảy ra, vui lòng thử lại sau");
                            }
                        });
                    }

                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentForgotPasswordBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private void setStatusHelperText() {
        binding.emailLayout.setHelperText("");
        binding.phoneLayout.setHelperText("");
        binding.passwordLayout.setHelperText("");
        binding.newPassLayout.setHelperText("");
    }

    private void validateChangePass() {
        validateEmail();
        validatePhone();
        validatePassword();
        validateRePass();
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
}