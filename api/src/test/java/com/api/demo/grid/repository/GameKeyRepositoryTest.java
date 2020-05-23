package com.api.demo.grid.repository;

import com.api.demo.grid.models.GameKey;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class GameKeyRepositoryTest {
    @Autowired
    private TestEntityManager pEntityManager;

    @Autowired
    private GameKeyRepository pRepository;

    @Test
    void whenFindById_getGameKey(){
        GameKey key = new GameKey();

        pEntityManager.persistAndFlush(key);

        assertEquals(key, pRepository.findById(key.getId()).get());
    }

    @Test
    void whenInvalidId_ReceiveEmpty(){
        assertEquals(Optional.empty(), pRepository.findById(2L));
    }

    @Test
    void whenFindByKey_GetSell(){
        GameKey gameKey = new GameKey();
        gameKey.setKey("k");
        pEntityManager.persistAndFlush(gameKey);

        assertEquals("k", pRepository.findByKey("k").get().getKey());
    }

    @Test
    void whenInvalidKey_ReceiveEmpty(){
        GameKey gameKey = pEntityManager.persistAndFlush(new GameKey());

        assertEquals(Optional.empty(), pRepository.findByKey("key"));
    }
}