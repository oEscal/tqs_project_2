package com.api.demo.grid.pojos;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;


@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonDeserialize
@JsonSerialize
public class AuctionPOJO {

    // auctioneer username
    private String auctioneer;

    // buyer username
    private String buyer;

    // game key
    private String gameKey;

    private Date endDate;

    private double price;


    // because lombok doesnt support get and set params of Date type with security (clone)
    public Date getEndDate() {
        return (Date) endDate.clone();
    }

    public void setEndDate(Date endDate) {
        this.endDate = (Date) endDate.clone();
    }


    public AuctionPOJO(String auctioneer, String buyer, String gameKey, Date endDate, double price) {
        this.auctioneer = auctioneer;
        this.buyer = buyer;
        this.gameKey = gameKey;
        this.endDate = (Date) endDate.clone();
        this.price = price;
    }
}
