package com.example.eu_fstyle_mobile.src.view.user.home;

import android.graphics.Color;
import android.icu.text.DecimalFormat;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.viewpager.widget.ViewPager;

import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.databinding.BottomSheetDathangBinding;
import com.example.eu_fstyle_mobile.databinding.BottomSheetHinhthucvcBinding;
import com.example.eu_fstyle_mobile.databinding.FragmentDetailProductBinding;
import com.example.eu_fstyle_mobile.src.adapter.ViewPager_detail_Adapter;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;
import com.example.eu_fstyle_mobile.src.model.Product;
import com.example.eu_fstyle_mobile.src.model.User;
import com.example.eu_fstyle_mobile.ultilties.UserPrefManager;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

public class DetailProductFragment extends BaseFragment<FragmentDetailProductBinding> {
    public static final String Products = "product";
    FragmentDetailProductBinding binding;
    ViewPager_detail_Adapter adapter;
    User user;
    String color="";
    String size ="";
    Product product;
    boolean isCheckDetail = false;
    boolean isSizeS,isSizeM,isSizeL,isSizeXl= false;
    boolean isColorBlack, isColorWhite,isColorGray,isColorRed = false;
    public static DetailProductFragment newInstance(Product product){
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
        onCLickButton();

       return binding.getRoot();
    }
    private void initView() {
        user = UserPrefManager.getInstance(getActivity()).getUser();
    }
    private void onCLickButton() {
        binding.detailBack.setOnClickListener(v -> {getActivity().onBackPressed();});
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
                binding.detailTxtPhivanchuyen.setText("15000");
                binding.detailTxtDateline.setText("3 ngày");
                dialog.dismiss();
            });
            binding1.constraintLayoutTietkiem.setOnClickListener(v1 -> {
                binding.detailTxtVanchuyen.setText("Tiết kiệm");
                binding.detailTxtPhivanchuyen.setText("5000");
                binding.detailTxtDateline.setText("7 ngày");
                dialog.dismiss();
            });
            dialog.show();
        });
        binding.btnThemgiohang.setOnClickListener(v -> {Themgiohang();});
    }
    private void getData() {
        product = (Product) getArguments().getSerializable(Products);
        Log.d("Huy", "onCreateView: "+product.getName());
        adapter = new ViewPager_detail_Adapter(getActivity(), product.getImage64());
        binding.viewpagerDetail.setAdapter(adapter);
        binding.tenDetail.setText(product.getName());
        DecimalFormat def = new DecimalFormat("###,###,###");
        binding.giaDetail.setText(def.format(product.getPrice())+ " VNĐ");
        binding.detailTxtMota.setText(product.getDescription());
        binding.detailTextview.setText("1"+"/"+product.getImage64().length);
        binding.dabanDetail.setText("đã bán "+ product.getQuantity());
        Float total = Float.parseFloat(product.getPrice().toString());
        Float phivanchuyen = Float.parseFloat(String.valueOf(binding.detailTxtPhivanchuyen.getText()));
        Float totalProduct = total+phivanchuyen;
        binding.txtTotal.setText(def.format(totalProduct)+" VNĐ");
        binding.textShowMore.setOnClickListener(v -> {
            isCheckDetail = !isCheckDetail;
            if(isCheckDetail){
                binding.detailTxtMota.setMaxLines(Integer.MAX_VALUE);
                binding.textShowMore.setText("Rút gọn");
            }else {
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
    private void Themgiohang(){
        BottomSheetDathangBinding binding1 = BottomSheetDathangBinding.inflate(getLayoutInflater());
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
        View bottomView  = binding1.getRoot();
        bottomSheetDialog.setContentView(bottomView);

        binding1.itemDialogCancel.setOnClickListener(v -> bottomSheetDialog.dismiss());
        Picasso.get().load(product.getImage64()[0])
                .placeholder(R.drawable.icon_home)
                        .error(R.drawable.icon_erro)
                                .into(binding1.itemImageDathang);
        binding1.itemTxtGiaDathang.setText("Giá: "+product.getPrice());
        binding1.itemTxtKhoDathang.setText("Đã bán "+product.getQuantity());
        binding1.itemBtnS.setOnClickListener(v -> {
            isSizeS = !isSizeS;
            toggleButton(binding1.itemBtnS, isSizeS);
            if(isSizeS){
                isSizeL = false;
                isSizeM = false;
                isSizeXl = false;
                toggleButton(binding1.itemBtnM, false);
                toggleButton(binding1.itemBtnL, false);
                toggleButton(binding1.itemBtnXL, false);
                size = binding1.itemBtnS.getText().toString();
            }
        });
        binding1.itemBtnM.setOnClickListener(v -> {
            isSizeM = !isSizeM;
            toggleButton(binding1.itemBtnM, isSizeM);
            if(isSizeM){
                isSizeL = false;
                isSizeS = false;
                isSizeXl = false;
                toggleButton(binding1.itemBtnS, false);
                toggleButton(binding1.itemBtnL, false);
                toggleButton(binding1.itemBtnXL, false);
                size = binding1.itemBtnM.getText().toString();
            }
        });
        binding1.itemBtnL.setOnClickListener(v -> {
            isSizeL = !isSizeL;
            toggleButton(binding1.itemBtnL, isSizeL);
            if(isSizeL){
                isSizeS = false;
                isSizeM = false;
                isSizeXl = false;
                toggleButton(binding1.itemBtnM, false);
                toggleButton(binding1.itemBtnS, false);
                toggleButton(binding1.itemBtnXL, false);
                size = binding1.itemBtnL.getText().toString();
            }
        });
        binding1.itemBtnXL.setOnClickListener(v -> {
            isSizeXl = !isSizeXl;
            toggleButton(binding1.itemBtnXL, isSizeXl);
            if(isSizeXl){
                isSizeL = false;
                isSizeM = false;
                isSizeS = false;
                toggleButton(binding1.itemBtnM, false);
                toggleButton(binding1.itemBtnL, false);
                toggleButton(binding1.itemBtnS, false);
                size = binding1.itemBtnXL.getText().toString();
            }
        });
        binding1.itemBtnBlack.setOnClickListener(v -> {
            isColorBlack = !isColorBlack;
            toggleButton(binding1.itemBtnBlack, isColorBlack);
            if(isColorBlack){
                isColorRed = false;
                isColorGray = false;
                isColorWhite = false;
                toggleButton(binding1.itemBtnGray, false);
                toggleButton(binding1.itemBtnWhite, false);
                toggleButton(binding1.itemBtnRed, false);
                color = binding1.itemBtnBlack.getText().toString();
            }
        });
        binding1.itemBtnWhite.setOnClickListener(v -> {
            isColorWhite = !isColorWhite;
            toggleButton(binding1.itemBtnWhite, isColorWhite);
            if(isColorWhite){
                isColorRed = false;
                isColorGray = false;
                isColorBlack = false;
                toggleButton(binding1.itemBtnGray, false);
                toggleButton(binding1.itemBtnBlack, false);
                toggleButton(binding1.itemBtnRed, false);
                color = binding1.itemBtnWhite.getText().toString();
            }
        });
        binding1.itemBtnGray.setOnClickListener(v -> {
            isColorGray = !isColorGray;
            toggleButton(binding1.itemBtnGray, isColorGray);
            if(isColorGray){
                isColorRed = false;
                isColorBlack = false;
                isColorWhite = false;
                toggleButton(binding1.itemBtnBlack, false);
                toggleButton(binding1.itemBtnWhite, false);
                toggleButton(binding1.itemBtnRed, false);
                color = binding1.itemBtnGray.getText().toString();
            }
        });
        binding1.itemBtnRed.setOnClickListener(v -> {
            isColorRed = !isColorRed;
            toggleButton(binding1.itemBtnRed, isColorRed);
            if(isColorRed){
                isColorBlack = false;
                isColorGray = false;
                isColorWhite = false;
                toggleButton(binding1.itemBtnGray, false);
                toggleButton(binding1.itemBtnWhite, false);
                toggleButton(binding1.itemBtnBlack, false);
                color = binding1.itemBtnRed.getText().toString();
            }
        });
        binding1.itemBtnDathang.setOnClickListener(v -> {
            Log.d("Huy", "Themgiohang: "+size);
            Log.d("Huy", "Themgiohang: "+color);
        });
        //
        int sl = Integer.parseInt(binding1.itemBtnGiatri.getText().toString());
        if(sl<1){
            binding1.itemBtntruDonhang.setEnabled(false);
        }
        binding1.itemBtntruDonhang.setOnClickListener(v -> {
            int soluong = Integer.parseInt(binding1.itemBtnGiatri.getText().toString())-1;
            if(soluong<1){
                binding1.itemBtntruDonhang.setEnabled(false);
                binding1.itemBtnGiatri.setText(soluong+"");
            }else {
                binding1.itemBtntruDonhang.setEnabled(true);
                binding1.itemBtnGiatri.setText(soluong+"");
            }
        });
        binding1.itemBtncongDathang.setOnClickListener(v -> {
            int soluong = Integer.parseInt(binding1.itemBtnGiatri.getText().toString())+1;
            binding1.itemBtnGiatri.setText(""+soluong);
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
    @Override
    protected FragmentDetailProductBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentDetailProductBinding.inflate(inflater,container,false);
    }
}