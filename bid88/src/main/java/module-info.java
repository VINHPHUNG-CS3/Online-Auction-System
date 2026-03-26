module com.bt {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.bt to javafx.fxml;
    exports com.bt;
}
