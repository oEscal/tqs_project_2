package com.api.demo.grid.repository;


import com.api.demo.grid.models.ReviewUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewUserRepository extends JpaRepository<ReviewUser, Long> {
}
