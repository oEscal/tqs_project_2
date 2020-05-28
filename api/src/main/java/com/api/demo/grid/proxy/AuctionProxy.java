package com.api.demo.grid.proxy;


import com.api.demo.grid.models.Auction;
import com.api.demo.grid.models.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;


@Getter
@Setter
@JsonSerialize
public class AuctionProxy {

    private Long id;

    // auctioneer username
    private String auctioneer;

    // buyer username
    private String buyer;

    // game key
    private String gameKey;

    private String startDate;

    private String endDate;

    private double price;


    public AuctionProxy(Auction auction) {
        this.id = auction.getId();
        this.auctioneer = auction.getAuctioneer().getUsername();
        this.buyer = (auction.getBuyer() == null) ? null : auction.getBuyer().getUsername();
        this.gameKey = auction.getGameKey().getRKey();
        this.startDate = new SimpleDateFormat("dd/MM/yyyy").format(auction.getStartDate());
        this.endDate = new SimpleDateFormat("dd/MM/yyyy").format(auction.getEndDate());
        this.price = auction.getPrice();
    }
}
