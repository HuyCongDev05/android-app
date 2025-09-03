package com.example.test.service;

import com.example.test.repository.ImagesChapterRepository;
import com.example.test.repository.LoadCallBackImages;

public class ChapterComicService {

    public void handleImagesChapterComic(String chapterName, LoadCallBackImages callback) {
        ImagesChapterRepository.loadImagesChapterAsync(chapterName, callback);
    }
}

