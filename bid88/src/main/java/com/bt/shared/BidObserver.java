package com.bt.shared;

/**
 * This interface allows any UI screen (like SecondaryController)
 * to listen for new bids and update automatically.
 */
public interface BidObserver {
    // This method will be triggered every time a valid bid is placed
    void update(BidTransaction newBid);

}