package com.api.demo.grid.repository;

import com.api.demo.grid.models.GameGenre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface GameGenreRepository extends CrudRepository<GameGenre, Long> {
    Optional<GameGenre> findByName(String name);
}
