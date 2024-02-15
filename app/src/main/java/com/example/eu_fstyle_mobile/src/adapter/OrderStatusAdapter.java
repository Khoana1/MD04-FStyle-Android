package com.example.eu_fstyle_mobile.src.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.eu_fstyle_mobile.src.view.OrderStatusFragment;

public class OrderStatusAdapter extends FragmentStateAdapter {
    private final String[] titles = new String[]{"Tất cả", "Chờ xác nhận", "Xác nhận", "Đang giao", "Đã giao", "Đã hủy"};
    public OrderStatusAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        OrderStatusFragment fragment = new OrderStatusFragment();
        Bundle args = new Bundle();
        args.putString("title", titles[position]);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}
