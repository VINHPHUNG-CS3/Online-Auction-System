package com.bt.client.controller;

import com.bt.client.SessionData;

import java.io.IOException;
import javafx.application.Platform; // Import this!
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import com.bt.client.App;
import com.bt.client.SessionData;
import com.bt.shared.Auction;
import com.bt.shared.BidTransaction;
import com.bt.shared.exceptions.AuctionClosedException;
import com.bt.shared.exceptions.InvalidBidException;
import com.bt.shared.BidObserver; // Import your observer

// 1. Implement the BidObserver interface
public class SecondaryController implements BidObserver {

    @FXML
    private Label itemNameLabel;
    @FXML
    private Label itemDescriptionLabel;
    @FXML
    private Label currentPriceLabel;
    @FXML
    private TextField bidInputBox;

    private Auction currentAuction;

    @FXML
    public void initialize() {
        currentAuction = SessionData.sharedAuction;

        if (currentAuction != null) {
            // Register this screen to listen for live updates
            currentAuction.addObserver(this);

            // --- SET INITIAL LABELS ---

            // 2. Get the item details from the auction
            String name = currentAuction.getItem().getName();
            String desc = currentAuction.getItem().getDescription();

            // 3. Figure out what price to show initially
            // If someone has already bid, show the highest bid.
            // If no one has bid yet, show the item's starting price.
            double displayPrice;
            if (currentAuction.getHighestBid() != null) {
                displayPrice = currentAuction.getHighestBid().getBidAmount();
            } else {
                displayPrice = currentAuction.getItem().getStartingPrice();
            }

            // 4. Set the text on the JavaFX screen
            itemNameLabel.setText(name);
            // itemDescriptionLabel.setText(desc);
            currentPriceLabel.setText("Current Price: $" + displayPrice);
        }
    }

    // 3. This method is forced by the BidObserver interface
    @Override
    public void update(BidTransaction newBid) {
        // Platform.runLater safely updates the JavaFX UI from background threads
        Platform.runLater(() -> {
            currentPriceLabel.setText("Current Price: $" + newBid.getBidAmount());
        });
    }

    @FXML
    public void handlePlaceBid() { // Note: Make sure the name matches your FXML!
        try {
            // 1. Read the number from the text box
            double amount = Double.parseDouble(bidInputBox.getText());

            // 2. This line throws the exception if the bid is too low!
            currentAuction.placeBid(SessionData.currentUser, amount);

            // 3. If no error was thrown, show the success popup
            Alert successAlert = new Alert(AlertType.INFORMATION);
            successAlert.setTitle("Bid Successful");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Your bid of $" + amount + " has been placed successfully!");
            successAlert.showAndWait();

            bidInputBox.clear();

        } catch (NumberFormatException e) {
            showErrorPopup("Invalid Input", "Please enter a valid number for your bid.");

        } catch (AuctionClosedException e) {
            showErrorPopup("Auction Closed", e.getMessage());

        } catch (InvalidBidException e) {
            // THE FIX IS HERE: We catch the error and turn it into a popup!
            showErrorPopup("Invalid Bid", e.getMessage());
        }
    }

    @FXML
    public void handlePlaceBidButton() {
        try {
            // 1. Try to read the number from the text box
            double amount = Double.parseDouble(bidInputBox.getText());

            // 2. Try to place the bid (this might throw our custom exceptions)
            currentAuction.placeBid(SessionData.currentUser, amount);

            // 3. IF SUCCESS: Show a success popup
            Alert successAlert = new Alert(AlertType.INFORMATION);
            successAlert.setTitle("Bid Successful");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Your bid of $" + amount + " has been placed successfully!");
            successAlert.showAndWait();

            // Clear the text box for the next bid
            bidInputBox.clear();

        } catch (NumberFormatException e) {
            // ERROR: User typed letters or symbols instead of a number
            showErrorPopup("Invalid Input", "Please enter a valid number for your bid.");

        } catch (AuctionClosedException e) {
            // ERROR: Auction is finished
            showErrorPopup("Auction Closed", e.getMessage());

        } catch (InvalidBidException e) {
            // ERROR: Bid was too low
            showErrorPopup("Invalid Bid", e.getMessage());
        }
    }

    // Helper method to keep our code clean!
    private void showErrorPopup(String title, String message) {
        Alert errorAlert = new Alert(AlertType.ERROR);
        errorAlert.setTitle(title);
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}