package com.example.test;

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

import com.example.test.animation.animationBack;

public class RegisterActivity extends AppCompatActivity {
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
            if (!checkRegister(this, emailInput.getText().toString().trim(), passwordInput.getText().toString().trim(), checkboxAgree.isChecked())) return;
            Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
            backToLogin();
        });
    }
    private void backToLogin() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        animationBack.apply(RegisterActivity.this);
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
