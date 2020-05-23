package com.api.demo.grid.pojos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SearchGamePOJO {
    String name;
    String[] genres;
    String[] plataforms;
    String[] publishers;
    String[] developers;
    double startPrice;
    double endPrice;
}
