package com.bt.shared;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

// Don't forget to import the exceptions at the top of Auction.java!
import com.bt.shared.exceptions.AuctionClosedException;
import com.bt.shared.exceptions.InvalidBidException;

public class Auction extends Entity {

    public enum AuctionStatus {
        OPEN, RUNNING, FINISHED, PAID, CANCELED
    }

    private Item item;
    private Seller seller;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private AuctionStatus status;

    private List<BidTransaction> bidHistory;
    private BidTransaction highestBid;

    // Concurrency: Lock to ensure thread safety
    private final ReentrantLock lock;

    // Observer Pattern: List of screens watching this auction
    private transient List<BidObserver> observers;

    public Auction(Item item, Seller seller, LocalDateTime startTime, LocalDateTime endTime) {
        super();
        this.item = item;
        this.seller = seller;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = AuctionStatus.OPEN;
        this.bidHistory = new ArrayList<>();
        this.lock = new ReentrantLock();
        this.observers = new ArrayList<>(); // Initialize the observer list
    }

    // --- LOGIC: OBSERVER PATTERN ---
    public void addObserver(BidObserver observer) {
        if (this.observers == null) {
            this.observers = new ArrayList<>();
        }
        this.observers.add(observer);
    }

    private void notifyObservers(BidTransaction bid) {
        for (BidObserver observer : observers) {
            observer.update(bid);
        }
    }

    // --- LOGIC: STATUS CHANGE ---
    public void changeStatus(AuctionStatus newStatus) {
        if (this.status == AuctionStatus.OPEN && newStatus == AuctionStatus.RUNNING) {
            this.status = newStatus;
        } else if (this.status == AuctionStatus.RUNNING && newStatus == AuctionStatus.FINISHED) {
            this.status = newStatus;
        } else if (this.status == AuctionStatus.FINISHED
                && (newStatus == AuctionStatus.PAID || newStatus == AuctionStatus.CANCELED)) {
            this.status = newStatus;
        } else {
            System.out.println("Error: Cannot change status from " + this.status + " to " + newStatus);
        }
    }

    // --- LOGIC: CONCURRENT BIDDING ---
    public boolean placeBid(Bidder bidder, double amount) throws AuctionClosedException, InvalidBidException {
        lock.lock(); // Block other threads from entering
        try {
            if (status != AuctionStatus.RUNNING) {
                System.out.println("Error: Auction is not running.");
                throw new AuctionClosedException("Bidding failed: This auction has already ended!");
            }

            double currentHighest = (highestBid != null) ? highestBid.getBidAmount() : item.getStartingPrice();

            if (amount <= currentHighest) {
                // THROW the custom exception instead of printing!
                throw new InvalidBidException("Bid must be higher than the current highest bid of $" + currentHighest);
            }

            BidTransaction newBid = new BidTransaction(bidder, amount);
            bidHistory.add(newBid);
            highestBid = newBid;

            // Notify the GUI that a new bid was placed!
            notifyObservers(newBid);

            return true;

        } finally {
            lock.unlock(); // ALWAYS unlock in finally block
        }
    }

    // Getters and Setters
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public BidTransaction getHighestBid() {
        return highestBid;
    }

    public AuctionStatus getStatus() {
        return status;
    }

    public void setStatus(AuctionStatus status) {
        this.status = status;
    }

    @Override
    public void displayInfo() {
        System.out.println("--- AUCTION SESSION: " + getId() + " ---");
        System.out.println("Status: " + status);
        item.displayInfo();
        System.out.println("Seller: " + seller.getUsername());

        if (highestBid != null) {
            System.out.println("Current Highest Bid: $" + highestBid.getBidAmount() + "by "
                    + highestBid.getBidder().getUsername());
        } else {
            System.out.println("No bids placed yet. Starting at: $" + item.getStartingPrice());
        }
        System.out.println("----------------------------------");
    }
}
