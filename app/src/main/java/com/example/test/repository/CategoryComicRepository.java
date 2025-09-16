package com.example.test.repository;

import com.example.test.config.ConnectAPI;
import com.example.test.entity.Comic;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class CategoryComicRepository {
    private static final ConnectAPI connectAPI = new ConnectAPI();

    public static void loadCategoryComicAsync(LoadCallbackCategory callback) {
        CompletableFuture.supplyAsync(() -> {
            try {
                String json = connectAPI.getAPI("https://otruyenapi.com/v1/api/the-loai");
                return parseCategories(json);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }, AppExecutors.getNetworkExecutor()).thenAccept(callback::onLoadSuccess).exceptionally(ex -> {
            System.out.println("Lỗi loadCategoryComicAsync: " + ex.getMessage());
            return null;
        });
    }

    public static void loadCategoryComicDetailAsync(String slug, LoadCallbackCategoryDetail callback) {
        CompletableFuture.supplyAsync(() -> {
                    try {
                        String json = connectAPI.getAPI("https://otruyenapi.com/v1/api/the-loai/" + slug);
                        return parseCategoryDetail(json);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }, AppExecutors.getNetworkExecutor())
                .thenAccept(callback::onLoadSuccess)
                .exceptionally(ex -> {
                    System.out.println("Lỗi loadCategoryComicDetailAsync: " + ex.getMessage());
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

    public static List<Comic> parseCategoryDetail(String json) {
        List<Comic> categoryComicDetail = new ArrayList<>();

        try {
            JsonObject data = JsonParser.parseString(json)
                    .getAsJsonObject()
                    .getAsJsonObject("data");

            JsonArray ogImages = data.getAsJsonObject("seoOnPage").getAsJsonArray("og_image");
            JsonArray items = data.getAsJsonArray("items");

            int size = Math.min(ogImages.size(), items.size());
            for (int i = 0; i < size; i++) {
                JsonObject itemObj = items.get(i).getAsJsonObject();
                String name = itemObj.get("name").getAsString();
                String slug = itemObj.get("slug").getAsString();
                String imageUrl = "https://otruyenapi.com/" + ogImages.get(i).getAsString();

                categoryComicDetail.add(new Comic(name, slug, imageUrl));
            }
        } catch (Exception e) {
            System.out.println("Lỗi parseCategoryDetail: " + e.getMessage());
        }
        return categoryComicDetail;
    }
}
