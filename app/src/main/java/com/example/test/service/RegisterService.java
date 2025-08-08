package com.example.test.service;

import com.example.test.repository.UserRepositoryDB;

public class RegisterService {
    UserRepositoryDB UserCreateCheck = new UserRepositoryDB();
    public boolean checkRegister (String UserName, String Password) {
        return UserCreateCheck.CreateUser(UserName, Password);
    }
}
