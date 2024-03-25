package com.example.eu_fstyle_mobile.src.view.admin;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eu_fstyle_mobile.src.model.Categories;
import com.example.eu_fstyle_mobile.src.request.RequestCategory;
import com.example.eu_fstyle_mobile.src.retrofit.ApiClient;
import com.example.eu_fstyle_mobile.src.retrofit.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCategoryViewModel extends ViewModel {
    private MutableLiveData<Categories> categoryData;
    private MutableLiveData<String> erroMessage;

    public AddCategoryViewModel() {
        categoryData = new MutableLiveData<>();
        erroMessage = new MutableLiveData<>();
    }

    public MutableLiveData<Categories> getCategoryData() {
        return categoryData;
    }

    public MutableLiveData<String> getErroMessage() {
        return erroMessage;
    }
    public void createCategory( String name,String image){
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        RequestCategory requestCategory = new RequestCategory(name, image);
        Log.d("huy", "createCategory: "+name+ "_"+ image);
        Call<Categories> call = apiService.createCategory(requestCategory);
        call.enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {
                if(response.body()!= null && response.isSuccessful()){
                    categoryData.setValue(response.body());
                }else {
                    erroMessage.setValue("Thêm loại sản phẩm thất bại, vui lòng kiểm tra lại thông tin!");
                }
            }

            @Override
            public void onFailure(Call<Categories> call, Throwable t) {
                erroMessage.setValue("Server error: " + t.getMessage());
            }
        });
    }
}
