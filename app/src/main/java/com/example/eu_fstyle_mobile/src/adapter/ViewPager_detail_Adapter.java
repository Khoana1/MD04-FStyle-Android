package com.example.eu_fstyle_mobile.src.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.eu_fstyle_mobile.R;
import com.squareup.picasso.Picasso;

public class ViewPager_detail_Adapter extends PagerAdapter {
    Context context;
    String[] list;

    public ViewPager_detail_Adapter(Context context, String[] list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.length;
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
        ImageView imageView = view.findViewById(R.id.item_image_banner);
        if(list[position] != null){
            if(list[position].startsWith("http")){
                Picasso.get().load(list[position])
                        .error(R.drawable.icon_erro)
                        .into(imageView);
            }else {
                byte[] decodedString = Base64.decode(list[position], Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                imageView.setImageBitmap(decodedByte);
            }
        }else {
          imageView.setImageResource(R.drawable.icon_erro);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
