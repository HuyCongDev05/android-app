package com.example.test.service;

import com.example.test.repository.UserRepositoryDB;

public class LoginService {
    UserRepositoryDB UserCheck = new UserRepositoryDB();
    public boolean CheckLogin(String UserName, String Password) {
        return UserCheck.CheckLogin(UserName, Password);
    }
}
