package com.api.demo.grid.pojos;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class GameGenrePOJO {
    private String name;
    private String description;

    public GameGenrePOJO(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
