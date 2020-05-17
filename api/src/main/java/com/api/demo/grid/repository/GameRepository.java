package com.api.demo.grid.repository;

import com.api.demo.grid.models.Game;
import com.api.demo.grid.models.GameGenre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Repository
@Transactional
public interface GameRepository extends CrudRepository<Game, Long>{
    List<Game> findAll();
    List<Game> findAllByGameGenres(Long gameGenre);
    List<Game> findAllByPlatform(String platform);
    List<Game> findAllById(Long id);
    List<Game> findAllByName(String name);
}
