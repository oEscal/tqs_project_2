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

    @ManyToMany
    private Set<GameGenre> gameGenres;

    @ManyToOne
    private Publisher publisher;

    @ManyToMany
    private Set<Developer> developers;

    @Temporal(TemporalType.DATE)
    private Date releaseDate;

    @OneToMany
    private Set<Report> reports;

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

    public Set<Report> getReports() {
        return new HashSet<>(reports);
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
