package com.example.eu_fstyle_mobile.src.view.user.home;

import android.icu.text.DecimalFormat;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.example.eu_fstyle_mobile.databinding.BottomSheetHinhthucvcBinding;
import com.example.eu_fstyle_mobile.databinding.FragmentDetailProductBinding;
import com.example.eu_fstyle_mobile.src.adapter.ViewPager_detail_Adapter;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;
import com.example.eu_fstyle_mobile.src.model.Product;
import com.example.eu_fstyle_mobile.src.model.User;
import com.example.eu_fstyle_mobile.src.request.RequestCreateCart;
import com.example.eu_fstyle_mobile.src.request.RequestCreateFavourite;
import com.example.eu_fstyle_mobile.src.retrofit.ApiClient;
import com.example.eu_fstyle_mobile.src.retrofit.ApiService;
import com.example.eu_fstyle_mobile.src.view.user.payment.CartFragment;
import com.example.eu_fstyle_mobile.ultilties.UserPrefManager;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProductFragment extends BaseFragment<FragmentDetailProductBinding> {
    public static final String Products = "product";
    FragmentDetailProductBinding binding;
    ViewPager_detail_Adapter adapter;
    User user;
    boolean isCheckDetail = false;

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
        onCLickButton();

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
                            Toast.makeText(getActivity(), "Thêm vào yêu thích thành công", Toast.LENGTH_SHORT).show();
                        } else {
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
        binding.btnThemgiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiService apiService = ApiClient.getClient().create(ApiService.class);
                RequestCreateCart requestCreateCart = new RequestCreateCart(product.getImage64()[0], product.getName(), product.getQuantity(), product.getPrice());
                Call<Product> call = apiService.createCart(user.get_id(), requestCreateCart);
                call.enqueue(new Callback<Product>() {
                    @Override
                    public void onResponse(Call<Product> call, Response<Product> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getActivity(), "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Thêm vào giỏ hàng thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<Product> call, Throwable t) {
                        Toast.makeText(getActivity(), "Server error: " + t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
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
    }

    private void onCLickButton() {
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
                binding.detailTxtPhivanchuyen.setText("15,000");
                binding.detailTxtDateline.setText("3 ngày");
                dialog.dismiss();
            });
            binding1.constraintLayoutTietkiem.setOnClickListener(v1 -> {
                binding.detailTxtVanchuyen.setText("Tiết kiệm");
                binding.detailTxtPhivanchuyen.setText("5,000");
                binding.detailTxtDateline.setText("7 ngày");
                dialog.dismiss();
            });
            dialog.show();
        });
    }

    private void getData() {
        Product product = (Product) getArguments().getSerializable(Products);
        Log.d("Huy", "onCreateView: " + product.getName());
        adapter = new ViewPager_detail_Adapter(getActivity(), product.getImage64());
        binding.viewpagerDetail.setAdapter(adapter);
        binding.tenDetail.setText(product.getName());
        DecimalFormat def = new DecimalFormat("###,###,###");
        binding.giaDetail.setText(def.format(product.getPrice()) + " VNĐ");
        binding.detailTxtMota.setText(product.getDescription());
        binding.detailTextview.setText("1" + "/" + product.getImage64().length);
        Float total = Float.parseFloat(product.getPrice().toString());
        Float phivanchuyen = Float.parseFloat(String.valueOf(binding.detailTxtPhivanchuyen.getText()));
        Float totalProduct = total + phivanchuyen;
        binding.txtTotal.setText(def.format(totalProduct) + " VNĐ");
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

    @Override
    protected FragmentDetailProductBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentDetailProductBinding.inflate(inflater, container, false);
    }
}