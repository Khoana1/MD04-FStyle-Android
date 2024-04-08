package com.example.eu_fstyle_mobile.src.view.user.payment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.FragmentTransaction;

import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.databinding.FragmentAddressBottomSheetDialogBinding;
import com.example.eu_fstyle_mobile.src.adapter.BsSelectAddressAdapter;
import com.example.eu_fstyle_mobile.src.model.Address;
import com.example.eu_fstyle_mobile.src.model.AddressRespone;
import com.example.eu_fstyle_mobile.src.model.User;
import com.example.eu_fstyle_mobile.src.retrofit.ApiClient;
import com.example.eu_fstyle_mobile.src.retrofit.ApiService;
import com.example.eu_fstyle_mobile.src.view.user.address.AddAddressFragment;
import com.example.eu_fstyle_mobile.ultilties.UserPrefManager;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

import retrofit2.Call;


public class AddressBottomSheetDialogFragment extends BottomSheetDialogFragment {

    private AddressSelectionListener listener;
    private FragmentAddressBottomSheetDialogBinding binding;

    private List<Address> addressList;

    public AddressBottomSheetDialogFragment(AddressSelectionListener listener, List<Address> addressList) {
        this.listener = listener;
        this.addressList = addressList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddressBottomSheetDialogBinding.inflate(inflater, container, false);
        initData();
        return binding.getRoot();
    }

    private void initData() {
        User user = UserPrefManager.getInstance(getActivity()).getUser();
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<AddressRespone> call = apiService.getAddress(user.get_id());
        call.enqueue(new retrofit2.Callback<AddressRespone>() {
            @Override
            public void onResponse(Call<AddressRespone> call, retrofit2.Response<AddressRespone> response) {
                if (response.isSuccessful()) {
                    addressList = response.body().getListAddress();
                    if (addressList != null && !addressList.isEmpty()) {
                        if (binding.rcvChooseAddress != null) {
                            binding.rcvChooseAddress.setVisibility(View.VISIBLE);
                        }
                        if (binding.llEmptyAddress != null) {
                            binding.llEmptyAddress.setVisibility(View.GONE);
                        }
                    } else {
                        if (binding.rcvChooseAddress != null) {
                            binding.rcvChooseAddress.setVisibility(View.GONE);
                        }
                        if (binding.llEmptyAddress != null) {
                            binding.llEmptyAddress.setVisibility(View.VISIBLE);
                        }
                    }
                    binding.btnEmptyAddress.setOnClickListener(v -> {
                        dismiss();
                        AddAddressFragment addAddressFragment = new AddAddressFragment();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, addAddressFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    });
                    BsSelectAddressAdapter adapter = new BsSelectAddressAdapter(addressList, listener::onAddressSelected);
                    binding.rcvChooseAddress.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<AddressRespone> call, Throwable t) {
                Toast.makeText(getActivity(), "Server error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}