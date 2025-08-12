package com.example.test.service;

import android.os.Handler;
import android.os.Looper;

import com.example.test.repository.UsersRepository;

public class RegisterService {
    private Handler mainHandler = new Handler(Looper.getMainLooper());

    public interface RegisterCallback {
        void onResult(boolean success);
    }
    UsersRepository usersRepository = new UsersRepository();

    public void CheckRegisterAsync(String userName, String password, RegisterService.RegisterCallback callback) {
        new Thread(() -> {
            boolean result = usersRepository.checkRegister(userName, password);
            mainHandler.post(() -> callback.onResult(result));
        }).start();
    }
}
