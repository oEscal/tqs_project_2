package com.api.demo.grid.repository;

import com.api.demo.grid.models.Auction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface AuctionRepository extends JpaRepository<Auction, Long> {
    Auction findByGameKey_rKey(String rKey);
}
