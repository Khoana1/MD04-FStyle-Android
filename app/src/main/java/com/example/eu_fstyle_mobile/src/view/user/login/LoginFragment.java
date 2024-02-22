package com.example.eu_fstyle_mobile.src.view.user.login;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.airbnb.lottie.LottieAnimationView;
import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.databinding.FragmentLoginBinding;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;
import com.example.eu_fstyle_mobile.src.model.User;
import com.example.eu_fstyle_mobile.src.view.user.ForgotPasswordFragment;
import com.example.eu_fstyle_mobile.src.view.user.home.HomeFragment;
import com.example.eu_fstyle_mobile.src.view.user.profile.ProfileFragment;
import com.example.eu_fstyle_mobile.src.view.user.register.RegisterFragment;
import com.example.eu_fstyle_mobile.ultilties.UserPrefManager;

import java.util.regex.Pattern;

public class LoginFragment extends BaseFragment<FragmentLoginBinding> {
    private boolean doubleBackToExitPressedOnce = false;
    private Dialog loginDialog;
    private String email;
    private String password;
    private LoginViewModel loginViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        observeViewModel();
        initView();

    }

    private void observeViewModel() {
        loginViewModel.getUserLiveData().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                UserPrefManager.getInstance(getActivity()).saveUser(user);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideLoginLoadingAnimation();
                        openScreenHome(new HomeFragment(), false);
                        Toast.makeText(getActivity(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    }
                }, 3000);
            }
        });

        loginViewModel.getErrorLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String error) {
                if (error.startsWith("Server error: ")) {
                    hideLoginLoadingAnimation();
                    Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            hideLoginLoadingAnimation();
                            Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                        }
                    }, 2000);
                }
            }
        });
    }
    @Override
    protected FragmentLoginBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentLoginBinding.inflate(inflater, container, false);
    }

    private void initView() {
        setStatusHelperText();
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateLogin();
                if ((binding.emailLayout.getHelperText() == null || binding.emailLayout.getHelperText().toString().isEmpty()) &&
                        (binding.passwordLayout.getHelperText() == null || binding.passwordLayout.getHelperText().toString().isEmpty()) &&
                        !binding.edtEmail.getText().toString().isEmpty() &&
                        !binding.edtPass.getText().toString().isEmpty()) {
                    showLoginLoadingAnimation();
                    loginViewModel.loginUser(email, password);
                }
            }
        });
        binding.edtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScreen(new RegisterFragment(), true);
            }
        });

        binding.tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScreen(new ForgotPasswordFragment(), true);
            }
        });
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (doubleBackToExitPressedOnce) {
                    setEnabled(false);
                    requireActivity().onBackPressed();
                    return;
                }

                doubleBackToExitPressedOnce = true;
                Toast.makeText(getActivity(), "Nhấn BACK một lần nữa để thoát", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000);
            }
        });
    }

    private void setStatusHelperText() {
        binding.emailLayout.setHelperText("");
        binding.passwordLayout.setHelperText("");
    }

    private void validateLogin() {
        validateEmail();
        validatePassword();
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


    private void showLoginLoadingAnimation() {
        loginDialog = new Dialog(getActivity(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        loginDialog.setContentView(R.layout.dialog_login_loading);
        LottieAnimationView animationView = loginDialog.findViewById(R.id.ltAnimationView);
        animationView.setAnimation(R.raw.lottile_loading);
        animationView.setSpeed(4f);
        animationView.playAnimation();
        loginDialog.show();
    }

    private void hideLoginLoadingAnimation() {
        if (loginDialog != null && loginDialog.isShowing()) {
            loginDialog.dismiss();
        }
    }

}