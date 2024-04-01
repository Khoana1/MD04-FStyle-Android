package com.example.eu_fstyle_mobile.src.adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.databinding.ItemCategoryFillerBinding;
import com.example.eu_fstyle_mobile.src.model.Categories;


import java.util.ArrayList;

public class CategoryFillerAdapter extends RecyclerView.Adapter<CategoryFillerAdapter.Viewholder>{
    private ArrayList<Categories> arrayList;
    public onClickItem onClickItem;
    private int selectedItemPosition = RecyclerView.NO_POSITION;
    public CategoryFillerAdapter(ArrayList<Categories> arrayList) {
        this.arrayList = arrayList;
    }
    public void setOnClickItem(onClickItem onClickItem){
        this.onClickItem = onClickItem;
    }
    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryFillerAdapter.Viewholder(ItemCategoryFillerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Categories categories = arrayList.get(position);
        holder.itemView.btnItemCategoryFiller.setText(categories.getName());
        if (position == selectedItemPosition) {
            holder.itemView.btnItemCategoryFiller.setBackgroundResource(R.drawable.bg_corner20_silver);
            holder.itemView.btnItemCategoryFiller.setTextColor(Color.WHITE);
        } else {
            holder.itemView.btnItemCategoryFiller.setBackgroundResource(R.drawable.bg_corner20);
            holder.itemView.btnItemCategoryFiller.setTextColor(Color.BLACK);
        }
        holder.itemView.btnItemCategoryFiller.setOnClickListener(v -> {
                if (onClickItem != null) {
                    Log.d("Huy", "onBindViewHolder: "+categories.getId());
                    onClickItem.onClick(categories.getId());

                }
            int previousSelectedItemPosition = selectedItemPosition;
            selectedItemPosition = holder.getAdapterPosition();
            notifyItemChanged(previousSelectedItemPosition);
            notifyItemChanged(selectedItemPosition);
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public interface onClickItem{
        void onClick(String idCategory);
    }
    public class Viewholder extends RecyclerView.ViewHolder {
        ItemCategoryFillerBinding itemView;
        public Viewholder(@NonNull ItemCategoryFillerBinding itemView) {
            super(itemView.getRoot());
            this.itemView = itemView;
        }
    }
    public void clearSelection() {
        selectedItemPosition = RecyclerView.NO_POSITION;
        notifyDataSetChanged();
    }
}
