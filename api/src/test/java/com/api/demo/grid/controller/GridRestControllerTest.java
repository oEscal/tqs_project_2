package com.api.demo.grid.controller;

import com.api.demo.grid.exception.UnavailableListingException;
import com.api.demo.grid.exception.UnsufficientFundsException;
import com.api.demo.grid.exception.GameNotFoundException;
import com.api.demo.grid.models.Buy;
import com.api.demo.grid.models.Developer;
import com.api.demo.grid.models.Game;
import com.api.demo.grid.models.GameGenre;
import com.api.demo.grid.models.GameKey;
import com.api.demo.grid.models.Publisher;
import com.api.demo.grid.models.ReviewGame;
import com.api.demo.grid.models.ReviewUser;
import com.api.demo.grid.models.Sell;
import com.api.demo.grid.models.User;
import com.api.demo.grid.pojos.BuyListingsPOJO;
import com.api.demo.grid.pojos.DeveloperPOJO;
import com.api.demo.grid.pojos.GameGenrePOJO;
import com.api.demo.grid.pojos.GameKeyPOJO;
import com.api.demo.grid.pojos.GamePOJO;
import com.api.demo.grid.pojos.PublisherPOJO;
import com.api.demo.grid.pojos.ReviewGamePOJO;
import com.api.demo.grid.pojos.ReviewUserPOJO;
import com.api.demo.grid.pojos.SearchGamePOJO;
import com.api.demo.grid.pojos.SellPOJO;
import com.api.demo.grid.service.GridService;
import com.api.demo.grid.utils.Pagination;
import com.api.demo.grid.utils.ReviewJoiner;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


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
    private Buy mBuy;
    private User mBuyer;
    private GameKey mGameKey;
    private GameGenrePOJO mGameGenrePOJO;
    private GamePOJO mGamePOJO;
    private PublisherPOJO mPublisherPOJO;
    private DeveloperPOJO mDeveloperPOJO;
    private SellPOJO mSellPOJO;
    private GameKeyPOJO mGameKeyPOJO;
    private BuyListingsPOJO mBuyListingsPOJO;
    private SearchGamePOJO mSearchGamePOJO;
    private ReviewGamePOJO mReviewGamePOJO;
    private ReviewUserPOJO mReviewUserPOJO;

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
        mGameKey.setRealKey("key");
        mGameKey.setGame(mGame);
        mGameKey.setId(3L);

        mSell = new Sell();
        mSell.setId(4L);
        mSell.setGameKey(mGameKey);
        mSell.setUser(mUser);
        mSell.setDate(new Date());

        mSellPOJO = new SellPOJO("key", 2L, 2.3, null);
        mGameKeyPOJO = new GameKeyPOJO("key", 1L, "steam", "ps3");

        mBuyer = new User();
        mBuyer.setId(5L);

        mBuy = new Buy();
        mBuy.setSell(mSell);
        mBuy.setUser(mBuyer);
        mBuy.setDate(new Date());
        mBuy.setId(6l);

        long[] buyList = {6};
        mBuyListingsPOJO = new BuyListingsPOJO(5l, buyList, false);

        mSearchGamePOJO = new SearchGamePOJO();

        mReviewGamePOJO = new ReviewGamePOJO("comment", 1, null, 1, 1, null);
        mReviewUserPOJO = new ReviewUserPOJO("comment", 1, null, null, 1, 2);
    }

    @Test
    @WithMockUser(username = "spring")
    void whenRequestAll_ReturnAll() throws Exception {
        Pagination<Game> pagination = new Pagination<>(Arrays.asList(mGame));
        Page<Game> games = pagination.pageImpl(0, 1);

        int page = 0;
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
    void whenSearchingGames_ReturnPaginatedResult() throws Exception {
        Pagination<Game> pagination = new Pagination<>(Arrays.asList(mGame));
        Page<Game> games = pagination.pageImpl(0, 1);

        int page = 0;
        Mockito.when(mGridService.pageSearchGames(mSearchGamePOJO)).thenReturn(games);

        mMockMvc.perform(post("/grid/search")
                .content(asJsonString(mSearchGamePOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].id", is(1)));

        Mockito.verify(mGridService, Mockito.times(1)).pageSearchGames(mSearchGamePOJO);
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
    void whenRequestSellListings_ReturnPagedListings() throws Exception {
        Pagination<Sell> pagination = new Pagination<>(Arrays.asList(mSell));
        Page<Sell> sells = pagination.pageImpl(0, 1);

        int page = 0;
        Mockito.when(mGridService.getAllSellListings(1, page)).thenReturn(sells);

        mMockMvc.perform(get("/grid/sell-listing")
                .param("gameId", String.valueOf(1))
                .param("page", String.valueOf(page))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].id", is(4)));

        Mockito.verify(mGridService, Mockito.times(1)).getAllSellListings(anyLong(), anyInt());
    }

    @Test
    void whenRequestSellListings_AndSearchIsInvalid_ThrowException() throws Exception {
        int page = 1;
        Mockito.when(mGridService.getAllSellListings(1, page))
                .thenThrow(new GameNotFoundException("Game not found in the database"));

        mMockMvc.perform(get("/grid/sell-listing")
                .param("gameId", String.valueOf(1))
                .param("page", String.valueOf(page))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("Game not found in Database"));

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
    void whenRequestInvalidName_ReturnException() throws Exception {
        Mockito.when(mGridService.getAllGamesByName("game")).thenReturn(null);

        mMockMvc.perform(get("/grid/name")
                .param("name", "game")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

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
    @WithMockUser(username = "spring")
    void whenPostingValidGameKey_ReturnValidGameKeyObject() throws Exception {
        Mockito.when(mGridService.saveGameKey(Mockito.any(GameKeyPOJO.class))).thenReturn(mGameKey);
        mMockMvc.perform(post("/grid/gamekey")
                .content(asJsonString(mGameKeyPOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameId", is(1)))
        ;
    }

    @Test
    @WithMockUser(username = "spring")
    void whenPostingInvalidGameKey_Return404Exception() throws Exception {
        Mockito.when(mGridService.saveGameKey(Mockito.any(GameKeyPOJO.class))).thenReturn(null);
        mMockMvc.perform(post("/grid/gamekey")
                .content(asJsonString(mGameKeyPOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("Could not save Game Key"))
        ;
    }

    @Test
    @WithMockUser(username = "spring")
    void whenPostingValidSellListing_ReturnValidSellObject() throws Exception {
        Mockito.when(mGridService.saveSell(Mockito.any(SellPOJO.class))).thenReturn(mSell);
        mMockMvc.perform(post("/grid/add-sell-listing")
                .content(asJsonString(mSellPOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(4)))
                .andExpect(jsonPath("$.userId", is(2)))
        ;
    }

    @Test
    @WithMockUser(username = "spring")
    void whenPostingInvalidSellListing_Return404Exception() throws Exception {
        Mockito.when(mGridService.saveSell(Mockito.any(SellPOJO.class))).thenReturn(null);
        mMockMvc.perform(post("/grid/add-sell-listing")
                .content(asJsonString(mSellPOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("Could not save Sell Listing"))
        ;
    }

    @Test
    @WithMockUser(username = "spring")
    void whenPostingValidBuylisting_ReturnBuyList() throws Exception {
        Mockito.when(mGridService.saveBuy(Mockito.any(BuyListingsPOJO.class))).thenReturn(Arrays.asList(mBuy));
        mMockMvc.perform(post("/grid/buy-listing")
                .content(asJsonString(mBuyListingsPOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
        ;
    }

    @Test
    @WithMockUser(username = "spring")
    void whenPostingValidBuylisting_AndItemHasBeenBought_ThrowException() throws Exception {
        Mockito.when(mGridService.saveBuy(Mockito.any(BuyListingsPOJO.class)))
                .thenThrow(new UnavailableListingException("This listing has been bought by another user"));
        mMockMvc.perform(post("/grid/buy-listing")
                .content(asJsonString(mBuyListingsPOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("This listing has been bought by another user"))
        ;
        System.out.println();
    }

    @Test
    @WithMockUser(username = "spring")
    void whenPostingValidBuylisting_AndListingHasBeenRemoved_ThrowException() throws Exception {
        Mockito.when(mGridService.saveBuy(Mockito.any(BuyListingsPOJO.class)))
                .thenThrow(new UnavailableListingException("This listing has been removed by the user"));
        mMockMvc.perform(post("/grid/buy-listing")
                .content(asJsonString(mBuyListingsPOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("This listing has been removed by the user"))
        ;
    }

    @Test
    @WithMockUser(username = "spring")
    void whenPostingValidBuylisting_AndUserHasNoFunds_ThrowException() throws Exception {
        Mockito.when(mGridService.saveBuy(Mockito.any(BuyListingsPOJO.class)))
                .thenThrow(new UnsufficientFundsException("This user doesn't have enough funds"));
        mMockMvc.perform(post("/grid/buy-listing")
                .content(asJsonString(mBuyListingsPOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("This user doesn't have enough funds"))
        ;
    }

    @Test
    @WithMockUser(username = "spring")
    void whenPostingValidWishList_ReturnSuccess() throws Exception {
        int gameID = 1;
        int userID = 1;
        Set<Game> games = new HashSet<>();
        games.add(mGame);
        Mockito.when(mGridService.addWishListByUserID(gameID, userID)).thenReturn(games);

        mMockMvc.perform(post("/grid/add-wish-list")
                .param("user_id", String.valueOf(1))
                .param("game_id", String.valueOf(1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", is(1)));

    }

    @Test
    @WithMockUser(username = "spring")
    void whenPostingInvalidWishList_ReturnException() throws Exception {
        int gameID = 1;
        int userID = 1;
        Mockito.when(mGridService.addWishListByUserID(gameID, userID)).thenReturn(null);

        mMockMvc.perform(post("/grid/add-wish-list")
                .param("user_id", String.valueOf(1))
                .param("game_id", String.valueOf(1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "spring")
    void whenPostingValidGameReview_ReturnSuccess() throws Exception {
        Game game = new Game();
        game.setName("game");
        game.setId(1L);
        User user = new User();
        user.setId(2L);
        user.setUsername("mUsername1");
        user.setName("mName1");
        user.setEmail("mEmail1");
        user.setPassword("mPassword1");
        user.setCountry("mCountry1");
        user.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse("17/10/2010"));

        mReviewGamePOJO.setAuthor(1);
        mReviewGamePOJO.setGame(1);

        ReviewGame review = new ReviewGame();

        review.setComment(mReviewGamePOJO.getComment());
        review.setScore(mReviewGamePOJO.getScore());
        review.setAuthor(user);
        review.setGame(game);
        review.setDate(mReviewGamePOJO.getDate());


        Set<ReviewGame> reviews = new HashSet<>();
        reviews.add(review);


        Mockito.when(mGridService.addGameReview(Mockito.any(ReviewGamePOJO.class))).thenReturn(reviews);

        mMockMvc.perform(post("/grid/add-game-review")
                .content(asJsonString(mReviewGamePOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].comment", is(mReviewGamePOJO.getComment())))
                .andExpect(jsonPath("$.[0].score", is(mReviewGamePOJO.getScore())));

    }


    @Test
    @WithMockUser(username = "spring")
    void whenPostingInvalidGameReview_ReturnException() throws Exception {


        Mockito.when(mGridService.addGameReview(Mockito.any(ReviewGamePOJO.class))).thenReturn(null);

        mMockMvc.perform(post("/grid/add-game-review")
                .content(asJsonString(mReviewGamePOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

    }


    @Test
    @WithMockUser(username = "spring")
    void whenPostingValidUserReview_ReturnSuccess() throws Exception {
        User author = new User();
        author.setId(1L);
        author.setUsername("mUsername1");
        author.setName("mName1");
        author.setEmail("mEmail1");
        author.setPassword("mPassword1");
        author.setCountry("mCountry1");
        author.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse("17/10/2010"));

        User target = new User();
        target.setId(2L);
        target.setUsername("mUsername2");
        target.setName("mName2");
        target.setEmail("mEmail2");
        target.setPassword("mPassword2");
        target.setCountry("mCountry2");
        target.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse("17/10/2010"));

        mReviewUserPOJO.setAuthor(1L);
        mReviewUserPOJO.setTarget(2L);

        ReviewUser review = new ReviewUser();
        review.setId(1);
        review.setComment(mReviewUserPOJO.getComment());
        review.setScore(mReviewUserPOJO.getScore());
        review.setAuthor(author);
        review.setTarget(target);
        review.setDate(mReviewUserPOJO.getDate());

        Set<ReviewUser> reviews = new HashSet<>();
        reviews.add(review);

        Mockito.when(mGridService.addUserReview(Mockito.any(ReviewUserPOJO.class))).thenReturn(reviews);

        mMockMvc.perform(post("/grid/add-user-review")
                .content(asJsonString(mReviewUserPOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].comment", is(mReviewUserPOJO.getComment())))
                .andExpect(jsonPath("$[0].score", is(mReviewUserPOJO.getScore())));
    }


    @Test
    @WithMockUser(username = "spring")
    void whenPostingInvalidUserReview_ReturnInvalid() throws Exception {
        Mockito.when(mGridService.addUserReview(Mockito.any(ReviewUserPOJO.class))).thenReturn(null);

        mMockMvc.perform(post("/grid/add-user-review")
                .content(asJsonString(mReviewUserPOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }


    @Test
    @WithMockUser(username = "spring")
    void whenGetValidGameReviews_ReturnSuccess() throws Exception {
        Game game = new Game();
        game.setId(1L);
        User user = new User();
        user.setId(1L);
        user.setUsername("mUsername1");
        user.setName("mName1");
        user.setEmail("mEmail1");
        user.setPassword("mPassword1");
        user.setCountry("mCountry1");
        user.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse("17/10/2010"));

        ReviewGame review = new ReviewGame();
        review.setComment("comment");
        review.setScore(1);
        review.setAuthor(user);
        review.setGame(game);
        review.setDate(mReviewGamePOJO.getDate());

        Set<ReviewGame> reviews = new HashSet<>();
        reviews.add(review);

        Pagination<ReviewGame> reviewsPage = new Pagination<>(new ArrayList<>(reviews));

        Mockito.when(mGridService.getGameReviews(Mockito.anyLong(), Mockito.anyInt())).thenReturn(reviewsPage.pageImpl(0, 18));

        mMockMvc.perform(get("/grid/game-review")
                .param("game_id", String.valueOf(game.getId()))
                .param("page", "0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.[0].comment", is(review.getComment())))
                .andExpect(jsonPath("$.content.[0].score", is(review.getScore())))
                .andExpect(jsonPath("$.content.[0].gameId", is((int) game.getId())));
    }

    @Test
    @WithMockUser(username = "spring")
    void whenGetInvalidGameReviews2_ReturnException() throws Exception {

        Mockito.when(mGridService.getGameReviews(Mockito.anyLong(), Mockito.anyInt())).thenReturn(null);

        mMockMvc.perform(get("/grid/game-review")
                .param("game_id", "1")
                .param("page", "0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "spring")
    void whenGetInvalidPageGameReviews_ReturnException() throws Exception {
        Game game = new Game();
        game.setId(1L);
        User user = new User();
        user.setId(1L);
        user.setUsername("mUsername1");
        user.setName("mName1");
        user.setEmail("mEmail1");
        user.setPassword("mPassword1");
        user.setCountry("mCountry1");
        user.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse("17/10/2010"));

        ReviewGame review = new ReviewGame();
        review.setComment("comment");
        review.setScore(1);
        review.setAuthor(user);
        review.setGame(game);
        review.setDate(mReviewGamePOJO.getDate());

        Set<ReviewGame> reviews = new HashSet<>();
        reviews.add(review);

        Pagination<ReviewGame> reviewsPage = new Pagination<>(new ArrayList<>(reviews));

        Mockito.when(mGridService.getGameReviews(Mockito.anyLong(), Mockito.anyInt())).thenReturn(reviewsPage.pageImpl(1, 18));

        mMockMvc.perform(get("/grid/game-review")
                .param("game_id", String.valueOf(game.getId()))
                .param("page", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.*", hasSize(0)));
    }


    @Test
    @WithMockUser(username = "spring")
    void whenGetInvalidGameReviews_ReturnException() throws Exception {
        Mockito.when(mGridService.getGameReviews(Mockito.anyLong(), Mockito.anyInt())).thenReturn(null);

        mMockMvc.perform(get("/grid/game-review")
                .param("game_id", String.valueOf(1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }


    @Test
    @WithMockUser(username = "spring")
    void whenGetValidUserReviews_ReturnSuccess() throws Exception {
        Game game = new Game();
        User user = new User();
        user.setUsername("mUsername1");
        user.setName("mName1");
        user.setEmail("mEmail1");
        user.setPassword("mPassword1");
        user.setCountry("mCountry1");
        user.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse("17/10/2010"));

        ReviewGame review = new ReviewGame();
        review.setId(1);
        review.setComment("comment");
        review.setScore(1);
        review.setAuthor(user);
        review.setGame(game);
        review.setDate(new SimpleDateFormat("dd/MM/yyyy").parse("17/10/2010"));

        Set<ReviewJoiner> reviews = new HashSet<>();
        long id = review.getId();
        String comment = review.getComment();
        int score = review.getScore();
        Date date = review.getDate();

        reviews.add(new ReviewJoiner(id, comment, score, date, new HashSet<>(), user, game));

        Pagination<ReviewJoiner> reviewsPage = new Pagination<>(new ArrayList<>(reviews));

        Mockito.when(mGridService.getUserReviews(Mockito.anyLong(), Mockito.anyInt())).thenReturn(reviewsPage.pageImpl(0, 18));

        mMockMvc.perform(get("/grid/user-reviewed")
                .param("user_id", "1")
                .param("page", "0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.[0].comment", is(review.getComment())))
                .andExpect(jsonPath("$.content.[0].score", is(review.getScore())));
    }


    @Test
    @WithMockUser(username = "spring")
    void whenGetInvalidUserReviews_ReturnException() throws Exception {

        Mockito.when(mGridService.getUserReviews(Mockito.anyLong(), Mockito.anyInt())).thenReturn(null);

        mMockMvc.perform(get("/grid/user-reviewed")
                .param("user_id", "1")
                .param("page", "0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "spring")
    void whenGetValidAllUserReviews_ReturnSuccess() throws Exception {
        Game game = new Game();
        User user = new User();
        user.setUsername("mUsername1");
        user.setName("mName1");
        user.setEmail("mEmail1");
        user.setPassword("mPassword1");
        user.setCountry("mCountry1");
        user.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse("17/10/2010"));

        ReviewGame review = new ReviewGame();
        review.setId(1);
        review.setComment("comment");
        review.setScore(1);
        review.setAuthor(user);
        review.setGame(game);
        review.setDate(new SimpleDateFormat("dd/MM/yyyy").parse("17/10/2010"));

        Set<ReviewJoiner> reviews = new HashSet<>();
        long id = review.getId();
        String comment = review.getComment();
        int score = review.getScore();
        Date date = review.getDate();

        reviews.add(new ReviewJoiner(id, comment, score, date, new HashSet<>(), user, game));

        Pagination<ReviewJoiner> reviewsPage = new Pagination<>(new ArrayList<>(reviews));

        Mockito.when(mGridService.getAllReviews(Mockito.anyInt(), Mockito.anyString())).thenReturn(reviewsPage.pageImpl(0, 18));


        mMockMvc.perform(get("/grid/all-reviews")
                .param("page", "0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.[0].comment", is(review.getComment())))
                .andExpect(jsonPath("$.content.[0].score", is(review.getScore())));
    }

    @Test
    @WithMockUser(username = "spring")
    void whenGetValidAllUserReviews_ReturnSuccessEmpty() throws Exception {
        Pagination<ReviewJoiner> reviewsPage = new Pagination<>(new ArrayList<>());

        Mockito.when(mGridService.getAllReviews(Mockito.anyInt(), Mockito.anyString())).thenReturn(reviewsPage.pageImpl(0, 18));

        mMockMvc.perform(get("/grid/all-reviews")
                .param("page", "0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)));
    }


    @Test
    @WithMockUser(username = "spring")
    void whenGetInvalidAllUserReviews_ReturnException() throws Exception {
        Mockito.when(mGridService.getAllReviews(Mockito.anyInt(), Mockito.anyString())).thenReturn(null);
        mMockMvc.perform(get("/grid/all-reviews")
                .param("page", "0")
                .param("sort","incorrect")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}