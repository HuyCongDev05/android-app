package com.example.test.entity;

import java.util.List;

public class ComicDetail {
    public String name;
    public String director;
    public String image;
    public List<Breadcrumb> breadcrumbs;
    public String content;
    public String slug;
    public List<Chapter> chapters;

    public static class Breadcrumb {
        String name;
        String slug;

        public Breadcrumb(String name, String slug) {
            this.name = name;
            this.slug = slug;
        }

        public String getName() {
            return name;
        }

        public String getSlug() {
            return slug;
        }

        @Override
        public String toString() {
            return "Breadcrumb{" +
                    "name='" + name + '\'' +
                    ", slug='" + slug + '\'' +
                    '}';
        }
    }

    public static class Chapter {
        String chapterName;
        String chapterApiData;

        public Chapter(String chapterName, String chapterApiData) {
            this.chapterName = chapterName;
            this.chapterApiData = chapterApiData;
        }

        public String getChapterApiData() {
            return chapterApiData;
        }

        public void setChapterApiData(String chapterApiData) {
            this.chapterApiData = chapterApiData;
        }

        public String getChapterName() {
            return chapterName;
        }

        public void setChapterName(String chapterName) {
            this.chapterName = chapterName;
        }

        @Override
        public String toString() {
            return "Chapter{" +
                    "chapterName='" + chapterName + '\'' +
                    ", chapterApiData='" + chapterApiData + '\'' +
                    '}';
        }
    }
}
