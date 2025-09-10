package com.example.test.service;

import android.os.Handler;
import android.os.Looper;

import com.example.test.entity.Comic;
import com.example.test.repository.ComicSearchRepository;
import com.example.test.repository.LoadCallBackSearchComic;

import java.util.List;

public class ComicSearchService {
    public void searchComic(String name, LoadCallBackSearchComic callback) {
        try {
            ComicSearchRepository.loadComicSearchAsync(new LoadCallBackSearchComic() {
                @Override
                public void onSuccess(List<Comic> comics) {
                    new Handler(Looper.getMainLooper()).post(() -> callback.onSuccess(comics));
                }

                @Override
                public void onFailed(Throwable ex) {
                    new Handler(Looper.getMainLooper()).post(() -> callback.onFailed(ex));
                }
            }, name);
        } catch (Exception e) {
            new Handler(Looper.getMainLooper()).post(() -> callback.onFailed(e));
        }
    }
}
