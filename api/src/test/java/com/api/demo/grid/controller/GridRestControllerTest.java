package com.api.demo.grid.controller;

import com.api.demo.grid.models.Developer;
import com.api.demo.grid.models.Game;
import com.api.demo.grid.models.GameGenre;
import com.api.demo.grid.models.GameKey;
import com.api.demo.grid.models.Publisher;
import com.api.demo.grid.models.Sell;
import com.api.demo.grid.models.User;
import com.api.demo.grid.pojos.DeveloperPOJO;
import com.api.demo.grid.pojos.GameGenrePOJO;
import com.api.demo.grid.pojos.GameKeyPOJO;
import com.api.demo.grid.pojos.GamePOJO;
import com.api.demo.grid.pojos.PublisherPOJO;
import com.api.demo.grid.pojos.SellPOJO;
import com.api.demo.grid.service.GridService;
import com.api.demo.grid.utils.Pagination;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class GridRestControllerTest {

    @Autowired
    private MockMvc mMockMvc;

    @MockBean
    private GridService mGridService;

    private Game mGame;
    private GameGenre mGameGenre;
    private Publisher mPublisher;
    private Developer mDeveloper;
    private Sell mSell;
    private User mUser;
    private GameKey mGameKey;
    private GameGenrePOJO mGameGenrePOJO;
    private GamePOJO mGamePOJO;
    private PublisherPOJO mPublisherPOJO;
    private DeveloperPOJO mDeveloperPOJO;
    private SellPOJO mSellPOJO;
    private GameKeyPOJO mGameKeyPOJO;

    @BeforeEach
    void setUp() {
        mGame = new Game();
        mGame.setDescription("");
        mGame.setName("game");
        mGame.setId(1L);
        mGame.setCoverUrl("");

        mGameGenre = new GameGenre();
        mGameGenre.setName("genre");
        mGameGenre.setDescription("");
        mGame.setGameGenres(new HashSet<>(Arrays.asList(mGameGenre)));

        mPublisher = new Publisher();
        mPublisher.setName("publisher");
        mPublisher.setDescription("");
        mGame.setPublisher(mPublisher);

        mDeveloper = new Developer();
        mDeveloper.setName("developer");
        mGame.setDevelopers(new HashSet<>(Arrays.asList(mDeveloper)));

        mGameGenrePOJO = new GameGenrePOJO("genre", "");
        mPublisherPOJO = new PublisherPOJO("publisher", "");
        mDeveloperPOJO = new DeveloperPOJO("developer");
        mGamePOJO = new GamePOJO("game", "", null, null, null, null, "");
        mGamePOJO.setDevelopers(new HashSet<String>(Arrays.asList("developer")));
        mGamePOJO.setGameGenres(new HashSet<String>(Arrays.asList("genre")));
        mGamePOJO.setPublisher("publisher");

        mUser = new User();
        mUser.setId(2L);

        mGameKey = new GameKey();
        mGameKey.setRKey("key");
        mGameKey.setGame(mGame);
        mGameKey.setId(3L);

        mSell = new Sell();
        mSell.setId(4L);
        mSell.setGameKey(mGameKey);
        mSell.setUser(mUser);
        mSell.setDate(new Date());

        mSellPOJO = new SellPOJO("key", 2L, 2.3, null);
        mGameKeyPOJO = new GameKeyPOJO("key", 1L, "steam", "ps3");
    }

    @Test
    @WithMockUser(username = "spring")
    void whenRequestAll_ReturnAll() throws Exception {
        Pagination<Game> pagination = new Pagination<>(Arrays.asList(mGame));
        Page<Game> games = pagination.pageImpl(1, 1);

        int page = 1;
        Mockito.when(mGridService.getAllGames(page)).thenReturn(games);

        mMockMvc.perform(get("/grid/all")
                .param("page", String.valueOf(page))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].id", is(1)));

        Mockito.verify(mGridService, Mockito.times(1)).getAllGames(page);
    }

    @Test
    @WithMockUser(username = "spring")
    void whenRequestAllEmpty_ReturnEmpty() throws Exception {
        Pagination<Game> pagination = new Pagination<>(new ArrayList<>());
        Page<Game> games = pagination.pageImpl(10, 10);

        int page = 1;
        Mockito.when(mGridService.getAllGames(page)).thenReturn(games);

        mMockMvc.perform(get("/grid/all")
                .param("page", String.valueOf(page))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)));

        Mockito.verify(mGridService, Mockito.times(1)).getAllGames(page);
    }


    @Test
    @WithMockUser(username = "spring")
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
    @WithMockUser(username = "spring")
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
    @WithMockUser(username = "spring")
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
    @WithMockUser(username = "spring")
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
    @WithMockUser(username = "spring")
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
    @WithMockUser(username = "spring")
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
    @WithMockUser(username = "spring")
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
    @WithMockUser(username = "spring")
    void whenInvalidDev_Return404Exception() throws Exception {
        Mockito.when(mGridService.getAllGamesByDev("dev")).thenReturn(null);

        mMockMvc.perform(get("/grid/developer")
                .param("dev", "dev")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("No Game found with Id dev"));

        Mockito.verify(mGridService, Mockito.times(1)).getAllGamesByDev(Mockito.anyString());
    }

    @Test
    @WithMockUser(username = "spring")
    void whenInvalidPub_Return404Exception() throws Exception {
        Mockito.when(mGridService.getAllGamesByPublisher("pub")).thenReturn(null);

        mMockMvc.perform(get("/grid/publisher")
                .param("pub", "pub")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("No Game found with Id pub"));

        Mockito.verify(mGridService, Mockito.times(1)).getAllGamesByPublisher(Mockito.anyString());
    }

    @Test
    @WithMockUser(username = "spring", authorities = "ADMIN")
    void whenPostingValidGenre_ReturnValidResponse() throws Exception {
        Mockito.when(mGridService.saveGameGenre(Mockito.any(GameGenrePOJO.class))).thenReturn(mGameGenre);
        mMockMvc.perform(post("/grid/add-genre")
                .content(asJsonString(mGameGenrePOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(mGameGenrePOJO.getName())));

    }

    @Test
    @WithMockUser(username = "spring", authorities = "ADMIN")
    void whenPostingValidPub_ReturnValidResponse() throws Exception {
        Mockito.when(mGridService.savePublisher(Mockito.any(PublisherPOJO.class))).thenReturn(mPublisher);
        mMockMvc.perform(post("/grid/add-publisher")
                .content(asJsonString(mPublisherPOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(mPublisherPOJO.getName())));
    }

    @Test
    @WithMockUser(username = "spring", authorities = "ADMIN")
    void whenPostingValidDeveloper_ReturnValidResponse() throws Exception {
        Mockito.when(mGridService.saveDeveloper(Mockito.any(DeveloperPOJO.class))).thenReturn(mDeveloper);
        mMockMvc.perform(post("/grid/add-developer")
                .content(asJsonString(mDeveloperPOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(mDeveloperPOJO.getName())));
    }

    @Test
    @WithMockUser(username = "spring", authorities = "ADMIN")
    void whenPostingValidGame_ReturnValidResponse() throws Exception {
        Mockito.when(mGridService.saveGame(Mockito.any(GamePOJO.class))).thenReturn(mGame);
        mMockMvc.perform(post("/grid/add-game")
                .content(asJsonString(mGamePOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(mGamePOJO.getName())));
    }

    @Test
    @WithMockUser(username = "spring", authorities = "ADMIN")
    void whenPostingInvalidGame_ReturnErrorResponse() throws Exception {
        Mockito.when(mGridService.saveGame(Mockito.any(GamePOJO.class))).thenReturn(null);
        mMockMvc.perform(post("/grid/add-game")
                .content(asJsonString(mGamePOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("Could not save Game"));
    }

    @Test
    @WithMockUser(username="spring")
    void whenPostingValidGameKey_ReturnValidGameKeyObject() throws Exception{
        Mockito.when(mGridService.saveGameKey(Mockito.any(GameKeyPOJO.class))).thenReturn(mGameKey);
        mMockMvc.perform(post("/grid/gamekey")
                .content(asJsonString(mGameKeyPOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rkey", is("key")))
                .andExpect(jsonPath("$.gameId", is(1)))
        ;
    }

    @Test
    @WithMockUser(username="spring")
    void whenPostingInvalidGameKey_Return404Exception() throws Exception{
        Mockito.when(mGridService.saveGameKey(Mockito.any(GameKeyPOJO.class))).thenReturn(null);
        mMockMvc.perform(post("/grid/gamekey")
                .content(asJsonString(mGameKeyPOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("Could not save Game Key"))
        ;
    }

    @Test
    @WithMockUser(username="spring")
    void whenPostingValidSellListing_ReturnValidSellObject() throws Exception{
        Mockito.when(mGridService.saveSell(Mockito.any(SellPOJO.class))).thenReturn(mSell);
        mMockMvc.perform(post("/grid/sell-listing")
                .content(asJsonString(mSellPOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(4)))
                .andExpect(jsonPath("$.userId", is(2)))
                ;
    }

    @Test
    @WithMockUser(username="spring")
    void whenPostingInvalidSellListing_Return404Exception() throws Exception{
        Mockito.when(mGridService.saveSell(Mockito.any(SellPOJO.class))).thenReturn(null);
        mMockMvc.perform(post("/grid/sell-listing")
                .content(asJsonString(mSellPOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("Could not save Sell Listing"))
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