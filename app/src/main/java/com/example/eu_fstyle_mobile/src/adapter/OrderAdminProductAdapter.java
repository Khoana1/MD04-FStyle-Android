package com.example.eu_fstyle_mobile.src.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.databinding.ItemOrderAdminProductBinding;
import com.example.eu_fstyle_mobile.src.model.ProductOrder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderAdminProductAdapter extends RecyclerView.Adapter<OrderAdminProductAdapter.ViewHolder>{
    private List<ProductOrder> ordersList;

    public OrderAdminProductAdapter(List<ProductOrder> ordersList) {
        this.ordersList = ordersList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderAdminProductAdapter.ViewHolder(ItemOrderAdminProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductOrder orders = ordersList.get(position);
        if(orders.getImageDefault() != null){
            if(orders.getImageDefault().startsWith("http")){
                Picasso.get().load(orders.getImageDefault())
                        .error(R.drawable.error_shoe)
                        .into(holder.binding.imgPayment);
            }else {
                byte[] decodedString = Base64.decode(orders.getImageDefault(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                holder.binding.imgPayment.setImageBitmap(decodedByte);
            }
        }else {
            holder.binding.imgPayment.setImageResource(R.drawable.error_shoe);
        }
        holder.binding.namePayment.setMaxLines(1);
        holder.binding.namePayment.setEllipsize(TextUtils.TruncateAt.END);
        holder.binding.namePayment.setText(orders.getName());
        holder.binding.sizePayment.setText(orders.getSize());
        holder.binding.quantumPayment.setText(orders.getQuantity().toString());
        holder.binding.pricePayment.setText(orders.getPrice().toString() + " VNĐ");
    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemOrderAdminProductBinding binding;

        public ViewHolder(@NonNull ItemOrderAdminProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
