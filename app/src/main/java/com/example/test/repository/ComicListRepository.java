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

public class ComicListRepository {
    private static final Handler mainHandler = new Handler(Looper.getMainLooper());
    private static final ConnectAPI connectAPI = new ConnectAPI();

    public static void loadComicListAsync(LoadCallbackComicList callback) {
        HashMap<String, List<Comic>> map = new HashMap<>();

        CompletableFuture<List<Comic>> proposeFuture = CompletableFuture.supplyAsync(() -> {
            String json = connectAPI.getAPI("https://otruyenapi.com/v1/api/home");
            return parseProposeComic(json);
        }, AppExecutors.getNetworkExecutor());

        CompletableFuture<List<Comic>> newFuture = CompletableFuture.supplyAsync(() -> {
            String json = connectAPI.getAPI("https://otruyenapi.com/v1/api/danh-sach/truyen-moi?page=3");
            return parseNewComics(json);
        }, AppExecutors.getNetworkExecutor());

        CompletableFuture<List<Comic>> finishedFuture = CompletableFuture.supplyAsync(() -> {
            String json = connectAPI.getAPI("https://otruyenapi.com/v1/api/danh-sach/hoan-thanh?page=2");
            return parseFinishedComics(json);
        }, AppExecutors.getNetworkExecutor());

        CompletableFuture.allOf(proposeFuture, newFuture, finishedFuture)
                .thenAccept(v -> {
                    try {
                        map.put("proposeComics", proposeFuture.get());
                        map.put("newComics", newFuture.get());
                        map.put("finishedComics", finishedFuture.get());

                        mainHandler.post(() -> callback.onLoaded(true, map));
                    } catch (Exception e) {
                        System.out.println("Lỗi loadComicListAsync: " + e.getMessage());
                    }
                });
    }

    // danh sách đề xuất
    private static List<Comic> parseProposeComic(String json) {
        List<Comic> comics = new ArrayList<>();
        JsonObject data = JsonParser.parseString(json).getAsJsonObject().getAsJsonObject("data");

        JsonArray items = data.getAsJsonArray("items");
        JsonArray ogImages = data.getAsJsonObject("seoOnPage").getAsJsonArray("og_image");

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
    private static List<Comic> parseFinishedComics(String json) {
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

    private static List<Comic> parseNewComics(String json) {
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
