package com.example.test.service;

import android.os.Handler;
import android.os.Looper;

import com.example.test.repository.CategoryComicRepository;
import com.example.test.repository.LoadCallbackCategory;
import com.example.test.repository.LoadCallbackCategoryDetail;

public class CategoryComicService {
    public void getCategories(LoadCallbackCategory callback) {
        CategoryComicRepository.loadCategoryComicAsync(categories -> new Handler(Looper.getMainLooper()).post(() -> callback.onLoadSuccess(categories)));
    }

    public void getCategoryDetail(String slug, LoadCallbackCategoryDetail callback) {
        CategoryComicRepository.loadCategoryComicDetailAsync(slug, categoryComicDetail ->
                new Handler(Looper.getMainLooper()).post(() -> callback.onLoadSuccess(categoryComicDetail))
        );
    }

}
