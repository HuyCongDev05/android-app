package com.example.test.repository;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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

    public static void loadComicsAsync(OnComicsLoaded callback) {
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
                        HashMap<String, List<Comic>> map = new HashMap<>();
                        map.put("proposeComics", proposeFuture.get());
                        map.put("newComics", newFuture.get());

                        mainHandler.post(() -> callback.onResult(map));
                    } catch (Exception e) {
                        mainHandler.post(() -> callback.onError(e));
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
            String img = ogImages.get(i).getAsString();
            String originName = items.get(i).getAsJsonObject()
                    .getAsJsonArray("origin_name").get(0).getAsString();
            comics.add(new Comic(originName, img));
        }
        return comics;
    }

    // danh sách truyện mới
    private static List<Comic> parseNewComicsAPI(String json) {
        List<Comic> comics = new ArrayList<>();
        JsonObject data = JsonParser.parseString(json).getAsJsonObject().getAsJsonObject("data");

        JsonArray items = data.getAsJsonArray("items");
        for (JsonElement el : items) {
            JsonObject obj = el.getAsJsonObject();
            String originName = obj.getAsJsonArray("origin_name").get(0).getAsString();
            String thumb = obj.get("thumb_url").getAsString();
            comics.add(new Comic(originName, thumb));
        }
        return comics;
    }

    public interface OnComicsLoaded {
        void onResult(HashMap<String, List<Comic>> data);

        void onError(Exception e);
    }

    public static class Comic {
        public String name;
        public String imageUrl;

        public Comic(String name, String imageUrl) {
            this.name = name;
            this.imageUrl = imageUrl;
        }
    }

}
