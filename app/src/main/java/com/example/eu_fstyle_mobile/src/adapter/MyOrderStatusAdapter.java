package com.example.eu_fstyle_mobile.src.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.databinding.ItemOrderStatusBinding;
import com.example.eu_fstyle_mobile.src.model.Orders;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class MyOrderStatusAdapter extends RecyclerView.Adapter<MyOrderStatusAdapter.ViewHolder> {
    private List<Orders> ordersList;
    private OnItemOrderClickListener onItemOrderClickListener;

    public MyOrderStatusAdapter(List<Orders> ordersList, OnItemOrderClickListener onItemOrderClickListener) {
        this.ordersList = ordersList;
        this.onItemOrderClickListener = onItemOrderClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemOrderStatusBinding.inflate(parent.getContext().getSystemService(LayoutInflater.class), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Orders orders = ordersList.get(position);
        holder.binding.tvOrderId.setText(orders.get_id());
        if (orders.getStatus().equals("active")) {
            holder.binding.tvOrderStatus.setText("Xác nhận");
            holder.binding.tvOrderStatus.setTextColor(Color.parseColor("#00CC00"));
            holder.binding.tvOrderStatus.setBackgroundResource(R.drawable.bg_order_active);
        } else if (orders.getStatus().equals("deactive")) {
            holder.binding.tvOrderStatus.setText("Đã hủy");
            holder.binding.tvOrderStatus.setTextColor(Color.parseColor("#000000"));
            holder.binding.tvOrderStatus.setBackgroundResource(R.drawable.bg_order_deactive);
        } else if (orders.getStatus().equals("pending")) {
            holder.binding.tvOrderStatus.setText("Chờ xác nhận");
            holder.binding.tvOrderStatus.setTextColor(Color.parseColor("#C67C4E"));
            holder.binding.tvOrderStatus.setBackgroundResource(R.drawable.bg_order_pending);
        } else if (orders.getStatus().equals("trading")) {
            holder.binding.tvOrderStatus.setText("Đang giao");
            holder.binding.tvOrderStatus.setTextColor(Color.parseColor("#FFA500"));
            holder.binding.tvOrderStatus.setBackgroundResource(R.drawable.bg_order_trading);
        } else if (orders.getStatus().equals("delivered")) {
            holder.binding.tvOrderStatus.setText("Đã giao hàng");
            holder.binding.tvOrderStatus.setTextColor(Color.parseColor("#FF0000"));
            holder.binding.tvOrderStatus.setBackgroundResource(R.drawable.bg_order_success);
        }
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            date = inputFormat.parse(orders.getTimeOrder());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        String formattedDate = outputFormat.format(date);

        holder.binding.tvOrderTime.setText(formattedDate);
        holder.binding.tvOrderCustomerName.setText(orders.getCustomerName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        double totalPrice = Double.parseDouble(orders.getTotalPrice());
        holder.binding.tvOrderTotalPrice.setText(decimalFormat.format(totalPrice) + " VNĐ");
        holder.itemView.setOnClickListener(v -> {
            onItemOrderClickListener.onOrderClick(ordersList.get(position).get_id());
        });

    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemOrderStatusBinding binding;

        public ViewHolder(@NonNull ItemOrderStatusBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnItemOrderClickListener {
        void onOrderClick(String orderId);
    }
}
