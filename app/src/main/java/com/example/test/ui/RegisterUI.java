package com.example.test.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test.R;
import com.example.test.animation.AnimationBack;
import com.example.test.service.RegisterService;

public class RegisterUI extends AppCompatActivity {
    RegisterService registerService = new RegisterService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView backToLogin = findViewById(R.id.backToLogin);
        CheckBox checkboxAgree = findViewById(R.id.checkboxAgree);
        EditText emailInput = findViewById(R.id.emailInput);
        EditText passwordInput = findViewById(R.id.passwordInput);
        Button buttonRegister = findViewById(R.id.buttonRegister);

        backToLogin.setOnClickListener(v -> backToLogin());

        buttonRegister.setOnClickListener(v -> {
            if (!checkRegister(this, emailInput.getText().toString().trim(), passwordInput.getText().toString().trim(), checkboxAgree.isChecked())) {
                registerService.CheckRegisterAsync(emailInput.getText().toString().trim(), passwordInput.getText().toString().trim(), success -> {
                    if (!Patterns.EMAIL_ADDRESS.matcher(emailInput.getText().toString().trim()).matches()) {
                        if (success) {
                            Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            backToLogin();
                        } else {
                            Toast.makeText(this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void backToLogin() {
        Intent intent = new Intent(RegisterUI.this, LoginUI.class);
        startActivity(intent);
        AnimationBack.apply(RegisterUI.this);
        finish();
    }

    private boolean checkRegister(Context context, String email, String password, boolean agree) {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(context, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.length() < 6) {
            Toast.makeText(context, "Mật khẩu phải có ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!agree) {
            Toast.makeText(context, "Vui lòng đồng ý với điều khoản", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
