package com.example.eu_fstyle_mobile.ultilties;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.eu_fstyle_mobile.src.model.User;
import com.google.gson.Gson;

public class AdminPreManager {
    private static final String SHARED_PREF_NAME = "admin_shared_pref";

    private static final String KEY_ADMIN = "admin";

    private static AdminPreManager instance;

    private static Context ctx;

    private AdminPreManager(Context context) {
        ctx = context;
    }

    public static synchronized AdminPreManager getInstance(Context context) {
        if (instance == null) {
            instance = new AdminPreManager(context);
        }
        return instance;
    }

    public void saveAdminData(User user) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ADMIN, new Gson().toJson(user));
        editor.apply();
    }

    public User getAdminData() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String userJson = sharedPreferences.getString(KEY_ADMIN, null);
        return new Gson().fromJson(userJson, User.class);
    }
}
