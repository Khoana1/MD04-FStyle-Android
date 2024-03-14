package com.example.eu_fstyle_mobile.src.view.admin;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.databinding.FragmentAddProductBinding;
import com.example.eu_fstyle_mobile.src.adapter.CategorySpinnerAdapter;
import com.example.eu_fstyle_mobile.src.adapter.ImageAdapter;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;
import com.example.eu_fstyle_mobile.src.model.Category;
import com.example.eu_fstyle_mobile.src.model.DataCategory;
import com.example.eu_fstyle_mobile.src.model.Product;
import com.example.eu_fstyle_mobile.src.model.User;
import com.example.eu_fstyle_mobile.src.view.custom.CustomSpinner;
import com.example.eu_fstyle_mobile.src.view.custom.NumberTextWatcherForThousand;
import com.example.eu_fstyle_mobile.ultilties.AdminPreManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class AddProductFragment extends BaseFragment<FragmentAddProductBinding> implements CustomSpinner.OnSpinnerEventsListener {
    ArrayList<Uri> uriArrayList = new ArrayList<>();
    ImageAdapter imageAdapter;
    private static final int READ_PERMISSION = 101;
    ArrayList<String> base64Images;
    private String productName;
    private String productBrand;
    private String productPrice;
    private String productColor;
    private String productQuantity;
    private String productType;
    private String productDescription;

    private CategorySpinnerAdapter adapter;

    private AddProductViewModel addProductViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = getFragmentBinding(inflater, container);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), onBackPressedCallback);
        spinnerCategory();
        initView();
        addProductViewModel = new ViewModelProvider(this).get(AddProductViewModel.class);
        observeViewModel();
    }

    private void spinnerCategory() {
        binding.spinnerType.setSpinnerEventsListener(this);
        adapter = new CategorySpinnerAdapter(requireContext(), DataCategory.getDataCategory());
        binding.spinnerType.setAdapter(adapter);
    }

    private void observeViewModel() {
        addProductViewModel.getProductLiveData().observe(getViewLifecycleOwner(), new Observer<Product>() {
            @Override
            public void onChanged(Product product) {
                binding.edtProductName.setText("");
                binding.edtBrand.setText("");
                binding.edtPrice.setText("");
                binding.edtColor.setText("");
                binding.edtQuantity.setText("");
                binding.edtDes.setText("");
                uriArrayList.clear();
                imageAdapter.notifyDataSetChanged();
                binding.tvErrorPickImage.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                openScreenAdmin(new HomeAdminFragment(), false);
            }
        });
        addProductViewModel.getErrorLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String error) {
                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        setStatusHelperText();
        binding.icBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().onBackPressed();
            }
        });
        binding.edtPrice.addTextChangedListener(new NumberTextWatcherForThousand(binding.edtPrice));
        binding.spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Category selectedCategory = (Category) parent.getItemAtPosition(position);
                productType = selectedCategory.getName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        imageAdapter = new ImageAdapter(uriArrayList);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        binding.recyclerView.setAdapter(imageAdapter);
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_PERMISSION);
        }
        binding.btnImagePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
                hideKeyboard();
            }
        });
        binding.btnDeletePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uriArrayList.clear();
                imageAdapter.notifyDataSetChanged();
            }
        });
        binding.btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAddProduct();
                if (binding.productNameContainer.getHelperText() == null && binding.brandContainer.getHelperText() == null &&
                        binding.priceContainer.getHelperText() == null && binding.colorContainer.getHelperText() == null &&
                        binding.desContainer.getHelperText() == null && uriArrayList.size() >= 2) {
                    productType = ((Category) binding.spinnerType.getSelectedItem()).getName();

                    base64Images = convertImagesToBase64(uriArrayList);
                    int productPriceNumber = Integer.parseInt(binding.edtPrice.getText().toString().replace(",", ""));
                    User user = AdminPreManager.getInstance(getActivity()).getAdminData();
                    addProductViewModel.createProduct(user.get_id(), productName, base64Images.toArray(new String[0]), productBrand, productPriceNumber, productColor, productQuantity, productType, productDescription);
                }
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

    private void validateAddProduct() {
        String helperText = "";
        productName = binding.edtProductName.getText().toString();
        productBrand = binding.edtBrand.getText().toString();
        productPrice = binding.edtPrice.getText().toString();
        productColor = binding.edtColor.getText().toString();
        productQuantity = binding.edtQuantity.getText().toString();
        productDescription = binding.edtDes.getText().toString();
        if (productName.isEmpty()) {
            helperText = "Không được để trốn tên giày";
            binding.productNameContainer.setHelperText(helperText);
        } else {
            binding.productNameContainer.setHelperText("");
        }
        if (productBrand.isEmpty()) {
            helperText = "Không được để trống tên hãng";
            binding.brandContainer.setHelperText(helperText);
        } else {
            binding.brandContainer.setHelperText("");
        }
        if (productPrice.isEmpty()) {
            helperText = "Không được để trống giá giày";
            binding.priceContainer.setHelperText(helperText);
        } else {
            binding.priceContainer.setHelperText("");
        }
        if (productColor.isEmpty()) {
            helperText = "Không được để trống màu giày";
            binding.colorContainer.setHelperText(helperText);
        } else {
            binding.colorContainer.setHelperText("");
        }
        if (productQuantity.isEmpty()) {
            helperText = "Không được để trống số lượng giày";
            binding.quantityContainer.setHelperText(helperText);
        } else {
            binding.quantityContainer.setHelperText("");
        }
        if (productDescription.isEmpty()) {
            helperText = "Không để trống mô tả";
            binding.desContainer.setHelperText(helperText);
        } else if (productDescription.length() < 20) {
            helperText = "Mô tả phải có ít nhất 20 ký tự";
            binding.desContainer.setHelperText(helperText);
        } else {
            binding.desContainer.setHelperText("");
        }
        if (uriArrayList.size() < 2) {
            binding.tvErrorPickImage.setVisibility(View.VISIBLE);
            binding.tvErrorPickImage.setText("Bạn phải chọn ít nhất 2 hình ảnh");
        } else {
            binding.tvErrorPickImage.setVisibility(View.GONE);
        }
    }

    private void setStatusHelperText() {
        binding.productNameContainer.setHelperText("");
        binding.brandContainer.setHelperText("");
        binding.priceContainer.setHelperText("");
        binding.colorContainer.setHelperText("");
        binding.quantityContainer.setHelperText("");
        binding.desContainer.setHelperText("");
    }

    @Override
    protected FragmentAddProductBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentAddProductBinding.inflate(inflater, container, false);
    }

    private final OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNavigation);
            bottomNavigationView.setVisibility(View.VISIBLE);
            openScreenAdmin(new HomeAdminFragment(), false);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data.getClipData() != null) {
                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                    uriArrayList.add(imageUri);
                }
            } else if (data.getData() != null) {
                String imageUri = data.getData().getPath();
                uriArrayList.add(Uri.parse(imageUri));
            }
            imageAdapter.notifyDataSetChanged();
        }
    }

    public ArrayList<String> convertImagesToBase64(ArrayList<Uri> imageUris) {
        ArrayList<String> base64Images = new ArrayList<>();
        for (Uri uri : imageUris) {
            try {
                InputStream inputStream = getContext().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 10, outputStream);
                byte[] bytes = outputStream.toByteArray();
                String base64Image = Base64.encodeToString(bytes, Base64.DEFAULT);
                base64Images.add(base64Image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return base64Images;
    }

    @Override
    public void onPopupWindowOpened(Spinner spinner) {
        binding.spinnerType.setBackground(getResources().getDrawable(R.drawable.bg_spinner_down));
    }

    @Override
    public void onPopupWindowClosed(Spinner spinner) {
        binding.spinnerType.setBackground(getResources().getDrawable(R.drawable.bg_spinner_up));
    }

    private void clearFocusEditText() {
        binding.edtProductName.clearFocus();
        binding.edtBrand.clearFocus();
        binding.edtPrice.clearFocus();
        binding.edtColor.clearFocus();
        binding.edtQuantity.clearFocus();
        binding.edtDes.clearFocus();
    }
}