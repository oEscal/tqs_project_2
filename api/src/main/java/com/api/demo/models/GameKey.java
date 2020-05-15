package com.api.demo.models;

import javax.persistence.*;


@Entity
public class GameKey {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @Column(unique = true)
    private String gameKey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Game game;

    private String retailer;


    public int getId() { return id; }

    public String getGameKey() {
        return gameKey;
    }

    public Game getGame() {
        return game;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setGameKey(String gameKey) {
        this.gameKey = gameKey;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getRetailer() { return this.retailer; }

    public void setRetailer(String retailer) { this.retailer = retailer; }
}
