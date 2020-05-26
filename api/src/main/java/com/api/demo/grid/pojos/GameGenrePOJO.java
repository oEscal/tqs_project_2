package com.api.demo.grid.pojos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class GameGenrePOJO {
    private String name;
    private String description;

    public GameGenrePOJO(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
