package com.bt.client.controller;

import java.io.IOException;

import com.bt.client.App;

import javafx.fxml.FXML;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}