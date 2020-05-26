package com.api.demo.grid.pojos;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Objects;

@AllArgsConstructor
@Setter
@Getter
public class SearchGamePOJO {
    String name;
    String[] genres;
    String[] plataforms;
    double startPrice;
    double endPrice;
    int page;

    public SearchGamePOJO(){
        name = "";
        genres = new String[0];
        plataforms = new String[0];
        startPrice = 0;
        endPrice = 0;
        page = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchGamePOJO that = (SearchGamePOJO) o;
        return Double.compare(that.startPrice, startPrice) == 0 &&
                Double.compare(that.endPrice, endPrice) == 0 &&
                Objects.equals(name, that.name) &&
                Arrays.equals(genres, that.genres) &&
                Arrays.equals(plataforms, that.plataforms);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, startPrice, endPrice);
        result = 31 * result + Arrays.hashCode(genres);
        result = 31 * result + Arrays.hashCode(plataforms);
        return result;
    }
}
