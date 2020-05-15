package com.api.demo.models;


import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


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

    public Set<GameGenre> getGameGenres() {
        return new HashSet<>(gameGenres);
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public Set<Developer> getDeveloper() {
        return new HashSet<>(developers);
    }
    
    public void setId(Integer id) {
        this.id = id;
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

}
