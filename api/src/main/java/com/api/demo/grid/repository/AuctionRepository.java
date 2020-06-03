package com.api.demo.grid.repository;

import com.api.demo.grid.models.Auction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional
public interface AuctionRepository extends JpaRepository<Auction, Long> {
    Auction findByGameKey_RealKey(String rKey);

    @Query("select a from Auction a where a.endDate >= CURRENT_DATE and a.gameKey.game.id = :gameId")
    List<Auction> findAllByGameWithEndDateAfterCurrent(@Param("gameId") long gameId);
}
