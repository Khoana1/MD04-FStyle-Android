package com.example.eu_fstyle_mobile.src.model;

import com.airbnb.lottie.L;
import com.example.eu_fstyle_mobile.R;

import java.util.ArrayList;
import java.util.List;

public class DataCategory {
    public static List<Category>  getDataCategory() {
        List<Category> categoryList = new ArrayList<>();

        Category sneaker = new Category();
        sneaker.setName("Giày Sneaker");
        sneaker.setImage(String.valueOf(R.drawable.ic_sneaker));
        categoryList.add(sneaker);

        Category running = new Category();
        running.setName("Giày Chạy Bộ");
        running.setImage(String.valueOf(R.drawable.ic_running_shoe));
        categoryList.add(running);

        Category lazy = new Category();
        lazy.setName("Giày Lười");
        lazy.setImage(String.valueOf(R.drawable.ic_lazy_shoe));
        categoryList.add(lazy);

        Category walking = new Category();
        walking.setName("Giày Đi Bộ");
        walking.setImage(String.valueOf(R.drawable.ic_walking_shoe));
        categoryList.add(walking);

        Category tennis = new Category();
        tennis.setName("Giày Tennis");
        tennis.setImage(String.valueOf(R.drawable.ic_tennis_shoe));
        categoryList.add(tennis);

        Category crossTraining = new Category();
        crossTraining.setName("Giày Tập Luyện Đa Năng");
        crossTraining.setImage(String.valueOf(R.drawable.cross_training_shoes));
        categoryList.add(crossTraining);

        Category offRoadRunning = new Category();
        offRoadRunning.setName("Giày Chạy Địa Hình");
        offRoadRunning.setImage(String.valueOf(R.drawable.off_road_running_shoe));
        categoryList.add(offRoadRunning);

        Category highTop = new Category();
        highTop.setName("Giày Cổ Cao");
        highTop.setImage(String.valueOf(R.drawable.high_top_shoe));
        categoryList.add(highTop);

        return categoryList;
    }
}
