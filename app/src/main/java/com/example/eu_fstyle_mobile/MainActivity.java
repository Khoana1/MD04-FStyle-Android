package com.example.eu_fstyle_mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.Manifest;
import android.widget.Toast;

import com.example.eu_fstyle_mobile.src.view.user.IntroFragment;
import com.example.eu_fstyle_mobile.src.view.user.SplashFragment;
import com.example.eu_fstyle_mobile.src.view.user.home.HomeFragment;
import com.example.eu_fstyle_mobile.src.view.user.payment.CartFragment;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_NOTIFICATION_PERMISSION = 1001;

    private Handler handler;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String fragmentName = getIntent().getStringExtra("FRAGMENT_NAME");

        handler = new Handler();

        sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE);

        boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);

        if (isFirstRun) {
            openIntroFragment();
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, REQUEST_NOTIFICATION_PERMISSION);
            }
            sharedPreferences.edit().putBoolean("isFirstRun", false).apply();
        } else if (fragmentName != null) {
            switch (fragmentName) {
                case "HomeFragment":
                    replaceFragment(new HomeFragment());
                    break;
                case "CartFragment":
                    replaceFragment(new CartFragment());
                    break;
                default:
                    break;
            }
        } else {
            replaceFragment(new SplashFragment());
        }

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
    private void openIntroFragment() {
        IntroFragment introFragment = new IntroFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, introFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_NOTIFICATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(this, "Bật thông báo để nhận các thông tin từ FStyle", Toast.LENGTH_SHORT).show();
            }
        }
    }
}