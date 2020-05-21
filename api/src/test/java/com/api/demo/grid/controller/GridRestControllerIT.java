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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashSet;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DemoApplication.class)
class GridRestControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DeveloperRepository developerRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private GameGenreRepository gameGenreRepository;

    @Autowired
    private GameRepository gameRepository;

    private Game game;

    @BeforeEach
    void setUp(){
        game = new Game();
        game.setName("DS");
        game.setDescription("");
        game.setCoverUrl("");
        gameRepository.deleteAll();
        developerRepository.deleteAll();
        publisherRepository.deleteAll();
        gameGenreRepository.deleteAll();
    }

    @Test
    void whenRequestAll_ReturnAll() throws Exception {
        gameRepository.save(game);

        mockMvc.perform(get("/grid/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("DS")));

    }

    @Test
    void whenRequestGameInfo_ReturnGame() throws Exception {
        gameRepository.save(game);

        mockMvc.perform(
                get("/grid/game")
                        .param("id", ""+game.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("DS")));

    }

    @Test
    void whenRequestGenre_ReturnValidGames() throws Exception {
        GameGenre gameGenre = new GameGenre();
        gameGenre.setName("genre");
        gameGenreRepository.save(gameGenre);
        game.setGameGenres(new HashSet<>(Arrays.asList(gameGenre)));
        gameRepository.save(game);

        mockMvc.perform(get("/grid/genre")
                .param("genre", "genre")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("DS")));
    }

    @Test
    void whenRequestName_ReturnValidGames() throws Exception {
        gameRepository.save(game);

        mockMvc.perform(get("/grid/name")
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
        developerRepository.save(developer);
        game.setDevelopers(new HashSet<>(Arrays.asList(developer)));
        gameRepository.save(game);

        mockMvc.perform(get("/grid/developer")
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
        publisherRepository.save(publisher);
        game.setPublisher(publisher);
        gameRepository.save(game);

        mockMvc.perform(get("/grid/publisher")
                .param("pub", "Pub")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("DS")));

    }
    @Test
    void whenInvalidGameId_Return404Exception() throws Exception {

        mockMvc.perform(
                get("/grid/game")
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("No Game found with Id 1"));

    }

    @Test
    void whenInvalidGameGenre_Return404Exception() throws Exception {

        mockMvc.perform(
                get("/grid/genre")
                        .param("genre", "no")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("No Game found with Id no"));

    }

    @Test
    void whenRequestDev_Return404Exception() throws Exception {

        mockMvc.perform(get("/grid/developer")
                .param("dev", "dev")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("No Game found with Id dev"));

    }

    @Test
    void whenRequestPub_Return404Exception() throws Exception {

        mockMvc.perform(get("/grid/publisher")
                .param("pub", "pub")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("No Game found with Id pub"));

    }
}
