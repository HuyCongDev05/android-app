package com.example.test.service;

import com.example.test.repository.ComicListRepository;
import com.example.test.repository.DataCache;

import java.util.concurrent.CompletableFuture;

public class ComicListService {
    public CompletableFuture<Boolean> handleComicList() {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        try {
            ComicListRepository.loadComicListAsync((success, map) -> {
                if (success) {
                    DataCache.comicMap = map;
                }
                future.complete(success);
            });
        } catch (Exception e) {
            System.out.println("Lỗi handleComicList: " + e.getMessage());
            future.complete(false);
        }

        return future;
    }

}
