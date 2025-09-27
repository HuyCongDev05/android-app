package com.example.test.repository;

import com.example.test.config.ConnectAPI;
import com.example.test.entity.Message;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MessageRepository {
    private static final ConnectAPI connectAPI = new ConnectAPI();

    public static CompletableFuture<List<Message>> loadMessageAsync() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String json = connectAPI.getAPI("http://10.0.2.2:8080/api/message");
                return parseMessage(json);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }, AppExecutors.getNetworkExecutor());
    }

    public static List<Message> parseMessage(String json) {
        List<Message> messages = new ArrayList<>();

        JsonArray array = JsonParser.parseString(json).getAsJsonArray();

        for (JsonElement element : array) {
            JsonObject obj = element.getAsJsonObject();
            long id = obj.get("id").getAsLong();
            String message = obj.get("message").getAsString();
            messages.add(new Message(id, message));
        }
        return messages;
    }
}
