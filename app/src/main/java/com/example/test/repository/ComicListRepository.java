package com.example.test.repository;

import android.os.Handler;
import android.os.Looper;

import com.example.test.entity.Comic;
import com.example.test.ui.HomeUI;
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
    private static final ExecutorService executor = Executors.newFixedThreadPool(2);
    private static final Handler mainHandler = new Handler(Looper.getMainLooper());
    private static final ConnectAPI connectAPI = new ConnectAPI();

    private static HomeUI homeUI = new HomeUI();

    public static void loadComicsAsync() {
        HashMap<String, List<Comic>> map = new HashMap<>();
        CompletableFuture<List<Comic>> proposeFuture = CompletableFuture.supplyAsync(() -> {
            String json = connectAPI.get("https://otruyenapi.com/v1/api/home");
            return parseHomeAPI(json);
        }, executor);

        CompletableFuture<List<Comic>> newFuture = CompletableFuture.supplyAsync(() -> {
            String json = connectAPI.get("https://otruyenapi.com/v1/api/danh-sach/truyen-moi?page=1");
            return parseNewComicsAPI(json);
        }, executor);

        CompletableFuture.allOf(proposeFuture, newFuture)
                .thenAccept(v -> {
                    try {
                        map.put("proposeComics", proposeFuture.get());
                        map.put("newComics", newFuture.get());
                        mainHandler.post(() -> homeUI.ComicListBook(map));
                    } catch (Exception e) {
                        e.printStackTrace();
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
            String originName = items.get(i).getAsJsonObject()
                    .getAsJsonArray("origin_name").get(0).getAsString();
            String slug = items.get(i).getAsJsonObject().get("slug").getAsString();
            comics.add(new Comic(originName, slug, img));
        }
        return comics;
    }

    // danh sách truyện mới
    private static List<Comic> parseNewComicsAPI(String json) {
        List<Comic> comics = new ArrayList<>();
        JsonObject data = JsonParser.parseString(json)
                .getAsJsonObject()
                .getAsJsonObject("data");

        JsonArray items = data.getAsJsonArray("items");
        JsonArray ogImages = data.getAsJsonObject("seoOnPage").getAsJsonArray("og_image");

        int size = Math.min(items.size(), ogImages.size());
        for (int i = 0; i < size; i++) {
            JsonObject obj = items.get(i).getAsJsonObject();
            String img = "https://otruyenapi.com" + ogImages.get(i).getAsString();
            String originName = obj.getAsJsonArray("origin_name").get(0).getAsString();
            String slug = obj.get("slug").getAsString();
            comics.add(new Comic(originName, slug, img));
        }
        return comics;
    }


    public interface OnComicsLoaded {
        void onResult(HashMap<String, List<Comic>> data);

        void onError(Exception e);
    }

}
