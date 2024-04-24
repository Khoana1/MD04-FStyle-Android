package com.example.eu_fstyle_mobile.src.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.src.model.Categories;
import com.example.eu_fstyle_mobile.src.model.Category;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryHomeAdapter extends RecyclerView.Adapter<CategoryHomeAdapter.Viewholder> {
    Context context;
    ArrayList<Categories> arrayList;
    private OnclickCategory onclickCategory;

    public CategoryHomeAdapter(Context context, ArrayList<Categories> arrayList, OnclickCategory onclickCategory) {
        this.context = context;
        this.arrayList = arrayList;
        this.onclickCategory = onclickCategory;
    }
    public void setOnclickCategory(OnclickCategory onclickCategory){
        this.onclickCategory = onclickCategory;
    }
    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_category_home,parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Categories category = arrayList.get(position);
        if(category.getImage() != null){
            if(category.getImage().startsWith("http")){
                Picasso.get().load(category.getImage())
                        .error(R.drawable.icon_erro)
                        .into(holder.imageView);
            }else {
                byte[] decodedString = Base64.decode(category.getImage(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                holder.imageView.setImageBitmap(decodedByte);
            }
        }else {
            holder.imageView.setImageResource(R.drawable.icon_erro);
        }
        holder.txtname.setText(category.getName());
        holder.txtname.setMaxLines(2);
        holder.txtname.setEllipsize(TextUtils.TruncateAt.END);
        holder.itemView.setOnClickListener(v -> {
            onclickCategory.onclick(category.getName(), category.getId());
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        ShapeableImageView imageView;
        TextView txtname;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_category_image_home);
            txtname = itemView.findViewById(R.id.item_category_name_home);
        }
    }
    public interface OnclickCategory{
        void onclick(String nameCategory, String idCategory);
    }
}