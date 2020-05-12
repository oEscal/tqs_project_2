package com.api.demo.models;

import javax.persistence.*;
import java.sql.Date;


@Entity
public class Auction {
    @Id
    @GeneratedValue( strategy= GenerationType.AUTO )

    private int auctionId;

    @ManyToOne
    private User user;

    //@OneToMany
    //private Game game;

    @Column(unique = true)
    private String gameKey;

    @OneToOne
    private Bid bid;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    private double price;


    public int getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(int auctionId) {
        this.auctionId = auctionId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getGameKey() {
        return gameKey;
    }

    public void setGameKey(String gameKey) {
        this.gameKey = gameKey;
    }

    public Date getEndDate() {
        return (Date) endDate.clone();
    }

    public void setEndDate(Date endDate) {
        this.endDate = (Date) endDate.clone();
    }

    public Bid getBid() {
        return bid;
    }

    public void setBid(Bid bid) {
        this.bid = bid;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}