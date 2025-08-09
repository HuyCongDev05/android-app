package com.example.test.service;

import com.example.test.repository.UserRepositoryDB;

public class LoginService {
    UserRepositoryDB UserLoginCheck = new UserRepositoryDB();
    public boolean CheckLogin(String UserName, String Password) {
        return UserLoginCheck.CheckLogin(UserName, Password);
    }
}
