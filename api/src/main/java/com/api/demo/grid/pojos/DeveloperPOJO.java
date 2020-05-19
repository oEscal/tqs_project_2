package com.api.demo.grid.pojos;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class DeveloperPOJO {
    private String name;

    public DeveloperPOJO(String name) {
        this.name = name;
    }
}
