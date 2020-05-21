package com.api.demo.grid.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique=true)
    private String name;

    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_genre_id")
    private Set<GameGenre> gameGenres;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "developer_id")
    private Set<Developer> developers;

    @Temporal(TemporalType.DATE)
    private Date releaseDate;

    @OneToMany(orphanRemoval = true)
    private Set<ReviewGame> reviews;

    @OneToMany(orphanRemoval = true)
    private Set<GameKey> gameKeys;

    @ManyToMany
    private Set<User> userWish;

    private String coverUrl;

    public Date getReleaseDate(){
        return (releaseDate == null)? null:(Date) releaseDate.clone();
    }

    public void setReleaseDate(Date releaseDate) {
        if (releaseDate != null) this.releaseDate = (Date) releaseDate.clone();
        else this.releaseDate = null;
    }

    public void addGameKey(GameKey gameKey) {
        if (gameKeys == null) gameKeys = new HashSet<>();
        else if (gameKeys.contains(gameKey)) return;
        gameKeys.add(gameKey);
        gameKey.setGame(this);
    }
}
