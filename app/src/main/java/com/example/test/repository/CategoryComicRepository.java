package com.example.test.repository;

import com.example.test.config.ConnectAPI;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CategoryComicRepository {
    private static final ExecutorService executor = Executors.newFixedThreadPool(1);
    private static final ConnectAPI connectAPI = new ConnectAPI();

    public static void loadComicDetailAsync(LoadCallbackCategory callback) {
        CompletableFuture.supplyAsync(() -> {
            try {
                String json = connectAPI.getAPIComic("https://otruyenapi.com/v1/api/the-loai");
                return parseCategories(json);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }, executor).thenAccept(callback::onLoadSuccess).exceptionally(ex -> {
            System.out.println("Lỗi: " + ex.getMessage());
            return null;
        });
    }
    public static Map<String, String> parseCategories(String json) {
        Map<String, String> categories = new HashMap<>();

        JsonObject data = JsonParser.parseString(json)
                .getAsJsonObject()
                .getAsJsonObject("data");

        JsonArray items = data.getAsJsonArray("items");

        for (JsonElement element : items) {
            JsonObject obj = element.getAsJsonObject();
            String slug = obj.get("slug").getAsString();
            String name = obj.get("name").getAsString();
            categories.put(slug, name);
        }
        return categories;
    }

}
