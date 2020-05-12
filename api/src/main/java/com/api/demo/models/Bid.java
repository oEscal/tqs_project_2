package com.api.demo.models;
import javax.persistence.*;
import java.util.Date;


@Entity
public class Bid {
    @Id
    @GeneratedValue( strategy= GenerationType.AUTO )

    private int bidId;

    @OneToOne
    private User user;

    @OneToOne
    private Auction auction;

    @Temporal(TemporalType.DATE)
    private Date date;

    private double value;

    private boolean active;

    public int getBidId() {
        return bidId;
    }

    public void setBidId(int bidId) {
        this.bidId = bidId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Date getDate() {
        return (Date) date.clone();
    }

    public void setDate(Date date) {
        this.date = (Date) date.clone();
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
