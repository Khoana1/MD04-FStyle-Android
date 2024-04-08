
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
import com.example.eu_fstyle_mobile.databinding.ItemCartProductBinding;
import com.example.eu_fstyle_mobile.src.model.Address;
import com.example.eu_fstyle_mobile.src.model.ProductCart;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private List<ProductCart> productCartList;

    private OnCartClickListener onCartClickListener;

    public CartAdapter(List<ProductCart> productCartList, OnCartClickListener onCartClickListener) {
        this.productCartList = productCartList;
        this.onCartClickListener = onCartClickListener;
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
                        .into(holder.binding.imageCart);
            }else {
                byte[] decodedString = Base64.decode(productCart.getImageDefault(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                holder.binding.imageCart.setImageBitmap(decodedByte);
            }
        }else {
            holder.binding.imageCart.setImageResource(R.drawable.error_shoe);
        }
        holder.binding.nameCart.setMaxLines(1);
        holder.binding.nameCart.setEllipsize(TextUtils.TruncateAt.END);
        holder.binding.nameCart.setText(productCart.getName());
        holder.binding.sizeCart.setText("size: "+productCart.getSize());

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.binding.priceCart.setText(decimalFormat.format(productCart.getPrice()) + " VNĐ");
        holder.binding.btnGiatriCart.setText(productCart.getSoLuong().toString());
        holder.binding.btntruCart.setOnClickListener(v -> {
            int currentQuantity = productCart.getSoLuong().intValue();
            if (currentQuantity > 1) {
                currentQuantity--;
                productCart.setSoLuong(currentQuantity);
                holder.binding.btnGiatriCart.setText(String.valueOf(currentQuantity));
                double pricePerItem = productCart.getPrice().doubleValue();
                double totalPrice = currentQuantity * pricePerItem;
                holder.binding.priceCart.setText(decimalFormat.format(totalPrice) + " VNĐ");
            }
        });

        holder.binding.btncongCart.setOnClickListener(v -> {
            int currentQuantity = productCart.getSoLuong().intValue();
            currentQuantity++;
            productCart.setSoLuong(currentQuantity);
            holder.binding.btnGiatriCart.setText(String.valueOf(currentQuantity));
            double pricePerItem = productCart.getPrice().doubleValue();
            double totalPrice = currentQuantity * pricePerItem;
            holder.binding.priceCart.setText(decimalFormat.format(totalPrice) + " VNĐ");
        });

        holder.binding.btnDelCart.setOnClickListener(v -> {
            onCartClickListener.onDeleteCartClick(position);
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

    public interface OnCartClickListener {
        void onDeleteCartClick(int position);
    }

}
