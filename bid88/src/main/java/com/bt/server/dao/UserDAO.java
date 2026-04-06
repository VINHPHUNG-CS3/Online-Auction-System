package com.bt.server.dao;

import com.bt.shared.User; // Import class User từ shared
import java.sql.*;

public class UserDAO {

    // 1. Hàm Kiểm tra Đăng nhập
    public User login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");
                User user;

                // Dựa vào role trong database để khởi tạo đúng class con
                if ("ADMIN".equalsIgnoreCase(role)) {
                    user = new com.bt.shared.Admin(); // Sửa lại đúng tên class Admin của bạn
                } else if ("SELLER".equalsIgnoreCase(role)) {
                    user = new com.bt.shared.Seller();
                } else {
                    user = new com.bt.shared.Bidder();
                }

                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setRole(role);

                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Đăng nhập thất bại
    }

    // 2. Hàm Đăng ký (Thêm User mới)
    public boolean register(User user) {
        String sql = "INSERT INTO users (username, email, password, role) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getRole());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}