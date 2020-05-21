package com.api.demo.grid.controller;

import com.api.demo.grid.models.Game;
import com.api.demo.grid.service.GridService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;


@SpringBootTest
@AutoConfigureMockMvc
@DataJpaTest
class GridRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GridService gridService;

    private Game game;

    @BeforeEach
    void setUp(){
        game = new Game();
        game.setId(1L);
    }

    @Test
    void whenRequestAll_ReturnAll() throws Exception {
        Mockito.when(gridService.getAllGames()).thenReturn(Arrays.asList(game));

        mockMvc.perform(get("/grid/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)));

        Mockito.verify(gridService, Mockito.times(1)).getAllGames();
    }

    @Test
    void whenRequestGameInfo_ReturnGame() throws Exception {
        Mockito.when(gridService.getGameById(1L)).thenReturn(game);

        mockMvc.perform(
                get("/grid/game")
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));

        Mockito.verify(gridService, Mockito.times(1)).getGameById(anyLong());
    }

    @Test
    void whenRequestGenre_ReturnValidGames() throws Exception {
        Mockito.when(gridService.getAllGamesWithGenre("genre")).thenReturn(Arrays.asList(game));

        mockMvc.perform(get("/grid/genre")
                .param("genre", "genre")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)));

        Mockito.verify(gridService, Mockito.times(1)).getAllGamesWithGenre(Mockito.anyString());
    }

    @Test
    void whenRequestName_ReturnValidGames() throws Exception {
        Mockito.when(gridService.getAllGamesByName("game")).thenReturn(Arrays.asList(game));

        mockMvc.perform(get("/grid/name")
                .param("name", "game")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)));

        Mockito.verify(gridService, Mockito.times(1)).getAllGamesByName(Mockito.anyString());
    }

    @Test
    void whenRequestDev_ReturnValidGames() throws Exception {
        Mockito.when(gridService.getAllGamesByDev("dev")).thenReturn(Arrays.asList(game));

        mockMvc.perform(get("/grid/developer")
                .param("dev", "dev")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)));

        Mockito.verify(gridService, Mockito.times(1)).getAllGamesByDev(Mockito.anyString());
    }

    @Test
    void whenRequestPub_ReturnValidGames() throws Exception {
        Mockito.when(gridService.getAllGamesByPublisher("pub")).thenReturn(Arrays.asList(game));

        mockMvc.perform(get("/grid/publisher")
                .param("pub", "pub")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)));

        Mockito.verify(gridService, Mockito.times(1)).getAllGamesByPublisher(Mockito.anyString());
    }

    @Test
    void whenInvalidGameId_Return404Exception() throws Exception {
        Mockito.when(gridService.getGameById(1L)).thenReturn(null);

        mockMvc.perform(
                get("/grid/game")
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("No Game found with Id 1"));

        Mockito.verify(gridService, Mockito.times(1)).getGameById(anyLong());
    }

    @Test
    void whenInvalidGameGenre_Return404Exception() throws Exception {
        Mockito.when(gridService.getAllGamesWithGenre("no")).thenReturn(null);

        mockMvc.perform(
                get("/grid/genre")
                        .param("genre", "no")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("No Game found with Id no"));

        Mockito.verify(gridService, Mockito.times(1)).getAllGamesWithGenre(anyString());
    }

    @Test
    void whenRequestDev_Return404Exception() throws Exception {
        Mockito.when(gridService.getAllGamesByDev("dev")).thenReturn(null);

        mockMvc.perform(get("/grid/developer")
                .param("dev", "dev")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("No Game found with Id dev"));

        Mockito.verify(gridService, Mockito.times(1)).getAllGamesByDev(Mockito.anyString());
    }

    @Test
    void whenRequestPub_Return404Exception() throws Exception {
        Mockito.when(gridService.getAllGamesByPublisher("pub")).thenReturn(null);

        mockMvc.perform(get("/grid/publisher")
                .param("pub", "pub")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("No Game found with Id pub"));

        Mockito.verify(gridService, Mockito.times(1)).getAllGamesByPublisher(Mockito.anyString());
    }

}