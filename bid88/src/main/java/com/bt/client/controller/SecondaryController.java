package com.bt.client.controller; // Make sure this matches your package structure!

import java.io.IOException;
import java.time.LocalDateTime;

import com.bt.client.App;
import com.bt.shared.Art;
import com.bt.shared.Auction;
import com.bt.shared.Bidder;
import com.bt.shared.Seller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SecondaryController {

    // --- UI Elements linked from secondary.fxml ---
    @FXML
    private Label itemNameLabel;
    @FXML
    private Label currentPriceLabel;
    @FXML
    private TextField bidInputBox;

    // --- Core OOP Objects ---
    private Auction currentAuction;
    private Bidder currentBidder;

    /**
     * This method runs automatically when the secondary.fxml is loaded.
     * We use it to set up our mock data to test the UI.
     */
    @FXML
    public void initialize() {
        // 1. Create a mock Seller and a mock Item (Art)
        Seller seller = new Seller("SellerBob", "bob@example.com", "password", 4.8);
        Art painting = new Art("Rare Oil Painting", "A beautiful 19th-century landscape.", 500.00, "Vincent", 1885);

        // 2. Create the Auction and set it to RUNNING so we can bid on it
        currentAuction = new Auction(painting, seller, LocalDateTime.now(), LocalDateTime.now().plusDays(3));
        currentAuction.setStatus(Auction.AuctionStatus.RUNNING);

        // 3. Create a mock Bidder (The person currently using the app)
        currentBidder = new Bidder("You_The_User", "user@example.com", "123", 10000.00);

        // 4. Update the User Interface to show the item's details
        itemNameLabel.setText("Item: " + currentAuction.getItem().getName());
        currentPriceLabel.setText("Current Price: $" + currentAuction.getItem().getStartingPrice());
    }

    /**
     * This method is triggered when the user clicks the "Place Bid" button.
     */
    @FXML
    private void handlePlaceBid() {
        try {
            // Grab the text from the input box and turn it into a decimal number
            double bidAmount = Double.parseDouble(bidInputBox.getText());

            // Try to place the bid using our OOP Auction logic
            boolean success = currentAuction.placeBid(currentBidder, bidAmount);

            if (success) {
                // If the bid was valid (higher than current price), update the screen
                double newPrice = currentAuction.getHighestBid().getBidAmount();
                currentPriceLabel.setText("Current Price: $" + newPrice);
                bidInputBox.clear();

                // Show a success popup
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Success! You are the highest bidder currently.");
                alert.setTitle("Bid Placed");
                alert.setHeaderText(null);
                alert.show();

                // Print the updated auction info to the console
                currentAuction.displayInfo();
            } else {
                // If the bid was too low, show a warning popup
                double currentPrice = (currentAuction.getHighestBid() != null)
                        ? currentAuction.getHighestBid().getBidAmount()
                        : currentAuction.getItem().getStartingPrice();

                Alert alert = new Alert(Alert.AlertType.WARNING, "Your bid must be higher than $" + currentPrice);
                alert.setTitle("Invalid Bid");
                alert.setHeaderText(null);
                alert.show();
            }

        } catch (NumberFormatException e) {
            // If the user typed words instead of numbers, show an error
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid number!");
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.show();
        }
    }

    /**
     * Navigates back to the Dashboard.
     */
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary"); // Ensure your App class has the setRoot method!
    }
}