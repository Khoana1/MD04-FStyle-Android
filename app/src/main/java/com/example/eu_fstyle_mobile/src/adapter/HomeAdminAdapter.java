package com.example.eu_fstyle_mobile.src.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.databinding.ItemProductAdminBinding;
import com.example.eu_fstyle_mobile.src.model.Product;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class HomeAdminAdapter extends RecyclerView.Adapter<HomeAdminAdapter.ViewHolder> {

    private List<Product> productList;

    public HomeAdminAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemProductAdminBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdminAdapter.ViewHolder holder, int position) {
        Product product = productList.get(position);
        String[] imageArray = product.getImage64();
        String image = imageArray.length>0 ? imageArray[0]: "";
        if(image != null){
            if(image.startsWith("http")){
                Picasso.get().load(image)
                        .error(R.drawable.icon_erro)
                        .into(holder.binding.itemImageProductHome);
            }else {
                byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                holder.binding.itemImageProductHome.setImageBitmap(decodedByte);
            }
        }else {
            holder.binding.itemImageProductHome.setImageResource(R.drawable.icon_erro);
        }
        holder.binding.itemNameProductHome.setText(product.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.binding.itemPriceProductHome.setText(decimalFormat.format(product.getPrice()) + "VNĐ");
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemProductAdminBinding binding;
        public ViewHolder(@NonNull  ItemProductAdminBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
