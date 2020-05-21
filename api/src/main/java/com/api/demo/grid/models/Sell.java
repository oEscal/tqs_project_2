package com.api.demo.grid.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Entity
@Setter
@Getter
@NoArgsConstructor
public class Sell {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "game_key_id")
    private GameKey gameKey;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "buy_id")
    private Buy purchased;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    private double price;

    @Temporal(TemporalType.DATE)
    private Date date;

    public void setUser(User user) {
        //prevent endless loop
        if (sameAsFormer(user))
            return ;
        //set new user
        this.user = user;

        //set myself into new owner
        if (user!=null)
            user.addSell(this);
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
