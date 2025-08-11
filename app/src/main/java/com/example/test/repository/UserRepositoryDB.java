package com.example.test.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepositoryDB {
    public boolean CheckLogin(String UserName, String Password) {
        String sql = "SELECT COUNT(*) FROM account WHERE Username = ? AND Password = ?";
        try (Connection conn = ConnectDB.open();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, UserName);
            preparedStatement.setString(2, Password);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean CreateUser(String UserName, String Password) {
        String sql = "INSERT INTO account (Username, Password, CreationTime) VALUES (?, ?, NOW())";
        try (Connection conn = ConnectDB.open();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, UserName);
            preparedStatement.setString(2, Password);

            int rows = preparedStatement.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
