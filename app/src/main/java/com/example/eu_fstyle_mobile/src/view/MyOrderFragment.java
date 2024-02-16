package com.example.eu_fstyle_mobile.src.view;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.databinding.FragmentMyOrderBinding;
import com.example.eu_fstyle_mobile.src.adapter.OrderStatusAdapter;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MyOrderFragment extends BaseFragment<FragmentMyOrderBinding> {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMyOrderBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        OrderStatusAdapter orderStatusAdapter = new OrderStatusAdapter(getActivity());
        binding.viewPager.setAdapter(orderStatusAdapter);
        new TabLayoutMediator(binding.tabLayout, binding.viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Tất cả");
                    break;
                case 1:
                    tab.setText("Chờ xác nhận");
                    break;
                case 2:
                    tab.setText("Xác nhận");
                    break;
                case 3:
                    tab.setText("Đang giao");
                    break;
                case 4:
                    tab.setText("Đã giao");
                    break;
                case 5:
                    tab.setText("Đã hủy");
                    break;
            }
        }).attach();

        for (int i = 0; i < binding.tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = binding.tabLayout.getTabAt(i);
            if (tab != null) {
                View tabView = LayoutInflater.from(getContext()).inflate(R.layout.tab_item, null);
                TextView tabTitle = tabView.findViewById(R.id.tab_title);
                View tabDivider = tabView.findViewById(R.id.tab_divider);

                tabTitle.setText(tab.getText());

                if (i == binding.tabLayout.getTabCount() - 1) {
                    tabDivider.setVisibility(View.GONE);
                }

                tab.setCustomView(tabView);
            }
        }
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                if (view != null) {
                    TextView tabTitle = view.findViewById(R.id.tab_title);
                    @SuppressLint("ResourceType") ColorStateList colorStateList = ContextCompat.getColorStateList(getContext(), R.drawable.tab_text_selector);
                    tabTitle.setTextColor(colorStateList);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                if (view != null) {
                    TextView tabTitle = view.findViewById(R.id.tab_title);
                    @SuppressLint("ResourceType") ColorStateList colorStateList = ContextCompat.getColorStateList(getContext(), R.drawable.tab_text_selector);
                    tabTitle.setTextColor(colorStateList);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        binding.icBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    @Override
    protected FragmentMyOrderBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentMyOrderBinding.inflate(inflater, container, false);
    }
}