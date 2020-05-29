package com.api.demo.grid.repository;

import com.api.demo.grid.models.GameKey;
import com.api.demo.grid.models.Sell;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface SellRepository extends JpaRepository<Sell, Long> {
    Optional<Sell> findById(long id);
    Optional<Sell> findByGameKey(GameKey key);
    @Query("Select s from Sell s where s.gameKey.game.id = ?1 and s.purchased IS NULL")
    Page<Sell> findAllByGames(Long game, Pageable pageable);

    Sell findByGameKey_RealKey(String rKey);
}
