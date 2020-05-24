package com.api.demo.grid.repository;

import com.api.demo.grid.models.Developer;
import com.api.demo.grid.models.Game;
import com.api.demo.grid.models.GameGenre;
import com.api.demo.grid.models.Publisher;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface GameRepository extends JpaRepository<Game, Long>  {
    Page<Game> findAll(Pageable pageable);
    List<Game> findAllByGameGenresContains(GameGenre gameGenre);
    List<Game> findAllByPublisher(Publisher publisher);
    List<Game> findAllByDevelopersContaining(Developer developer);
    Optional<Game> findById(Long id);
    List<Game> findAllByNameContaining(String name);
}
