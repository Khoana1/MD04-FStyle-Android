package com.example.eu_fstyle_mobile.src.view.user.home;

import android.app.Dialog;
import android.graphics.Color;
import android.icu.text.DecimalFormat;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.databinding.BottomSheetDathangBinding;
import com.example.eu_fstyle_mobile.databinding.BottomSheetHinhthucvcBinding;
import com.example.eu_fstyle_mobile.databinding.DialogGoiysizeBinding;
import com.example.eu_fstyle_mobile.databinding.FragmentDetailProductBinding;
import com.example.eu_fstyle_mobile.src.adapter.ViewPager_detail_Adapter;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;
import com.example.eu_fstyle_mobile.src.model.Cart;
import com.example.eu_fstyle_mobile.src.model.Product;
import com.example.eu_fstyle_mobile.src.model.ProductCart;
import com.example.eu_fstyle_mobile.src.model.User;
import com.example.eu_fstyle_mobile.src.request.RequestCreateCart;
import com.example.eu_fstyle_mobile.src.request.RequestCreateFavourite;
import com.example.eu_fstyle_mobile.src.retrofit.ApiClient;
import com.example.eu_fstyle_mobile.src.retrofit.ApiService;
import com.example.eu_fstyle_mobile.src.view.user.payment.CartFragment;
import com.example.eu_fstyle_mobile.src.view.user.profile.MyFavouriteFragment;
import com.example.eu_fstyle_mobile.ultilties.UserPrefManager;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProductFragment extends BaseFragment<FragmentDetailProductBinding> {
    public static final String Products = "product";
    FragmentDetailProductBinding binding;
    ViewPager_detail_Adapter adapter;
    User user;
    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
    boolean isCheckDetail = false;
    Product product;
    String size = "";
    int orderCheck = 0;
    boolean isSize36, isSize37, isSize38, isSize39 = false;
    int soluong;

    private Boolean isCart;

    private Boolean isBuy;

    public static DetailProductFragment newInstance(Product product) {
        DetailProductFragment detail = new DetailProductFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Products, product);
        detail.setArguments(bundle);
        return detail;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDetailProductBinding.inflate(inflater, container, false);
        initView();
        getData();
        initListener();
        getCartCountNumber();
        setTotolPrice();
        return binding.getRoot();
    }

    private void initListener() {
        Product product = (Product) getArguments().getSerializable(Products);
        binding.btnFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiService apiService = ApiClient.getClient().create(ApiService.class);
                RequestCreateFavourite requestCreateFavourite = new RequestCreateFavourite(product.getName(), product.getQuantity(), product.getPrice().toString(), product.getImage64()[0]);
                Call<Product> call = apiService.createFavorite(user.get_id(), requestCreateFavourite);
                call.enqueue(new Callback<Product>() {
                    @Override
                    public void onResponse(Call<Product> call, Response<Product> response) {
                        if (response.isSuccessful()) {
                            binding.btnFavourite.setImageResource(R.drawable.icon_heart_red);
                            Toast.makeText(getActivity(), "Thêm vào yêu thích thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            binding.btnFavourite.setImageResource(R.drawable.icon_heart);
                            Toast.makeText(getActivity(), "Thêm vào yêu thích thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Product> call, Throwable t) {
                        Toast.makeText(getActivity(), "Server error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        binding.cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderCheck = 1;
                bottomSheetDetail();
            }
        });
        binding.btnDathangDetail.setOnClickListener(v -> {
            orderCheck = 2;
            bottomSheetDetail();
        });
        binding.detailBack.setOnClickListener(v -> {
            getActivity().onBackPressed();
        });
        binding.detailBtnVanchuyen.setOnClickListener(v -> {
            BottomSheetHinhthucvcBinding binding1 = BottomSheetHinhthucvcBinding.inflate(getLayoutInflater());
            BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
            View bottomView = binding1.getRoot();
            dialog.setContentView(bottomView);

            ViewGroup.LayoutParams params = bottomView.getLayoutParams();
            params.height = (int) (getResources().getDisplayMetrics().heightPixels * 0.5);
            bottomView.setLayoutParams(params);

            binding1.constraintLayoutNhanh.setOnClickListener(v1 -> {
                binding.detailTxtVanchuyen.setText("Nhanh");
                binding.detailTxtPhivanchuyen.setText(decimalFormat.format(15000));
                binding.detailTxtDateline.setText("3 ngày");
                setTotolPrice();
                dialog.dismiss();
            });
            binding1.constraintLayoutTietkiem.setOnClickListener(v1 -> {
                binding.detailTxtVanchuyen.setText("Tiết kiệm");
                binding.detailTxtPhivanchuyen.setText(decimalFormat.format(5000));
                binding.detailTxtDateline.setText("7 ngày");
                setTotolPrice();
                dialog.dismiss();
            });
            dialog.show();
        });
    }

    private void initView() {
        user = UserPrefManager.getInstance(getActivity()).getUser();
        binding.imgOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScreen(new CartFragment(), true);
            }
        });
        binding.cardView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScreenHome(new MyFavouriteFragment(), true);
            }
        });
    }


    private void getData() {
        product = (Product) getArguments().getSerializable(Products);
        Log.d("Huy", "onCreateView: " + product.getName());
        adapter = new ViewPager_detail_Adapter(getActivity(), product.getImage64());
        binding.viewpagerDetail.setAdapter(adapter);
        binding.detailTxtPhivanchuyen.setText(decimalFormat.format(15000));
        binding.tenDetail.setText(product.getName());
        binding.giaDetail.setText(decimalFormat.format(product.getPrice()) + " VNĐ");
        binding.detailTxtMota.setText(product.getDescription());
        binding.detailTextview.setText("1" + "/" + product.getImage64().length);
        binding.textShowMore.setOnClickListener(v -> {
            isCheckDetail = !isCheckDetail;
            if (isCheckDetail) {
                binding.detailTxtMota.setMaxLines(Integer.MAX_VALUE);
                binding.textShowMore.setText("Rút gọn");
            } else {
                binding.detailTxtMota.setMaxLines(2);
                binding.detailTxtMota.setEllipsize(TextUtils.TruncateAt.END);
                binding.textShowMore.setText("Xem thêm");
            }

        });
        binding.viewpagerDetail.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                int currentPage = position + 1;
                int totalPage = product.getImage64().length;
                String pageIndicator = currentPage + "/" + totalPage;
                binding.detailTextview.setText(pageIndicator);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void setTotolPrice() {
        Float total = Float.parseFloat(product.getPrice().toString());
        Float phivanchuyen = Float.parseFloat(String.valueOf(binding.detailTxtPhivanchuyen.getText()).replaceAll(",", ""));
        Float totalProduct = total + phivanchuyen;
        binding.txtTotal.setText(decimalFormat.format(totalProduct) + " VNĐ");
    }

    private void bottomSheetDetail() {
        BottomSheetDathangBinding binding1 = BottomSheetDathangBinding.inflate(getLayoutInflater());
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
        View bottomView = binding1.getRoot();
        bottomSheetDialog.setContentView(bottomView);
        if (!bottomSheetDialog.isShowing()) {
            size = "";
        }
        binding1.itemDialogCancel.setOnClickListener(v -> bottomSheetDialog.dismiss());
        Picasso.get().load(product.getImage64()[0])
                .placeholder(R.drawable.icon_home)
                .error(R.drawable.icon_erro)
                .into(binding1.itemImageDathang);
        binding1.itemTxtGiaDathang.setText("Giá: " + product.getPrice());
        binding1.itemTxtKhoDathang.setText("Đã bán: " + product.getQuantity());
        binding1.banggoiysize.setOnClickListener(v -> {
            goiysize();
        });
        binding1.itemBtn36.setOnClickListener(v -> {
            isSize36 = !isSize36;
            toggleButton(binding1.itemBtn36, isSize36);
            if (isSize36) {
                isSize38 = false;
                isSize37 = false;
                isSize39 = false;
                toggleButton(binding1.itemBtn37, false);
                toggleButton(binding1.itemBtn38, false);
                toggleButton(binding1.itemBtn39, false);
                size = "36";
            } else {
                size = "";
            }
        });
        binding1.itemBtn37.setOnClickListener(v -> {
            isSize37 = !isSize37;
            toggleButton(binding1.itemBtn37, isSize37);
            if (isSize37) {
                isSize38 = false;
                isSize36 = false;
                isSize39 = false;
                toggleButton(binding1.itemBtn36, false);
                toggleButton(binding1.itemBtn38, false);
                size = "37";
            } else {
                size = "";
            }
        });
        binding1.itemBtn38.setOnClickListener(v -> {
            isSize38 = !isSize38;
            toggleButton(binding1.itemBtn38, isSize38);
            if (isSize38) {
                isSize36 = false;
                isSize37 = false;
                isSize39 = false;
                toggleButton(binding1.itemBtn37, false);
                toggleButton(binding1.itemBtn36, false);
                toggleButton(binding1.itemBtn39, false);
                size = "38";
            } else {
                size = "";
            }
        });
        binding1.itemBtn39.setOnClickListener(v -> {
            isSize39 = !isSize39;
            toggleButton(binding1.itemBtn39, isSize39);
            if (isSize39) {
                isSize38 = false;
                isSize37 = false;
                isSize36 = false;
                toggleButton(binding1.itemBtn37, false);
                toggleButton(binding1.itemBtn38, false);
                toggleButton(binding1.itemBtn36, false);
                size = "39";
            } else {
                size = "";
            }
        });
        //
        int sl = Integer.parseInt(binding1.itemBtnGiatri.getText().toString());
        if (sl == 1) {
            binding1.itemBtntruDonhang.setEnabled(false);
        } else if (sl > 1) {
            binding1.itemBtntruDonhang.setEnabled(true);
        }
        binding1.itemBtntruDonhang.setOnClickListener(v -> {
            soluong = Integer.parseInt(binding1.itemBtnGiatri.getText().toString()) - 1;
            if (soluong == 1) {
                binding1.itemBtntruDonhang.setEnabled(false);
                binding1.itemBtnGiatri.setText(soluong + "");
            } else if (soluong > 1) {
                binding1.itemBtntruDonhang.setEnabled(true);
                binding1.itemBtnGiatri.setText(soluong + "");
            }
        });
        binding1.itemBtncongDathang.setOnClickListener(v -> {
            soluong = Integer.parseInt(binding1.itemBtnGiatri.getText().toString()) + 1;
            if (soluong == 1) {
                binding1.itemBtntruDonhang.setEnabled(false);
                binding1.itemBtnGiatri.setText("" + soluong);
            } else if (soluong > 1) {
                binding1.itemBtntruDonhang.setEnabled(true);
                binding1.itemBtnGiatri.setText("" + soluong);
            }
        });
        //
        binding1.itemBtnDathang.setOnClickListener(v -> {
            Log.d("Huy", "Themgiohang: " + size);
            Log.d("Huy", "Themgiohang: " + binding1.itemBtnGiatri.getText().toString());
            if (size.isEmpty()) {
                showAlertDialog("Bạn chưa chọn size");
            } else {
                switch (orderCheck) {
                    case 1:
                        User user = UserPrefManager.getInstance(getActivity()).getUser();
                        ApiService apiService = ApiClient.getClient().create(ApiService.class);
                        RequestCreateCart requestCreateCart = new RequestCreateCart(product.get_id(), product.getName(), binding1.itemBtnGiatri.getText().toString(), size, product.getPrice(), product.getImage64()[0]);
                        Call<Product> call = apiService.createCart(user.get_id(), requestCreateCart);
                        call.enqueue(new Callback<Product>() {
                            @Override
                            public void onResponse(Call<Product> call, Response<Product> response) {
                                Toast.makeText(getActivity(), "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<Product> call, Throwable t) {
                                Toast.makeText(getActivity(), "Thêm vào giỏ hàng thất bại, thử lại sau", Toast.LENGTH_SHORT).show();
                            }
                        });
                        if (orderCheck == 1 || orderCheck == 2) {
                            orderCheck = 0;
                        }
                        bottomSheetDialog.dismiss();
                        break;
                    case 2:
                        // xử lý mua hàng
                        if (orderCheck == 1 || orderCheck == 2) {
                            orderCheck = 0;
                        }
                        bottomSheetDialog.dismiss();
                        break;
                }


            }
        });
        bottomSheetDialog.show();
    }

    private void toggleButton(Button button, boolean isSelected) {
        if (isSelected) {
            button.setBackgroundResource(R.drawable.bg_corner20_silver);
            button.setTextColor(Color.WHITE);
        } else {
            button.setBackgroundResource(R.drawable.bg_btn_size_color);
            button.setTextColor(Color.BLACK);
        }
    }

    private void goiysize() {
        Dialog dialog = new Dialog(getActivity());
        DialogGoiysizeBinding binding1 = DialogGoiysizeBinding.inflate(getLayoutInflater());
        View dialogView = binding1.getRoot();
        dialog.setContentView(dialogView);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        binding1.goiysizeBack.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    private void getCartCountNumber() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<Cart> call = apiService.getCart(user.get_id());
        call.enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                if (response.isSuccessful()) {
                    String numberCart = String.valueOf(response.body().getListProduct().size());
                    binding.tvNumberCart.setText(numberCart);
                }
            }

            @Override
            public void onFailure(Call<Cart> call, Throwable t) {
                Log.d("Error", "Server error: " + t);
            }
        });

    }


    @Override
    protected FragmentDetailProductBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentDetailProductBinding.inflate(inflater, container, false);
    }
}