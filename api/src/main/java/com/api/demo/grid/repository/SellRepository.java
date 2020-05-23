package com.api.demo.grid.repository;

import com.api.demo.grid.models.GameKey;
import com.api.demo.grid.models.Sell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellRepository extends JpaRepository<Sell, Long> {
    Optional<Sell> findById(long id);
    Optional<Sell> findByGameKey(GameKey key);
}
