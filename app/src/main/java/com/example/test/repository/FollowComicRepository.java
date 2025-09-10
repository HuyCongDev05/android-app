package com.example.test.repository;

import com.example.test.config.ConnectAPI;

import java.util.concurrent.CompletableFuture;

public class FollowComicRepository {
    private static final ConnectAPI connectAPI = new ConnectAPI();

    public static void loadFollowComicAsync(String userId, String slug) {
        String jsonData = String.format(
                "{\"userId\":\"%s\",\"slug\":\"%s\"}",
                userId, slug
        );

        CompletableFuture.runAsync(() -> {
            try {
                connectAPI.postAPI("http://10.0.2.2:8080/api/comic/follow", jsonData);
            } catch (Exception e) {
                System.out.println("Lỗi loadFollowComicAsync: " + e.getMessage());
            }
        }, AppExecutors.getNetworkExecutor());
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
                System.out.println("Lỗi loadUnFollowComicAsync: " + e.getMessage());
            }
        }, AppExecutors.getNetworkExecutor());
    }
}
