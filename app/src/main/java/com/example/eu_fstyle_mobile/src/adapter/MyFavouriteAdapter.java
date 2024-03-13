package com.example.eu_fstyle_mobile.src.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eu_fstyle_mobile.databinding.ItemFavouriteBinding;
import com.example.eu_fstyle_mobile.databinding.ItemMyAddressBinding;
import com.example.eu_fstyle_mobile.src.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyFavouriteAdapter extends RecyclerView.Adapter<MyFavouriteAdapter.ViewHolder> {

    private List<Product>  productList;

    public MyFavouriteAdapter(List<Product> productList) {
        this.productList = productList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        String[] imageArray = product.getImage64();
        String image = imageArray.length>0 ? imageArray[0]: "";
        Picasso.get().load(image).into(holder.binding.itemImageProductHome);
        holder.binding.itemNameProductHome.setText(product.getName());
        holder.binding.itemPriceProductHome.setText(product.getPrice() + "đ");
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
