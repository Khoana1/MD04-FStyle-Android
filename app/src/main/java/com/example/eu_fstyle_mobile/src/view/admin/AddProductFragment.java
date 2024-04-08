package com.example.eu_fstyle_mobile.src.view.admin;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.example.eu_fstyle_mobile.src.model.Categories;
import com.example.eu_fstyle_mobile.src.model.ListCategories;
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
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AddProductFragment extends BaseFragment<FragmentAddProductBinding> implements CustomSpinner.OnSpinnerEventsListener {
    ArrayList<Uri> uriArrayList = new ArrayList<>();
    ImageAdapter imageAdapter;
    private static final int READ_PERMISSION = 101;
    private static final String PRODUCTS = "product";
    ArrayList<String> base64Images;
    private String productName;
    private String productBrand;
    private String productPrice;
    private String productSize;
    private String productColor;
    private int productQuantity;
    private String idProductType;
    private String productDescription;

    private CategorySpinnerAdapter adapter;

    private AddProductViewModel addProductViewModel;
    private CategoriesViewModel categoriesViewModel;
    private boolean isCheckEdit= false;
    public static AddProductFragment getInstance(Product product){
        AddProductFragment addProductFragment = new AddProductFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(PRODUCTS, product);
        addProductFragment.setArguments(bundle);
        return addProductFragment;
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
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), onBackPressedCallback);
        categoriesViewModel = new ViewModelProvider(this).get(CategoriesViewModel.class);
        categoriesViewModel.getAllCategories();
        spinnerCategory();
        initView();
        addProductViewModel = new ViewModelProvider(this).get(AddProductViewModel.class);
        observeViewModel();
    }
    private void getDataUpdate(){
        if(getArguments() != null){
            Product product = (Product) getArguments().getSerializable(PRODUCTS);
            if(product!= null){
               isCheckEdit = true;
               binding.edtProductName.setText(product.getName());
               binding.edtBrand.setText(product.getBrand());
               binding.edtPrice.setText(""+product.getPrice());
               binding.edtColor.setText(product.getColor());
               binding.edtDes.setText(product.getDescription());
               binding.edtQuantity.setText(""+product.getQuantity());

                StringBuilder arrSize = new StringBuilder();
                for (int i = 0; i < product.getSize().size(); i++) {
                    arrSize.append(product.getSize().get(i));
                    if (i < product.getSize().size()-1) {
                        arrSize.append(",");
                    }
                }
               binding.edSzie.setText(""+arrSize);

            }
        }
    }
    private void spinnerCategory() {
        categoriesViewModel.getCategorieData().observe(getViewLifecycleOwner(), new Observer<ListCategories>() {
            @Override
            public void onChanged(ListCategories listCategories) {
                ArrayList<Categories> list = listCategories.getArrayList();
                adapter = new CategorySpinnerAdapter(requireContext(), list);
                binding.spinnerType.setAdapter(adapter);
            }
        });
        categoriesViewModel.getErrorMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
            }
        });
        binding.spinnerType.setSpinnerEventsListener(this);
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
                Categories selectedCategory = (Categories) parent.getItemAtPosition(position);
                idProductType = selectedCategory.getId();
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
                        binding.priceContainer.getHelperText() == null && binding.sizeContainer.getHelperText() == null &&
                        binding.colorContainer.getHelperText() == null && binding.desContainer.getHelperText() == null &&
                        uriArrayList.size() >= 2) {
                    idProductType = ((Categories) binding.spinnerType.getSelectedItem()).getId();

                    base64Images = convertImagesToBase64(uriArrayList);
                    int productPriceNumber = Integer.parseInt(binding.edtPrice.getText().toString().replace(",", ""));
                    String[] sizeArrayString = binding.edSzie.getText().toString().split(",");
                    addProductViewModel.createProduct(productName, base64Images.toArray(new String[0]), productBrand, productPriceNumber, sizeArray(sizeArrayString), productColor, productQuantity, idProductType, productDescription);
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
    private List<Integer> sizeArray(String[] listString){
        List<Integer> list = new ArrayList<>();
        for(String size :listString){
            try {
                int sizeInt = Integer.parseInt(size.trim());
                list.add(sizeInt);
            }catch (NumberFormatException e){
                Toast.makeText(getActivity(), "Chuỗi nhập vào không hợp lệ", Toast.LENGTH_SHORT).show();
            }
        }
        return list;
    }
    private void validateAddProduct() {
        String helperText = "";
        productName = binding.edtProductName.getText().toString();
        productBrand = binding.edtBrand.getText().toString();
        productPrice = binding.edtPrice.getText().toString();
        productSize = binding.edSzie.getText().toString();
        productColor = binding.edtColor.getText().toString();
        productQuantity = Integer.parseInt(binding.edtQuantity.getText().toString());
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
        if (productSize.isEmpty()) {
            helperText = "Không được để trống size giày";
            binding.sizeContainer.setHelperText(helperText);
        } else {
            binding.sizeContainer.setHelperText("");
            Pattern pattern = Pattern.compile("[^0-9,]");
            Matcher matcher = pattern.matcher(productSize);
            if(matcher.find()){
                helperText ="Chuỗi nhập vào chứa ký tự không phải số";
                binding.sizeContainer.setHelperText(helperText);
                return;
            }else {
                binding.sizeContainer.setHelperText("");
            }
        }
        if (productColor.isEmpty()) {
            helperText = "Không được để trống màu giày";
            binding.colorContainer.setHelperText(helperText);
        } else {
            binding.colorContainer.setHelperText("");
        }
        if (productQuantity==0) {
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
        binding.sizeContainer.setHelperText("");
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