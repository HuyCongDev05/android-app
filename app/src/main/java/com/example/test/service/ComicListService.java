package com.example.test.service;

import com.example.test.repository.ComicListRepository;
import com.example.test.repository.DataCacheComicList;

import java.util.concurrent.CompletableFuture;

public class ComicListService {
    public CompletableFuture<Boolean> handleComicList() {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        try {
            ComicListRepository.loadComicListAsync((success, map) -> {
                if (success) {
                    DataCacheComicList.comicMap = map;
                    System.out.println(map);
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
