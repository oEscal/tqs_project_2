package com.api.demo.models;

import javax.persistence.*;


@Entity
public class GameKey {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @Column(unique = true)
    private String key;

    @ManyToOne
    private Game game;

    public int getId() {
        return id;
    }

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
}
