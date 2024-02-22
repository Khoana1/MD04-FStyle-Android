package com.example.eu_fstyle_mobile.src.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.databinding.ItemProductHomeBinding;
import com.example.eu_fstyle_mobile.src.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductHomeAdapter extends RecyclerView.Adapter<ProductHomeAdapter.Viewholder> {
    Context context;
    ArrayList<Product> arrayList;

    public ProductHomeAdapter(Context context, ArrayList<Product> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_product_home,parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Product product = arrayList.get(position);
        Picasso.get().load(product.getImage()).into(holder.imageView);
        holder.txtPrice.setText(product.getPrice()+" VNĐ");
        holder.txtName.setText(product.getName());
        holder.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView txtName,txtPrice;
        LinearLayout btn_add;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image_product_home);
            txtName = itemView.findViewById(R.id.item_name_product_home);
            txtPrice = itemView.findViewById(R.id.item_price_product_home);
            btn_add = itemView.findViewById(R.id.item_btnCong_product_home);
        }
    }
}