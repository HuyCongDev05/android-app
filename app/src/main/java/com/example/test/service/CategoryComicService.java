package com.example.test.service;

import android.os.Handler;
import android.os.Looper;

import com.example.test.repository.CategoryComicRepository;
import com.example.test.repository.LoadCallbackCategory;

import java.util.Map;

public class CategoryComicService {
    public void getCategories(LoadCallbackCategory callback) {
        CategoryComicRepository.loadComicDetailAsync(categories -> new Handler(Looper.getMainLooper()).post(() -> callback.onLoadSuccess(categories)));
    }
}
