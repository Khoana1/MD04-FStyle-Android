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
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.airbnb.lottie.LottieAnimationView;
import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.databinding.BottomSheetDathangBinding;
import com.example.eu_fstyle_mobile.databinding.BottomSheetHinhthucvcBinding;
import com.example.eu_fstyle_mobile.databinding.DialogGoiysizeBinding;
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
    String size ="";
    Product product;
    boolean isCheckDetail = false;
    boolean isCheckDathang=false;
    boolean isSize36,isSize37,isSize38,isSize39= false;
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
        binding.btnThemgiohang.setOnClickListener(v -> {
            isCheckDathang = true;
            buttomSheetDetail();
        });
        binding.btnDathangDetail.setOnClickListener(v -> {
            isCheckDathang =false;
            buttomSheetDetail();
        });
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
    private void buttomSheetDetail(){
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
        binding1.itemTxtKhoDathang.setText("Đã bán: "+product.getQuantity());
        binding1.banggoiysize.setOnClickListener(v -> {
            goiysize();
        });
        binding1.itemBtn36.setOnClickListener(v -> {
            isSize36 = !isSize36;
            toggleButton(binding1.itemBtn36, isSize36);
            if(isSize36){
                isSize38 = false;
                isSize37 = false;
                isSize39 = false;
                toggleButton(binding1.itemBtn37, false);
                toggleButton(binding1.itemBtn38, false);
                toggleButton(binding1.itemBtn39, false);
                size = "36";
            }
        });
        binding1.itemBtn37.setOnClickListener(v -> {
            isSize37 = !isSize37;
            toggleButton(binding1.itemBtn37, isSize37);
            if(isSize37){
                isSize38 = false;
                isSize36 = false;
                isSize39 = false;
                toggleButton(binding1.itemBtn36, false);
                toggleButton(binding1.itemBtn38, false);
                size = "37";
            }
        });
        binding1.itemBtn38.setOnClickListener(v -> {
            isSize38 = !isSize38;
            toggleButton(binding1.itemBtn38, isSize38);
            if(isSize38){
                isSize36 = false;
                isSize37 = false;
                isSize39 = false;
                toggleButton(binding1.itemBtn37, false);
                toggleButton(binding1.itemBtn36, false);
                toggleButton(binding1.itemBtn39, false);
                size = "38";
            }
        });
        binding1.itemBtn39.setOnClickListener(v -> {
            isSize39 = !isSize39;
            toggleButton(binding1.itemBtn39, isSize39);
            if(isSize39){
                isSize38 = false;
                isSize37 = false;
                isSize36 = false;
                toggleButton(binding1.itemBtn37, false);
                toggleButton(binding1.itemBtn38, false);
                toggleButton(binding1.itemBtn36, false);
                size = "39";
            }
        });
        //
        int sl = Integer.parseInt(binding1.itemBtnGiatri.getText().toString());
        if(sl==1){
            binding1.itemBtntruDonhang.setEnabled(false);
        }else if(sl>1) {
            binding1.itemBtntruDonhang.setEnabled(true);
        }
        binding1.itemBtntruDonhang.setOnClickListener(v -> {
            int soluong = Integer.parseInt(binding1.itemBtnGiatri.getText().toString())-1;
            if(soluong==1){
                binding1.itemBtntruDonhang.setEnabled(false);
                binding1.itemBtnGiatri.setText(soluong+"");
            }else if(soluong>1){
                binding1.itemBtntruDonhang.setEnabled(true);
                binding1.itemBtnGiatri.setText(soluong+"");
            }
        });
        binding1.itemBtncongDathang.setOnClickListener(v -> {
            int soluong = Integer.parseInt(binding1.itemBtnGiatri.getText().toString())+1;
            if(soluong==1){
                binding1.itemBtntruDonhang.setEnabled(false);
                binding1.itemBtnGiatri.setText(""+soluong);
            }else if(soluong>1){
                binding1.itemBtntruDonhang.setEnabled(true);
                binding1.itemBtnGiatri.setText(""+soluong);
            }
        });
        //
        binding1.itemBtnDathang.setOnClickListener(v -> {
            Log.d("Huy", "Themgiohang: "+size);
            Log.d("Huy", "Themgiohang: "+binding1.itemBtnGiatri.getText().toString());
            if(size.isEmpty()){
               showAlertDialog("Bạn chưa chọn size");
            }else{
                if(isCheckDathang){
                    themGioHang(product, size, Integer.parseInt(binding1.itemBtnGiatri.getText().toString()));
                }else {
                    bottomSheetDialog.dismiss();
                   muaNgay(product,size,Integer.parseInt(binding1.itemBtnGiatri.getText().toString()));
                }
            }
        });
        bottomSheetDialog.show();
    }
    private void themGioHang(Product product, String size, int quality){

    }
    private void muaNgay(Product product, String size, int quality){
        ThanhtoanFragment thanhtoanFragment = ThanhtoanFragment.getInstance(product,size,quality);
            openScreen(thanhtoanFragment,true);
    }
    private void toggleButton(Button button, boolean isSelected) {
        if (isSelected) {
            button.setBackgroundResource(R.drawable.bg_corner20_silver);
            button.setTextColor(Color.WHITE);
        } else {
            button.setBackgroundResource(R.drawable.bg_btn_size_color);
            button.setTextColor(Color.BLACK);
            size="";
        }
    }
    private void goiysize(){
        Dialog dialog = new Dialog(getActivity());
        DialogGoiysizeBinding binding1 = DialogGoiysizeBinding.inflate(getLayoutInflater());
        View dialogView = binding1.getRoot();
        dialog.setContentView(dialogView);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        binding1.goiysizeBack.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }
    @Override
    protected FragmentDetailProductBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentDetailProductBinding.inflate(inflater,container,false);
    }

}