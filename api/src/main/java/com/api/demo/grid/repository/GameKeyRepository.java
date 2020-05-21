package com.api.demo.grid.repository;

import com.api.demo.grid.models.Game;
import com.api.demo.grid.models.GameKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameKeyRepository extends JpaRepository<GameKey, Long> {
    Optional<GameKey> findByKey(String key);
    Optional<GameKey> findById(long id);
}
