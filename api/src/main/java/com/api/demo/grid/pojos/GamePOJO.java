package com.api.demo.grid.pojos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GamePOJO {
    private String name;
    private String description;
    private Set<String> gameGenres;
    private String publisher;
    private Set<String> developers;
    private Date releaseDate;
    private String coverUrl;
}
