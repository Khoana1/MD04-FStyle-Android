package com.example.eu_fstyle_mobile.src.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eu_fstyle_mobile.databinding.ItemCategoryFillerBinding;


import java.util.ArrayList;

public class CategoryFillerAdapter extends RecyclerView.Adapter<CategoryFillerAdapter.Viewholder>{
    private ArrayList<String> arrayList;

    public CategoryFillerAdapter(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryFillerAdapter.Viewholder(ItemCategoryFillerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
           String name = arrayList.get(position);
           holder.itemView.btnItemCategoryFiller.setText(name);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ItemCategoryFillerBinding itemView;
        public Viewholder(@NonNull ItemCategoryFillerBinding itemView) {
            super(itemView.getRoot());
            this.itemView = itemView;
        }
    }
}
