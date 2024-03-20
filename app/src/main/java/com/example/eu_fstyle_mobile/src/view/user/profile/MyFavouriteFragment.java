package com.example.eu_fstyle_mobile.src.view.user.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.eu_fstyle_mobile.databinding.FragmentMyFavouriteBinding;
import com.example.eu_fstyle_mobile.src.adapter.MyFavouriteAdapter;
import com.example.eu_fstyle_mobile.src.base.BaseFragment;
import com.example.eu_fstyle_mobile.src.model.Favourite;
import com.example.eu_fstyle_mobile.src.model.ProductFavourite;
import com.example.eu_fstyle_mobile.src.model.User;
import com.example.eu_fstyle_mobile.ultilties.UserPrefManager;

import java.util.List;

public class MyFavouriteFragment extends BaseFragment<FragmentMyFavouriteBinding> {
    private MyFavouriteAdapter myFavouriteAdapter;

    private MyFavouriteViewModel myFavouriteViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = getFragmentBinding(inflater, container);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        User user = UserPrefManager.getInstance(getActivity()).getUser();
        myFavouriteViewModel = new ViewModelProvider(this).get(MyFavouriteViewModel.class);
        myFavouriteViewModel.getFavourite(user.get_id());
        observeViewModel();
    }

    private void observeViewModel() {
        myFavouriteViewModel.getFavouriteLiveData().observe(getViewLifecycleOwner(), new Observer<Favourite>() {
            @Override
            public void onChanged(Favourite favourite) {
                if (favourite != null) {
                    List<ProductFavourite> productList = favourite.getListProduct();
                    myFavouriteAdapter = new MyFavouriteAdapter(productList);
                    binding.rcvFavourite.setAdapter(myFavouriteAdapter);
                }
            }
    });
        myFavouriteViewModel.getErrorLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
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
    }

    @Override
    protected FragmentMyFavouriteBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentMyFavouriteBinding.inflate(inflater, container, false);
    }
}