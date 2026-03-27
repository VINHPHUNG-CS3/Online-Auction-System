package com.bt.client.controller;

import java.io.IOException;

import com.bt.client.App;

import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
