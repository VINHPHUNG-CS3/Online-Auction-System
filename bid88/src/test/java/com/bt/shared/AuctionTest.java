package com.bt.shared;

import static org.junit.jupiter.api.Assertions.*; // Imports JUnit assertion methods
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bt.shared.exceptions.AuctionClosedException;
import com.bt.shared.exceptions.InvalidBidException;
import com.bt.shared.Auction.AuctionStatus;

import java.time.LocalDateTime;

public class AuctionTest {

    private Auction auction;
    private Bidder testBidder;

    // @BeforeEach runs before EVERY test to give us a fresh, clean auction
    @BeforeEach
    public void setUp() {
        // Create dummy data for testing
        Item dummyItem = new Electronics("Test Laptop", "A fast laptop", 500.00, "Electronics", 2023);
        Seller dummySeller = new Seller("Seller1", "seller@test.com", "pass", 5.0);

        auction = new Auction(dummyItem, dummySeller, LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        testBidder = new Bidder("Bidder1", "bidder@test.com", "pass", 1000.00);
    }

    // TEST 1: Prove that a valid bid works
    @Test
    public void testValidBidUpdatesHighestBid() throws AuctionClosedException, InvalidBidException {
        // 1. Arrange: Set auction to RUNNING
        auction.setStatus(AuctionStatus.RUNNING);

        // 2. Act: Place a valid bid
        boolean result = auction.placeBid(testBidder, 600.00);

        // 3. Assert: Verify the result was true and the price updated
        assertTrue(result, "The bid should be accepted.");
        assertNotNull(auction.getHighestBid(), "Highest bid should not be null.");
        assertEquals(600.00, auction.getHighestBid().getBidAmount(), "The highest bid should be $600.");
    }

    // TEST 2: Prove that our custom exception is thrown for low bids
    @Test
    public void testLowBidThrowsInvalidBidException() {
        // 1. Arrange: Set auction to RUNNING
        auction.setStatus(AuctionStatus.RUNNING);

        // 2. Act & Assert: Expect an InvalidBidException to be thrown!
        assertThrows(InvalidBidException.class, () -> {
            // Trying to bid $400 when starting price is $500
            auction.placeBid(testBidder, 400.00);
        }, "Bidding lower than starting price should throw InvalidBidException.");
    }

    // TEST 3: Prove that closed auctions reject bids
    @Test
    public void testBiddingOnClosedAuctionThrowsException() {
        // 1. Arrange: Set auction to FINISHED
        auction.setStatus(AuctionStatus.FINISHED);

        // 2. Act & Assert: Expect an AuctionClosedException to be thrown
        assertThrows(AuctionClosedException.class, () -> {
            auction.placeBid(testBidder, 1000.00);
        }, "Bidding on a finished auction should throw AuctionClosedException.");
    }
}