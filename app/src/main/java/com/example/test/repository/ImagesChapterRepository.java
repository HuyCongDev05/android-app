package com.example.test.repository;

import android.os.Handler;
import android.os.Looper;

import com.example.test.config.ConnectAPI;
import com.example.test.entity.ComicDetail;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImagesChapterRepository {
    private static final ExecutorService executor = Executors.newFixedThreadPool(1);
    private final static ConnectAPI connectAPI = new ConnectAPI();
    public static List<ComicDetail.Chapter> chapters;

    public static void loadImagesChapterAsync(String chapterName, LoadCallBackImages callback) {
        CompletableFuture.supplyAsync(() -> {
            List<String> linkImages = new ArrayList<>();
            try {
                for (ComicDetail.Chapter chapter : chapters) {
                    if (chapter.getChapterName().equals(chapterName)) {
                        String json = connectAPI.getAPIComic(chapter.getChapterApiData());
                        linkImages = handleImagesChapter(json);
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            List<String> finalLinkImages = linkImages;
            new Handler(Looper.getMainLooper()).post(() -> {
                if (callback != null) callback.onLoadSuccess(finalLinkImages);
            });

            return null;
        }, executor);
    }


    public static List<String> handleImagesChapter(String json) {
        List<String> linkImages = new ArrayList<>();

        JsonObject data = JsonParser.parseString(json)
                .getAsJsonObject()
                .getAsJsonObject("data");

        String domain = data.get("domain_cdn").getAsString();

        JsonObject item = data.getAsJsonObject("item");
        String chapterPath = item.get("chapter_path").getAsString();

        JsonArray images = item.getAsJsonArray("chapter_image");

        for (JsonElement e : images) {
            JsonObject imgObj = e.getAsJsonObject();
            String page = imgObj.get("image_page").getAsString();
            String file = imgObj.get("image_file").getAsString();
            String fullUrl = domain + "/" + chapterPath + "/" + file;
            linkImages.add(fullUrl);
        }
        return linkImages;
    }
}
