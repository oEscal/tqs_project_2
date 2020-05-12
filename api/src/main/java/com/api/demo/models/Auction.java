package com.api.demo.models;

import javax.persistence.*;
import java.util.Date;

public class Auction {
    @Id
    @GeneratedValue( strategy= GenerationType.AUTO )

    private int sellId;

    @ManyToOne
    private User user;

    @Column(unique = true)
    private String gameKey;

    private double currentPrice;
    private Date endDate;

    public int getSellId() {
        return sellId;
    }

    public void setSellId(int sellId) {
        this.sellId = sellId;
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

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
