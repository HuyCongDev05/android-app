package com.example.test.entity;

public class ComicList {
    private int chapter;
    private String ChapterLink;

    public ComicList(int chapter, String chapterLink) {
        this.chapter = chapter;
        ChapterLink = chapterLink;
    }

    public int getChapter() {
        return chapter;
    }

    public void setChapter(int chapter) {
        this.chapter = chapter;
    }

    public String getChapterLink() {
        return ChapterLink;
    }

    public void setChapterLink(String chapterLink) {
        ChapterLink = chapterLink;
    }
}
