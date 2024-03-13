package com.example.eu_fstyle_mobile.src.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eu_fstyle_mobile.databinding.ItemBsAddressBinding;
import com.example.eu_fstyle_mobile.src.model.Address;

import java.util.List;

public class BsSelectAddressAdapter extends RecyclerView.Adapter<BsSelectAddressAdapter.ViewHolder> {
    private List<Address> addressList;
    private AddressClickListener listener;

    public BsSelectAddressAdapter(List<Address> addressList, AddressClickListener listener) {
        this.addressList = addressList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemBsAddressBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Address address = addressList.get(position);
        holder.binding.tvConsigneeName.setText(address.getConsigneeName());
        holder.binding.tvPhone.setText(address.getPhoneNumber());
        String completeAddress = address.getHomeNumber() + " " + address.getStreet() + ", " + address.getDistrict() + ", " + address.getCity();
        holder.binding.tvAddress.setText(completeAddress);
        holder.itemView.setOnClickListener(v -> listener.onAddressClick(address));
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemBsAddressBinding binding;

        public ViewHolder(@NonNull ItemBsAddressBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface AddressClickListener {
        void onAddressClick(Address address);
    }
}