package com.api.demo.models;

import javax.persistence.*;
import java.util.Date;

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
    private Date endDate;

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
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Bid getBid() {
        return bid;
    }

    public void setBid(Bid bid) {
        this.bid = bid;
    }
}
