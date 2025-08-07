package com.example.test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test.animation.animationNext;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView registerText = findViewById(R.id.registerText);
        Button loginButton = findViewById(R.id.buttonLogin);
        TextView emailInput = findViewById(R.id.emailInput);
        TextView passwordInput = findViewById(R.id.passwordInput);


        registerText.setOnClickListener(v -> {
            register();
        });
        loginButton.setOnClickListener(v -> {
            if (!checkLogin(this, emailInput.getText().toString().trim(), passwordInput.getText().toString().trim())) return;
            Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
            // nên thay bằng animation quay tròn cho đẹp
        });
    }
    public void register() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
        animationNext.apply(LoginActivity.this);
        finish();
    }
    public boolean checkLogin(Context context, String emailInput, String passwordInput) {
        if (TextUtils.isEmpty(emailInput) || TextUtils.isEmpty(passwordInput)) {
            Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }
        // thêm điều kiện kiểm tra mật khẩu với database

        return true;
    }
}
