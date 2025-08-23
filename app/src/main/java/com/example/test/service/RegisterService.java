package com.example.test.service;

import android.os.Handler;
import android.os.Looper;

import com.example.test.repository.UsersRepository;

public class RegisterService {
    private final UsersRepository usersRepository = new UsersRepository();
    private Handler mainHandler = new Handler(Looper.getMainLooper());

    public void CheckRegisterAsync(String userName, String password, RegisterService.RegisterCallback callback) {
        new Thread(() -> {
            boolean result = usersRepository.checkRegister(userName, password);
            mainHandler.post(() -> callback.onResult(result));
        }).start();
    }

    public interface RegisterCallback {
        void onResult(boolean success);
    }
}
