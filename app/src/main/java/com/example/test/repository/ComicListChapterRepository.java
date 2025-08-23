package com.example.test.repository;

import com.example.test.config.ConnectAPI;
import com.example.test.entity.ComicDetail;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ComicListChapterRepository {
    private static final ExecutorService executor = Executors.newFixedThreadPool(1);
    private static final ConnectAPI connectAPI = new ConnectAPI();

    public static void loadComicDetailAsync(LoadCallbackComicDetail callback) {
        CompletableFuture.supplyAsync(() -> {
            try {
                String json = connectAPI.getAPIComic("https://otruyenapi.com/v1/api/truyen-tranh/");
                return parseComicDetail(json);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }, executor).thenAccept(callback::onLoadSuccess).exceptionally(ex -> {
            System.out.println("Lỗi: " + ex.getMessage());
            callback.onLoadFailed(ex);
            return null;
        });
    }
    public static ComicDetail parseComicDetail(String json) {
        ComicDetail detail = new ComicDetail();
        JsonObject data = JsonParser.parseString(json)
                .getAsJsonObject()
                .getAsJsonObject("data");

        detail.director = data.getAsJsonObject("seoOnPage")
                .getAsJsonObject("seoSchema")
                .get("director").getAsString();

        detail.descriptionHead = data.getAsJsonObject("seoOnPage")
                .get("descriptionHead").getAsString();

        detail.breadcrumbs = new ArrayList<>();
        JsonArray breadCrumbs = data.getAsJsonArray("breadCrumb");
        for (JsonElement el : breadCrumbs) {
            JsonObject obj = el.getAsJsonObject();
            if (obj.has("position") && obj.get("position").getAsInt() == 2) {
                String name = obj.get("name").getAsString();
                String slug = obj.get("slug").getAsString();
                detail.breadcrumbs.add(new ComicDetail.Breadcrumb(name, slug));
            }
        }

        detail.content = data.getAsJsonObject("item")
                .get("content").getAsString();

        detail.chapters = new ArrayList<>();
        JsonArray servers = data.getAsJsonObject("item").getAsJsonArray("chapters");
        for (JsonElement serverEl : servers) {
            JsonObject serverObj = serverEl.getAsJsonObject();
            JsonArray serverData = serverObj.getAsJsonArray("server_data");
            for (JsonElement chapterEl : serverData) {
                JsonObject chapterObj = chapterEl.getAsJsonObject();
                String chapterName = chapterObj.get("chapter_name").getAsString();
                String chapterApi = chapterObj.get("chapter_api_data").getAsString();
                detail.chapters.add(new ComicDetail.Chapter(chapterName, chapterApi));
            }
        }
        return detail;
    }
}
