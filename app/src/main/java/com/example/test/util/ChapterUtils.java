package com.example.test.util;

public class ChapterUtils {
    public static String extractChapterNumber(String chapterName) {
        if (chapterName == null || chapterName.isEmpty()) {
            return "";
        }
        return chapterName.replaceAll("\\D+", "");
    }
}
