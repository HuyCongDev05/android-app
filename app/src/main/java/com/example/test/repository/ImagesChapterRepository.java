package com.example.test.repository;


import com.example.test.config.ConnectAPI;
import com.example.test.entity.ComicDetail;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ImagesChapterRepository {
    public static Map<String, String> linkImages;
    public static List<ComicDetail.Chapter> chapters;
    private static final ExecutorService executor = Executors.newFixedThreadPool(1);
    private final static ConnectAPI connectAPI = new ConnectAPI();

    public static void loadImagesChapterAsync(String chapterName) {
        CompletableFuture.supplyAsync(() -> {
            try {
                for (ComicDetail.Chapter chapter : chapters) {
                    if (chapter.getChapterName().equals(chapterName)) {
                        String json = connectAPI.getAPIComic(chapter.getChapterApiData());
                        linkImages = handleImagesChapter(json);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return null;
        }, executor);
    }
    public static Map<String, String> handleImagesChapter(String json) {
        Map<String, String> linkImages = new HashMap<>();

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
            linkImages.put(page, fullUrl);
        }
        return linkImages;
    }
}
