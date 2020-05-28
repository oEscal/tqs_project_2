package com.api.demo.grid.repository;

import com.api.demo.grid.models.Game;
import com.api.demo.grid.models.GameKey;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    void whenFindByKey_GetKey(){
        GameKey gameKey = new GameKey();
        gameKey.setRKey("k");
        pEntityManager.persistAndFlush(gameKey);

        assertEquals("k", pRepository.findByrKey("k").get().getRKey());
    }

    @Test
    void whenInvalidKey_ReceiveEmpty(){
        GameKey gameKey = pEntityManager.persistAndFlush(new GameKey());

        assertEquals(Optional.empty(), pRepository.findByrKey("key"));
    }

    @Test
    void whenFindByGame_GetKey(){
        Game game = new Game();
        pEntityManager.persistAndFlush(game);
        game.setName("game");
        GameKey gameKey = new GameKey();
        gameKey.setGame(game);
        pEntityManager.persistAndFlush(gameKey);

        assertEquals(1, pRepository.findAllByGame(game).size());
        assertEquals("game", pRepository.findAllByGame(game).get(0).getGame().getName());
    }

    @Test
    void whenInvalidGame_ReceiveEmpty(){
        Game game = new Game();
        pEntityManager.persistAndFlush(game);
        pEntityManager.persistAndFlush(new GameKey());

        assertTrue(pRepository.findAllByGame(game).isEmpty());
    }
}