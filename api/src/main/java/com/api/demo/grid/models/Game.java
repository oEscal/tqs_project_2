package com.api.demo.grid.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;


@Entity
@Table
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@JsonSerialize
@SuppressFBWarnings
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique=true)
    private String name;

    @Column(columnDefinition = "LONGTEXT")
    private String description;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "game_genre_id")
    @EqualsAndHashCode.Exclude
    private Set<GameGenre> gameGenres = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "publisher_id")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Publisher publisher;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "developer_id")
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private Set<Developer> developers = new HashSet<>();

    @Temporal(TemporalType.DATE)
    private Date releaseDate;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<ReviewGame> reviews = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<GameKey> gameKeys = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Set<User> userWish;

    private String coverUrl;


    public Date getReleaseDate(){ return (releaseDate==null)? null:(Date) releaseDate.clone(); }

    public void setReleaseDate(Date date) {
        if (date != null) releaseDate = (Date) date.clone();
    }

    public void addGameKey(GameKey gameKey) {
        if (gameKeys == null) gameKeys = new HashSet<>();
        else if (gameKeys.contains(gameKey)) return;
        gameKeys.add(gameKey);
        gameKey.setGame(this);
    }

    public Sell getBestSell(){
        if (gameKeys == null || gameKeys.isEmpty()) return null;
        Sell bestSell = new Sell();
        boolean foundPrice = false;
        for (GameKey gameKey : gameKeys){
            if (gameKey.getSell() != null){
                if (!foundPrice || bestSell.getPrice() > gameKey.getSell().getPrice()) {
                    bestSell = gameKey.getSell();
                }
                foundPrice = true;
            }
        }
        return (foundPrice)? bestSell:null;
    }

    public List<String> getPlatforms(){
        if (gameKeys == null || gameKeys.isEmpty()) return new ArrayList<>();
        List<String> gamePlatforms = new ArrayList<>();
        String platform;
        for (GameKey gameKey : gameKeys){
            platform = gameKey.getPlatform();
            if (!gamePlatforms.contains(platform)) gamePlatforms.add(platform);
        }
        return gamePlatforms;
    }

    public String getPublisherName() { return (this.publisher == null)? "":this.publisher.getName(); }

    public String[] getDeveloperNames() {
        if (developers == null || developers.size()==0) return new String[0];
        String[] devNames = new String[developers.size()];
        int count = 0;
        for (Developer developer : developers){
            devNames[count] = developer.getName();
            count++;
        }
        return devNames;
    }

    public void setPublisher(Publisher publisher){
        if (Objects.equals(this.publisher, publisher)) return;
        this.publisher = publisher;
        publisher.addGame(this);
    }

    public void addGenre(GameGenre gameGenre) {
        if (this.gameGenres.contains(gameGenre)) return;
        this.gameGenres.add(gameGenre);
        gameGenre.addGame(this);
    }

    public void addDeveloper(Developer developer) {
        if (this.developers.contains(developer)) return;
        this.developers.add(developer);
        developer.addGame(this);
    }
}
