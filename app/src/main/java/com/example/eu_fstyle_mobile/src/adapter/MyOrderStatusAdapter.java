package com.example.eu_fstyle_mobile.src.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eu_fstyle_mobile.databinding.ItemOrderStatusBinding;

public class MyOrderStatusAdapter extends RecyclerView.Adapter<MyOrderStatusAdapter.ViewHolder> {

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemOrderStatusBinding binding;
        public ViewHolder(@NonNull ItemOrderStatusBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
