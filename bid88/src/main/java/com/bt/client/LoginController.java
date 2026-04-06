package com.bt.client;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;

    @FXML
    private void handleLogin() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Hardcoded basic check for now (Later this will check your SQLite Database!)
        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Please enter both username and password.");
        } else if (username.equals("admin") && password.equals("123")) {
            // Login successful! Send them to the dashboard.
            App.setRoot("primary");
        } else {
            errorLabel.setText("Invalid credentials. Try admin / 123");
        }
    }
}