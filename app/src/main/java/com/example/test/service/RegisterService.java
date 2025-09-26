package com.example.test.service;

import android.os.Handler;
import android.os.Looper;

import com.example.test.repository.AccountRepository;

public class RegisterService {
    private final AccountRepository accountRepository = new AccountRepository();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public void CheckRegisterAsync(String userName, String password, RegisterService.RegisterCallback callback) {
        new Thread(() -> {
            boolean result = accountRepository.checkRegister(userName, password);
            mainHandler.post(() -> callback.onResult(result));
        }).start();
    }

    public interface RegisterCallback {
        void onResult(boolean success);
    }
}
