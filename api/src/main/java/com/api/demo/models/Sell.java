package com.api.demo.models;
import javax.persistence.*;
import java.sql.Date;


@Entity
public class Sell {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @ManyToOne
    private User user;

    @Column(unique = true)
    private String gameKey;

    private double price;

    //@OneToMany
    //private Game game;

    @Temporal(TemporalType.DATE)
    private Date date;


    public int getId() {
        return id;
    }

    public void setId(int sellId) {
        this.id = sellId;
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
