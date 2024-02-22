package com.example.eu_fstyle_mobile.ultilties;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.example.eu_fstyle_mobile.src.model.User;

public class UserPrefManager {
    private static final String SHARED_PREF_NAME = "user_shared_pref";
    private static final String KEY_USER = "user";
    private static UserPrefManager instance;
    private static Context ctx;

    private UserPrefManager(Context context) {
        ctx = context;
    }

    public static synchronized UserPrefManager getInstance(Context context) {
        if (instance == null) {
            instance = new UserPrefManager(context);
        }
        return instance;
    }

    public void saveUser(User user) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER, new Gson().toJson(user));
        editor.apply();
    }

    public User getUser() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String userJson = sharedPreferences.getString(KEY_USER, null);
        return new Gson().fromJson(userJson, User.class);
    }
}
