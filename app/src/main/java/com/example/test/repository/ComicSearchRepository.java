package com.example.test.repository;

import android.os.Handler;
import android.os.Looper;

import com.example.test.config.ConnectAPI;
import com.example.test.entity.Comic;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ComicSearchRepository {
    private static final ConnectAPI connectAPI = new ConnectAPI();

    public static void loadComicSearchAsync(LoadCallBackSearchComic callback, String name) {
        CompletableFuture.supplyAsync(() -> {
                    try {
                        String json = connectAPI.getAPI("https://otruyenapi.com/v1/api/tim-kiem?keyword=" + name);
                        return parseComicSearch(json);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }, AppExecutors.getNetworkExecutor())
                .thenAccept(result -> new Handler(Looper.getMainLooper()).post(() -> callback.onSuccess(result)))
                .exceptionally(ex -> {
                    System.out.println("Lỗi loadComicSearchAsync: " + ex.getMessage());
                    new Handler(Looper.getMainLooper()).post(() -> callback.onFailed(ex));
                    return null;
                });
    }

    private static List<Comic> parseComicSearch(String json) {
        List<Comic> comics = new ArrayList<>();
        JsonObject data = JsonParser.parseString(json).getAsJsonObject().getAsJsonObject("data");

        JsonArray items = data.getAsJsonArray("items");
        JsonArray ogImages = data.getAsJsonObject("seoOnPage").getAsJsonArray("og_image");

        int size = Math.min(ogImages.size(), items.size());
        for (int i = 0; i < size; i++) {
            String img = "https://otruyenapi.com/uploads/" + ogImages.get(i).getAsString();
            String name = items.get(i).getAsJsonObject().get("name").getAsString();
            String slug = items.get(i).getAsJsonObject().get("slug").getAsString();
            comics.add(new Comic(name, slug, img));
        }
        return comics;
    }
}
