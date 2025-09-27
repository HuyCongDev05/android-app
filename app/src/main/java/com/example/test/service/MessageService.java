package com.example.test.service;

import com.example.test.entity.Message;
import com.example.test.repository.DataCache;
import com.example.test.repository.MessageRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MessageService {

    public static CompletableFuture<List<Message>> getMessage() {
        return MessageRepository.loadMessageAsync()
                .thenApply(messages -> {
                    DataCache.messages = messages;
                    return messages;
                })
                .exceptionally(ex -> {
                    System.err.println("Lỗi loadMessageAsync: " + ex.getMessage());
                    return null;
                });
    }
}

