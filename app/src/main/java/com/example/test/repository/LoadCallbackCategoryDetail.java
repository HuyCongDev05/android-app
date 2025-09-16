package com.example.test.repository;

import com.example.test.entity.Comic;

import java.util.List;

public interface LoadCallbackCategoryDetail {
    void onLoadSuccess(List<Comic> categoryComicDetail);
}
