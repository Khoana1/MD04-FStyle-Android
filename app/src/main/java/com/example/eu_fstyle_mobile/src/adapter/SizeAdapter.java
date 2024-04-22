package com.example.eu_fstyle_mobile.src.adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.databinding.ItemCategoryFillerBinding;
import com.example.eu_fstyle_mobile.databinding.ItemSizeBinding;
import com.example.eu_fstyle_mobile.src.model.Categories;


import java.util.ArrayList;

public class SizeAdapter extends RecyclerView.Adapter<SizeAdapter.Viewholder>{
    private ArrayList<Integer> arrayList;
    public onClickItem onClickItem;
    private int selectedItemPosition = RecyclerView.NO_POSITION;
    public SizeAdapter(ArrayList<Integer> arrayList) {
        this.arrayList = arrayList;
    }
    public void setOnClickItem(onClickItem onClickItem){
        this.onClickItem = onClickItem;
    }
    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SizeAdapter.Viewholder(ItemSizeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        int size = arrayList.get(position);
        holder.itemView.itemBtnSize.setText(String.valueOf(size));
        if (position == selectedItemPosition) {
            holder.itemView.itemBtnSize.setBackgroundResource(R.drawable.bg_corner20_silver);
            holder.itemView.itemBtnSize.setTextColor(Color.WHITE);
        } else {
            holder.itemView.itemBtnSize.setBackgroundResource(R.drawable.bg_btn_size_color);
            holder.itemView.itemBtnSize.setTextColor(Color.BLACK);
        }
        holder.itemView.itemBtnSize.setOnClickListener(v -> {
            if (onClickItem != null) {
                onClickItem.onClick(String.valueOf(size));

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
        void onClick(String size);
    }
    public class Viewholder extends RecyclerView.ViewHolder {
        ItemSizeBinding itemView;
        public Viewholder(@NonNull ItemSizeBinding itemView) {
            super(itemView.getRoot());
            this.itemView = itemView;
        }
    }
    public void clearSelection() {
        selectedItemPosition = RecyclerView.NO_POSITION;
        notifyDataSetChanged();
    }
}

