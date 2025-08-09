package com.example.test.entity;

public class Comic {
    private String ComicName;
    private String ComicImage;
    private String APIChapter;

    public Comic(String comicName, String comicImage, String APIChapter) {
        ComicName = comicName;
        ComicImage = comicImage;
        this.APIChapter = APIChapter;
    }

    public String getComicName() {
        return ComicName;
    }

    public String getComicImage() {
        return ComicImage;
    }

}
