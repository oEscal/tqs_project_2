package com.api.demo.grid.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.ManyToMany;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import java.util.Date;
import java.util.Set;


@Entity
@Table
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@JsonSerialize
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique=true)
    private String name;

    private String description;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "game_genre_id")
    private Set<GameGenre> gameGenres;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id")
    @JsonIgnore
    private Publisher publisher;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "developer_id")
    private Set<Developer> developers;

    @Temporal(TemporalType.DATE)
    private Date releaseDate;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<ReviewGame> reviews;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<GameKey> gameKeys;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<User> userWish;

    private String coverUrl;

    public Date getReleaseDate(){ return (releaseDate==null)? null:(Date) releaseDate.clone(); }

    public void setReleaseDate(Date date) {
        if (date != null) releaseDate = (Date) date.clone();
    }
}
