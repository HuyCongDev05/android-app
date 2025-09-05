package com.example.test.service;

import com.example.test.repository.FollowComicRepository;
import com.example.test.repository.GetFollowComicRepository;
import com.example.test.repository.LoadCallBackFollowComicList;
import com.example.test.ui.ComicDetailFragment;

import java.util.List;

public class FollowComicService {
    public void followComic(String userId, String slug) {

        FollowComicRepository.loadComicDetailAsync(userId, slug,
                responseBody -> System.out.println("Response: " + responseBody),
                error -> System.err.println("Error: " + error.getMessage())
        );
    }

    public void getFollowComic(String userId) {
        GetFollowComicRepository.loadComicDetailAsync(userId, new LoadCallBackFollowComicList() {
            @Override
            public void onSuccess(List<String> comics) {
                ComicDetailFragment.listFollowComic.addAll(comics);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });

    }
}
