package com.example.eu_fstyle_mobile.src.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.databinding.ItemProductAdminBinding;
import com.example.eu_fstyle_mobile.src.model.Categories;
import com.example.eu_fstyle_mobile.src.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoriesAdminAdapter extends RecyclerView.Adapter<CategoriesAdminAdapter.ViewHolder> {

    private List<Categories> categoriesList;

    public CategoriesAdminAdapter(List<Categories> categoriesList) {
        this.categoriesList = categoriesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemProductAdminBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesAdminAdapter.ViewHolder holder, int position) {
        Categories categories = categoriesList.get(position);
        Picasso.get().load(categories.getImage())
                .placeholder(R.drawable.icon_home)
                .error(R.drawable.icon_erro)
                .into(holder.binding.itemImageProductHome);
        holder.binding.itemNameProductHome.setText(categories.getName());
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemProductAdminBinding binding;
        public ViewHolder(@NonNull  ItemProductAdminBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
