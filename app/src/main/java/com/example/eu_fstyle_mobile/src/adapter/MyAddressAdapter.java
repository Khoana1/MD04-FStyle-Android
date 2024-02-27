package com.example.eu_fstyle_mobile.src.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eu_fstyle_mobile.databinding.ItemMyAddressBinding;
import com.example.eu_fstyle_mobile.src.model.Address;

import java.util.List;

public class MyAddressAdapter extends RecyclerView.Adapter<MyAddressAdapter.ViewHolder> {

    private List<Address> addressList;
    private OnEditClickListener onEditClickListener;

    public MyAddressAdapter(List<Address> addressList, OnEditClickListener onEditClickListener) {
        this.addressList = addressList;
        this.onEditClickListener = onEditClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemMyAddressBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Address address = addressList.get(position);
        holder.binding.tvConsigneeName.setText(address.getConsigneeName());
        holder.binding.tvPhone.setText(address.getPhoneNumber());
        String completeAddress = address.getHomeNumber() + " " + address.getStreet() + ", " + address.getDistrict() + ", " + address.getCity();
        holder.binding.tvAddress.setText(completeAddress);
        holder.binding.tvEditItemAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEditClickListener.onEditClick(addressList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemMyAddressBinding binding;

        public ViewHolder(@NonNull ItemMyAddressBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnEditClickListener {
        void onEditClick(Address address);
    }
}
