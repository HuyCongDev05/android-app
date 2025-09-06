package com.example.test.repository;

import com.example.test.config.ConnectAPI;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class FollowComicRepository {
    private static final Executor executor = java.util.concurrent.Executors.newFixedThreadPool(4);
    private static final ConnectAPI connectAPI = new ConnectAPI();

    public static CompletableFuture<Boolean> loadFollowComicAsync(String userId, String slug) {
        String jsonData = String.format(
                "{\"userId\":\"%s\",\"slug\":\"%s\"}",
                userId, slug
        );

        return CompletableFuture.supplyAsync(() -> {
            try {
                String json = connectAPI.postAPI("http://10.0.2.2:8080/api/comic/follow", jsonData);
                JsonObject resObj = JsonParser.parseString(json).getAsJsonObject();
                String status = resObj.get("status").getAsString();
                return "success".equalsIgnoreCase(status);
            } catch (Exception e) {
                System.out.println("Lỗi" + e.getMessage());
                return false;
            }
        }, executor);
    }

    public static void loadUnFollowComicAsync(String userId, String slug) {
        String jsonData = String.format(
                "{\"userId\":\"%s\",\"slug\":\"%s\"}",
                userId, slug
        );

        CompletableFuture.runAsync(() -> {
            try {
                connectAPI.postAPI("http://10.0.2.2:8080/api/comic/unfollow", jsonData);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, executor);
    }
}
