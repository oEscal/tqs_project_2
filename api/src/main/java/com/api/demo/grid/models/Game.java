package com.api.demo.grid.models;


import javax.persistence.*;
import java.util.*;


@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

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

    @OneToMany
    private Set<ReviewGame> reviews;

    @OneToMany
    private Set<GameKey> gameKeys;

    @ManyToMany
    private Set<User> userWish;

    private String coverUrl;

    private String platform;

    public Game(){ }

    public Game(String name, String description, Set<GameGenre> gameGenres, Publisher publisher, Date releaseDate,
                String coverUrl, String platform) {
        this.name = name;
        this.description = description;
        this.gameGenres = gameGenres;
        this.publisher = publisher;
        this.releaseDate = releaseDate;
        this.coverUrl = coverUrl;
        this.platform = platform;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getReleaseDate() {
        return (Date) releaseDate.clone();
    }

    public List<GameGenre> getGameGenres() {
        return new ArrayList<>(gameGenres);
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public List<Developer> getDeveloper() {
        return new ArrayList<>(developers);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = (Date) releaseDate.clone();
    }

    public String getCoverUrl() { return coverUrl; }

    public void setCoverUrl(String cover) { this.coverUrl = cover; }

    public String getPlatform() { return this.platform; }

    public void setPlatform(String platform) { this.platform = platform; }

    public void setGameGenres(Set<GameGenre> gameGenres) { this.gameGenres = gameGenres; }

    public void setDevelopers(Set<Developer> developers) { this.developers = developers; }
}
