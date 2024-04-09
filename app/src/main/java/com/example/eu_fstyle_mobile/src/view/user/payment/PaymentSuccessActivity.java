package com.example.eu_fstyle_mobile.src.view.user.payment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.eu_fstyle_mobile.MainActivity;
import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.databinding.ActivityPaymentSuccessBinding;
import com.example.eu_fstyle_mobile.src.model.Cart;

import java.text.DecimalFormat;

public class PaymentSuccessActivity extends AppCompatActivity {
    private ActivityPaymentSuccessBinding binding;
    public static final String CART = "CART";
    public static final String TOTAL = "TOTAL";

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentSuccessBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        intent = getIntent();
        Cart cart = (Cart) intent.getSerializableExtra(CART);
        String totalPayment = intent.getStringExtra(TOTAL);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        double totalPaymentDouble = Double.parseDouble(totalPayment);
        binding.tvTotalPaymentSuccess.setText(decimalFormat.format(totalPaymentDouble) + " VNĐ");
        binding.btnBackToHome.setOnClickListener(v -> {
            intent = new Intent(PaymentSuccessActivity.this, MainActivity.class);
            intent.putExtra("FRAGMENT_NAME", "HomeFragment");
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(this, "Quay lại màn hình chính", Toast.LENGTH_SHORT).show();
        intent = new Intent(PaymentSuccessActivity.this, MainActivity.class);
        intent.putExtra("FRAGMENT_NAME", "HomeFragment");
        startActivity(intent);
        finish();
    }
}