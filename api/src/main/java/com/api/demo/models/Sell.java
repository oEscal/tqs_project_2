package com.api.demo.models;

import javax.persistence.*;
import java.util.Date;


@Entity
public class Sell {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @OneToOne
    private GameKey gameKey;

    @OneToOne
    private Buy purchased;

    @ManyToOne
    private User user;

    private double price;

    @Temporal(TemporalType.DATE)
    private Date date;


    public int getId() {
        return id;
    }

    public void setId(int sellId) {
        this.id = sellId;
    }

    public GameKey getGameKey() {
        return gameKey;
    }

    public void setGameKey(GameKey gameKey) {
        this.gameKey = gameKey;
    }

    public Buy getPurchased() {
        return purchased;
    }

    public void setPurchased(Buy purchased) {
        this.purchased = purchased;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDate() {
        return (Date) date.clone();
    }

    public void setDate(Date date) {
        this.date = (Date) date.clone();
    }
}
