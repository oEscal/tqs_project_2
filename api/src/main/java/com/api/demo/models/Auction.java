package com.api.demo.models;

import javax.persistence.*;
import java.util.Date;


@Entity
public class Auction {

    @Id
    @GeneratedValue( strategy= GenerationType.AUTO )
    private int id;

    @ManyToOne
    private User user;

    //@OneToMany
    //private Game game;

    @OneToOne
    private GameKey gameKey;

    @OneToOne
    private Buy purchased;

    @Column(insertable = false, updatable = false, columnDefinition = "DATE DEFAULT (CURRENT_DATE)")
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    private double price;


    public int getId() {
        return id;
    }

    public void setId(int auctionId) {
        this.id = auctionId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public GameKey getGameKey() {
        return gameKey;
    }

    public void setGameKey(GameKey gameKey) {
        this.gameKey = gameKey;
    }

    public Date getEndDate() {
        return (Date) endDate.clone();
    }

    public void setEndDate(Date endDate) {
        this.endDate = (Date) endDate.clone();
    }

    public Buy getPurchased() {
        return purchased;
    }

    public void setPurchased(Buy purchased) {
        this.purchased = purchased;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getStartDate() {
        return (Date) startDate.clone();
    }
}
