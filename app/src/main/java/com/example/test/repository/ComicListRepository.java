package com.example.test.repository;

import android.os.Handler;
import android.os.Looper;

import com.example.test.config.ConnectAPI;
import com.example.test.entity.Comic;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ComicListRepository {
    private static final ExecutorService executor = Executors.newFixedThreadPool(3);
    private static final Handler mainHandler = new Handler(Looper.getMainLooper());
    private static final ConnectAPI connectAPI = new ConnectAPI();

    public static void loadComicsAsync(LoadCallbackComicList callback) {
        HashMap<String, List<Comic>> map = new HashMap<>();

        CompletableFuture<List<Comic>> proposeFuture = CompletableFuture.supplyAsync(() -> {
            String json = connectAPI.getAPIComic("https://otruyenapi.com/v1/api/home");
            return parseHomeAPI(json);
        }, executor);

        CompletableFuture<List<Comic>> newFuture = CompletableFuture.supplyAsync(() -> {
            String json = connectAPI.getAPIComic("https://otruyenapi.com/v1/api/danh-sach/truyen-moi?page=3");
            return parseNewComicsAPI(json);
        }, executor);

        CompletableFuture<List<Comic>> finishedFuture = CompletableFuture.supplyAsync(() -> {
            String json = connectAPI.getAPIComic("https://otruyenapi.com/v1/api/danh-sach/hoan-thanh?page=2");
            return parseFinishedComicsAPI(json);
        }, executor);

        CompletableFuture.allOf(proposeFuture, newFuture, finishedFuture)
                .thenAccept(v -> {
                    try {
                        map.put("proposeComics", proposeFuture.get());
                        map.put("newComics", newFuture.get());
                        map.put("finishedComics", finishedFuture.get());

                        mainHandler.post(() -> callback.onLoaded(true, map));
                    } catch (Exception e) {
                        System.out.println("Lỗi: " + e.getMessage());
                    }
                });
    }

    // danh sách đề xuất
    private static List<Comic> parseHomeAPI(String json) {
        List<Comic> comics = new ArrayList<>();
        JsonObject data = JsonParser.parseString(json).getAsJsonObject().getAsJsonObject("data");

        JsonArray ogImages = data.getAsJsonObject("seoOnPage").getAsJsonArray("og_image");
        JsonArray items = data.getAsJsonArray("items");

        int size = Math.min(ogImages.size(), items.size());
        for (int i = 0; i < size; i++) {
            String img = "https://otruyenapi.com" + ogImages.get(i).getAsString();
            String name = items.get(i).getAsJsonObject().get("name").getAsString();
            String slug = items.get(i).getAsJsonObject().get("slug").getAsString();
            comics.add(new Comic(name, slug, img));
        }
        return comics;
    }

    // danh sách truyện mới
    private static List<Comic> parseFinishedComicsAPI(String json) {
        List<Comic> comics = new ArrayList<>();
        JsonObject data = JsonParser.parseString(json).getAsJsonObject().getAsJsonObject("data");

        JsonArray items = data.getAsJsonArray("items");
        JsonArray ogImages = data.getAsJsonObject("seoOnPage").getAsJsonArray("og_image");

        int size = Math.min(items.size(), ogImages.size());
        for (int i = 0; i < size; i++) {
            JsonObject obj = items.get(i).getAsJsonObject();
            String img = "https://otruyenapi.com" + ogImages.get(i).getAsString();
            String name = obj.get("name").getAsString();
            String slug = obj.get("slug").getAsString();
            comics.add(new Comic(name, slug, img));
        }
        return comics;
    }

    private static List<Comic> parseNewComicsAPI(String json) {
        List<Comic> comics = new ArrayList<>();
        JsonObject data = JsonParser.parseString(json).getAsJsonObject().getAsJsonObject("data");

        JsonArray items = data.getAsJsonArray("items");
        JsonArray ogImages = data.getAsJsonObject("seoOnPage").getAsJsonArray("og_image");

        int size = Math.min(items.size(), ogImages.size());
        for (int i = 0; i < size; i++) {
            JsonObject obj = items.get(i).getAsJsonObject();
            String img = "https://otruyenapi.com" + ogImages.get(i).getAsString();
            String name = obj.get("name").getAsString();
            String slug = obj.get("slug").getAsString();
            comics.add(new Comic(name, slug, img));
        }
        return comics;
    }
}
