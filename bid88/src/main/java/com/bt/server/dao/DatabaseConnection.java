package com.bt.server.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Lưu ý: Dù bạn đổi cổng Apache thành 8080, thì cổng của MySQL vẫn luôn là 3306 nhé!
    private static final String URL = "jdbc:mysql://localhost:3306/auction_db";
    private static final String USER = "root"; 
    private static final String PASSWORD = ""; 

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ KET NOI DATABASE THANH CONG!");
        } catch (SQLException e) {
            System.out.println("❌ LOI KET NOI DATABASE: " + e.getMessage());
        }
        return conn;
    }

    // Hàm main này để bạn chạy test thử ngay lập tức
    public static void main(String[] args) {
        getConnection();
    }
}