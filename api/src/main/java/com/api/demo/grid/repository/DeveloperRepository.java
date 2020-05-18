package com.api.demo.grid.repository;

import com.api.demo.grid.models.Developer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface DeveloperRepository extends CrudRepository<Developer, Long> {
    Optional<Developer> findByName(String name);
}
