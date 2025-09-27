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
    public static void getFollowComic(String accountId) {
        GetFollowComicRepository.loadGetFollowComicAsync(accountId, new LoadCallBackFollowComicList() {
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

    public void followComic(String accountId, String slug) {
        try {
            loadFollowComicAsync(accountId, slug);
        } catch (Exception e) {
            System.out.println("Lỗi followComic: " + e.getMessage());
        }
    }

    public void unFollowComic(String accountId, String slug) {
        loadUnFollowComicAsync(accountId, slug);
    }

    public void setUpFollowComic(String accountId, Button btnFollow, String slug) {
        GetFollowComicRepository.loadGetFollowComicAsync(accountId, new LoadCallBackFollowComicList() {
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
}
