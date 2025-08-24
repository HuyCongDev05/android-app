package com.example.test.repository;

import com.example.test.entity.ComicDetail;

public interface LoadCallbackComicDetail {
    void onLoadSuccess(ComicDetail comicDetail);

    void onLoadFailed(Throwable error);
}
