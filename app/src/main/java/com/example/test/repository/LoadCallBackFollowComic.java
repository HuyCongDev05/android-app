package com.example.test.repository;

public interface LoadCallBackFollowComic {
    void onSuccess(String responseBody);

    void onError(Exception e);
}
