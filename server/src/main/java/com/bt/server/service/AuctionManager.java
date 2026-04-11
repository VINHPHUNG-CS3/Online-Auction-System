package com.bt.server.service;

import java.util.HashMap;
import java.util.Map;

import com.bt.shared.Auction;

public class AuctionManager {
    private static AuctionManager instance;

    //Lưu trữ các phiên đấu giá, với key là ID của phiên đấu giá và value là đối tượng Auction tương ứng
    private Map<String, Auction> auctions;

    private AuctionManager() {
        auctions = new HashMap<>();
    }

    //Áp dụng Singleton pattern để đảm bảo chỉ có một instance của AuctionManager tồn tại
    public static synchronized AuctionManager getInstance() {
        if (instance == null) {
            instance = new AuctionManager();
        }
        return instance;
    }

    //Thêm một phiên đấu giá mới vào hệ thống
    public void addAuction(Auction auction) {
        auctions.put(auction.getId(), auction);
    }

    //Lấy một phiên đấu giá theo ID
    public Auction getAuction(String auctionId) {
        return auctions.get(auctionId);
    }
}
