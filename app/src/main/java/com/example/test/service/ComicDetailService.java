package com.example.test.service;

import android.os.Handler;
import android.os.Looper;

import com.example.test.entity.ComicDetail;
import com.example.test.repository.ComicListChapterRepository;
import com.example.test.repository.LoadCallbackComicDetail;

public class ComicDetailService {

    public void getComicDetail(String slug, LoadCallbackComicDetail callback) {
        ComicListChapterRepository.loadComicListAsync(new LoadCallbackComicDetail() {
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
