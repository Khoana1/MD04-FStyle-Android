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
import com.example.eu_fstyle_mobile.databinding.ItemPaymentBinding;
import com.example.eu_fstyle_mobile.src.model.ProductCart;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class PaymentProductAdapter extends RecyclerView.Adapter<PaymentProductAdapter.ViewHolder> {
    private List<ProductCart> productCartList;

    private OnItemClickListener onItemClickListener;

    public PaymentProductAdapter(List<ProductCart> productCartList, OnItemClickListener onItemClickListener) {
        this.productCartList = productCartList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PaymentProductAdapter.ViewHolder(ItemPaymentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductCart productCart = productCartList.get(position);
        if(productCart.getImageDefault() != null){
            if(productCart.getImageDefault().startsWith("http")){
                Picasso.get().load(productCart.getImageDefault())
                        .error(R.drawable.error_shoe)
                        .into(holder.binding.imgPayment);
            }else {
                byte[] decodedString = Base64.decode(productCart.getImageDefault(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                holder.binding.imgPayment.setImageBitmap(decodedByte);
            }
        }else {
            holder.binding.imgPayment.setImageResource(R.drawable.error_shoe);
        }
        String productName = productCart.getName();
        if (productName.length() > 13) {
            productName = productName.substring(0, 13) + "...";
        }
        holder.binding.namePayment.setText(productName);
        holder.binding.sizePayment.setText(productCart.getSize());
        holder.binding.quantumPayment.setText(productCart.getSoLuong().toString());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.binding.pricePayment.setText(decimalFormat.format(productCart.getPrice()) + " VNĐ");
        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(productCart) );
    }

    @Override
    public int getItemCount() {
        return productCartList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemPaymentBinding binding;

        public ViewHolder(@NonNull ItemPaymentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(ProductCart productCart);
    }
}
