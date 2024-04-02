package com.example.eu_fstyle_mobile.src.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.databinding.ItemCategoryAdminBinding;
import com.example.eu_fstyle_mobile.databinding.ItemProductAdminBinding;
import com.example.eu_fstyle_mobile.src.model.Categories;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoriesAdminAdapter extends RecyclerView.Adapter<CategoriesAdminAdapter.ViewHolder> {

    private List<Categories> categoriesList;
    public onClickItem onClickItem;

    public CategoriesAdminAdapter(List<Categories> categoriesList, onClickItem onClickItem) {
        this.onClickItem = onClickItem;
        this.categoriesList = categoriesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemCategoryAdminBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesAdminAdapter.ViewHolder holder, int position) {
        Categories categories = categoriesList.get(position);
        if(categories.getImage() != null){
            if(categories.getImage().startsWith("http")){
                Picasso.get().load(categories.getImage())
                        .error(R.drawable.icon_erro)
                        .into(holder.binding.itemImageCategoryAdmin);
            }else {
                byte[] decodedString = Base64.decode(categories.getImage(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                holder.binding.itemImageCategoryAdmin.setImageBitmap(decodedByte);
            }
        }else {
            holder.binding.itemImageCategoryAdmin.setImageResource(R.drawable.icon_erro);
        }
        holder.binding.itemNameCategoryAdmin.setText(categories.getName());
        holder.binding.itemEditCategoryAdmin.setOnClickListener(v -> {
            if(onClickItem!= null){
                onClickItem.onClick(categories);
            }
        });
        holder.binding.deleteCategoryAdmin.setOnClickListener(v -> {
            if(onClickItem!= null){
                onClickItem.onDelete(categories);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }
    public interface onClickItem{
        void onClick(Categories categories);
        void onDelete(Categories categories);
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemCategoryAdminBinding binding;
        public ViewHolder(@NonNull  ItemCategoryAdminBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
