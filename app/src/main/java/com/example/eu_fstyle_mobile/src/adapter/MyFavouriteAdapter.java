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

    public MyFavouriteAdapter(List<ProductFavourite> productList) {
        this.productList = productList;
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
        if (image != null) {
            if (image.startsWith("http")) {
                Picasso.get().load(image)
                        .error(R.drawable.error_shoe)
                        .into(holder.binding.itemImageProductHome);
            } else {
                boolean isBase64 = isBase64(image);
                if (isBase64) {
                    byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    holder.binding.itemImageProductHome.setImageBitmap(decodedByte);
                } else {
                    holder.binding.itemImageProductHome.setImageResource(R.drawable.error_shoe);
                }
            }
        }
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

    public static boolean isBase64(String input) {
        try {
            byte[] decodedBytes = Base64.decode(input, Base64.DEFAULT);
            String decodedString = new String(decodedBytes);
            byte[] reEncodedBytes = Base64.encode(decodedString.getBytes(), Base64.DEFAULT);
            String reEncodedString = new String(reEncodedBytes);
            return reEncodedString.equals(input);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
