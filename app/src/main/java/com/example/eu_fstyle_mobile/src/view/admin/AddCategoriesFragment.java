package com.example.eu_fstyle_mobile.src.view.admin;

import static android.app.Activity.RESULT_OK;
import static android.util.Base64.encodeToString;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.databinding.DialogBottomBinding;
import com.example.eu_fstyle_mobile.databinding.FragmentAddCategoriesBinding;
import com.example.eu_fstyle_mobile.src.adapter.ImageAdapter;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;
import com.example.eu_fstyle_mobile.src.model.Categories;
import com.example.eu_fstyle_mobile.src.model.User;
import com.example.eu_fstyle_mobile.src.request.RequestCategory;
import com.example.eu_fstyle_mobile.src.request.RequestString;
import com.example.eu_fstyle_mobile.src.retrofit.ApiClient;
import com.example.eu_fstyle_mobile.src.retrofit.ApiService;
import com.example.eu_fstyle_mobile.ultilties.AdminPreManager;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddCategoriesFragment extends BaseFragment<FragmentAddCategoriesBinding> {
        public static final String CATEGORIES = "categories";
        private static final int IMAGE_PICKER_SELECT = 1889;
        private static final int CAMERA_REQUEST = 1888;
        private static final int REQUEST_CODE_PERMISSIONS = 100;
        private static final int REQUEST_CODE_PERMISSIONS_STORAGE = 101;
        private Uri uriImage;
        private static final int READ_PERMISSION = 101;
        private String imageBase64;
        private Categories categories;
        private String name;
        private AddCategoryViewModel addCategoryViewModel;
        private boolean isEditing = false;
        private DialogBottomBinding bottomBinding;
       private BottomSheetDialog bottom;
       public static AddCategoriesFragment getInstance(Categories categories){
           AddCategoriesFragment addCategoriesFragment = new AddCategoriesFragment();
           Bundle bundle = new Bundle();
           bundle.putSerializable(CATEGORIES, categories);
           addCategoriesFragment.setArguments(bundle);
           return addCategoriesFragment;
       }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = getFragmentBinding(inflater,container);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        addCategoryViewModel = new AddCategoryViewModel();
        observeViewModel();
        getDataEdit();
    }

    private void getDataEdit() {
           if(getArguments()!= null){
               categories = (Categories) getArguments().getSerializable(CATEGORIES);
               if(categories != null){
                   isEditing = true;
                   binding.edtCategoryName.setText(categories.getName());
                   if(categories.getImage() != null){
                       if(categories.getImage().startsWith("http")){
                           Picasso.get().load(categories.getImage())
                                   .error(R.drawable.icon_erro)
                                   .into(binding.imageResultCategory);
                       }else {
                           byte[] decodedString = Base64.decode(categories.getImage(), Base64.DEFAULT);
                           Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                           if(decodedByte != null){
                               binding.imageResultCategory.setImageBitmap(decodedByte);
                               uriImage = getImageUri(getActivity(), decodedByte);
                           }

                       }
                   }else {
                       binding.imageResultCategory.setImageResource(R.drawable.icon_erro);
                   }
               }
           }
           updateUI();

    }
    private void updateUI() {
        if (isEditing) {
            binding.btnAddCategory.setText("Sửa");
        } else {
            binding.btnAddCategory.setText("Thêm");
        }
    }
    private void observeViewModel() {
        addCategoryViewModel.getCategoryData().observe(getViewLifecycleOwner(), new Observer<Categories>() {
            @Override
            public void onChanged(Categories categories) {
                binding.edtCategoryName.setText("");
                binding.imageResultCategory.setImageResource(R.drawable.image);
                binding.btnImagePicker.setImageResource(R.drawable.image);
                binding.tvErrorPickImage.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Thêm loại sản phẩm thành công", Toast.LENGTH_SHORT).show();
                openScreenAdmin(new CategoryAdminFragment(), false);
            }
        });
        addCategoryViewModel.getErroMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d("Huy", "onChanged: "+s);
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        binding.icBackCategory.setOnClickListener(v -> requireActivity().onBackPressed());
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_PERMISSION);
        }
        binding.btnImagePicker.setOnClickListener(v -> {
            openDialog();
            hideKeyboard();
        });
        binding.btnAddCategory.setOnClickListener(v -> {
            validateAddCategory();
            if(binding.categoryNameContainer.getHelperText()== null && uriImage != null ){
                imageBase64 = convertImagesToBase64(uriImage);
                Log.d("Huy", "initView: "+"_"+name+"_"+imageBase64);
                if(!isEditing){
                    addCategoryViewModel.createCategory( name,imageBase64);
                }else {
                    ApiService apiService = ApiClient.getClient().create(ApiService.class);
                    RequestCategory requestCategory = new RequestCategory(name, imageBase64);
                    Call<RequestString> call = apiService.updateCategory(categories.getId(),requestCategory);
                    call.enqueue(new Callback<RequestString>() {
                        @Override
                        public void onResponse(Call<RequestString> call, Response<RequestString> response) {
                          if(response.isSuccessful()){
                              binding.edtCategoryName.setText("");
                              binding.imageResultCategory.setImageResource(R.drawable.image);
                              binding.btnImagePicker.setImageResource(R.drawable.image);
                              binding.tvErrorPickImage.setVisibility(View.GONE);
                              Toast.makeText(getActivity(), "Update thành công", Toast.LENGTH_SHORT).show();
                              openScreenAdmin(new CategoryAdminFragment(), false);
                          }else {
                              Toast.makeText(getActivity(), "Update thất bại", Toast.LENGTH_SHORT).show();
                          }
                        }

                        @Override
                        public void onFailure(Call<RequestString> call, Throwable t) {
                            Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }

    private void validateAddCategory() {
        String textHelper ="";
        name = binding.edtCategoryName.getText().toString();
        if(name.isEmpty()){
            textHelper = "Không được để trốn tên loại giày";
            binding.categoryNameContainer.setHelperText(textHelper);
        }else {
            binding.categoryNameContainer.setHelperText("");
        }
        if(uriImage == null){
            binding.tvErrorPickImage.setVisibility(View.VISIBLE);
            binding.tvErrorPickImage.setText("Bạn chưa chọn ảnh");
        }else {
            binding.tvErrorPickImage.setVisibility(View.GONE);
        }
    }
    public String convertImagesToBase64(Uri uriImage){
        String base64Image="";
            try {
                InputStream inputStream = getContext().getContentResolver().openInputStream(uriImage);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 10,outputStream);
                byte[] bytes = outputStream.toByteArray();
                base64Image = Base64.encodeToString(bytes,Base64.DEFAULT);
            }catch (IOException e){
                e.printStackTrace();
            }
        return base64Image;
    }

    private void openDialog(){
        bottomBinding = DialogBottomBinding.inflate(LayoutInflater.from(getActivity()));
        bottom = new BottomSheetDialog(getActivity());
        bottom.setContentView(bottomBinding.getRoot());
        bottomBinding.btnSelectImage.setOnClickListener(v -> {
            openStorage();
        });
        bottomBinding.btnOpenCamera.setOnClickListener(v -> {
            try {
                openCamera();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        bottom.show();
    }
    private void openStorage(){
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            startStorage();
        }else {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.MANAGE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSIONS_STORAGE);
        }
    }
    private void startStorage(){
        closeDialog();
        Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,"select image"), IMAGE_PICKER_SELECT);
    }



    private void openCamera() throws FileNotFoundException {
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
            startCamera();
        }else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_PERMISSIONS);

        }
    }


    private void startCamera() throws FileNotFoundException {
        closeDialog();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST);
    }

    @SuppressLint("Static0FieldLeak")
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMAGE_PICKER_SELECT && resultCode== RESULT_OK && data!= null){
            uriImage = data.getData();
            binding.imageResultCategory.setImageURI(uriImage);
        }
        if(requestCode==CAMERA_REQUEST && resultCode ==RESULT_OK && data != null){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            uriImage = getImageUri(getActivity(), imageBitmap);
            Log.d("Huy", "onActivityResult: "+uriImage);
            binding.imageResultCategory.setImageURI(uriImage);
        }
    }
    private Uri getImageUri(Context context, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                try {
                    startCamera();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else {
                Toast.makeText(getActivity(), "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == REQUEST_CODE_PERMISSIONS_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startStorage();
            } else {
                Toast.makeText(getActivity(), "Storage permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void closeDialog() {
        if (bottom.isShowing()) {
            bottom.dismiss();
        }
    }
    @Override
    protected FragmentAddCategoriesBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentAddCategoriesBinding.inflate(inflater,container,false);
    }
}