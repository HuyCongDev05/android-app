package com.example.test.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test.R;
import com.example.test.animation.animationNext;
import com.example.test.service.LoginService;

public class LoginUI extends AppCompatActivity {
    LoginService loginService = new LoginService();
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
            if (checkLogin(this, emailInput.getText().toString().trim(), passwordInput.getText().toString().trim())) {
                loginService.CheckLoginAsync(emailInput.getText().toString().trim(), passwordInput.getText().toString().trim(), success -> {
                    if (success) {
                        // thêm icon xoay
                        nextHomeUI();
                    } else {
                        Toast.makeText(LoginUI.this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    public void register() {
        Intent intent = new Intent(LoginUI.this, RegisterUI.class);
        startActivity(intent);
        animationNext.apply(LoginUI.this);
        finish();
    }
    public boolean checkLogin(Context context, String emailInput, String passwordInput) {
        if (TextUtils.isEmpty(emailInput) || TextUtils.isEmpty(passwordInput)) {
            Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public void nextHomeUI() {
        Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(LoginUI.this, HomeUI.class);
        startActivity(intent);
        // animation load
        finish();
    }
}
