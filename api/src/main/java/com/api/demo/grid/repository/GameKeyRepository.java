package com.api.demo.grid.repository;

import com.api.demo.grid.models.Game;
import com.api.demo.grid.models.GameKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface GameKeyRepository extends JpaRepository<GameKey, Long> {
    Optional<GameKey> findByrKey(String key);
    Optional<GameKey> findById(long id);
    List<GameKey> findAllByGame(Game game);
}
