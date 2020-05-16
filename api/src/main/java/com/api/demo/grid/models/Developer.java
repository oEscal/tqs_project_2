package com.api.demo.grid.models;


import javax.persistence.*;
import java.util.Set;


@Entity
public class Developer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(unique=true)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Set<Game> games;


    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Game> getGames() { return games; }
}
