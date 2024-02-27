package com.example.eu_fstyle_mobile.src.view.user.address;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.eu_fstyle_mobile.databinding.FragmentMyAddressBinding;
import com.example.eu_fstyle_mobile.src.adapter.MyAddressAdapter;
import com.example.eu_fstyle_mobile.src.adapter.ProductHomeAdapter;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;
import com.example.eu_fstyle_mobile.src.model.Address;
import com.example.eu_fstyle_mobile.src.model.AddressRespone;
import com.example.eu_fstyle_mobile.src.model.User;
import com.example.eu_fstyle_mobile.ultilties.UserPrefManager;

import java.util.ArrayList;
import java.util.List;

public class MyAddressFragment extends BaseFragment<FragmentMyAddressBinding> implements MyAddressAdapter.OnEditClickListener {
    private MyAddressAdapter myAddressAdapter;
    private MyAddressViewModel myAddressViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = getFragmentBinding(inflater, container);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListener();
        initView();
        User user = UserPrefManager.getInstance(getActivity()).getUser();
        myAddressViewModel = new ViewModelProvider(this).get(MyAddressViewModel.class);
        myAddressViewModel.getAddress(user.get_id());
        observeViewModel();
    }

    private void initListener() {
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                observeViewModel();
                binding.swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void observeViewModel() {
        myAddressViewModel.getAddressLiveData().observe(getViewLifecycleOwner(), new Observer<AddressRespone>() {
            @Override
            public void onChanged(AddressRespone addressRespone) {
                List<Address> addressList = addressRespone.getListAddress();
                myAddressAdapter = new MyAddressAdapter(addressList, MyAddressFragment.this);
                binding.rcvMyAddress.setAdapter(myAddressAdapter);
            }
        });

        myAddressViewModel.getErrorLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String error) {
                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        binding.icBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        binding.tvAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScreen(new AddAddressFragment(), true);
            }
        });
    }

    @Override
    protected FragmentMyAddressBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentMyAddressBinding.inflate(inflater, container, false);
    }

    @Override
    public void onEditClick(Address address) {
        EditAddressFragment editAddressFragment = EditAddressFragment.newInstance(address);
        openScreen(editAddressFragment, true);
    }
}