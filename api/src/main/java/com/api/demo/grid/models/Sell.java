package com.api.demo.grid.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.Objects;


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

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "game_key_id")
    private GameKey gameKey;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "buy_id")
    @EqualsAndHashCode.Exclude
    private Buy purchased;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;

    private double price;

    @EqualsAndHashCode.Exclude
    @Column(nullable = false, columnDefinition = "DATETIME DEFAULT NOW()")
    @Temporal(TemporalType.DATE)
    private Date date = new Date();


    @Transactional
    public void setUser(User user) {
        //prevent endless loop
        if (sameAsFormer(user)) return ;
        //set new user
        this.user = user;

        //set myself into new owner
        if (user!=null) user.addSell(this);
    }

    private boolean sameAsFormer(User newUser) {
        return user==null? newUser == null : newUser.equals(user);
    }

    public void setGameKey(GameKey gameKey) {
        //prevent endless loop
        if (sameAsFormerGK(gameKey)) return ;
        //set new user
        this.gameKey = gameKey;

        //set myself into new owner
        if (gameKey!=null) gameKey.setSell(this);
    }

    private boolean sameAsFormerGK(GameKey newGameKey) {
        return Objects.equals(newGameKey, gameKey);
    }

    public void setPurchased(Buy purchased) {
        if (Objects.equals(purchased, this.purchased)) return;
        this.purchased = purchased;
        purchased.setSell(this);
    }

    public Date getDate() {
        if (date == null) return null;
        return (Date) date.clone();
    }

    public void setDate(Date date) {
        if (date != null) this.date = (Date) date.clone();
    }

    public long getUserId() {
        if (user == null) return -1L;
        return this.user.getId();
    }
}
