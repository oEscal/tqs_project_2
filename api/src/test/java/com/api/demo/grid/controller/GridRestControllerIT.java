package com.api.demo.grid.controller;

import com.api.demo.DemoApplication;
import com.api.demo.grid.models.*;
import com.api.demo.grid.pojos.*;
import com.api.demo.grid.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DemoApplication.class)
class GridRestControllerIT {

    @Autowired
    private GameRepository mGameRepository;

    @Autowired
    private GameGenreRepository mGameGenreRepository;

    @Autowired
    private DeveloperRepository mDeveloperRepository;

    @Autowired
    private PublisherRepository mPublisherRepository;

    @Autowired
    private UserRepository mUserRepository;

    @Autowired
    private SellRepository mSellRepository;

    @Autowired
    private GameKeyRepository mGameKeyRepository;

    @Autowired
    private ReviewUserRepository mReviewUserRepository;


    @Autowired
    private MockMvc mMockMvc;

    private GameGenrePOJO mGameGenrePOJO;
    private GamePOJO mGamePOJO;
    private PublisherPOJO mPublisherPOJO;
    private DeveloperPOJO mDeveloperPOJO;
    private SellPOJO mSellPOJO;
    private GameKeyPOJO mGameKeyPOJO;
    private ReviewGamePOJO mReviewGamePOJO;
    private ReviewUserPOJO mReviewUserPOJO;


    @BeforeEach
    public void setUp() {
        mGameGenrePOJO = new GameGenrePOJO("genre", "");
        mPublisherPOJO = new PublisherPOJO("publisher", "");
        mDeveloperPOJO = new DeveloperPOJO("developer");
        mGamePOJO = new GamePOJO("game", "", null, null, null, null, "");
        mGamePOJO.setDevelopers(new HashSet<String>(Arrays.asList("developer")));
        mGamePOJO.setGameGenres(new HashSet<String>(Arrays.asList("genre")));
        mGamePOJO.setPublisher("publisher");
        mGameKeyPOJO = new GameKeyPOJO("key", 2L, "steam", "ps3");
        mSellPOJO = new SellPOJO("key", 6L, 2.3, null);
        mReviewGamePOJO = new ReviewGamePOJO("comment", 1, null, 1, 1, null);
        mReviewUserPOJO = new ReviewUserPOJO("comment", 1, null, null, 1, 2);

        mGameRepository.deleteAll();
        mReviewUserRepository.deleteAll();
        mGameGenreRepository.deleteAll();
        mDeveloperRepository.deleteAll();
        mPublisherRepository.deleteAll();
        mUserRepository.deleteAll();
        mGameKeyRepository.deleteAll();
        mSellRepository.deleteAll();

    }

