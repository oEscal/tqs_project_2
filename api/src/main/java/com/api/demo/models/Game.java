package com.api.demo.models;


import javax.persistence.*;
import java.sql.Date;
import java.util.Set;


@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(unique=true)
    private String name;

    @ManyToMany
    private Set<GameGenre> gameGenre;

    @ManyToOne
    private Publisher publisher;

    @ManyToMany
    private Set<GameGenre> developer;

    private String description;

    @Temporal(TemporalType.DATE)
    private Date releaseDate;

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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = (Date) releaseDate.clone();
    }
}