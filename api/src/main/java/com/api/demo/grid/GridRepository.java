package com.api.demo.grid;

import com.api.demo.grid.models.Game;
import com.api.demo.grid.models.GameGenre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface GridRepository extends CrudRepository<Game, Long>{
    List<Game> findAll();
    List<Game> findAllByGameGenres(Set<GameGenre> genres);
    List<Game> findAllByPriceRange(double initialPrice, double finalPrice);
    List<Game> findAllByPlatform(String platform);
    List<Game> findAllById(int id);
}
