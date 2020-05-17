package com.api.demo.grid.repository;

import com.api.demo.grid.models.Game;
import com.api.demo.grid.models.GameGenre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface GameRepository extends CrudRepository<Game, Integer>{
    List<Game> findAll();
    List<Game> findAllByGameGenres(Set<GameGenre> genres);
    List<Game> findAllByPlatform(String platform);
    List<Game> findAllById(int id);

    List<Game> findAllByName(String name);
}
