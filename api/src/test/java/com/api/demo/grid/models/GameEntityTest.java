package com.api.demo.grid.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@DataJpaTest
public class GameEntityTest {
    @Autowired
    private TestEntityManager entityManager;

    private Game game;

    @BeforeEach
    public void setUp(){
        this.game = new Game();
    }

    @Test
    public void saveGameName(){
        this.game.setName("Example 1");
        Game savedGame = this.entityManager.persistAndFlush(this.game);
        assertEquals("Example 1", savedGame.getName());
    }

    @Test
    public void saveGameDescription(){
        this.game.setDescription("Lorem ipsum");
        Game savedGame = this.entityManager.persistAndFlush(this.game);
        assertEquals("Lorem ipsum", savedGame.getDescription());
    }

    @Test
    public void saveGameDate(){
        Date date = new Date(System.currentTimeMillis());
        this.game.setReleaseDate(date);
        Game savedGame = this.entityManager.persistAndFlush(this.game);
        assertEquals(date, savedGame.getReleaseDate());
    }

    @Test
    public void savePlatform(){
        this.game.setPlatform("Platform1");
        Game savedGame = this.entityManager.persistAndFlush(this.game);
        assertEquals("Platform1", savedGame.getPlatform());
    }

    @Test
    public void saveCoverUrl(){
        this.game.setCoverUrl("image.png");
        Game savedGame = this.entityManager.persistAndFlush(this.game);
        assertEquals("image.png", savedGame.getCoverUrl());
    }

    @Test
    public void saveGameGenre(){
        GameGenre genre1 = new GameGenre();
        Set<GameGenre> genreSet = new HashSet<>();
        genreSet.add(genre1);
        this.game.setGameGenres(genreSet);
        Game savedGame = this.entityManager.persistAndFlush(this.game);
        assertAll("All this.game genres should be the same",
                () -> assertEquals(1, savedGame.getGameGenres().size()),
                () -> assertEquals(genre1.getId(), savedGame.getGameGenres().get(0).getId())
        );
    }

    @Test
    public void saveDevelopers(){
        Developer dev = new Developer();
        Set<Developer> devSet = new HashSet<>();
        devSet.add(dev);
        this.game.setDevelopers(devSet);
        Game savedGame = this.entityManager.persistAndFlush(this.game);
        assertAll("All game devs should be the same",
                () -> assertEquals(1, savedGame.getDeveloper().size()),
                () -> assertEquals(dev.getId(), savedGame.getDeveloper().get(0).getId())
        );
    }

    @Test
    public void saveGamePublisher(){
        Publisher publisher = new Publisher();
        this.game.setPublisher(publisher);
        Game savedGame = this.entityManager.persistAndFlush(this.game);
        assertEquals(publisher.getId(), savedGame.getPublisher().getId());
    }
}
