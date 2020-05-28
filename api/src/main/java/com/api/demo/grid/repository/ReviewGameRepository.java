package com.api.demo.grid.repository;

import com.api.demo.grid.models.ReviewGame;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReviewGameRepository extends JpaRepository<ReviewGame, Long> {
}
