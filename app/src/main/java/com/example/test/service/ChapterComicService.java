package com.example.test.service;


import com.example.test.repository.ImagesChapterRepository;

import java.util.Map;

public class ChapterComicService {
    public Map<String, String> handleImagesChapterComic(String chapterName) {
        ImagesChapterRepository.loadImagesChapterAsync(chapterName);
        return ImagesChapterRepository.linkImages;
    }
}
