package com.example.eu_fstyle_mobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.eu_fstyle_mobile.src.view.IntroduceFragment;
import com.example.eu_fstyle_mobile.src.view.LoginFragment;
import com.example.eu_fstyle_mobile.src.view.SplashFragment;

public class MainActivity extends AppCompatActivity {

    private static final int SPLASH_DELAY = 3000; // Sau 3 giây sẽ vào ứng dụng
    private Handler handler;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler();

        sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE);

        boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);

        if (isFirstRun) {
            replaceFragment(new IntroduceFragment());
            sharedPreferences.edit().putBoolean("isFirstRun", false).apply();
        } else {
            replaceFragment(new SplashFragment());
        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                replaceFragment(new LoginFragment());
            }
        }, SPLASH_DELAY);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
}