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
    private TestEntityManager mEntityManager;

    @Autowired
    private GameRepository mRepository;

    @Test
    void whenFindById_getGame(){
        Game example = new Game();
        mEntityManager.persistAndFlush(example);
        assertNotNull(example.getId());
        assertEquals(example.getId(), mRepository.findById(example.getId()).get().getId());
    }

    @Test
    void whenInvalidId_getNull(){
        assertThat(mRepository.findById((long) 0)).isEmpty();
    }

    @Test
    void whenFindByName_getGame(){
        Game example = new Game();
        example.setName("Exemplo");

        mEntityManager.persistAndFlush(example);

        assertEquals(Arrays.asList(example), mRepository.findAllByNameContaining("Exemplo"));
    }

    @Test
    void whenInvalidName_ReceiveEmpty(){
        assertEquals(Arrays.asList(), mRepository.findAllByNameContaining("Not exemplo"));
    }

    @Test
    void whenFindAll_ReceiveAll(){
        Game example = new Game();
        mEntityManager.persistAndFlush(example);

        Game example2 = new Game();
        mEntityManager.persistAndFlush(example2);

        assertEquals(Arrays.asList(example, example2), mRepository.findAll());
    }

    @Test
    void whenFindByGenre_ReceiveAllWithGenre(){
        GameGenre genre = new GameGenre();
        genre.setName("Action");

        mEntityManager.persistAndFlush(genre);

        Set<GameGenre> s = new HashSet<>();
        s.add(genre);

        Game example = new Game();
        example.setGameGenres(s);
        Game game2 = new Game();
        game2.setGameGenres(s);

        mEntityManager.persistAndFlush(example);
        mEntityManager.persistAndFlush(game2);
        assertEquals(Arrays.asList(example, game2), mRepository.findAllByGameGenresContains(genre));
    }

    @Test
    void whenFindByPublisher_ReceiveAllWithGenre(){
        Publisher publisher = new Publisher();
        publisher.setName("Publisher");

        mEntityManager.persistAndFlush(publisher);

        Game example = new Game();
        example.setPublisher(publisher);
        Game game2 = new Game();
        game2.setPublisher(publisher);

        mEntityManager.persistAndFlush(example);
        mEntityManager.persistAndFlush(game2);
        assertEquals(Arrays.asList(example, game2), mRepository.findAllByPublisher(publisher));
    }

    @Test
    void whenFindByDeveloper_ReceiveAllWithGenre(){
        Developer developer = new Developer();
        developer.setName("Action");

        mEntityManager.persistAndFlush(developer);

        Set<Developer> s = new HashSet<>();
        s.add(developer);

        Game example = new Game();
        example.setDevelopers(s);
        Game game2 = new Game();
        game2.setDevelopers(s);

        mEntityManager.persistAndFlush(example);
        mEntityManager.persistAndFlush(game2);
        assertEquals(Arrays.asList(example, game2), mRepository.findAllByDevelopersContaining(developer));
    }

}