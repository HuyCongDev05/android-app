package com.example.test.repository;

import com.example.test.config.ConnectAPI;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class GetFollowComicRepository {
    private static final Gson gson = new Gson();
    private static final ConnectAPI connectAPI = new ConnectAPI();

    public static void loadGetFollowComicAsync(String userId, LoadCallBackFollowComicList callback) {
        CompletableFuture.<List<String>>supplyAsync(() -> {
                    try {
                        String json = connectAPI.getAPI("http://10.0.2.2:8080/api/comic/getAll/" + userId);
                        Type listType = new TypeToken<List<String>>() {
                        }.getType();
                        return gson.fromJson(json, listType);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }, AppExecutors.getNetworkExecutor())
                .thenAccept(callback::onSuccess)
                .exceptionally(ex -> {
                    callback.onError(ex.getCause() instanceof Exception ? (Exception) ex.getCause() : new Exception(ex.getCause()));
                    return null;
                });
    }
}
