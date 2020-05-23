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

    private String description;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "game_genre_id")
    @EqualsAndHashCode.Exclude
    private Set<GameGenre> gameGenres = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Publisher publisher;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "developer_id")
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private Set<Developer> developers;

    @Temporal(TemporalType.DATE)
    private Date releaseDate;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<ReviewGame> reviews;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
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

    public double getLowestPrice(){
        if (gameKeys.isEmpty()) return -1;
        double lowestPrice = 0;
        boolean foundPrice = false;
        for (GameKey gameKey : gameKeys){
            if (gameKey.getSell() != null){
                if (!foundPrice || lowestPrice > gameKey.getSell().getPrice()) {
                    lowestPrice = gameKey.getSell().getPrice();
                }
                foundPrice = true;
            }
        }
        return (foundPrice)? lowestPrice:-1;
    }

    public String[] getPlatforms(){
        if (gameKeys.isEmpty()) return new String[0];
        ArrayList<String> gamePlatforms = new ArrayList<>();
        String platform;
        for (GameKey gameKey : gameKeys){
            platform = gameKey.getPlatform();
            if (!gamePlatforms.contains(platform)) gamePlatforms.add(platform);
        }
        return gamePlatforms.toArray(new String[gamePlatforms.size()]);
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
}