    @Test
    @WithMockUser(username = "spring", authorities = "ADMIN")
    void whenPostingValidGenre_ReturnValidResponse() throws Exception {

        mMockMvc.perform(post("/grid/add-genre")
                .content(asJsonString(mGameGenrePOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(mGameGenrePOJO.getName()))).andReturn();

        assertFalse(mGameGenreRepository.findByName(mGameGenrePOJO.getName()).isEmpty());

    }

    @Test
    @WithMockUser(username = "spring", authorities = "ADMIN")
    void whenPostingValidPub_ReturnValidResponse() throws Exception {
        mMockMvc.perform(post("/grid/add-publisher")
                .content(asJsonString(mPublisherPOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(mPublisherPOJO.getName())));
        assertFalse(mPublisherRepository.findByName(mPublisherPOJO.getName()).isEmpty());

    }

    @Test
    @WithMockUser(username = "spring", authorities = "ADMIN")
    void whenPostingValidDeveloper_ReturnValidResponse() throws Exception {
        mMockMvc.perform(post("/grid/add-developer")
                .content(asJsonString(mDeveloperPOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(mDeveloperPOJO.getName())));
        assertFalse(mDeveloperRepository.findByName(mDeveloperPOJO.getName()).isEmpty());
    }

    @Test
    @WithMockUser(username = "spring", authorities = "ADMIN")
    void whenPostingValidGame_ReturnValidResponse() throws Exception {
        Developer developer = new Developer();
        developer.setName("dev");
        mDeveloperRepository.save(developer);

        Publisher publisher = new Publisher();
        publisher.setName("pub");
        mPublisherRepository.save(publisher);

        GameGenre gameGenre = new GameGenre();
        gameGenre.setName("genre");
        mGameGenreRepository.save(gameGenre);

        mGamePOJO.setPublisher("pub");
        mGamePOJO.setDevelopers(new HashSet<>(Arrays.asList("dev")));
        mGamePOJO.setGameGenres(new HashSet<>(Arrays.asList("genre")));
        mMockMvc.perform(post("/grid/add-game")
                .content(asJsonString(mGamePOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(mGamePOJO.getName())))
        ;
        assertFalse(mGameRepository.findAllByNameContaining(mGamePOJO.getName()).isEmpty());
    }

    @Test
    @WithMockUser(username = "spring", authorities = "ADMIN")
    void whenPostingInvalidGame_ReturnErrorResponse() throws Exception {

        mGamePOJO.setPublisher(null);

        mMockMvc.perform(post("/grid/add-game")
                .content(asJsonString(mGamePOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("Could not save Game"));
        assertTrue(mGameRepository.findAllByNameContaining(mGamePOJO.getName()).isEmpty());
    }

    @Test
    @WithMockUser(username = "spring")
    void whenPostingValidGameKey_ReturnValidGameKeyObject() throws Exception {
        Game game = new Game();
        mGameRepository.save(game);
        mGameKeyPOJO.setGameId(game.getId());
        mMockMvc.perform(post("/grid/gamekey")
                .content(asJsonString(mGameKeyPOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rkey", is("key")))
                .andExpect(jsonPath("$.gameId", is(Math.toIntExact(game.getId()))))
        ;
    }

    @Test
    @WithMockUser(username = "spring")
    void whenPostingInvalidGameKey_Return404Exception() throws Exception {
        mGameKeyPOJO.setGameId(-1);
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
        User user = new User();
        user.setUsername("mUsername1");
        user.setName("mName1");
        user.setEmail("mEmail1");
        user.setPassword("mPassword1");
        user.setCountry("mCountry1");
        user.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse("17/10/2010"));
        mUserRepository.save(user);
        GameKey gameKey = new GameKey();
        gameKey.setRKey("key");
        mGameKeyRepository.save(gameKey);
        mSellPOJO.setUserId(user.getId());
        mSellPOJO.setGameKey("key");
        mMockMvc.perform(post("/grid/sell-listing")
                .content(asJsonString(mSellPOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameKey.rkey", is("key")))
                .andExpect(jsonPath("$.userId", is(Math.toIntExact(user.getId()))))
        ;
    }

    @Test
    @WithMockUser(username = "spring")
    void whenPostingValidSellListing_AndAskingGame_ReturnLowestPriceAndPlatformUsed() throws Exception {
        Game game = new Game();
        GameKey gameKey = new GameKey();
        gameKey.setRKey("key");
        gameKey.setPlatform("ps4");
        gameKey.setGame(game);
        mGameRepository.save(game);
        User user = new User();
        user.setUsername("mUsername1");
        user.setName("mName1");
        user.setEmail("mEmail1");
        user.setPassword("mPassword1");
        user.setCountry("mCountry1");
        user.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse("17/10/2010"));
        mUserRepository.save(user);
        mSellPOJO.setUserId(user.getId());
        mSellPOJO.setPrice(2.4);
        mSellPOJO.setGameKey("key");
        mMockMvc.perform(post("/grid/sell-listing")
                .content(asJsonString(mSellPOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameKey.platform", is("ps4")))
                .andExpect(jsonPath("$.price", is(2.4)))
        ;
        mMockMvc.perform(get("/grid/game")
                .param("id", "" + game.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bestSell.price", is(2.4)))
                .andExpect(jsonPath("$.platforms[0]", is("ps4")))
        ;
    }

    @Test
    @WithMockUser(username = "spring")
    void whenPostingInvalidSellListing_Return404Exception() throws Exception {


        mMockMvc.perform(post("/grid/sell-listing")
                .content(asJsonString(mSellPOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("Could not save Sell Listing"))
        ;
    }

    /*
    @Test
    @WithMockUser(username = "spring")
    void whenPostingValidWishList_ReturnSuccess() throws  Exception {

        Game game = new Game();
        mGameRepository.save(game);
        User user = new User();
        user.setUsername("mUsername1");
        user.setName("mName1");
        user.setEmail("mEmail1");
        user.setPassword("mPassword1");
        user.setCountry("mCountry1");
        user.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse("17/10/2010"));
        mUserRepository.save(user);


        mMockMvc.perform(post("/grid/add-wish-list")
                .param("user_id", String.valueOf(user.getId()))
                .param("game_id", String.valueOf(game.getId()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", is((int)game.getId())));
    }
    */


    @Test
    @WithMockUser(username = "spring")
    void whenPostingInvalidWishList_ReturnException() throws Exception {
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
        mGameRepository.save(game);
        User user = new User();
        user.setUsername("mUsername1");
        user.setName("mName1");
        user.setEmail("mEmail1");
        user.setPassword("mPassword1");
        user.setCountry("mCountry1");
        user.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse("17/10/2010"));
        mUserRepository.save(user);

        mReviewGamePOJO.setAuthor(user.getId());
        mReviewGamePOJO.setGame(game.getId());

        mMockMvc.perform(post("/grid/add-game-review")
                .content(asJsonString(mReviewGamePOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].comment", is(mReviewGamePOJO.getComment())))
                .andExpect(jsonPath("$.[0].score", is(mReviewGamePOJO.getScore())))
                .andExpect(jsonPath("$.[0].author.username", is(user.getUsername())));
    }

    @Test
    @WithMockUser(username = "spring")
    void whenPostingRepeatedGameReview_ReturnException() throws Exception {
        Game game = new Game();
        game.setReviews(new HashSet<ReviewGame>());
        User user = new User();
        user.setReviewGames(new HashSet<ReviewGame>());
        user.setUsername("mUsername1");
        user.setName("mName1");
        user.setEmail("mEmail1");
        user.setPassword("mPassword1");
        user.setCountry("mCountry1");
        user.setBuys(new HashSet<>());
        user.setReportsOnGameReview(new HashSet<>());
        user.setReportsOnUser(new HashSet<>());
        user.setReportsOnUserReview(new HashSet<>());
        user.setReportsThisUser(new HashSet<>());
        user.setReviewUsers(new HashSet<>());
        user.setReviewedUsers(new HashSet<>());
        user.setReviewUsers(new HashSet<>());
        user.setAuctions(new HashSet<>());
        user.setSells(new HashSet<>());
        user.setWishList(new HashSet<>());
        user.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse("17/10/2010"));


        mReviewGamePOJO.setAuthor(1);
        mReviewGamePOJO.setGame(1);

        Set<ReviewGame> gameReviews = game.getReviews();
        Set<ReviewGame> userGameReviews = user.getReviewGames();


        ReviewGame review = new ReviewGame();
        review.setComment(mReviewGamePOJO.getComment());
        review.setScore(mReviewGamePOJO.getScore());
        review.setAuthor(user);
        review.setGame(game);
        review.setDate(mReviewGamePOJO.getDate());

        gameReviews.add(review);
        userGameReviews.add(review);

        user.setReviewGames(userGameReviews);
        game.setReviews(gameReviews);

        mGameRepository.save(game);
        mUserRepository.save(user);


        mMockMvc.perform(post("/grid/add-game-review")
                .content(asJsonString(mReviewGamePOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "spring")
    void whenPostingInvalidGameReview_ReturnException() throws Exception {

        mMockMvc.perform(post("/grid/add-game-review")
                .content(asJsonString(mReviewGamePOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

    }

    @Test
    @WithMockUser(username = "spring")
    void whenPostingValidUserReview_ReturnSuccess() throws Exception {
        User author = new User();
        author.setUsername("mUsername1");
        author.setName("mName1");
        author.setEmail("mEmail1");
        author.setPassword("mPassword1");
        author.setCountry("mCountry1");
        author.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse("17/10/2010"));

        User target = new User();
        target.setUsername("mUsername2");
        target.setName("mName2");
        target.setEmail("mEmail2");
        target.setPassword("mPassword2");
        target.setCountry("mCountry2");
        target.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse("17/10/2010"));


        this.mUserRepository.save(author);
        this.mUserRepository.save(target);

        mReviewUserPOJO.setAuthor(author.getId());
        mReviewUserPOJO.setTarget(target.getId());

        mMockMvc.perform(post("/grid/add-user-review")
                .content(asJsonString(mReviewUserPOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].comment",is(mReviewUserPOJO.getComment())))
                .andExpect(jsonPath("$[0].score",is(mReviewUserPOJO.getScore())))
                .andExpect(jsonPath("$[0].author.username",is(author.getUsername())));
    }

    @Test
    @WithMockUser(username = "spring")
    void whenPostingRepeatedUserReview_ReturnException() throws Exception {
        User author = new User();
        author.setUsername("mUsername1");
        author.setName("mName1");
        author.setEmail("mEmail1");
        author.setPassword("mPassword1");
        author.setCountry("mCountry1");
        author.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse("17/10/2010"));

        User target = new User();
        target.setUsername("mUsername2");
        target.setName("mName2");
        target.setEmail("mEmail2");
        target.setPassword("mPassword2");
        target.setCountry("mCountry2");
        target.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse("17/10/2010"));


        mReviewUserPOJO.setAuthor(1);
        mReviewUserPOJO.setTarget(2);

        ReviewUser review = new ReviewUser();

        review.setComment(mReviewUserPOJO.getComment());
        review.setScore(mReviewUserPOJO.getScore());
        review.setAuthor(author);
        review.setTarget(target);
        review.setDate(mReviewUserPOJO.getDate());


        this.mUserRepository.save(author);
        this.mUserRepository.save(target);
        this.mReviewUserRepository.save(review);

        mMockMvc.perform(post("/grid/add-user-review")
                .content(asJsonString(mReviewUserPOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

    }

    @Test
    @WithMockUser(username = "spring")
    void whenPostingInvalidUserReview_ReturnException() throws Exception {
        mReviewUserPOJO.setAuthor(1);
        mReviewUserPOJO.setTarget(2);
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

        game.setReviews(reviews);

        this.mGameRepository.save(game);

        mMockMvc.perform(get("/grid/game-review")
                .param("game_id", String.valueOf(game.getId()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].comment",is(review.getComment())))
                .andExpect(jsonPath("$[0].score",is(review.getScore())))
                .andExpect(jsonPath("$[0].game.id", is((int) game.getId())));
    }


    @Test
    @WithMockUser(username = "spring")
    void whenGetInvalidGameReviews_ReturnException() throws Exception {

        mMockMvc.perform(get("/grid/game-review")
                .param("game_id", String.valueOf(1))
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
