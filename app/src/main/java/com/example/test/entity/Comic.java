package com.example.test.entity;

public class Comic {
    private String ComicName;
    private int ComicImage;

    public Comic (String ComicName, int ComicImage) {
        this.ComicName = ComicName;
        this.ComicImage = ComicImage;
    }

    public String getComicName() {
        return ComicName;
    }

    public void setComicName(String comicName) {
        ComicName = comicName;
    }

    public int getComicImage() {
        return ComicImage;
    }

    public void setComicImage(int comicImage) {
        ComicImage = comicImage;
    }
}
