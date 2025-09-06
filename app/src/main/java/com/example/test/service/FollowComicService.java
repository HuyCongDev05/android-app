package com.example.test.service;

import static com.example.test.repository.FollowComicRepository.loadFollowComicAsync;
import static com.example.test.repository.FollowComicRepository.loadUnFollowComicAsync;

import android.widget.Button;

import com.example.test.repository.GetFollowComicRepository;
import com.example.test.repository.LoadCallBackFollowComicList;
import com.example.test.ui.ComicDetailFragment;

import java.util.ArrayList;
import java.util.List;

public class FollowComicService {
    public boolean followComic(String userId, String slug) {
        try {
            return loadFollowComicAsync(userId, slug).get();
        } catch (Exception e) {
            System.out.println("Lỗi" + e.getMessage());
            return false;
        }
    }

    public void unFollowComic(String userId, String slug) {
        loadUnFollowComicAsync(userId, slug);
    }


    public void getFollowComic(String userId, Button btnFollow, String slug) {
        GetFollowComicRepository.loadComicDetailAsync(userId, new LoadCallBackFollowComicList() {
            @Override
            public void onSuccess(List<String> comics) {
                ComicDetailFragment.listFollowComic = comics != null ? comics : new ArrayList<>();
                btnFollow.setText(ComicDetailFragment.listFollowComic.contains(slug) ?
                        "Đang theo dõi" : "Theo dõi");
            }

            @Override
            public void onError(Exception e) {
                System.out.println("Lỗi " + e.getMessage());
            }
        });
    }

}
