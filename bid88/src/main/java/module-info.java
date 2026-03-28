module com.bt {
    requires java.sql; //Thêm sql
    requires javafx.controls;
    requires javafx.fxml;

    // Cấp quyền cho JavaFX truy cập vào thư mục controller để gắn sự kiện (nút bấm, text...)
    opens com.bt.client.controller to javafx.fxml;
    
    // Cấp quyền cho thư mục chứa App.java (nếu cần)
    opens com.bt.client to javafx.fxml;

    // Cho phép module chạy class App.java
    exports com.bt.client;
}