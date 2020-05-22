package com.api.demo.grid.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;


@Entity
public class Buy {

    @Id
    @GeneratedValue( strategy= GenerationType.AUTO )
    private long id;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "sell_id")
    private Sell sell;

    @OneToOne
    private Auction auction;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @Temporal(TemporalType.DATE)
    private Date date;


    public long getId() {
        return id;
    }

    public void setId(long buyId) {
        this.id = buyId;
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
        //prevent endless loop
        if (sameAsFormer(user)) return ;
        //set new user
        this.user = user;
    }

    private boolean sameAsFormer(User newUser) {
        return user==null? newUser == null : newUser.equals(user);
    }

    public Date getDate() {
        return (Date) date.clone();
    }

    public void setDate(Date date) {
        this.date = (Date) date.clone();
    }

    public long getUserId() {
        if (user == null) return -1L;
        return this.user.getId();
    }
}
