package com.api.demo.grid.repository;

import com.api.demo.grid.models.Game;
import com.api.demo.grid.models.Developer;
import com.api.demo.grid.models.GameGenre;
import com.api.demo.grid.models.Publisher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class GameRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private GameRepository repository;

    @Test
    public void whenFindById_getGame(){
        Game example = new Game();
        entityManager.persistAndFlush(example);
        assertNotNull(example.getId());
        assertEquals(example.getId(), repository.findById(example.getId()).get().getId());
    }

    @Test
    public void whenInvalidId_getNull(){
        assertThat(repository.findById((long) 0)).isEmpty();
    }

    @Test
    public void whenFindByName_getGame(){
        Game example = new Game();
        example.setName("Exemplo");

        entityManager.persistAndFlush(example);

        assertEquals(Arrays.asList(example), repository.findAllByNameContaining("Exemplo"));
    }

    @Test
    public void whenInvalidName_ReceiveEmpty(){
        assertEquals(Arrays.asList(), repository.findAllByNameContaining("Not exemplo"));
    }

    @Test
    public void whenFindAll_ReceiveAll(){
        Game example = new Game();
        entityManager.persistAndFlush(example);

        Game example2 = new Game();
        entityManager.persistAndFlush(example2);

        assertEquals(Arrays.asList(example, example2), repository.findAll());
    }

    @Test
    public void whenFindByGenre_ReceiveAllWithGenre(){
        GameGenre genre = new GameGenre();
        genre.setName("Action");

        entityManager.persistAndFlush(genre);

        Set<GameGenre> s = new HashSet<>();
        s.add(genre);

        Game example = new Game();
        example.setGameGenres(s);
        Game game2 = new Game();
        game2.setGameGenres(s);

        entityManager.persistAndFlush(example);
        entityManager.persistAndFlush(game2);
        assertEquals(Arrays.asList(example, game2), repository.findAllByGameGenresContains(genre));
    }

    @Test
    public void whenFindByPublisher_ReceiveAllWithGenre(){
        Publisher publisher = new Publisher();
        publisher.setName("Publisher");

        entityManager.persistAndFlush(publisher);

        Game example = new Game();
        example.setPublisher(publisher);
        Game game2 = new Game();
        game2.setPublisher(publisher);

        entityManager.persistAndFlush(example);
        entityManager.persistAndFlush(game2);
        assertEquals(Arrays.asList(example, game2), repository.findAllByPublisher(publisher));
    }

    @Test
    public void whenFindByDeveloper_ReceiveAllWithGenre(){
        Developer developer = new Developer();
        developer.setName("Action");

        entityManager.persistAndFlush(developer);

        Set<Developer> s = new HashSet<>();
        s.add(developer);

        Game example = new Game();
        example.setDevelopers(s);
        Game game2 = new Game();
        game2.setDevelopers(s);

        entityManager.persistAndFlush(example);
        entityManager.persistAndFlush(game2);
        assertEquals(Arrays.asList(example, game2), repository.findAllByDevelopersContaining(developer));
    }

}