package com.example.test.service;

import android.os.Handler;
import android.os.Looper;

import com.example.test.repository.UsersRepository;

public class LoginService {
    private Handler mainHandler = new Handler(Looper.getMainLooper());

    public interface LoginCallback {
        void onResult(boolean success);
    }
    UsersRepository usersRepository = new UsersRepository();

    public void CheckLoginAsync(String userName, String password, LoginCallback callback) {
        new Thread(() -> {
            boolean result = usersRepository.checkLogin(userName, password);
            mainHandler.post(() -> callback.onResult(result));
        }).start();
    }
}
