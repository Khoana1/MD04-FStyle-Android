package com.example.eu_fstyle_mobile.src.view.user.profile;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.databinding.ChooseAvatarBottomsheetBinding;
import com.example.eu_fstyle_mobile.databinding.FragmentProfileBinding;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;
import com.example.eu_fstyle_mobile.src.model.User;
import com.example.eu_fstyle_mobile.src.request.RequestUpdateUser;
import com.example.eu_fstyle_mobile.src.retrofit.ApiClient;
import com.example.eu_fstyle_mobile.src.retrofit.ApiService;
import com.example.eu_fstyle_mobile.src.view.user.ContactFragment;
import com.example.eu_fstyle_mobile.src.view.user.address.MyAddressFragment;
import com.example.eu_fstyle_mobile.src.view.user.login.LoginFragment;
import com.example.eu_fstyle_mobile.ultilties.UserPrefManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;

public class ProfileFragment extends BaseFragment<FragmentProfileBinding> {
    private ProfileViewModel profileViewModel;
    private static final int MY_REQUEST_CODE = 1;
    private String base64Image;
    private User currentUser;
    private User user;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 200;

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
        switchFingerPrint();
        getAvatar();
    }

    private void showTakePictureIntent() {
        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        } else {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showTakePictureIntent();
                } else {
                    Toast.makeText(getActivity(), "Camera permission is needed to use this feature", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    private void switchFingerPrint() {
        binding.swFingerPrint.setTrackResource(R.drawable.track_switch);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginPreferences", Context.MODE_PRIVATE);
        boolean savedSwitchState = sharedPreferences.getBoolean("isFingerprintEnabled", false);

        binding.swFingerPrint.setChecked(savedSwitchState);
        binding.swFingerPrint.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginPreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isFingerprintEnabled", isChecked);
                editor.apply();
                if (isChecked) {
                    showLoadingDialog();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "Bật xác thực vân tay thành công", Toast.LENGTH_SHORT).show();
                            hideLoadingDialog();
                        }
                    }, 1000);
                } else {
                    Toast.makeText(getActivity(), "Xác thực vân tay đã bị hủy", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void observeViewModel() {
        profileViewModel.getUserData().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                binding.tvName.setText(user.getName());
                binding.tvPhone.setText(user.getPhone());
                currentUser = user;
                openEdit();
                hideLoadingDialog();
            }
        });

        profileViewModel.getErrorData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String error) {
                showAlertDialog(error);
                hideLoadingDialog();
            }
        });

        user = UserPrefManager.getInstance(getActivity()).getUser();
        profileViewModel.fetchUserData(user.get_id());

    }

    private void openEdit() {
        binding.tvEditInfo.setOnClickListener(new View.OnClickListener() {
            EditInfoFragment editInfoFragment = EditInfoFragment.newInstance(currentUser);

            @Override
            public void onClick(View v) {
                openScreen(editInfoFragment, true);
            }
        });
    }

    private void initView() {
        setSwitchState();
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
        binding.llYourOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScreen(new OrderStatusFragment(), true);
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
                        editor.putBoolean("isFingerprintEnabled", false);
                        editor.apply();
                        openScreen(new LoginFragment(), false);
                    }
                });
            }
        });
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.swipeRefreshLayout.setRefreshing(false);
                user = UserPrefManager.getInstance(getActivity()).getUser();
                profileViewModel.fetchUserData(user.get_id());
                showLoadingDialog();
                getAvatar();
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
                showTakePictureIntent();
            }
        });
        binding.lnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRequestPermission();
            }
        });
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogBottomSheetAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void onClickRequestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (requireActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                openImagePicker();
            } else {
//                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_REQUEST_CODE);
                openImagePicker();
            }
        } else {
            openImagePicker();
        }
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, MY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            if (selectedImage != null) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), selectedImage);
                    base64Image = convertBitmapToBase64(bitmap);
                    ApiService apiService = ApiClient.getClient().create(ApiService.class);
                    RequestUpdateUser requestUpdateUser = new RequestUpdateUser(base64Image, currentUser.getName(), currentUser.getEmail(), currentUser.getPassword(), currentUser.getPhone());
                    Call<User> call = apiService.updateUser(currentUser.get_id(), requestUpdateUser);
                    call.enqueue(new retrofit2.Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                            if (response.isSuccessful()) {
                                getAvatar();
                                Toast.makeText(getActivity(), "Cập nhật ảnh đại diện thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Cập nhật ảnh đại diện thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(getActivity(), "Server error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data != null ? data.getExtras() : null;
            if (extras != null) {
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                if (imageBitmap != null) {
                    base64Image = convertBitmapToBase64(imageBitmap);
                    ApiService apiService = ApiClient.getClient().create(ApiService.class);
                    RequestUpdateUser requestUpdateUser = new RequestUpdateUser(base64Image, currentUser.getName(), currentUser.getEmail(), currentUser.getPassword(), currentUser.getPhone());
                    Call<User> call = apiService.updateUser(currentUser.get_id(), requestUpdateUser);
                    call.enqueue(new retrofit2.Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                            if (response.isSuccessful()) {
                                getAvatar();
                                Toast.makeText(getActivity(), "Cập nhật ảnh đại diện thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Cập nhật ảnh đại diện thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(getActivity(), "Server error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

        }
    }

    private String convertBitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, outputStream);
        byte[] byteArray = outputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    private void getAvatar() {
        String avatarUrl = "http://192.168.55.104:3000/api/user/avatar/image/%s"; // thay IPv4 của máy tính chạy server vào đây để test
        user = UserPrefManager.getInstance(getActivity()).getUser();
        String userId = user.get_id();
        String apiUrl = String.format(avatarUrl, userId);
        Glide.with(getActivity())
                .load(apiUrl)
                .placeholder(R.drawable.avatar_home)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(binding.icAvatar);
    }

    private void setSwitchState() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginPreferences", Context.MODE_PRIVATE);
        boolean savedSwitchState = sharedPreferences.getBoolean("isVisibleSwitch", false);
        if (!savedSwitchState) {
            binding.rltFingerPrint.setVisibility(View.GONE);
        } else {
            binding.rltFingerPrint.setVisibility(View.VISIBLE);
        }
    }

}
