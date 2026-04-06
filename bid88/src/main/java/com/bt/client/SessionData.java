package com.bt.client;

import java.time.LocalDateTime;

import com.bt.shared.Art;
import com.bt.shared.Auction;
import com.bt.shared.Bidder;
import com.bt.shared.Seller;

public class SessionData {

    // Static variables act as our temporary shared database across screens
    public static Auction sharedAuction;
    public static Bidder currentUser;

    // This static block runs once exactly when the app starts
    static {
        Seller seller = new Seller("SellerBob", "bob@example.com", "password", 4.8);
        Art painting = new Art("Rare Oil Painting", "A beautiful 19th-century landscape.", 500.00, "Vincent", 1885);

        sharedAuction = new Auction(painting, seller, LocalDateTime.now(), LocalDateTime.now().plusDays(3));
        sharedAuction.setStatus(Auction.AuctionStatus.RUNNING);

        currentUser = new Bidder("You_The_User", "user@example.com", "123", 10000.00);
    }
}