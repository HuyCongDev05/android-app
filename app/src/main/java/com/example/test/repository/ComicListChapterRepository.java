//package com.example.test.repository;
//
//import android.os.Handler;
//import android.os.Looper;
//
//import com.example.test.config.ConnectAPI;
//import com.example.test.entity.ComicList;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//public class ComicListChapterRepository {
//    private static final ExecutorService executor = Executors.newFixedThreadPool(1);
//    private static final Handler mainHandler = new Handler(Looper.getMainLooper());
//    private static final ConnectAPI connectAPI = new ConnectAPI();
//
//    public static void loadListComicsAsync(LoadCallbackComicList callback) {
//        List<ComicList> comicLists = new ArrayList<>();
//
//        CompletableFuture<List<ComicList>> comicListChapterFuture = CompletableFuture.supplyAsync(() -> {
//            String json = connectAPI.getAPIComic("https://otruyenapi.com/v1/api/home");
//            return
//        }, executor);
//
//        comicListChapterFuture.thenAccept(list -> {
//            comicLists.addAll(list);
//
//        }).exceptionally(ex -> {
//            System.out.println("Lỗi: " + ex.getMessage());
//            return null;
//        });
//
//    }
//    public static List<ComicList> comicList(String json) {
//        List<ComicList> chapterLink = new ArrayList<>();
//
//        return chapterLink;
//    }
//
//}
