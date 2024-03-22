package com.example.eu_fstyle_mobile.src.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.eu_fstyle_mobile.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

public class BannerAdapter extends PagerAdapter {
    Context context;
    String[] listImage;

    public BannerAdapter(Context context, String[] listImage) {
        this.context = context;
        this.listImage = listImage;
    }

    @Override
    public int getCount() {
        return listImage.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_banner_home, container, false);
        ShapeableImageView imageView = view.findViewById(R.id.item_image_banner);
        Picasso.get().load(listImage[position])
                .into(imageView);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
