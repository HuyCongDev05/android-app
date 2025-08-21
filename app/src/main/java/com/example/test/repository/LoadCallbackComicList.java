package com.example.test.repository;

import com.example.test.entity.Comic;

import java.util.HashMap;
import java.util.List;

public interface LoadCallbackComicList {
    void onLoaded(boolean success, HashMap<String, List<Comic>> map);
}
