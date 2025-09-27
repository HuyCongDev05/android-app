package com.example.test.repository;

import com.example.test.entity.Account;
import com.example.test.entity.Comic;
import com.example.test.entity.ComicDetail;
import com.example.test.entity.Message;

import java.util.HashMap;
import java.util.List;

public class DataCache {
    public static HashMap<String, List<Comic>> comicMap;
    public static List<String> listFollowComic;
    public static List<ComicDetail> listComicDetail;
    public static Account account;
    public static List<Message> messages;

}

