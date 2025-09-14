package com.example.test.service;

import static com.example.test.repository.FollowComicRepository.loadFollowComicAsync;
import static com.example.test.repository.FollowComicRepository.loadUnFollowComicAsync;

import android.widget.Button;

import com.example.test.repository.DataCache;
import com.example.test.repository.GetFollowComicRepository;
import com.example.test.repository.LoadCallBackFollowComicList;
import com.example.test.ui.ComicDetailFragment;

import java.util.ArrayList;
import java.util.List;

public class FollowComicService {
    public void followComic(String userId, String slug) {
        try {
            loadFollowComicAsync(userId, slug);
        } catch (Exception e) {
            System.out.println("Lỗi followComic: " + e.getMessage());
        }
    }

    public void unFollowComic(String userId, String slug) {
        loadUnFollowComicAsync(userId, slug);
    }

    public void setUpFollowComic(String userId, Button btnFollow, String slug) {
        GetFollowComicRepository.loadGetFollowComicAsync(userId, new LoadCallBackFollowComicList() {
            @Override
            public void onSuccess(List<String> comics) {
                ComicDetailFragment.listFollowComic = comics != null ? comics : new ArrayList<>();
                btnFollow.setText(ComicDetailFragment.listFollowComic.contains(slug) ?
                        "Đang theo dõi" : "Theo dõi");
            }

            @Override
            public void onError(Exception e) {
                System.out.println("Lỗi setUpFollowComic: " + e.getMessage());
            }
        });
    }

    public void getFollowComic(String userId) {
        GetFollowComicRepository.loadGetFollowComicAsync(userId, new LoadCallBackFollowComicList() {
            @Override
            public void onSuccess(List<String> comics) {
                DataCache.listFollowComic = comics != null ? comics : new ArrayList<>();
            }

            @Override
            public void onError(Exception e) {
                System.out.println("Lỗi getFollowComic: " + e.getMessage());
            }
        });
    }
}
