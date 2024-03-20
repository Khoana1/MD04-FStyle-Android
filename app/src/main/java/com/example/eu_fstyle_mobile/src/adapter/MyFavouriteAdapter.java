package com.example.eu_fstyle_mobile.src.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eu_fstyle_mobile.databinding.ItemFavouriteBinding;
import com.example.eu_fstyle_mobile.databinding.ItemMyAddressBinding;
import com.example.eu_fstyle_mobile.src.model.Product;
import com.example.eu_fstyle_mobile.src.model.ProductFavourite;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class MyFavouriteAdapter extends RecyclerView.Adapter<MyFavouriteAdapter.ViewHolder> {

    private List<ProductFavourite> productList;

    public MyFavouriteAdapter(List<ProductFavourite> productList) {
        this.productList = productList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyFavouriteAdapter .ViewHolder(ItemFavouriteBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductFavourite product = productList.get(position);
        String image = product.getDefaultImage();
        holder.binding.itemNameProductHome.setText(product.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.binding.itemPriceProductHome.setText(decimalFormat.format(product.getPrice())+" VNĐ");
        Picasso.get().load(image).into(holder.binding.itemImageProductHome);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemFavouriteBinding binding;

        public ViewHolder(@NonNull ItemFavouriteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
