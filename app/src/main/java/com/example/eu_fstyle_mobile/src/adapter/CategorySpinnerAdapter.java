package com.example.eu_fstyle_mobile.src.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.src.model.Categories;
import com.example.eu_fstyle_mobile.src.model.Category;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CategorySpinnerAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Categories> categoryList;

    public CategorySpinnerAdapter(Context context, ArrayList<Categories> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @Override
    public int getCount() {
        return categoryList != null ? categoryList.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return categoryList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rootView = LayoutInflater.from(context)
                .inflate(R.layout.item_shoe_spinner, viewGroup, false);

        TextView txtName = rootView.findViewById(R.id.categoryName);
        ImageView image = rootView.findViewById(R.id.categoryImage);

        Categories categories = categoryList.get(i);
        txtName.setText(categories.getName());
        if(categories.getImage() != null){
            if(categories.getImage().startsWith("http")){
                Picasso.get().load(categories.getImage())
                        .error(R.drawable.icon_erro)
                        .into(image);
            }else {
                byte[] decodedString = Base64.decode(categories.getImage(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                image.setImageBitmap(decodedByte);
            }
        }else {
            image.setImageResource(R.drawable.icon_erro);
        }

        return rootView;
    }
}
