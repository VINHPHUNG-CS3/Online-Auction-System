package com.bt.shared;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the central auction session for a specific item.
 */
public class Auction extends Entity {

    // Define the specific states required by the assignment
    public enum AuctionStatus {
        OPEN, RUNNING, FINISHED, PAID, CANCELED
    }

    private Item item;
    private Seller seller;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private AuctionStatus status;

    // Aggregation: An auction contains a history of bids
    private List<BidTransaction> bidHistory;
    private BidTransaction highestBid;

    public Auction(Item item, Seller seller, LocalDateTime startTime, LocalDateTime endTime) {
        super();
        this.item = item;
        this.seller = seller;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = AuctionStatus.OPEN; // Default state
        this.bidHistory = new ArrayList<>();
        this.highestBid = null;
    }

    /**
     * Core Logic: Place a bid.
     * The 'synchronized' keyword ensures thread safety for Concurrent Bidding.
     * If two bidders click at the exact same time, Java processes them one by one.
     */
    public synchronized boolean placeBid(Bidder bidder, double amount) {
        // Validation: Cannot bid if auction is not running
        if (status != AuctionStatus.RUNNING) {
            System.out.println("Error: Auction is not currently active.");
            return false;
        }

        double currentHighest = (highestBid != null) ? highestBid.getBidAmount() : item.getStartingPrice();

        // Validation: Bid must be strictly higher than current price
        if (amount <= currentHighest) {
            System.out.println("Error: Bid must be higher than the current price of $" + currentHighest);
            return false;
        }

        // Create transaction, record it, and update the highest bid
        BidTransaction newBid = new BidTransaction(bidder, amount);
        bidHistory.add(newBid);
        highestBid = newBid;

        System.out.println("Success! " + bidder.getUsername() + " placed a bid of $" + amount);
        return true;
    }

    // Getters and Setters
    public Item getItem() {
        return item;
    }

    public Seller getSeller() {
        return seller;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public AuctionStatus getStatus() {
        return status;
    }

    public void setStatus(AuctionStatus status) {
        this.status = status;
    }

    public List<BidTransaction> getBidHistory() {
        return bidHistory;
    }

    public BidTransaction getHighestBid() {
        return highestBid;
    }

    @Override
    public void displayInfo() {
        System.out.println("--- AUCTION SESSION: " + getId() + " ---");
        System.out.println("Status: " + status);
        item.displayInfo();
        System.out.println("Seller: " + seller.getUsername());

        if (highestBid != null) {
            System.out.println("Current Highest Bid: $" + highestBid.getBidAmount() + " by "
                    + highestBid.getBidder().getUsername());
        } else {
            System.out.println("No bids placed yet. Starting at: $" + item.getStartingPrice());
        }
        System.out.println("----------------------------------");
    }
}