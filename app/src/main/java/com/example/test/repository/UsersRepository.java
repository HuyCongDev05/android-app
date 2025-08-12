package com.example.test.repository;

import com.example.test.config.ConnectAPI;

public class UsersRepository {
    ConnectAPI connectAPI = new ConnectAPI();
    public boolean checkLogin(String username, String password) {
        String jsonData = String.format("{\"username\":\"%s\",\"password\":\"%s\"}", username, password);
        return connectAPI.postAPIUsers("http://10.0.2.2:8080/api/users/login",jsonData);
    }
    public boolean checkRegister(String username, String password) {
        String jsonData = String.format("{\"username\":\"%s\",\"password\":\"%s\"}", username, password);
        return connectAPI.postAPIUsers("http://10.0.2.2:8080/api/users/register",jsonData);
    }
}
