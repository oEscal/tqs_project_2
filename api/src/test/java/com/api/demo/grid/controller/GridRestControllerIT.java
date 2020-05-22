package com.api.demo.grid.controller;

import com.api.demo.DemoApplication;
import com.api.demo.grid.models.Developer;
import com.api.demo.grid.models.Game;
import com.api.demo.grid.models.GameGenre;
import com.api.demo.grid.models.Publisher;
import com.api.demo.grid.repository.DeveloperRepository;
import com.api.demo.grid.repository.GameGenreRepository;
import com.api.demo.grid.repository.GameRepository;
import com.api.demo.grid.repository.PublisherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashSet;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DemoApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class GridRestControllerIT {
    @Autowired
    private MockMvc mMockMvc;

    @Autowired
    private DeveloperRepository mDeveloperRepository;

    @Autowired
    private PublisherRepository mPublisherRepository;

    @Autowired
    private GameGenreRepository mGameGenreRepository;

    @Autowired
    private GameRepository mGameRepository;

    private Game mGame;

    @BeforeEach
    void setUp(){
        mGame = new Game();
        mGame.setName("DS");
        mGame.setDescription("");
        mGame.setCoverUrl("");
        mGameRepository.deleteAll();
        mDeveloperRepository.deleteAll();
        mPublisherRepository.deleteAll();
        mGameGenreRepository.deleteAll();
    }

    @Test
    void whenRequestAll_ReturnAll() throws Exception {
        mGameRepository.save(mGame);

        mMockMvc.perform(get("/grid/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("DS")));

    }

    @Test
    void whenRequestGameInfo_ReturnGame() throws Exception {
        mGameRepository.save(mGame);

        mMockMvc.perform(
                get("/grid/game")
                        .param("id", ""+ mGame.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("DS")));

    }

    @Test
    void whenRequestGenre_ReturnValidGames() throws Exception {
        GameGenre gameGenre = new GameGenre();
        gameGenre.setName("genre");
        mGameGenreRepository.save(gameGenre);
        mGame.setGameGenres(new HashSet<>(Arrays.asList(gameGenre)));
        mGameRepository.save(mGame);

        mMockMvc.perform(get("/grid/genre")
                .param("genre", "genre")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("DS")));

    }

    @Test
    void whenRequestName_ReturnValidGames() throws Exception {
        mGameRepository.save(mGame);

        mMockMvc.perform(get("/grid/name")
                .param("name", "D")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("DS")));

    }

    @Test
    void whenRequestDev_ReturnValidGames() throws Exception {
        Developer developer = new Developer();
        developer.setName("Dev");
        mDeveloperRepository.save(developer);
        mGame.setDevelopers(new HashSet<>(Arrays.asList(developer)));
        mGameRepository.save(mGame);

        mMockMvc.perform(get("/grid/developer")
                .param("dev", "Dev")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("DS")));

    }

    @Test
    void whenRequestPub_ReturnValidGames() throws Exception {
        Publisher publisher = new Publisher();
        publisher.setName("Pub");
        mPublisherRepository.save(publisher);
        mGame.setPublisher(publisher);
        mGameRepository.save(mGame);

        mMockMvc.perform(get("/grid/publisher")
                .param("pub", "Pub")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("DS")));

    }
    @Test
    void whenInvalidGameId_Return404Exception() throws Exception {

        mMockMvc.perform(
                get("/grid/game")
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("No Game found with Id 1"));

    }

    @Test
    void whenInvalidGameGenre_Return404Exception() throws Exception {

        mMockMvc.perform(
                get("/grid/genre")
                        .param("genre", "no")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("No Game found with Id no"));

    }

    @Test
    void whenRequestDev_Return404Exception() throws Exception {

        mMockMvc.perform(get("/grid/developer")
                .param("dev", "dev")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("No Game found with Id dev"));

    }

    @Test
    void whenRequestPub_Return404Exception() throws Exception {

        mMockMvc.perform(get("/grid/publisher")
                .param("pub", "pub")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("No Game found with Id pub"));

    }
}
