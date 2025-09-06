package com.example.test.repository;

import java.util.List;

public interface LoadCallBackFollowComicList {
    void onSuccess(List<String> comics);

    void onError(Exception e);
}
