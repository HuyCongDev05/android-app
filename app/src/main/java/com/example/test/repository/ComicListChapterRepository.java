package com.example.test.repository;

import com.example.test.config.ConnectAPI;
import com.example.test.entity.ComicList;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ComicListChapterRepository {
    private final ConnectAPI connectAPI = new ConnectAPI();

    public static void loadListComicsAsync(LoadCallback callback) {
        List<ComicList> comicLists = new ArrayList<>();

        CompletableFuture<List<ComicList>> proposeFuture = CompletableFuture.supplyAsync(() -> {

        }
    }
}
