package com.example.eu_fstyle_mobile.src.view.user.payment;

import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.databinding.FragmentCartBinding;
import com.example.eu_fstyle_mobile.src.adapter.CartAdapter;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;
import com.example.eu_fstyle_mobile.src.model.Cart;
import com.example.eu_fstyle_mobile.src.model.ProductCart;
import com.example.eu_fstyle_mobile.src.model.User;
import com.example.eu_fstyle_mobile.src.view.user.home.HomeFragment;
import com.example.eu_fstyle_mobile.ultilties.UserPrefManager;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class CartFragment extends BaseFragment<FragmentCartBinding> {
    private CartAdapter cartAdapter;
    private CartViewModel cartViewModel;

    boolean isUndoClicked = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = getFragmentBinding(inflater, container);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        User user = UserPrefManager.getInstance(getActivity()).getUser();
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        cartViewModel.getCart(user.get_id());
        observeViewModel();
        initListener();
    }

    private void observeViewModel() {
        cartViewModel.getCartData().observe(getViewLifecycleOwner(), new Observer<Cart>() {
            @Override
            public void onChanged(Cart cart) {
                if (cart != null) {
                    List<ProductCart> productCartList = cart.getListProduct();
                    cartAdapter = new CartAdapter(productCartList);
                    binding.rcvCart.setAdapter(cartAdapter);
                    int listSize = productCartList.size();
                    binding.tvQuantum.setText(String.valueOf(listSize));
                    binding.tvDetailQuantum.setText(String.valueOf(listSize));
                    binding.tvTotal.setText(cart.getTotalCart().toString() + "VNĐ");
                    binding.tvTotalDetail.setText(cart.getTotalCart().toString() + "VNĐ");
                    if (productCartList.isEmpty()) {
                        binding.rcvCart.setVisibility(View.GONE);
                        binding.llQuantum.setVisibility(View.GONE);
                        binding.viewEmpty.setVisibility(View.VISIBLE);
                    } else {
                        binding.rcvCart.setVisibility(View.VISIBLE);
                        binding.llQuantum.setVisibility(View.VISIBLE);
                        binding.viewEmpty.setVisibility(View.GONE);
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
                            cartAdapter.removeItem(position);
                            if (productCartList.isEmpty()) {
                                binding.rcvCart.setVisibility(View.GONE);
                                binding.viewEmpty.setVisibility(View.VISIBLE);
                            } else {
                                binding.rcvCart.setVisibility(View.VISIBLE);
                                binding.viewEmpty.setVisibility(View.GONE);
                            }
                            Snackbar snackbar = Snackbar.make(binding.rcvCart, "Sản phẩm sẽ bị xóa khỏi giỏ hàng", Snackbar.LENGTH_LONG).setAction("Hoàn tác", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    binding.rcvCart.setVisibility(View.VISIBLE);
                                    binding.viewEmpty.setVisibility(View.GONE);
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
                                        Toast.makeText(getActivity(), "Xóa sản phẩm khỏi giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                                        //deleteData();
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
        binding.btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScreen(new PaymentFragment(), true);
            }
        });
        binding.btnEmpty.setOnClickListener(v -> {
            openScreenHome(new HomeFragment(), true);
        });
    }

    @Override
    protected FragmentCartBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentCartBinding.inflate(inflater, container, false);
    }

}