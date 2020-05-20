package com.api.demo.grid.pojos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
public class GamePOJO {
    private String name;
    private String description;
    private Set<String> gameGenres;
    private String publisher;
    private Set<String> developers;
    private Date releaseDate = new Date();
    private String coverUrl;

    public Date getReleaseDate(){
        return (Date) releaseDate.clone();
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = (Date) releaseDate.clone();
    }
}
