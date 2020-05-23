package com.api.demo.grid.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@JsonSerialize
@SuppressFBWarnings
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
    @EqualsAndHashCode.Exclude
    private User user;

    private double price;

    @EqualsAndHashCode.Exclude
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
