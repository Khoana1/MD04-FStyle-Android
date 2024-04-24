package com.example.eu_fstyle_mobile.src.view.user.login;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.airbnb.lottie.LottieAnimationView;
import com.example.eu_fstyle_mobile.AdminActivity;
import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.databinding.FragmentLoginBinding;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;
import com.example.eu_fstyle_mobile.src.dialog.Loading100Dialog;
import com.example.eu_fstyle_mobile.src.dialog.LoadingDialog;
import com.example.eu_fstyle_mobile.src.dialog.LoginLoadingDialog;
import com.example.eu_fstyle_mobile.src.model.User;
import com.example.eu_fstyle_mobile.src.view.user.home.HomeFragment;
import com.example.eu_fstyle_mobile.src.view.user.register.RegisterFragment;
import com.example.eu_fstyle_mobile.ultilties.AdminPreManager;
import com.example.eu_fstyle_mobile.ultilties.UserPrefManager;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.concurrent.Executor;
import java.util.regex.Pattern;

public class LoginFragment extends BaseFragment<FragmentLoginBinding> {
    private boolean doubleBackToExitPressedOnce = false;
    private String email;
    private String password;
    private LoginViewModel loginViewModel;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    private String tokenDevice;
    private LoginLoadingDialog loginDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getTokenDevice();
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        observeViewModel();
        initView();
        executor = ContextCompat.getMainExecutor(getActivity());
        biometricPrompt = new BiometricPrompt(LoginFragment.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                showLoginLoadingAnimation();
                loginViewModel.loginUser(UserPrefManager.getInstance(getActivity()).getUser().getEmail(), UserPrefManager.getInstance(getActivity()).getUser().getPassword(), tokenDevice
                );
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getActivity(), "Xác thực vân tay thất bại", Toast.LENGTH_SHORT).show();
            }
        });
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("FStyle Mobile")
                .setSubtitle("Sử dụng vân tay để đăng nhập")
                .setNegativeButtonText("Hủy")
                .build();
    }

    private void getTokenDevice() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("Login", "Quá trình lấy mã đăng ký FCM thất bại", task.getException());
                        return;
                    }
                    tokenDevice = task.getResult();
                    Log.d("Login", "FCM Token: " + tokenDevice);
                });
    }

    private void observeViewModel() {
        loginViewModel.getUserLiveData().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                UserPrefManager.getInstance(getActivity()).saveUser(user);
                AdminPreManager.getInstance(getActivity()).saveAdminData(user);
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginPreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLoggedIn", true);
                editor.putBoolean("isVisibleSwitch", true);
                editor.apply();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (user.getAdmin().equals(true)) {
                            hideLoginLoadingAnimation();
                            Toast.makeText(getActivity(), "Chào mừng ADMIN!!!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), AdminActivity.class);
                            startActivity(intent);
                        } else {
                            hideLoginLoadingAnimation();
                            Toast.makeText(getActivity(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            openScreenHome(new HomeFragment(), false);
                        }

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
                            showAlertDialog(error);
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
                    loginViewModel.loginUser(email, password, tokenDevice);
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
        binding.tvFingerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginPreferences", Context.MODE_PRIVATE);
                boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
                boolean isFingerprintEnabled = sharedPreferences.getBoolean("isFingerprintEnabled", false);
                if (isLoggedIn) {
                    if (isFingerprintEnabled) {
                        biometricPrompt.authenticate(promptInfo);
                    } else {
                        String title = "Vui lòng bật xác thực vân tay trong cài đặt trước khi sử dụng tính năng này";
                        showAlertDialog(title);
                    }
                } else {
                    String title = "Vui lòng đăng nhập lại trước khi sử dụng xác thực vân tay";
                    showAlertDialog(title);
                }

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
        loginDialog = new LoginLoadingDialog(getContext());
        loginDialog.show();
    }

    private void hideLoginLoadingAnimation() {
        if (loginDialog != null && loginDialog.isShowing()) {
            loginDialog.dismiss();
        }
    }

}