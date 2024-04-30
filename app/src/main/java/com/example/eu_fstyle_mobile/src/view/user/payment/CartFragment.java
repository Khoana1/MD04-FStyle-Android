package com.example.eu_fstyle_mobile.src.view.user.payment;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.databinding.FragmentCartBinding;
import com.example.eu_fstyle_mobile.src.adapter.CartAdapter;
import com.example.eu_fstyle_mobile.src.adapter.ProductHomeAdapter;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;
import com.example.eu_fstyle_mobile.src.model.Cart;
import com.example.eu_fstyle_mobile.src.model.ListCart;
import com.example.eu_fstyle_mobile.src.model.ListProduct;
import com.example.eu_fstyle_mobile.src.model.Product;
import com.example.eu_fstyle_mobile.src.model.ProductCart;
import com.example.eu_fstyle_mobile.src.model.User;
import com.example.eu_fstyle_mobile.src.request.RequestCreateFavourite;
import com.example.eu_fstyle_mobile.src.retrofit.ApiClient;
import com.example.eu_fstyle_mobile.src.retrofit.ApiService;
import com.example.eu_fstyle_mobile.src.view.user.home.DetailProductFragment;
import com.example.eu_fstyle_mobile.src.view.user.home.HomeFragment;
import com.example.eu_fstyle_mobile.ultilties.UserPrefManager;
import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends BaseFragment<FragmentCartBinding> implements CartAdapter.OnCartClickListener, ProductHomeAdapter.onClickItem {
    private CartAdapter cartAdapter;

    private ProductHomeAdapter mayBeLikeAdapter;
    private CartViewModel cartViewModel;

    boolean isUndoClicked = false;

    private List<ProductCart> productCartList = new ArrayList<>();

    Dialog cartDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = getFragmentBinding(inflater, container);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showHintCartDialog();
        showCartLoading();
        User user = UserPrefManager.getInstance(getActivity()).getUser();
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        cartViewModel.getCart(user.get_id());
        observeViewModel();
        getDataMayBeLike();
        initListener();
    }

    private void showHintCartDialog() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("app_prefs", MODE_PRIVATE);
        boolean isFirstRun = sharedPreferences.getBoolean("isFirstHint", true);

        if (isFirstRun) {
            showHintDialog();
            sharedPreferences.edit().putBoolean("isFirstHint", false).apply();
        }
    }


    private void getDataMayBeLike() {
        binding.cstTotal.setVisibility(View.VISIBLE);
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<ListProduct> call = apiService.getAllProducts();
        call.enqueue(new Callback<ListProduct>() {
            @Override
            public void onResponse(Call<ListProduct> call, Response<ListProduct> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ArrayList<Product> listProduct = response.body().getArrayList();
                    Collections.sort(listProduct, new Comparator<Product>() {
                        @Override
                        public int compare(Product p1, Product p2) {
                            int quantity1 = 0;
                            int quantity2 = 0;
                            try {
                                quantity1 = Integer.parseInt(p1.getQuantity());
                                quantity2 = Integer.parseInt(p2.getQuantity());
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                            if (quantity1 > 0 && quantity2 <= 0) {
                                return -1;
                            }
                            if (quantity1 <= 0 && quantity2 > 0) {
                                return 1;
                            }
                            return 0;
                        }
                    });
                    mayBeLikeAdapter = new ProductHomeAdapter(getActivity(),listProduct,CartFragment.this);
                    binding.rcvMaybeLike.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                    binding.rcvMaybeLike.setAdapter(mayBeLikeAdapter);
                    mayBeLikeAdapter.setOnClickItem(new ProductHomeAdapter.onClickItem() {
                        @Override
                        public void onClick(Product product) {
                            DetailProductFragment detailProductFragment = DetailProductFragment.newInstance(product);
                            openScreenHome(detailProductFragment, true);
                        }

                        @Override
                        public void onClickFavourite(Product product) {

                        }
                    });
                    binding.rcvMaybeLike.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);

                            GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                            int lastVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition();
                            int totalItemCount = layoutManager.getItemCount();

                            if (lastVisibleItemPosition == totalItemCount - 1) {
                                binding.cstTotal.setVisibility(View.GONE);
                            } else {
                                binding.cstTotal.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ListProduct> call, Throwable t) {
                Toast.makeText(getActivity(), "Server error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void observeViewModel() {
        cartViewModel.getCartData().observe(getViewLifecycleOwner(), new Observer<Cart>() {
            @Override
            public void onChanged(Cart cart) {
                if (cart != null) {
                    hideLoadingDialog();
                    productCartList = cart.getListProduct();
                    cartAdapter = new CartAdapter(productCartList, CartFragment.this);
                    binding.rcvCart.setAdapter(cartAdapter);
                    int listSize = productCartList.size();
                    if (listSize == 0) {
                        binding.llQuantum.setVisibility(View.GONE);
                        binding.rltTotal.setVisibility(View.GONE);
                        binding.rltTotal.setVisibility(View.GONE);
                    } else {
                        binding.llQuantum.setVisibility(View.VISIBLE);
                        binding.rltTotal.setVisibility(View.VISIBLE);
                        binding.rltTotal.setVisibility(View.VISIBLE);
                        binding.tvQuantum.setText(String.valueOf(listSize));
                        binding.tvDetailQuantum.setText(cart.getTotalProduct().toString());
                    }

                    String totalPrice = cart.getTotalCart().toString();
                    setTotalPriceText(totalPrice, binding.tvTotal);
                    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                    binding.tvTotalDetail.setText(decimalFormat.format(cart.getTotalCart()) + " VNĐ");
                    if (productCartList.isEmpty()) {
                        setStatusDisable();
                    } else {
                        setStatusEnable();
                    }

                    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                        @Override
                        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                            return false;
                        }

                        @Override
                        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                            int position = viewHolder.getAdapterPosition();
                            ProductCart removedProduct = productCartList.get(position);
                            String productId = removedProduct.getIdProduct();
                            cartAdapter.removeItem(position);
                            if (productCartList.isEmpty()) {
                                setStatusDisable();
                            } else {
                                setStatusEnable();
                            }
                            Snackbar snackbar = Snackbar.make(binding.rcvCart, "Sản phẩm sẽ bị xóa khỏi giỏ hàng", Snackbar.LENGTH_LONG).setAction("Hoàn tác", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    setStatusEnable();
                                    isUndoClicked = true;
                                    productCartList.add(position, removedProduct);
                                    cartAdapter.notifyItemInserted(position);
                                }
                            });
                            snackbar.setDuration(2000);
                            snackbar.addCallback(new Snackbar.Callback() {
                                @Override
                                public void onDismissed(Snackbar transientBottomBar, int event) {
                                    if (!isUndoClicked && event != DISMISS_EVENT_ACTION) {
                                        User user = UserPrefManager.getInstance(getActivity()).getUser();
                                        ApiService apiService = ApiClient.getClient().create(ApiService.class);
                                        Call<Cart> call = apiService.deleteCart(user.get_id(), productId);
                                        call.enqueue(new Callback<Cart>() {
                                            @Override
                                            public void onResponse(Call<Cart> call, Response<Cart> response) {
                                                if (!response.isSuccessful()) {
                                                    productCartList.add(position, removedProduct);
                                                    cartAdapter.notifyItemInserted(position);
                                                    Toast.makeText(requireContext(), "Lỗi xóa sản phẩm khỏi giỏ hàng hãy thử lại", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(requireContext(), "Xóa sản phẩm khỏi giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<Cart> call, Throwable t) {
                                                productCartList.add(position, removedProduct);
                                                cartAdapter.notifyItemInserted(position);
                                                Toast.makeText(requireContext(), "Server error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                    super.onDismissed(transientBottomBar, event);
                                }
                            });
                            snackbar.show();

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    isUndoClicked = false;
                                }
                            }, 2000);
                        }

                        @Override
                        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                                    .addSwipeLeftBackgroundColor(getContext().getColor(R.color.colorRed))
                                    .addSwipeLeftActionIcon(R.drawable.ic_delete_forever)
                                    .addSwipeLeftLabel("Xóa sản phẩm")
                                    .setSwipeLeftLabelColor(getContext().getColor(R.color.colorWhite))
                                    .create()
                                    .decorate();
                            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                        }
                    };
                    new ItemTouchHelper(simpleCallback).attachToRecyclerView(binding.rcvCart);
                }
                binding.btnPayment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PaymentFragment paymentFragment = PaymentFragment.newInstance(cart);
                        openScreen(paymentFragment, true);
                    }
                });
            }
        });
        cartViewModel.getErrorMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initListener() {
        binding.icBack.setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });
        binding.icHint.setOnClickListener(v -> {
            showHintDialog();
        });
        binding.btnEmpty.setOnClickListener(v -> {
            openScreenHome(new HomeFragment(), true);
        });
        binding.swipeCart.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.swipeCart.setRefreshing(false);
                User user = UserPrefManager.getInstance(getActivity()).getUser();
                cartViewModel.getCart(user.get_id());
                getDataMayBeLike();
                showLoadingDialog();
                binding.cstTotal.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected FragmentCartBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentCartBinding.inflate(inflater, container, false);
    }

    private void showCartAnimation() {
        cartDialog = new Dialog(getActivity(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        cartDialog.setContentView(R.layout.dialog_cart_loading);
        LottieAnimationView animationView = cartDialog.findViewById(R.id.ltAnimationView);
        animationView.setAnimation(R.raw.cart_loading);
        animationView.setSpeed(4f);
        animationView.playAnimation();
        cartDialog.show();
    }

    private void hideCartLoadingAnimation() {
        if (cartDialog != null && cartDialog.isShowing()) {
            cartDialog.dismiss();
        }
    }

    private void showCartLoading() {
        showCartAnimation();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                hideCartLoadingAnimation();
            }
        }, 2000);
    }


    private void setStatusEnable() {
        binding.rcvCart.setVisibility(View.VISIBLE);
        binding.viewEmpty.setVisibility(View.GONE);
        binding.rltTotal.setVisibility(View.VISIBLE);
        binding.llTotal.setVisibility(View.VISIBLE);
        binding.llQuantum.setVisibility(View.VISIBLE);
        binding.btnPayment.setEnabled(true);
        binding.btnPayment.setAlpha(1f);
    }

    private void setStatusDisable() {
        binding.rcvCart.setVisibility(View.GONE);
        binding.viewEmpty.setVisibility(View.VISIBLE);
        binding.rltTotal.setVisibility(View.GONE);
        binding.llTotal.setVisibility(View.GONE);
        binding.llQuantum.setVisibility(View.GONE);
        binding.btnPayment.setEnabled(false);
        binding.btnPayment.setAlpha(0.4f);
    }

    public void setTotalPriceText(String totalPrice, TextView textView) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        int maxLength = 6;
        try {
            Number number = decimalFormat.parse(totalPrice);
            String formattedPrice = decimalFormat.format(number);
            if (formattedPrice.length() > maxLength) {
                String truncatedPrice = formattedPrice.substring(0, maxLength) + "..." + " VNĐ";
                textView.setText(truncatedPrice);
            } else {
                textView.setText(formattedPrice + " VNĐ");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    public void onDeleteCartClick(int position) {
        showDialog("Xóa sản phẩm khỏi giỏ hàng", "Bạn có chắc chắn muốn xóa sản phẩm này khỏi giỏ hàng không?", new Runnable() {
            @Override
            public void run() {
                ProductCart removedProduct = productCartList.get(position);

                productCartList.remove(position);
                cartAdapter.notifyItemRemoved(position);

                if (productCartList.isEmpty()) {
                    setStatusDisable();
                } else {
                    setStatusEnable();
                }

                User user = UserPrefManager.getInstance(getActivity()).getUser();
                ApiService apiService = ApiClient.getClient().create(ApiService.class);
                Call<Cart> call = apiService.deleteCart(user.get_id(), removedProduct.getIdProduct());
                call.enqueue(new Callback<Cart>() {
                    @Override
                    public void onResponse(Call<Cart> call, Response<Cart> response) {
                        if (!response.isSuccessful()) {
                            productCartList.add(position, removedProduct);
                            cartAdapter.notifyItemInserted(position);
                            Toast.makeText(requireContext(), "Lỗi xóa sản phẩm khỏi giỏ hàng hãy thử lại", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(requireContext(), "Xóa sản phẩm khỏi giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Cart> call, Throwable t) {
                        productCartList.add(position, removedProduct);
                        cartAdapter.notifyItemInserted(position);
                        Toast.makeText(requireContext(), "Server error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public void onReduceCartClick(int position) {
        User user = UserPrefManager.getInstance(getActivity()).getUser();
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<ListCart> call = apiService.reduceCart(user.get_id(), productCartList.get(position).getIdProduct());
        call.enqueue(new Callback<ListCart>() {
            @Override
            public void onResponse(Call<ListCart> call, Response<ListCart> response) {
                if (response.isSuccessful()) {
                    ProductCart product = productCartList.get(position);
                    product.setSoLuong(product.getSoLuong().intValue() + 1);
                    cartAdapter.notifyItemChanged(position);

                    Cart cartList = (Cart) response.body().getListCart();
                    String totalPrice = cartList.getTotalCart().toString();
                    setTotalPriceText(totalPrice, binding.tvTotal);
                    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                    binding.tvTotalDetail.setText(decimalFormat.format(cartList.getTotalCart()) + " VNĐ");
                }
            }

            @Override
            public void onFailure(Call<ListCart> call, Throwable t) {
                User user = UserPrefManager.getInstance(getActivity()).getUser();
                cartViewModel.getCart(user.get_id());
            }
        });

    }

    @Override
    public void onIncreaseCartClick(int position) {
        User user = UserPrefManager.getInstance(getActivity()).getUser();
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<ListCart> call = apiService.increaseCart(user.get_id(), productCartList.get(position).getIdProduct());
        call.enqueue(new Callback<ListCart>() {
            @Override
            public void onResponse(Call<ListCart> call, Response<ListCart> response) {
                if (response.isSuccessful()) {
                    ProductCart product = productCartList.get(position);
                    product.setSoLuong(product.getSoLuong().intValue() + 1);
                    cartAdapter.notifyItemChanged(position);

                    Cart cartList = (Cart) response.body().getListCart();
                    String totalPrice = cartList.getTotalCart().toString();
                    setTotalPriceText(totalPrice, binding.tvTotal);
                    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                    binding.tvTotalDetail.setText(decimalFormat.format(cartList.getTotalCart()) + "VNĐ");
                }
            }

            @Override
            public void onFailure(Call<ListCart> call, Throwable t) {
                User user = UserPrefManager.getInstance(getActivity()).getUser();
                cartViewModel.getCart(user.get_id());
            }
        });
    }

    @Override
    public void onItemCartClick(ProductCart productCart) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Thông tin chi tiết sản phẩm");
        builder.setMessage("Mã sản phẩm: " + productCart.getIdProduct() + "\nTên sản phẩm: " + productCart.getName() + "\nGiá sản phẩm: " + productCart.getPrice() + "\nSize: " + productCart.getSize() + "\nSố lượng: " + productCart.getSoLuong());
        builder.setPositiveButton("OK", null);
        builder.show();
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
        RequestCreateFavourite requestCreateFavourite = new RequestCreateFavourite(product.get_id(), product.getName(), product.getQuantity(), product.getPrice().toString(), product.getImage64()[0]);
        Call<Product> call = apiService.createFavorite(user.get_id(), requestCreateFavourite);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    showSuccessDialog("Thêm vào yêu thích thành công");
                } else {
                    showAlertDialog("Thêm vào yêu thích thất bại");
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(getActivity(), "Server error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}