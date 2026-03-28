package com.bt.server.controller;

import com.bt.server.dao.UserDAO;
import com.bt.shared.User;
import java.io.*;
import java.net.*;

public class AuctionServer {
    private static final int PORT = 1234; // Cổng để Client kết nối tới

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("🚀 SERVER DA SAN SANG TAI CONG: " + PORT);
            UserDAO userDAO = new UserDAO();

            while (true) {
                // Chờ máy Client kết nối
                Socket clientSocket = serverSocket.accept();
                System.out.println("📱 Co thiet bi moi ket noi: " + clientSocket.getInetAddress());

                // Xử lý đăng nhập đơn giản cho Client này
                new Thread(() -> handleClient(clientSocket, userDAO)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket socket, UserDAO userDAO) {
        try (DataInputStream in = new DataInputStream(socket.getInputStream());
             DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {
            
            // Đợi nhận username/password từ Client gửi lên
            String username = in.readUTF();
            String password = in.readUTF();

            // Kiểm tra trong Database
            User user = userDAO.login(username, password);

            if (user != null) {
                out.writeUTF("SUCCESS");
                out.writeUTF(user.getRole()); // Gửi về quyền của user
            } else {
                out.writeUTF("FAIL");
            }
        } catch (IOException e) {
            System.out.println("Loi khi trao doi voi Client: " + e.getMessage());
        }
    }
}