package com.api.demo.grid.repository;

import com.api.demo.grid.models.Developer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class DeveloperRepositoryTest {

    @Autowired
    private TestEntityManager mEntityManager;

    @Autowired
    private DeveloperRepository mRepository;

    @Test
    void whenFindByName_getDeveloper(){
        Developer example = new Developer();
        example.setName("Exemplo");

        mEntityManager.persistAndFlush(example);

        assertEquals(example, mRepository.findByName("Exemplo").get());
    }

    @Test
    void whenInvalidName_ReceiveEmpty(){
        assertEquals(Optional.empty(), mRepository.findByName("Not exemplo"));
    }
}