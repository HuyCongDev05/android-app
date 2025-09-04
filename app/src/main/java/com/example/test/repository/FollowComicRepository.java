package com.example.test.repository;

import com.example.test.entity.FollowComic;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

public class FollowComicRepository {
    private static final Executor executor = java.util.concurrent.Executors.newFixedThreadPool(4);
    private static final Gson gson = new Gson();

    public static void loadComicDetailAsync(String userId, String slug,
                                            Consumer<String> onSuccess,
                                            Consumer<Exception> onError) {

    }
}
