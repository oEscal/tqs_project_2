package com.api.demo.models;

import javax.persistence.*;
import java.util.Date;


@Entity
public class Buy {

    @Id
    @GeneratedValue( strategy= GenerationType.AUTO )

    private int buyId;

    @ManyToOne
    private Sell sell;

    @ManyToOne
    private User user;

    @Temporal(TemporalType.DATE)
    private Date date;

    public int getBuyId() {
        return buyId;
    }

    public void setBuyId(int buyId) {
        this.buyId = buyId;
    }

    public Sell getSell() {
        return sell;
    }

    public void setSell(Sell sell) {
        this.sell = sell;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return (Date) date.clone();
    }

    public void setDate(Date date) {
        this.date = (Date) date.clone();
    }
}
