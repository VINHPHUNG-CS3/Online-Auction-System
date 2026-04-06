package com.bt.server.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    // Lưu ý: Dù bạn đổi cổng Apache thành 8080, thì cổng của MySQL vẫn luôn là 3306
    // nhé!
    private static final String URL = "jdbc:mysql://26.4.214.252:3306/auction_db";
    private static final String USER = "VQTT";
    private static final String PASSWORD = "29122007";

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

    public static void initializeDatabase() {
        // 1. Get the connection first
        Connection conn = getConnection();

        // 2. Check if it's null BEFORE doing anything else
        if (conn == null) {
            System.out.println("❌ Khong the khoi tao database. Vui long bat MySQL XAMPP và tao database 'auction_db'!");
            return; // Stop running this method
        }

        // 3. If connected, proceed to create tables
        try (Statement stmt = conn.createStatement()) {

            // Create USERS Table
            String createUsersTable = "CREATE TABLE IF NOT EXISTS users ("
                    + "id VARCHAR(100) PRIMARY KEY, "
                    + "username VARCHAR(100) NOT NULL UNIQUE, "
                    + "email VARCHAR(100) NOT NULL UNIQUE, "
                    + "password VARCHAR(255) NOT NULL, "
                    + "role VARCHAR(50) NOT NULL, "
                    + "account_balance DOUBLE, "
                    + "seller_rating DOUBLE, "
                    + "access_level INT"
                    + ");";
            stmt.execute(createUsersTable);

            // Create ITEMS Table
            String createItemsTable = "CREATE TABLE IF NOT EXISTS items ("
                    + "id VARCHAR(100) PRIMARY KEY, "
                    + "name VARCHAR(255) NOT NULL, "
                    + "description TEXT, "
                    + "starting_price DOUBLE NOT NULL, "
                    + "category VARCHAR(50) NOT NULL, "
                    + "brand VARCHAR(100), "
                    + "warranty_months INT, "
                    + "artist VARCHAR(100), "
                    + "year_created INT, "
                    + "make VARCHAR(100), "
                    + "model VARCHAR(100), "
                    + "mileage INT"
                    + ");";
            stmt.execute(createItemsTable);

            // Create AUCTIONS Table
            String createAuctionsTable = "CREATE TABLE IF NOT EXISTS auctions ("
                    + "id VARCHAR(100) PRIMARY KEY, "
                    + "item_id VARCHAR(100) NOT NULL, "
                    + "seller_id VARCHAR(100) NOT NULL, "
                    + "start_time DATETIME NOT NULL, "
                    + "end_time DATETIME NOT NULL, "
                    + "status VARCHAR(50) NOT NULL, "
                    + "FOREIGN KEY(item_id) REFERENCES items(id), "
                    + "FOREIGN KEY(seller_id) REFERENCES users(id)"
                    + ");";
            stmt.execute(createAuctionsTable);

            // Create BID_TRANSACTIONS Table
            String createBidsTable = "CREATE TABLE IF NOT EXISTS bid_transactions ("
                    + "id VARCHAR(100) PRIMARY KEY, "
                    + "auction_id VARCHAR(100) NOT NULL, "
                    + "bidder_id VARCHAR(100) NOT NULL, "
                    + "bid_amount DOUBLE NOT NULL, "
                    + "timestamp DATETIME NOT NULL, "
                    + "FOREIGN KEY(auction_id) REFERENCES auctions(id), "
                    + "FOREIGN KEY(bidder_id) REFERENCES users(id)"
                    + ");";
            stmt.execute(createBidsTable);

            System.out.println("✅ Tự động tạo bảng (nếu chưa có) thành công!");

        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi khởi tạo database: " + e.getMessage());
        } finally {
            // Clean up the connection when done
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Hàm main này để bạn chạy test thử ngay lập tức
    public static void main(String[] args) {
        getConnection();
    }
}