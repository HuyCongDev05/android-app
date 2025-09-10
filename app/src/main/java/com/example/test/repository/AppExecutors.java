package com.example.test.repository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppExecutors {
    private static final int THREAD_COUNT = 10;
    private static final ExecutorService networkExecutor = Executors.newFixedThreadPool(THREAD_COUNT);

    public static ExecutorService getNetworkExecutor() {
        return networkExecutor;
    }
}
