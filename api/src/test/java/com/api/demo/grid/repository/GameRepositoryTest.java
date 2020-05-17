package com.api.demo.grid.repository;

import com.api.demo.grid.models.Game;
import com.api.demo.grid.models.Developer;
import com.api.demo.grid.models.GameGenre;
import com.api.demo.grid.models.GameKey;
import com.api.demo.grid.models.Publisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class GameRepositoryTest {

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
        assertEquals(1, repository.findAllById(game.getId()).size());
    }

    @Test
    public void whenFindByName_getGame(){
        game.setName("Exemplo");
        Game savedGame = repository.save(game);
        assertEquals("Exemplo", savedGame.getName());
        assertEquals(Arrays.asList(game), repository.findAllByName("Exemplo"));
    }

    @Test
    public void whenInvalidName_ReceiveEmpty(){
        assertEquals(Arrays.asList(), repository.findAllByName("Not exemplo"));
    }

    @Test
    public void whenFindAll_ReceiveAll(){
        repository.save(game);
        assertEquals(Arrays.asList(game), repository.findAll());
    }

    @Test
    public void whenGivenProperPlatform_ReceiveAll(){
        game.setPlatform("PC");
        assertEquals(Arrays.asList(game), repository.findAllByPlatform("PC"));
    }

    /*
    @Test
    public void whenFindByGenre_ReceiveAllWithGenre(){
        GameGenre genre = new GameGenre();
        genre.setId(3);
        genre.setName("Action");

        Set<GameGenre> s = new HashSet<>();
        s.add(genre);

        game.setGameGenres(s);
        Game game2 = new Game();
        game2.setGameGenres(s);

        repository.save(game);
        repository.save(game2);
        assertEquals(Arrays.asList(game, game2), repository.findAllByGameGenres(new Long(genre.getId())));
    }*/

}