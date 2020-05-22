package com.api.demo.grid.repository;

import com.api.demo.grid.models.Publisher;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface PublisherRepository extends CrudRepository<Publisher, Long> {
    Optional<Publisher> findByName(String name);
}
