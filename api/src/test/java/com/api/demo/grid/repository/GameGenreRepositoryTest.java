package com.api.demo.grid.repository;

import com.api.demo.grid.models.GameGenre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class GameGenreRepositoryTest {
    @Autowired
    private TestEntityManager mEntityManager;

    @Autowired
    private GameGenreRepository mRepository;

    @Test
    void whenFindByName_getDeveloper(){
        GameGenre example = new GameGenre();
        example.setName("Exemplo");

        mEntityManager.persistAndFlush(example);

        assertEquals(example, mRepository.findByName("Exemplo").get());
    }

    @Test
    void whenInvalidName_ReceiveEmpty(){
        assertEquals(Optional.empty(), mRepository.findByName("Not exemplo"));
    }
}