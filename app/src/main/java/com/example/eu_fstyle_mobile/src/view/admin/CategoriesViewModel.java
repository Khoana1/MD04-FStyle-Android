package com.example.eu_fstyle_mobile.src.view.admin;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.eu_fstyle_mobile.src.model.ListCategories;
import com.example.eu_fstyle_mobile.src.retrofit.ApiClient;
import com.example.eu_fstyle_mobile.src.retrofit.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class CategoriesViewModel extends ViewModel {
    private MutableLiveData<ListCategories> categorieData;
    private MutableLiveData<String> errorMessage;

    public CategoriesViewModel(){
        categorieData = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
    }
    public MutableLiveData<ListCategories> getCategorieData(){
        return categorieData;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }
    public void  getAllCategories(){
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<ListCategories> call = apiService.getAllCategories();
        call.enqueue(new Callback<ListCategories>() {
            @Override
            public void onResponse(Call<ListCategories> call, Response<ListCategories> response) {
                if(response.isSuccessful() && response.body()!= null){
                    categorieData.setValue(response.body());
                    Log.d("Huy", "onResponse: "+response.body());
                }else {
                    errorMessage.setValue("Lỗi lấy danh sách loại sản phẩm");
                }

            }

            @Override
            public void onFailure(Call<ListCategories> call, Throwable t) {
                errorMessage.setValue("Server error: " + t.getMessage());
            }
        });
    }
}
