package com.example.eu_fstyle_mobile.src.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.databinding.ItemCartProductBinding;
import com.example.eu_fstyle_mobile.src.model.ProductCart;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private List<ProductCart> productCartList;

    public CartAdapter(List<ProductCart> productCartList) {
        this.productCartList = productCartList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartAdapter.ViewHolder(ItemCartProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductCart productCart = productCartList.get(position);
        if(productCart.getImageDefault() != null){
            if(productCart.getImageDefault().startsWith("http")){
                Picasso.get().load(productCart.getImageDefault())
                        .error(R.drawable.error_shoe)
                        .into(holder.binding.imgShoe);
            }else {
                byte[] decodedString = Base64.decode(productCart.getImageDefault(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                holder.binding.imgShoe.setImageBitmap(decodedByte);
            }
        }else {
            holder.binding.imgShoe.setImageResource(R.drawable.error_shoe);
        }
        String defaultName = productCart.getName();
        int maxLength = 13;
        if (defaultName.length() > maxLength) {
            String truncatedName = defaultName.substring(0, maxLength) + "...";
            holder.binding.tvNameShoe.setText(truncatedName);
        } else {
            holder.binding.tvNameShoe.setText(productCart.getName());
        }


        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.binding.tvPriceShoe.setText(decimalFormat.format(productCart.getPrice()) + " VNĐ");
        holder.binding.tvSoluongShoe.setText(productCart.getSoLuong().toString());
        holder.binding.imgReduce.setOnClickListener(v -> {
            int currentQuantity = productCart.getSoLuong().intValue();
            if (currentQuantity > 1) {
                currentQuantity--;
                productCart.setSoLuong(currentQuantity);
                holder.binding.tvSoluongShoe.setText(String.valueOf(currentQuantity));
                double pricePerItem = productCart.getPrice().doubleValue();
                double totalPrice = currentQuantity * pricePerItem;
                holder.binding.tvPriceShoe.setText(decimalFormat.format(totalPrice) + " VNĐ");
            }
        });

        holder.binding.imgIncrease.setOnClickListener(v -> {
            int currentQuantity = productCart.getSoLuong().intValue();
            currentQuantity++;
            productCart.setSoLuong(currentQuantity);
            holder.binding.tvSoluongShoe.setText(String.valueOf(currentQuantity));
            double pricePerItem = productCart.getPrice().doubleValue();
            double totalPrice = currentQuantity * pricePerItem;
            holder.binding.tvPriceShoe.setText(decimalFormat.format(totalPrice) + " VNĐ");
        });


    }

    @Override
    public int getItemCount() {
        return productCartList.size();
    }

    public void removeItem(int position) {
        productCartList.remove(position);
        notifyItemRemoved(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemCartProductBinding binding;

        public ViewHolder(@NonNull ItemCartProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
