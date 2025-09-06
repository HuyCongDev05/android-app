package com.example.test.service;

import android.os.Handler;
import android.os.Looper;

import com.example.test.repository.UsersRepository;

public class LoginService {
    public static String userId;
    private final UsersRepository usersRepository = new UsersRepository();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public void CheckLoginAsync(String userName, String password, LoginCallback callback) {
        new Thread(() -> {
            boolean result = usersRepository.checkLogin(userName, password);
            mainHandler.post(() -> {
                if (result) {
                    userId = UsersRepository.userId;
                }
                callback.onResult(result);
            });
        }).start();
    }

    public interface LoginCallback {
        void onResult(boolean success);
    }
}
