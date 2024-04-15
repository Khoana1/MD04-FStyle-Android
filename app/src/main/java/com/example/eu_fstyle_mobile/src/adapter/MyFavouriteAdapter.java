package com.example.eu_fstyle_mobile.src.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.databinding.ItemFavouriteBinding;
import com.example.eu_fstyle_mobile.databinding.ItemMyAddressBinding;
import com.example.eu_fstyle_mobile.src.model.Product;
import com.example.eu_fstyle_mobile.src.model.ProductFavourite;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class MyFavouriteAdapter extends RecyclerView.Adapter<MyFavouriteAdapter.ViewHolder> {

    private List<ProductFavourite> productList;

    private onItemFavouriteClickListener onItemFavouriteClickListener;

    public MyFavouriteAdapter(List<ProductFavourite> productList, onItemFavouriteClickListener onItemFavouriteClickListener) {
        this.productList = productList;
        this.onItemFavouriteClickListener = onItemFavouriteClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyFavouriteAdapter.ViewHolder(ItemFavouriteBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductFavourite product = productList.get(position);
        String image = product.getDefaultImage();
        holder.binding.itemNameProductHome.setText(product.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.binding.itemPriceProductHome.setText(decimalFormat.format(product.getPrice()) + " VNĐ");
        if(product.getDefaultImage() != null){
            if(product.getDefaultImage().startsWith("http")){
                Picasso.get().load(product.getDefaultImage())
                        .error(R.drawable.error_shoe)
                        .into(holder.binding.itemImageProductHome);
            }else {
                byte[] decodedString = Base64.decode(product.getDefaultImage(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                holder.binding.itemImageProductHome.setImageBitmap(decodedByte);
            }
        }else {
            holder.binding.itemImageProductHome.setImageResource(R.drawable.error_shoe);
        }
        holder.itemView.setOnClickListener(v -> onItemFavouriteClickListener.onItemFavouriteClick(product.getIdProduct()));
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

    public interface onItemFavouriteClickListener {
        void onItemFavouriteClick(String productId);
    }

}