package com.example.test.service;

import android.os.Handler;
import android.os.Looper;

import com.example.test.entity.Account;
import com.example.test.repository.AccountRepository;
import com.example.test.repository.DataCache;
import com.example.test.repository.LoadCallbackLogin;

public class LoginService {
    private final AccountRepository usersRepository = new AccountRepository();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public void CheckLoginAsync(String userName, String password, LoadCallbackLogin loadCallbackLogin) {
        new Thread(() -> {
            Account account = usersRepository.checkLogin(userName, password);
            mainHandler.post(() -> loadCallbackLogin.onResult(account));
            DataCache.account = account;
        }).start();
    }
}
