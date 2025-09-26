package com.example.test.repository;

import android.annotation.SuppressLint;

import com.example.test.config.ConnectAPI;
import com.example.test.entity.Account;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class AccountRepository {
    ConnectAPI connectAPI = new ConnectAPI();

    public Account checkLogin(String username, String password) {
        String jsonData = String.format(
                "{\"username\":\"%s\",\"password\":\"%s\"}",
                username, password
        );

        String response = connectAPI.postAPI("http://10.0.2.2:8080/api/user/login", jsonData);

        if (response.isEmpty()) {
            return null;
        }

        try {
            Gson gson = new Gson();
            return gson.fromJson(response, Account.class);
        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
        return null;
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

    public boolean checkUpdateUser(Account account) {
        String accountIdStr = account.getAccountId() != null ? account.getAccountId().toString() : "null";
        String jsonData = String.format(
                "{"
                        + "\"accountId\": %s,"
                        + "\"firstName\": \"%s\","
                        + "\"lastName\": \"%s\","
                        + "\"email\": \"%s\","
                        + "\"phoneNumber\": \"%s\""
                        + "}",
                accountIdStr,
                account.getFirstName(),
                account.getLastName(),
                account.getEmail(),
                account.getPhoneNumber()
        );
        String response = connectAPI.postAPI("http://10.0.2.2:8080/api/user/update", jsonData);
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
