package com.example.eu_fstyle_mobile.src.view.user.payment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.databinding.FragmentCartBinding;
import com.example.eu_fstyle_mobile.src.adapter.CartAdapter;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;
import com.example.eu_fstyle_mobile.src.model.Cart;
import com.example.eu_fstyle_mobile.src.model.ProductCart;
import com.example.eu_fstyle_mobile.src.model.User;
import com.example.eu_fstyle_mobile.src.view.user.home.HomeFragment;
import com.example.eu_fstyle_mobile.ultilties.UserPrefManager;

import java.util.List;

public class CartFragment extends BaseFragment<FragmentCartBinding> {
    private CartAdapter cartAdapter;

    private CartViewModel cartViewModel;


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
                    binding.tvTotal.setText(cart.getTotalCart().toString()+ "VNĐ");
                    binding.tvTotalDetail.setText(cart.getTotalCart().toString()+ "VNĐ");
                    if (productCartList.isEmpty()) {
                        binding.rcvCart.setVisibility(View.GONE);
                        binding.llQuantum.setVisibility(View.GONE);
                        binding.viewEmpty.setVisibility(View.VISIBLE);
                    } else {
                        binding.rcvCart.setVisibility(View.VISIBLE);
                        binding.llQuantum.setVisibility(View.VISIBLE);
                        binding.viewEmpty.setVisibility(View.GONE);
                    }
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