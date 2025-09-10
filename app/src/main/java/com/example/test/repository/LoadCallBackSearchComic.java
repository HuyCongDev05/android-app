package com.example.test.repository;

import com.example.test.entity.Comic;

import java.util.List;

public interface LoadCallBackSearchComic {
    void onSuccess(List<Comic> comics);

    void onFailed(Throwable ex);
}
