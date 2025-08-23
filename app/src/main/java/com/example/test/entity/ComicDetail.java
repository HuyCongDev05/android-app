package com.example.test.entity;

import java.util.List;

public class ComicDetail {
    public String director;
    public String descriptionHead;
    public List<Breadcrumb> breadcrumbs;
    public String content;
    public List<Chapter> chapters;

    public static class Breadcrumb {
        String name;
        String slug;

        public Breadcrumb(String name, String slug) {
            this.name = name;
            this.slug = slug;
        }
    }
    public static class Chapter {
        String chapterName;
        String chapterApiData;

        public Chapter(String chapterName, String chapterApiData) {
            this.chapterName = chapterName;
            this.chapterApiData = chapterApiData;
        }
    }
}
