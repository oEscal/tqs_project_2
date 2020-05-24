package com.api.demo.grid.pojos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeveloperPOJO {
    private String name;

    public DeveloperPOJO(String name) {
        this.name = name;
    }
}
