package com.bt.shared;

import java.time.LocalDateTime;

/**
 * Represents a single bid made by a user on an auction.
 */
public class BidTransaction extends Entity {

    private Bidder bidder;
    private double bidAmount;
    private LocalDateTime timestamp;

    public BidTransaction(Bidder bidder, double bidAmount) {
        super(); // Generates a unique Transaction ID
        this.bidder = bidder;
        this.bidAmount = bidAmount;
        this.timestamp = LocalDateTime.now(); // Records the exact time the bid was placed
    }

    public Bidder getBidder() {
        return bidder;
    }

    public double getBidAmount() {
        return bidAmount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public void displayInfo() {
        System.out.println("Transaction ID: " + getId() +
                " | Bidder: " + bidder.getUsername() +
                " | Amount: $" + bidAmount +
                " | Time: " + timestamp);
    }
}