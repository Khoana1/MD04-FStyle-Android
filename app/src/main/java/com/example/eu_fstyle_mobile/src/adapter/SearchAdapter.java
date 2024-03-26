package com.example.eu_fstyle_mobile.src.adapter;

import android.content.Context;
import android.icu.text.DecimalFormat;
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

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.Viewholder> {
    private Context context;
    private ArrayList<Product> arrayList;
    private onclickItem onClick;

    public SearchAdapter(Context context, ArrayList<Product> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }
    public void setOnclickItem(onclickItem onClick){
        this.onClick = onClick;
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
        String[] imagearray = product.getImage64();
        String image = imagearray.length>0 ? imagearray[0]: "";
        Picasso.get().load(image)
                .placeholder(R.drawable.icon_home)
                .error(R.drawable.icon_erro)
                .into(holder.imageView);
        DecimalFormat dcf = new DecimalFormat("###,###,###");
        holder.txtPrice.setText(dcf.format(product.getPrice())+" VNĐ");
        holder.txtName.setText(product.getName());
        holder.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.btn_favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onClickFavourite(product);
            }
        });
        holder.itemView.setOnClickListener(v -> {
            if(onClick != null){
                onClick.onclick(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public interface onclickItem{
        void onclick(Product product);

        void onClickFavourite(Product product);
    }
    public class Viewholder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView txtName,txtPrice;
        LinearLayout btn_add, btn_favourite;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image_product_home);
            txtName = itemView.findViewById(R.id.item_name_product_home);
            txtPrice = itemView.findViewById(R.id.item_price_product_home);
            btn_add = itemView.findViewById(R.id.item_btnCong_product_home);
            btn_favourite = itemView.findViewById(R.id.ll_favourite);
        }
    }
}