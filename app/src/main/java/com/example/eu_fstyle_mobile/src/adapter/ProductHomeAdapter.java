package com.example.eu_fstyle_mobile.src.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.DecimalFormat;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.src.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductHomeAdapter extends RecyclerView.Adapter<ProductHomeAdapter.Viewholder> {
    private Context context;
    private ArrayList<Product> arrayList;
    private onClickItem onClickItem;
    public ProductHomeAdapter(Context context, ArrayList<Product> arrayList,onClickItem onClickItem) {
        this.context = context;
        this.arrayList = arrayList;
        this.onClickItem = onClickItem;
    }
    public void setOnClickItem( onClickItem onClickItem){
        this.onClickItem = onClickItem;
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
        if(image != null){
            if(image.startsWith("http")){
                Picasso.get().load(image)
                        .error(R.drawable.icon_erro)
                        .into(holder.imageView);
            }else {
                byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                holder.imageView.setImageBitmap(decodedByte);
            }
        }else {
            holder.imageView.setImageResource(R.drawable.icon_erro);
        }
        DecimalFormat dcf = new DecimalFormat("###,###,###");
        holder.txtPrice.setText(dcf.format(product.getPrice())+" VNĐ");
        holder.txtName.setText(product.getName());
        if (Integer.parseInt(product.getQuantity()) == 0) {
            holder.imageView_sold_out.setVisibility(View.VISIBLE);
            holder.itemView.setAlpha(0.5f);
        } else {
            holder.itemView.setAlpha(1f);
            holder.imageView_sold_out.setVisibility(View.INVISIBLE);
        }
        holder.ll_favourite.setOnClickListener(v -> {
            onClickItem.onClickFavourite(product);
        });
        holder.itemView.setOnClickListener(v -> {
            if(Integer.parseInt(product.getQuantity())!= 0){
                if(onClickItem != null){
                    onClickItem.onClick(product);
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public interface onClickItem{
        void onClick(Product product);

        void onClickFavourite(Product product);
    }
    public class Viewholder extends RecyclerView.ViewHolder{
        ImageView imageView,imageView_sold_out;
        TextView txtName,txtPrice;
        LinearLayout btn_add, ll_favourite;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imageView_sold_out = itemView.findViewById(R.id.item_image_sold_out_product_home);
            imageView = itemView.findViewById(R.id.item_image_product_home);
            txtName = itemView.findViewById(R.id.item_name_product_home);
            txtPrice = itemView.findViewById(R.id.item_price_product_home);
            btn_add = itemView.findViewById(R.id.item_btnCong_product_home);
            ll_favourite = itemView.findViewById(R.id.ll_favourite);
        }
    }
}