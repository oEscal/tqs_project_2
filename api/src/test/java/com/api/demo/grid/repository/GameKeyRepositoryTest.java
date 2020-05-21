package com.api.demo.grid.repository;

import com.api.demo.grid.models.GameKey;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class GameKeyRepositoryTest {
    @Autowired
    private TestEntityManager pEntityManager;

    @Autowired
    private GameKeyRepository pRepository;

    @Test
    void whenFindById_getGameKey(){
        GameKey key = new GameKey();
        key.setId(1L);

        pEntityManager.persistAndFlush(key);

        assertEquals(key, pRepository.findById(1L).get());
    }

    @Test
    void whenInvalidId_ReceiveEmpty(){
        assertEquals(Optional.empty(), pRepository.findById(2L));
    }
}