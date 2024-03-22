package com.example.eu_fstyle_mobile.src.view.user.home;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.databinding.BottomDialogFilterBinding;
import com.example.eu_fstyle_mobile.databinding.FragmentHomeBinding;
import com.example.eu_fstyle_mobile.src.adapter.BannerAdapter;
import com.example.eu_fstyle_mobile.src.adapter.CategoryHomeAdapter;
import com.example.eu_fstyle_mobile.src.adapter.ProductHomeAdapter;
import com.example.eu_fstyle_mobile.src.adapter.SearchAdapter;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;
import com.example.eu_fstyle_mobile.src.model.Category;
import com.example.eu_fstyle_mobile.src.model.ListProduct;
import com.example.eu_fstyle_mobile.src.model.Product;
import com.example.eu_fstyle_mobile.src.model.User;
import com.example.eu_fstyle_mobile.src.request.RequestCreateFavourite;
import com.example.eu_fstyle_mobile.src.retrofit.ApiClient;
import com.example.eu_fstyle_mobile.src.retrofit.ApiService;
import com.example.eu_fstyle_mobile.src.view.user.payment.CartFragment;
import com.example.eu_fstyle_mobile.src.view.user.profile.ProfileFragment;
import com.example.eu_fstyle_mobile.ultilties.SearchUltils;
import com.example.eu_fstyle_mobile.ultilties.UserPrefManager;
import com.example.eu_fstyle_mobile.src.model.User;
import com.example.eu_fstyle_mobile.ultilties.UserPrefManager;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends BaseFragment<FragmentHomeBinding> implements ProductHomeAdapter.onClickItem{
    FragmentHomeBinding binding;
    ArrayList<Category> arrayList;
    ArrayList<Product> listProduct;
    ArrayList<Product> listFilter;
    ProductHomeAdapter productAdapter;
    CategoryHomeAdapter adapter;
    String[] listBanner;
    BannerAdapter bannerAdapter;
    SearchAdapter searchAdapter;
    private final long DELAY_MS = 3000;
    private final long PERIOD_MS = 5000;
    private Handler handler;
    private Runnable runnable;
    String textButton="";
    boolean isThapToiCaoSelected= false;
    boolean isCaoToiThapSelected= false;
    boolean isDuo1trieuSelected = false;
    boolean isDuo2trieuSelected =false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        Banner();
        openSearch(Gravity.CENTER);
        openFilter();
        getAvatar();
        getCategory();
        getProduct();
        binding.avatarHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScreen(new ProfileFragment(), true);
            }
        });
        initView();
        return binding.getRoot();
    }



    private void initView() {
        User user = UserPrefManager.getInstance(getActivity()).getUser();
        String lastName = getLastName(user.getName());
        binding.textviewNameUser.setText(lastName);
        binding.cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScreen(new CartFragment(), true);
            }
        });
    }

    public String getLastName(String fullName) {
        String[] nameParts = fullName.split(" ");
        return nameParts[nameParts.length - 1];
    }

    private void Banner() {
        listBanner = new String[]{
                "https://i.pinimg.com/564x/ac/ac/a8/acaca828b541e302b54e99bdd1bf855d.jpg",
                "https://i.pinimg.com/564x/c8/ab/37/c8ab37a94b08d6b9758539e1affab07d.jpg",
                "https://i.pinimg.com/564x/f3/91/9a/f3919a2528cb9d114a19bc4ef9c5f31a.jpg",
                "https://i.pinimg.com/564x/3d/19/43/3d19433f4b72d31bec5cc01c26e331fa.jpg",
                "https://i.pinimg.com/564x/06/a4/75/06a47523c560499365d0373b0ce24116.jpg",
        };
        bannerAdapter = new BannerAdapter(getActivity(), listBanner);
        binding.viewpagerHome.setAdapter(bannerAdapter);
        binding.circleIndicatorHome.setViewPager(binding.viewpagerHome);

    }

    @Override
    protected FragmentHomeBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentHomeBinding.inflate(inflater,container,false);
    }


    private void getProduct() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<ListProduct> call = apiService.getAllProducts();
        call.enqueue(new Callback<ListProduct>() {
            @Override
            public void onResponse(Call<ListProduct> call, Response<ListProduct> response) {
                if(response.isSuccessful()&& response.body()!= null){
                    listProduct = response.body().getArrayList();
                    Log.d("Huy", "onResponse: "+listProduct.get(0).getName());
                    productAdapter = new ProductHomeAdapter(getActivity(), listProduct,HomeFragment.this);
                    binding.recycleProductHame.setLayoutManager(new GridLayoutManager(getActivity(),2));
                    binding.recycleProductHame.setAdapter(productAdapter);
                }
            }

            @Override
            public void onFailure(Call<ListProduct> call, Throwable t) {
                Log.d("Huy", "onFailure: "+t.getMessage());
            }
        });

    }

    private void getCategory() {
        arrayList = new ArrayList<>();
        arrayList.add(new Category(1, "https://i.pinimg.com/564x/70/6d/1e/706d1e17ddb9188407952985c83c4ab7.jpg","shoes"));
        arrayList.add(new Category(2, "https://i.pinimg.com/564x/87/e7/b1/87e7b1ecc2ef1580841e7a0d23ed49a0.jpg", "shoes2"));
        adapter = new CategoryHomeAdapter(getActivity(), arrayList);
        binding.recycleCategoryHome.setLayoutManager(new GridLayoutManager(getActivity(),2));
        binding.recycleCategoryHome.setAdapter(adapter);
    }
    private void openFilter() {
        listFilter = new ArrayList<>();
        binding.filterHome.setOnClickListener(v -> {
            BottomDialogFilterBinding binding1 = BottomDialogFilterBinding.inflate(getLayoutInflater());
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
            View bottomsetView = binding1.getRoot();
            bottomSheetDialog.setContentView(bottomsetView);
            ViewGroup.LayoutParams params = bottomsetView.getLayoutParams();
            params.height = (int) (getResources().getDisplayMetrics().heightPixels * 0.6);
            bottomsetView.setLayoutParams(params);

            binding1.backFilter.setOnClickListener(v1 -> bottomSheetDialog.dismiss());
            binding1.btnThaptoicaoFilter.setOnClickListener(v1 -> {
                isThapToiCaoSelected = !isThapToiCaoSelected;
                toggleButton(binding1.btnThaptoicaoFilter, isThapToiCaoSelected);
                if (isThapToiCaoSelected) {
                    isCaoToiThapSelected = false;
                    isDuo1trieuSelected = false;
                    isDuo2trieuSelected = false;
                    toggleButton(binding1.btnCaotoithapFilter, false);
                    toggleButton(binding1.btnDuoi1trieuFilter, false);
                    toggleButton(binding1.btnDuoi2trieuFilter, false);
                    textButton = String.valueOf(binding1.btnThaptoicaoFilter.getText());
                    getThaptoiCao();
                }
            });
            binding1.btnCaotoithapFilter.setOnClickListener(v1 -> {
                isCaoToiThapSelected = !isCaoToiThapSelected;
                toggleButton(binding1.btnCaotoithapFilter, isCaoToiThapSelected);
                if (isCaoToiThapSelected) {
                    isThapToiCaoSelected = false;
                    isDuo1trieuSelected = false;
                    isDuo2trieuSelected = false;
                    toggleButton(binding1.btnThaptoicaoFilter, false);
                    toggleButton(binding1.btnDuoi1trieuFilter, false);
                    toggleButton(binding1.btnDuoi2trieuFilter, false);
                    textButton = String.valueOf(binding1.btnCaotoithapFilter.getText());
                    getCaotoiThap();
                }
            });
            binding1.btnDuoi1trieuFilter.setOnClickListener(v1 -> {
                isDuo1trieuSelected = !isDuo1trieuSelected;
                toggleButton(binding1.btnDuoi1trieuFilter, isDuo1trieuSelected);
                if (isDuo1trieuSelected) {
                    isThapToiCaoSelected = false;
                    isCaoToiThapSelected = false;
                    isDuo2trieuSelected = false;
                    toggleButton(binding1.btnThaptoicaoFilter, false);
                    toggleButton(binding1.btnCaotoithapFilter, false);
                    toggleButton(binding1.btnDuoi2trieuFilter, false);
                    textButton = String.valueOf(binding1.btnDuoi1trieuFilter.getText());
                    getDuoi1trieu();
                }

            });
            binding1.btnDuoi2trieuFilter.setOnClickListener(v1 -> {
                isDuo2trieuSelected = !isDuo2trieuSelected;
                toggleButton(binding1.btnDuoi2trieuFilter, isDuo2trieuSelected);
                if (isDuo2trieuSelected) {
                    isThapToiCaoSelected = false;
                    isCaoToiThapSelected = false;
                    isDuo1trieuSelected = false;
                    toggleButton(binding1.btnThaptoicaoFilter, false);
                    toggleButton(binding1.btnCaotoithapFilter, false);
                    toggleButton(binding1.btnDuoi1trieuFilter, false);
                    textButton = String.valueOf(binding1.btnDuoi2trieuFilter.getText());
                    getDuoi2trieu();
                }
            });
            binding1.btnThietlaplaiFilter.setOnClickListener(v1 -> {
                listFilter.clear();
                toggleButton(binding1.btnThaptoicaoFilter, false);
                toggleButton(binding1.btnCaotoithapFilter, false);
                toggleButton(binding1.btnDuoi1trieuFilter, false);
                toggleButton(binding1.btnDuoi2trieuFilter, false);
                textButton = "";
            });
            binding1.btnApdungFilter.setOnClickListener(v1 -> {
                if(listFilter.size() != 0) {
                    ResultSearchFragment resultSearchFragment = ResultSearchFragment.newInstance(listFilter,textButton);
                    openScreenHome(resultSearchFragment,true);
                    bottomSheetDialog.dismiss();
                }else {
                    Toast.makeText(getActivity(), "chưa chọn điều kiện áp dụng", Toast.LENGTH_LONG).show();
                }
            });
            bottomSheetDialog.show();
        });
    }
    private void toggleButton(Button button, boolean isSelected) {
        if (isSelected) {
            button.setBackgroundResource(R.drawable.bg_corner20_silver);
            button.setTextColor(Color.WHITE);
        } else {
            button.setBackgroundResource(R.drawable.bg_corner20);
            button.setTextColor(Color.BLACK);
        }
    }
    private void getDuoi2trieu(){
        float maxPrice = 2000000;
        ArrayList<Product> listDuoi2Trieu = new ArrayList<>();
       for(Product product: listProduct){
           if(Float.parseFloat(product.getPrice().toString())<maxPrice){
               listDuoi2Trieu.add(product);
           }
       }
       listFilter.clear();
       listFilter.addAll(listDuoi2Trieu);
    }
    private void getDuoi1trieu(){
        float maxPrice = 1000000;
        ArrayList<Product> listDuoi1Trieu = new ArrayList<>();
        for(Product product: listProduct){
            if(Float.parseFloat(product.getPrice().toString())<maxPrice){
                listDuoi1Trieu.add(product);
            }
        }
        listFilter.clear();
        listFilter.addAll(listDuoi1Trieu);
    }
    private void getThaptoiCao(){
        ArrayList<Product> sortList = new ArrayList<>(listProduct);
        Collections.sort(sortList, new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                Float price1 = Float.valueOf(o1.getPrice().toString());
                Float price2 = Float.valueOf(o2.getPrice().toString());
                return price1.compareTo(price2);
            }
        });
        listFilter.clear();
        listFilter.addAll(sortList);
    }
    private void getCaotoiThap(){
        ArrayList<Product> sortList = new ArrayList<>(listProduct);
        Collections.sort(sortList, new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                Float price1 = Float.valueOf(o1.getPrice().toString());
                Float price2 = Float.valueOf(o2.getPrice().toString());
                return price2.compareTo(price1);
            }
        });
        listFilter.clear();
        listFilter.addAll(sortList);
    }
    private void openSearch(int gravity){
        binding.searchHome.setOnClickListener(v -> {
            Dialog dialogSearch = new Dialog(getActivity());
            dialogSearch.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogSearch.setContentView(R.layout.dialog_search);
            Window window = dialogSearch.getWindow();
            if(window == null)
                return;
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(window.getAttributes());

            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.gravity = Gravity.CENTER; // Căn giữa dialog

            window.setAttributes(layoutParams);
            window.setBackgroundDrawable(new ColorDrawable(Color.WHITE));

            ImageButton imageButton = dialogSearch.findViewById(R.id.back_home);
            imageButton.setOnClickListener(v1 -> {
                dialogSearch.dismiss();
            });
            RecyclerView recyclerView = dialogSearch.findViewById(R.id.recycle_search_home);
            LinearLayout layout = dialogSearch.findViewById(R.id.view_not_found);
            productAdapter = new ProductHomeAdapter(getActivity(), listProduct, HomeFragment.this);
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
            recyclerView.setAdapter(productAdapter);
            dialogSearch.show();
            EditText editText = dialogSearch.findViewById(R.id.edit_search_home);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                  String str = s.toString().toLowerCase();
                  String queryWithoutAccents = SearchUltils.removeAccents(str); // Loại bỏ dấu từ chuỗi tìm kiếm

                    ArrayList<Product> list = new ArrayList<>();
                  for (Product product : listProduct){
                      String productName = product.getName().toLowerCase();
                      String productNameWithoutAccents = SearchUltils.removeAccents(productName);
                      if (productNameWithoutAccents.contains(queryWithoutAccents)) {
                          list.add(product);
                      }
                      if (list.isEmpty()) {
                          recyclerView.setVisibility(View.GONE);
                          layout.setVisibility(View.VISIBLE);
                      } else {
                          recyclerView.setVisibility(View.VISIBLE);
                          layout.setVisibility(View.GONE);
                          searchAdapter = new SearchAdapter(getActivity(), list);
                          recyclerView.setAdapter(searchAdapter);
                          recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
                      }

                  }

                }

                @Override
                public void afterTextChanged(Editable s) {


                }
            });
        });

    }
    @NonNull
    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }
  
      private void getAvatar() {
        String avatarUrl = "http://10.64.5.110:3000/api/user/avatar/image/%s"; // thay IPv4 của máy tính chạy server vào đây để test
        User user = UserPrefManager.getInstance(getActivity()).getUser();
        String userId = user.get_id();
        String apiUrl = String.format(avatarUrl, userId);
        Glide.with(getActivity())
                .load(apiUrl)
                .placeholder(R.drawable.avatar_home)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(binding.avatarHome);
    }

    private void startAutoViewPager() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                int nextIndex = binding.viewpagerHome.getCurrentItem() + 1;
                if (nextIndex >= listBanner.length) {
                    nextIndex = 0;
                }
                binding.viewpagerHome.setCurrentItem(nextIndex, true);
                handler.postDelayed(this, PERIOD_MS);
            }
        };
        handler.postDelayed(runnable, DELAY_MS);
    }

    private void stopAutoViewPager() {
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        startAutoViewPager();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopAutoViewPager();
    }

    @Override
    public void onClick(Product product) {
          DetailProductFragment detailProductFragment = DetailProductFragment.newInstance(product);
          openScreenHome(detailProductFragment, true);
    }

    @Override
    public void onClickFavourite(Product product) {
        User user = UserPrefManager.getInstance(getActivity()).getUser();
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
}