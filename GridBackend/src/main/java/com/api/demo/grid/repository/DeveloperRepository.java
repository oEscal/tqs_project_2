package com.api.demo.grid.repository;

import com.api.demo.grid.models.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface DeveloperRepository extends JpaRepository<Developer, Long> {
    Optional<Developer> findByName(String name);
}
