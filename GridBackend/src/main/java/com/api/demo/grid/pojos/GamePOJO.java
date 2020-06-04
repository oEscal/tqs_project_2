package com.api.demo.grid.pojos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class GamePOJO {
    private String name;
    private String description;
    private Set<String> gameGenres = new HashSet<>();
    private String publisher;
    private Set<String> developers = new HashSet<>();
    private Date releaseDate = new Date();
    private String coverUrl;

    public GamePOJO(String name, String description, Set<String> gameGenres, String publisher,
                    Set<String> developers, Date releaseDate, String coverUrl) {
        this.name = name;
        this.description = description;
        this.gameGenres = gameGenres;
        this.publisher = publisher;
        this.developers = developers;
        if (releaseDate != null) this.releaseDate = (Date) releaseDate.clone();
        this.coverUrl = coverUrl;
    }

    public Date getReleaseDate(){
        return (Date) releaseDate.clone();
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = (Date) releaseDate.clone();
    }
}
