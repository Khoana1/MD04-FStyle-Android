package com.example.eu_fstyle_mobile.src.view.user.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.databinding.FragmentThanhtoanBinding;
import com.example.eu_fstyle_mobile.src.model.Product;

public class ThanhtoanFragment extends Fragment {
   private FragmentThanhtoanBinding binding ;
   private Product product;
   private String size;
   private int quality;
    public static ThanhtoanFragment getInstance(Product product,String size, int quality ){
        ThanhtoanFragment thanhtoanFragment = new ThanhtoanFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("product_thanhtoan",product);
        bundle.putString("size_thanhtoan", size);
        bundle.putInt("quality_thanhtoan", quality);
        thanhtoanFragment.setArguments(bundle);
        return thanhtoanFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentThanhtoanBinding.inflate(inflater,container, false);
        getData();
        return binding.getRoot();
    }

    private void getData() {
        product = (Product) getArguments().getSerializable("product_thanhtoan");
        size = getArguments().getString("size_thanhtoan");
        quality = getArguments().getInt("quality_thanhtoan");
        Log.d("Huy", "getData: "+size+ quality);
    }

}