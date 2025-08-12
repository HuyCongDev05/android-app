package com.example.test.config;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectAPI {
    public String getAPIComic(String apiUrl) {
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    result.append(line);
                }
                in.close();
            } else {
                System.out.println("Lỗi API: " + responseCode);
            }

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }
    public boolean postAPIUsers(String apiUrl, String data) {
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            // Gửi dữ liệu
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = data.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Đọc phản hồi
            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line.trim());
                }
            }

            // Lấy giá trị status
            String json = response.toString();
            String status = json.replaceAll(".*\"status\"\\s*:\\s*\"(.*?)\".*", "$1");

            return "success".equalsIgnoreCase(status);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
