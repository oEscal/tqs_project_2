package com.api.demo.grid.repository;

import com.api.demo.grid.models.GameKey;
import com.api.demo.grid.models.Sell;
import com.api.demo.grid.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class SellRepositoryTest {
    @Autowired
    private TestEntityManager pEntityManager;

    @Autowired
    private SellRepository pRepository;

    @Test
    void whenFindById_getSell(){
        User user = new User();
        pEntityManager.persistAndFlush(user);

        Sell sell = new Sell();
        sell.setUser(user);

        pEntityManager.persistAndFlush(sell);

        assertEquals(sell, pRepository.findById(2L).get());
    }

    @Test
    void whenInvalidId_ReceiveEmpty(){
        assertEquals(Optional.empty(), pRepository.findById(2L));
    }

    @Test
    void whenFindByKey_GetSell(){
        GameKey gameKey = new GameKey();
        pEntityManager.persistAndFlush(gameKey);

        Sell sell = new Sell();
        sell.setGameKey(gameKey);

        pEntityManager.persistAndFlush(sell);

        assertEquals(sell, pRepository.findByGameKey(gameKey).get());
    }

    @Test
    void whenInvalidKey_ReceiveEmpty(){
        GameKey gameKey = pEntityManager.persistAndFlush(new GameKey());

        assertEquals(Optional.empty(), pRepository.findByGameKey(gameKey));
    }
}