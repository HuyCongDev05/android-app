package com.example.test.repository;

import com.example.test.config.ConnectAPI;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class UsersRepository {
    public static String userId;
    ConnectAPI connectAPI = new ConnectAPI();

    public boolean checkLogin(String username, String password) {
        String jsonData = String.format(
                "{\"username\":\"%s\",\"password\":\"%s\"}",
                username, password
        );

        String response = connectAPI.postAPI("http://10.0.2.2:8080/api/user/login", jsonData);

        if (response.isEmpty()) {
            return false;
        }

        try {
            JsonObject resObj = JsonParser.parseString(response).getAsJsonObject();
            String status = resObj.get("status").getAsString();

            if ("success".equalsIgnoreCase(status)) {
                userId = resObj.get("user_id").getAsString();
                return true;
            }
        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
        return false;
    }

    public boolean checkRegister(String username, String password) {
        String jsonData = String.format(
                "{\"username\":\"%s\",\"password\":\"%s\"}",
                username, password
        );

        String response = connectAPI.postAPI("http://10.0.2.2:8080/api/user/register", jsonData);

        if (response.isEmpty()) {
            return false;
        }

        try {
            JsonObject resObj = JsonParser.parseString(response).getAsJsonObject();
            String status = resObj.get("status").getAsString();
            return "success".equalsIgnoreCase(status);
        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
            return false;
        }
    }
}
