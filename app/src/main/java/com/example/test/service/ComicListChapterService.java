package com.example.test.service;

import android.os.Handler;
import android.os.Looper;

import com.example.test.entity.ComicDetail;
import com.example.test.repository.ComicListChapterRepository;
import com.example.test.repository.LoadCallbackComicDetail;

import java.util.List;

public class ComicListChapterService {

    public void getComicDetail(String slug, LoadCallbackComicDetail callback) {
        ComicListChapterRepository.loadComicDetailAsync(new LoadCallbackComicDetail() {
            @Override
            public void onLoadSuccess(ComicDetail comicDetail) {
                new Handler(Looper.getMainLooper()).post(() -> callback.onLoadSuccess(comicDetail));
            }

            @Override
            public void onLoadFailed(Throwable error) {
                new Handler(Looper.getMainLooper()).post(() -> callback.onLoadFailed(error));
            }
        }, slug);
    }
}
