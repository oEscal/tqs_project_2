package com.api.demo.grid.pojos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
public class GamePOJO {
    private String name;
    private String description;
    private Set<GameGenrePOJO> gameGenres;
    private PublisherPOJO publisher;
    private Set<DeveloperPOJO> developers;
    private Date releaseDate;
    private String coverUrl;
}
