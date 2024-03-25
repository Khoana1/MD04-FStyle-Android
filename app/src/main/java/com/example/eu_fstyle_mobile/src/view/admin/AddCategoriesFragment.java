package com.example.eu_fstyle_mobile.src.view.admin;

import static android.app.Activity.RESULT_OK;
import static android.util.Base64.encodeToString;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.databinding.FragmentAddCategoriesBinding;
import com.example.eu_fstyle_mobile.src.adapter.ImageAdapter;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;
import com.example.eu_fstyle_mobile.src.model.Categories;
import com.example.eu_fstyle_mobile.src.model.User;
import com.example.eu_fstyle_mobile.ultilties.AdminPreManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;


public class AddCategoriesFragment extends BaseFragment<FragmentAddCategoriesBinding> {
       private Uri uriImage= Uri.parse("");
       private static final int READ_PERMISSION = 101;
       String imageBase64;
       String name;
       private AddCategoryViewModel addCategoryViewModel;
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
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,"select image"), 2);
            hideKeyboard();
        });
        binding.btnDeletePicker.setOnClickListener(v -> {
        });
        binding.btnAddCategory.setOnClickListener(v -> {
            validateAddCategory();
            if(binding.categoryNameContainer.getHelperText()== null && !uriImage.equals("")){
                imageBase64 = convertImagesToBase64(uriImage);
                User user = AdminPreManager.getInstance(getActivity()).getAdminData();
                Log.d("Huy", "initView: "+"_"+name+"_"+imageBase64);
                addCategoryViewModel.createCategory( name,imageBase64);
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
        if(uriImage.equals("")){
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2 && resultCode == RESULT_OK && data!= null){
                uriImage= data.getData();
                binding.imageResultCategory.setImageURI(uriImage);
            }else {
            Toast.makeText(getActivity(), "lỗi", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected FragmentAddCategoriesBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentAddCategoriesBinding.inflate(inflater,container,false);
    }
}