package com.example.eu_fstyle_mobile.src.view.user.payment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.eu_fstyle_mobile.MainActivity;
import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.databinding.ActivityPaymentFailBinding;

public class PaymentFailActivity extends AppCompatActivity {
    private Intent intent;
    private ActivityPaymentFailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentFailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnRepay.setOnClickListener(v -> {
            intent = new Intent(PaymentFailActivity.this, MainActivity.class);
            intent.putExtra("FRAGMENT_NAME", "PaymentFragment");
            startActivity(intent);
            finish();
        });
        binding.btnBackToHome.setOnClickListener(v -> {
            intent = new Intent(PaymentFailActivity.this, MainActivity.class);
            intent.putExtra("FRAGMENT_NAME", "HomeFragment");
            startActivity(intent);
            finish();
        });
    }
}