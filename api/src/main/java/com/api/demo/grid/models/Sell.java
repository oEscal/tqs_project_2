package com.api.demo.grid.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

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

    @OneToOne(cascade = CascadeType.MERGE, orphanRemoval = true)
    @JoinColumn(name = "game_key_id")
    private GameKey gameKey;

    @OneToOne(cascade = CascadeType.MERGE, orphanRemoval = true)
    @JoinColumn(name = "buy_id")
    private Buy purchased;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    private double price;

    @Temporal(TemporalType.DATE)
    private Date date;

    @Transactional
    public void setUser(User user) {
        //prevent endless loop
        if (sameAsFormer(user)) return ;
        //set new user
        this.user = user;

        //set myself into new owner
        if (user!=null) user.addSell(this);
    }

    public void setGameKey(GameKey gameKey){
        if (sameAsFormerGK(gameKey)) return;
        this.gameKey = gameKey;
        if (gameKey!= null) gameKey.setSell(this);
    }

    private boolean sameAsFormerGK(GameKey newGameKey) {
        return gameKey==null? newGameKey == null : newGameKey.equals(gameKey);
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
