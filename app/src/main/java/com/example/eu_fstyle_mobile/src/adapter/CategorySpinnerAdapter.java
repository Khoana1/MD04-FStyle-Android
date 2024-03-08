package com.example.eu_fstyle_mobile.src.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.src.model.Category;

import java.util.List;

public class CategorySpinnerAdapter extends BaseAdapter {
    private Context context;
    private List<Category> categoryList;

    public CategorySpinnerAdapter(Context context, List<Category> categoryList) {
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

        txtName.setText(categoryList.get(i).getName());
        image.setImageResource(Integer.parseInt(categoryList.get(i).getImage()));

        return rootView;
    }
}
