package com.api.demo.grid.pojos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PublisherPOJO {
    private String name;
    private String description;

    public PublisherPOJO(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
