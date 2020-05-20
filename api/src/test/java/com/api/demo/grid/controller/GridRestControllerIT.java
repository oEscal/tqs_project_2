package com.api.demo.grid.controller;

import com.api.demo.DemoApplication;
import com.api.demo.grid.pojos.DeveloperPOJO;
import com.api.demo.grid.pojos.GameGenrePOJO;
import com.api.demo.grid.pojos.GamePOJO;
import com.api.demo.grid.pojos.PublisherPOJO;
import com.api.demo.grid.repository.DeveloperRepository;
import com.api.demo.grid.repository.GameGenreRepository;
import com.api.demo.grid.repository.GameRepository;
import com.api.demo.grid.repository.PublisherRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DemoApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class GridRestControllerIT {
    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameGenreRepository gameGenreRepository;

    @Autowired
    private DeveloperRepository developerRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private MockMvc mockMvc;

    private GameGenrePOJO gameGenrePOJO;
    private GamePOJO gamePOJO;
    private PublisherPOJO publisherPOJO;
    private DeveloperPOJO developerPOJO;

    @BeforeEach
    public void setUp(){
        gameGenrePOJO = new GameGenrePOJO("genre", "");
        publisherPOJO = new PublisherPOJO("publisher", "");
        developerPOJO = new DeveloperPOJO("developer");
        gamePOJO = new GamePOJO("game", "", null, null, null, null, "");
        gamePOJO.setDevelopers(new HashSet<String>(Arrays.asList("developer")));
        gamePOJO.setGameGenres(new HashSet<String>(Arrays.asList("publisher")));
        gamePOJO.setPublisher("publisher");
    }

    @Test
    void whenPostingValidGenre_ReturnValidResponse() throws Exception{

        mockMvc.perform(post("/grid/genre")
                .content(asJsonString(gameGenrePOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(gameGenrePOJO.getName()))).andReturn();

        assertFalse(gameGenreRepository.findByName(gameGenrePOJO.getName()).isEmpty());

    }

    @Test
    void whenPostingValidPub_ReturnValidResponse() throws Exception{
        mockMvc.perform(post("/grid/publisher")
                .content(asJsonString(publisherPOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(publisherPOJO.getName())));
        assertFalse(publisherRepository.findByName(publisherPOJO.getName()).isEmpty());

    }

    @Test
    void whenPostingValidDeveloper_ReturnValidResponse() throws Exception{
        mockMvc.perform(post("/grid/developer")
                .content(asJsonString(developerPOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(developerPOJO.getName())));
        assertFalse(developerRepository.findByName(developerPOJO.getName()).isEmpty());
    }

    @Test
    void whenPostingValidGame_ReturnValidResponse() throws Exception{
        mockMvc.perform(post("/grid/game")
                .content(asJsonString(gamePOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(gamePOJO.getName())));
        assertFalse(gameRepository.findAllByNameContains(gamePOJO.getName()).isEmpty());
    }

    @Test
    void whenPostingInvalidGame_ReturnErrorResponse() throws Exception{
        gamePOJO.setPublisher(null);
        mockMvc.perform(post("/grid/game")
                .content(asJsonString(gamePOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("Could not save Game"));
        assertTrue(gameRepository.findAllByNameContains(gamePOJO.getName()).isEmpty());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
