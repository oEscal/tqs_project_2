package com.api.demo.grid.repository;

import com.api.demo.grid.models.Game;
import com.api.demo.grid.models.Developer;
import com.api.demo.grid.models.GameGenre;
import com.api.demo.grid.models.GameKey;
import com.api.demo.grid.models.Publisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class GameRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private GameRepository repository;

    private Game game;

    @BeforeEach
    public void setUp() {
        game = new Game();
    }

    @Test
    public void saveGameAndFindById(){
        repository.save(game);
        assertNotNull(game.getId());
    }

}