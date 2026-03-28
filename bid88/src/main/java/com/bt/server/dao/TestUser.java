package com.bt.server.dao;

import com.bt.shared.User;

public class TestUser {
    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();
        
        // Thử đăng nhập với tài khoản admin đã tạo trong SQL
        User u = userDAO.login("admin", "123456");
        
        if (u != null) {
            System.out.println("✅ Đăng nhập thành công!");
            System.out.println("Chào mừng " + u.getRole() + ": " + u.getUsername());
        } else {
            System.out.println("❌ Sai tài khoản hoặc mật khẩu!");
        }
    }
}