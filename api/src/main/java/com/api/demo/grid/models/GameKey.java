package com.api.demo.grid.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
@Setter
public class GameKey {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @Column(unique = true)
    private String key;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Game game;

    @OneToOne
    private Sell sell;

    @OneToOne
    private Auction auction;

    private String retailer;

    private String platform;

<<<<<<< HEAD
=======
    public int getId() { return id; }

    public String getKey() {
        return key;
    }

    public Game getGame() {
        return game;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setKey(String gameKey) {
        this.key = gameKey;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getRetailer() { return this.retailer; }

    public void setRetailer(String retailer) { this.retailer = retailer; }

    public Sell getSell() { return sell; }

    public Auction getAuction() { return auction; }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
>>>>>>> e443ceeb830555475777640e22a4185c538c0cf2
}
