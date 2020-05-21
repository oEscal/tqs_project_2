package com.api.demo.grid.controller;

import com.api.demo.grid.models.*;
import com.api.demo.grid.pojos.*;
import com.api.demo.grid.service.GridService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

@WebMvcTest
class GridRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GridService gridService;

    private Game game;
    private GameGenre gameGenre;
    private Publisher publisher;
    private Developer developer;
    private Sell sell;
    private User user;
    private GameKey gameKey;
    private GameGenrePOJO gameGenrePOJO;
    private GamePOJO gamePOJO;
    private PublisherPOJO publisherPOJO;
    private DeveloperPOJO developerPOJO;
    private SellPOJO sellPOJO;

    @BeforeEach
    void setUp(){
        game = new Game();
        game.setDescription("");
        game.setName("game");
        game.setId(1L);
        game.setCoverUrl("");

        gameGenre = new GameGenre();
        gameGenre.setName("genre");
        gameGenre.setDescription("");
        game.setGameGenres(new HashSet<>(Arrays.asList(gameGenre)));

        publisher = new Publisher();
        publisher.setName("publisher");
        publisher.setDescription("");
        game.setPublisher(publisher);

        developer = new Developer();
        developer.setName("developer");
        game.setDevelopers(new HashSet<>(Arrays.asList(developer)));

        gameGenrePOJO = new GameGenrePOJO("genre", "");
        publisherPOJO = new PublisherPOJO("publisher", "");
        developerPOJO = new DeveloperPOJO("developer");
        gamePOJO = new GamePOJO("game", "", null, null, null, null, "");
        gamePOJO.setDevelopers(new HashSet<String>(Arrays.asList("developer")));
        gamePOJO.setGameGenres(new HashSet<String>(Arrays.asList("genre")));
        gamePOJO.setPublisher("publisher");

        user = new User();
        user.setId(2L);

        gameKey = new GameKey();
        gameKey.setGame(game);
        gameKey.setId(3L);

        sell = new Sell();
        sell.setId(4L);
        sell.setGameKey(gameKey);
        sell.setUser(user);
        sell.setDate(new Date());

        sellPOJO = new SellPOJO("key", "s", "steam", 2L, 6L, 2.3, null);
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
    void whenInvalidDev_Return404Exception() throws Exception {
        Mockito.when(gridService.getAllGamesByDev("dev")).thenReturn(null);

        mockMvc.perform(get("/grid/developer")
                .param("dev", "dev")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("No Game found with Id dev"));

        Mockito.verify(gridService, Mockito.times(1)).getAllGamesByDev(Mockito.anyString());
    }

    @Test
    void whenInvalidPub_Return404Exception() throws Exception {
        Mockito.when(gridService.getAllGamesByPublisher("pub")).thenReturn(null);

        mockMvc.perform(get("/grid/publisher")
                .param("pub", "pub")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("No Game found with Id pub"));

        Mockito.verify(gridService, Mockito.times(1)).getAllGamesByPublisher(Mockito.anyString());
    }

    @Test
    void whenPostingValidGenre_ReturnValidResponse() throws Exception{
        Mockito.when(gridService.saveGameGenre(Mockito.any(GameGenrePOJO.class))).thenReturn(gameGenre);
        MvcResult result = mockMvc.perform(post("/grid/genre")
                .content(asJsonString(gameGenrePOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(gameGenrePOJO.getName()))).andReturn();

    }

    @Test
    void whenPostingValidPub_ReturnValidResponse() throws Exception{
        Mockito.when(gridService.savePublisher(Mockito.any(PublisherPOJO.class))).thenReturn(publisher);
        mockMvc.perform(post("/grid/publisher")
                .content(asJsonString(publisherPOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(publisherPOJO.getName())));
    }

    @Test
    void whenPostingValidDeveloper_ReturnValidResponse() throws Exception{
        Mockito.when(gridService.saveDeveloper(Mockito.any(DeveloperPOJO.class))).thenReturn(developer);
        mockMvc.perform(post("/grid/developer")
                .content(asJsonString(developerPOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(developerPOJO.getName())));
    }

    @Test
    void whenPostingValidGame_ReturnValidResponse() throws Exception{
        Mockito.when(gridService.saveGame(Mockito.any(GamePOJO.class))).thenReturn(game);
        mockMvc.perform(post("/grid/game")
                .content(asJsonString(gamePOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(gamePOJO.getName())));
    }

    @Test
    void whenPostingInvalidGame_ReturnErrorResponse() throws Exception{
        Mockito.when(gridService.saveGame(Mockito.any(GamePOJO.class))).thenReturn(null);
        mockMvc.perform(post("/grid/game")
                .content(asJsonString(gamePOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("Could not save Game"));
    }

    @Test
    void whenPostingValidSellListing_ReturnValidSellObject() throws Exception{
        Mockito.when(gridService.saveSell(Mockito.any(SellPOJO.class))).thenReturn(sell);
        String json = asJsonString(sellPOJO);
        mockMvc.perform(post("/grid/sell-listing")
                .content(asJsonString(sellPOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(4)))
                ;
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}