package com.example.eu_fstyle_mobile.src.view.user.payment;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eu_fstyle_mobile.MainActivity;
import com.example.eu_fstyle_mobile.R;
import com.example.eu_fstyle_mobile.databinding.ActivityConfirmPaymentBinding;
import com.example.eu_fstyle_mobile.src.dialog.Loading100Dialog;
import com.example.eu_fstyle_mobile.src.model.Cart;
import com.example.eu_fstyle_mobile.src.model.zalopay.CreateOrder;

import org.json.JSONObject;

import java.text.DecimalFormat;

import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class ConfirmPaymentActivity extends AppCompatActivity {
    public static final String CART = "CART";
    public ActivityConfirmPaymentBinding binding;
    private Dialog loading100Dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConfirmPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        IsLoading();
        Intent intent = getIntent();
        Cart cart = (Cart) intent.getSerializableExtra(CART);
        String paymentMethod = intent.getStringExtra("PAYMENT_METHOD");
        String totalPayment = intent.getStringExtra("TOTAL");
        String paymentAddress = intent.getStringExtra("PAYMENT_ADDRESS");
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ZaloPaySDK.init(554, Environment.SANDBOX);
        switch (paymentMethod) {
            case "COD":
                binding.tvPaymentMethod.setText("COD");
                binding.icPaymentMethod.setImageDrawable(getResources().getDrawable(R.drawable.ic_payment_money));
                binding.btnPayment.setVisibility(View.VISIBLE);
                binding.llZaloPayment.setVisibility(View.GONE);
                break;
            case "ZALO":
                binding.tvPaymentMethod.setText("ZaloPay");
                binding.icPaymentMethod.setImageDrawable(getResources().getDrawable(R.drawable.ic_zalo_text));
                binding.llZaloPayment.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        double totalPaymentDouble = Double.parseDouble(totalPayment);
        binding.tvTotalPrice.setText(decimalFormat.format(totalPaymentDouble) + " VNĐ");
        binding.tvPaymentAddress.setText(paymentAddress);
        binding.btnCreateOrder.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                CreateOrder orderApi = new CreateOrder();

                try {
                    JSONObject data = orderApi.createOrder(totalPayment);
                    binding.lblZpTransToken.setVisibility(View.VISIBLE);
                    String code = data.getString("return_code");

                    if (code.equals("1")) {
                        binding.lblZpTransToken.setText("Mã giao dịch");
                        binding.txtToken.setText(data.getString("zp_trans_token"));
                        IsDone();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        binding.btnPayment.setOnClickListener(v -> {
            switch (paymentMethod) {
                case "COD":
                    showLoading100Dialog();
                    new Handler().postDelayed(() -> {
                        hideLoading100Dialog();
                        Intent intent1 = new Intent(ConfirmPaymentActivity.this, PaymentSuccessActivity.class);
                        intent1.putExtra(CART, cart);
                        intent1.putExtra("TOTAL", totalPayment);
                        startActivity(intent1);
                        finish();
                    }, 2000);

                    break;
                case "ZALO":
                    binding.tvPaymentMethod.setText("ZaloPay");
                    binding.icPaymentMethod.setImageDrawable(getResources().getDrawable(R.drawable.ic_zalo_text));
                    String token = binding.txtToken.getText().toString();
                    ZaloPaySDK.getInstance().payOrder(ConfirmPaymentActivity.this, token, "demozpdk://app", new PayOrderListener() {
                        @Override
                        public void onPaymentSucceeded(final String transactionId, final String transToken, final String appTransID) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showSuccessDialog(String.format("Thanh toán đơn hàng thành công\nMã giao dịch: %s\nMã Token giao dịch: %s", transactionId, transToken), new Runnable() {
                                        @Override
                                        public void run() {
                                            showLoading100Dialog();
                                            new Handler().postDelayed(() -> {
                                                hideLoading100Dialog();
                                                Intent intent1 = new Intent(ConfirmPaymentActivity.this, PaymentSuccessActivity.class);
                                                intent1.putExtra(CART, cart);
                                                intent1.putExtra("TOTAL", totalPayment);
                                                startActivity(intent1);
                                                finish();
                                            }, 2000);
                                        }
                                    });
                                }

                            });
                            IsLoading();
                        }

                        @Override
                        public void onPaymentCanceled(String zpTransToken, String appTransID) {
                            new AlertDialog.Builder(ConfirmPaymentActivity.this)
                                    .setTitle("Người dùng từ chối thanh toán")
                                    .setMessage(String.format("zpTransToken: %s \n", zpTransToken))
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    })
                                    .setNegativeButton("Cancel", null).show();
                        }

                        @Override
                        public void onPaymentError(ZaloPayError zaloPayError, String zpTransToken, String appTransID) {
                            new AlertDialog.Builder(ConfirmPaymentActivity.this)
                                    .setTitle("Thanh toán thất bại")
                                    .setMessage(String.format("ZaloPayErrorCode: %s \nTransToken: %s", zaloPayError.toString(), zpTransToken))
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    })
                                    .setNegativeButton("Cancel", null).show();
                        }
                    });
                    break;
                default:
                    break;
            }

        });
    }

    private void IsDone() {
        binding.lblZpTransToken.setVisibility(View.VISIBLE);
        binding.txtToken.setVisibility(View.VISIBLE);
        binding.btnPayment.setVisibility(View.VISIBLE);
        binding.btnCreateOrder.setEnabled(false);
        binding.btnCreateOrder.setAlpha(0.5f);
    }

    private void IsLoading() {
        binding.lblZpTransToken.setVisibility(View.GONE);
        binding.txtToken.setVisibility(View.GONE);
        binding.btnPayment.setVisibility(View.GONE);
        binding.btnCreateOrder.setEnabled(true);
        binding.btnCreateOrder.setAlpha(1.0f);
    }

    private void showLoading100Dialog() {
        loading100Dialog = new Loading100Dialog(this);
        loading100Dialog.show();
    }

    private void hideLoading100Dialog() {
        if (loading100Dialog != null && loading100Dialog.isShowing()) {
            loading100Dialog.dismiss();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }

    private void showSuccessDialog(String content, Runnable onConfirm) {
        Dialog dialog = new Dialog(this);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.setContentView(R.layout.success_dialog);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_dialog_background));
        TextView tvContent = dialog.findViewById(R.id.tv_success_content);
        tvContent.setText(content);

        Button btnConfirm = dialog.findViewById(R.id.btn_success_ok);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onConfirm.run();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}