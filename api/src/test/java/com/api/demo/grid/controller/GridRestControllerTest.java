package com.api.demo.grid.controller;

import com.api.demo.grid.models.Game;
import com.api.demo.grid.service.GridService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

@WebMvcTest
class GridRestControllerTest {
    @Autowired
    private MockMvc mMockMvc;

    @MockBean
    private GridService mGridService;

    private Game mGame;

    @BeforeEach
    void setUp(){
        mGame = new Game();
        mGame.setId(1L);
    }

    @Test
    void whenRequestAll_ReturnAll() throws Exception {
        Mockito.when(mGridService.getAllGames()).thenReturn(Arrays.asList(mGame));

        mMockMvc.perform(get("/grid/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)));

        Mockito.verify(mGridService, Mockito.times(1)).getAllGames();
    }

    @Test
    void whenRequestGameInfo_ReturnGame() throws Exception {
        Mockito.when(mGridService.getGameById(1L)).thenReturn(mGame);

        mMockMvc.perform(
                get("/grid/game")
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));

        Mockito.verify(mGridService, Mockito.times(1)).getGameById(anyLong());
    }

    @Test
    void whenRequestGenre_ReturnValidGames() throws Exception {
        Mockito.when(mGridService.getAllGamesWithGenre("genre")).thenReturn(Arrays.asList(mGame));

        mMockMvc.perform(get("/grid/genre")
                .param("genre", "genre")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)));

        Mockito.verify(mGridService, Mockito.times(1)).getAllGamesWithGenre(Mockito.anyString());
    }

    @Test
    void whenRequestName_ReturnValidGames() throws Exception {
        Mockito.when(mGridService.getAllGamesByName("game")).thenReturn(Arrays.asList(mGame));

        mMockMvc.perform(get("/grid/name")
                .param("name", "game")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)));

        Mockito.verify(mGridService, Mockito.times(1)).getAllGamesByName(Mockito.anyString());
    }

    @Test
    void whenRequestDev_ReturnValidGames() throws Exception {
        Mockito.when(mGridService.getAllGamesByDev("dev")).thenReturn(Arrays.asList(mGame));

        mMockMvc.perform(get("/grid/developer")
                .param("dev", "dev")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)));

        Mockito.verify(mGridService, Mockito.times(1)).getAllGamesByDev(Mockito.anyString());
    }

    @Test
    void whenRequestPub_ReturnValidGames() throws Exception {
        Mockito.when(mGridService.getAllGamesByPublisher("pub")).thenReturn(Arrays.asList(mGame));

        mMockMvc.perform(get("/grid/publisher")
                .param("pub", "pub")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)));

        Mockito.verify(mGridService, Mockito.times(1)).getAllGamesByPublisher(Mockito.anyString());
    }

    @Test
    void whenInvalidGameId_Return404Exception() throws Exception {
        Mockito.when(mGridService.getGameById(1L)).thenReturn(null);

        mMockMvc.perform(
                get("/grid/game")
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("No Game found with Id 1"));

        Mockito.verify(mGridService, Mockito.times(1)).getGameById(anyLong());
    }

    @Test
    void whenInvalidGameGenre_Return404Exception() throws Exception {
        Mockito.when(mGridService.getAllGamesWithGenre("no")).thenReturn(null);

        mMockMvc.perform(
                get("/grid/genre")
                        .param("genre", "no")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("No Game found with Id no"));

        Mockito.verify(mGridService, Mockito.times(1)).getAllGamesWithGenre(anyString());
    }

    @Test
    void whenRequestDev_Return404Exception() throws Exception {
        Mockito.when(mGridService.getAllGamesByDev("dev")).thenReturn(null);

        mMockMvc.perform(get("/grid/developer")
                .param("dev", "dev")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("No Game found with Id dev"));

        Mockito.verify(mGridService, Mockito.times(1)).getAllGamesByDev(Mockito.anyString());
    }

    @Test
    void whenRequestPub_Return404Exception() throws Exception {
        Mockito.when(mGridService.getAllGamesByPublisher("pub")).thenReturn(null);

        mMockMvc.perform(get("/grid/publisher")
                .param("pub", "pub")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("No Game found with Id pub"));

        Mockito.verify(mGridService, Mockito.times(1)).getAllGamesByPublisher(Mockito.anyString());
    }

}