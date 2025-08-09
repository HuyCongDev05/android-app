package com.example.test.repository;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectDB {
    private static final String URL = "jdbc:mysql://localhost:3306/phone";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";
    private Connection connection;

    public Connection open() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Đã kết nối MySQL");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi kết nối MySQL: " + e.getMessage());
        }
        return connection;
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Đã đóng kết nối MySQL");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi đóng kết nối MySQL: " + e.getMessage());
        }
    }
}
