package com.api.demo.grid.repository;

import com.api.demo.grid.models.ReviewGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReviewGameRepository extends JpaRepository<ReviewGame, Long> {

}
