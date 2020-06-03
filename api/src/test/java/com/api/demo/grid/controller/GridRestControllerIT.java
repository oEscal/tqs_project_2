package com.api.demo.grid.controller;

import com.api.demo.DemoApplication;
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
import com.api.demo.grid.pojos.SellPOJO;

import com.api.demo.grid.repository.DeveloperRepository;
import com.api.demo.grid.repository.GameGenreRepository;
import com.api.demo.grid.repository.GameKeyRepository;
import com.api.demo.grid.repository.GameRepository;
import com.api.demo.grid.repository.PublisherRepository;
import com.api.demo.grid.repository.ReviewUserRepository;
import com.api.demo.grid.repository.SellRepository;
import com.api.demo.grid.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DemoApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
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
    private BuyListingsPOJO mBuyListingsPOJO;
    private ReviewGamePOJO mReviewGamePOJO;
    private ReviewUserPOJO mReviewUserPOJO;
    private static int mNumberUser = 0;
    private static BCryptPasswordEncoder mPasswordEncoder = new BCryptPasswordEncoder();

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
        mReviewGamePOJO = new ReviewGamePOJO("comment", 1, 1, 1);
        mReviewUserPOJO = new ReviewUserPOJO("comment", 1, 1, 2);

        mBuyListingsPOJO = new BuyListingsPOJO();
        mNumberUser = 0;
    }

    @Test
    @WithMockUser(username = "spring", authorities = "ADMIN")
    void whenPostingValidGenre_ReturnValidResponse() throws Exception {

        mMockMvc.perform(post("/grid/games/genre")
                .content(asJsonString(mGameGenrePOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(mGameGenrePOJO.getName()))).andReturn();

        assertFalse(mGameGenreRepository.findByName(mGameGenrePOJO.getName()).isEmpty());
    }

    @Test
    @WithMockUser(username = "spring", authorities = "ADMIN")
    void whenPostingValidPub_ReturnValidResponse() throws Exception {
        mMockMvc.perform(post("/grid/games/publisher")
                .content(asJsonString(mPublisherPOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(mPublisherPOJO.getName())));
        assertFalse(mPublisherRepository.findByName(mPublisherPOJO.getName()).isEmpty());

    }

    @Test
    @WithMockUser(username = "spring", authorities = "ADMIN")
    void whenPostingValidDeveloper_ReturnValidResponse() throws Exception {
        mMockMvc.perform(post("/grid/games/developer")
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

        mMockMvc.perform(post("/grid/games/game")
                .content(asJsonString(mGamePOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(mGamePOJO.getName())))
        ;
        assertFalse(mGameRepository.findAllByNameContaining(mGamePOJO.getName()).isEmpty());
    }

    @Test
    void whenRequestSellListings_ReturnPagedListings() throws Exception {
        Game game = new Game();
        mGameRepository.save(game);

        GameKey gameKey = new GameKey();
        gameKey.setGame(game);
        mGameKeyRepository.save(gameKey);

        Sell sell = new Sell();
        sell.setGameKey(gameKey);
        mSellRepository.save(sell);
        mMockMvc.perform(get("/grid/sell-listing")
                .param("gameId", String.valueOf(game.getId()))
                .param("page", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        ;

    }

    @Test
    void whenRequestSellListings_AndSearchIsInvalid_ThrowException() throws Exception {

        mMockMvc.perform(get("/grid/sell-listing")
                .param("gameId", "1")
                .param("page", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("Game not found in Database"));

    }

    @Test
    @WithMockUser(username = "spring", authorities = "ADMIN")
    void whenPostingInvalidGame_ReturnErrorResponse() throws Exception {

        mGamePOJO.setPublisher(null);

        mMockMvc.perform(post("/grid/games/game")
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
                .andExpect(jsonPath("$.platform", is("ps3")))
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
        User user = createUser();
        mUserRepository.save(user);
        GameKey gameKey = new GameKey();
        gameKey.setRealKey("key");
        mGameKeyRepository.save(gameKey);
        GameKey gameKey2 = new GameKey();
        gameKey2.setRealKey("key2");
        mGameKeyRepository.save(gameKey2);
        mSellPOJO.setUserId(user.getId());
        mSellPOJO.setGameKey("key");
        mMockMvc.perform(post("/grid/sell-listing")
                .content(asJsonString(mSellPOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameKey.id", is(Math.toIntExact(gameKey.getId()))))
                .andExpect(jsonPath("$.userId", is(Math.toIntExact(user.getId()))))
        ;
    }

    @Test
    @WithMockUser(username = "spring")
    void whenPostingValidSellListing_AndAskingGame_ReturnLowestPriceAndPlatformUsed() throws Exception {
        Game game = new Game();
        mGameRepository.save(game);

        GameKey gameKey = new GameKey();
        gameKey.setRealKey("key");
        gameKey.setPlatform("ps4");
        gameKey.setGame(game);
        mGameRepository.save(game);

        User user = createUser();
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

        mMockMvc.perform(get("/grid/games/game")
                .param("id", "" + game.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bestSell.price", is(2.4)))
                .andExpect(jsonPath("$.platforms[0]", is("ps4")))
        ;
    }

    @Test
    @WithMockUser(username = "spring")
    void whenPostingInvalidSellListing_AndGotExceptionDetails_Return409Exception() throws Exception {
        User seller = createUser();
        mUserRepository.save(seller);

        Game game = new Game();
        game.setName("game");
        game.setCoverUrl("cover.jpg");
        mGameRepository.save(game);

        GameKey gameKey = new GameKey();
        gameKey.setGame(game);
        mGameKeyRepository.save(gameKey);

        Sell sell = new Sell();
        sell.setUser(seller);
        sell.setGameKey(gameKey);
        mSellRepository.save(sell);

        mSellPOJO.setUserId(seller.getId());
        mSellPOJO.setPrice(2.4);
        mSellPOJO.setGameKey("key");
        mMockMvc.perform(post("/grid/sell-listing")
                .content(asJsonString(mSellPOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("Could not save Sell Listing"))
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

    @Test
    @WithMockUser(username = "spring")
    void whenPostingValidBuylisting_ReturnBuyList() throws Exception {
        User seller = createUser();
        mUserRepository.save(seller);

        Game game = new Game();
        game.setName("game");
        game.setCoverUrl("cover.jpg");
        mGameRepository.save(game);

        GameKey gameKey = new GameKey();
        gameKey.setGame(game);
        mGameKeyRepository.save(gameKey);

        Sell sell = new Sell();
        sell.setUser(seller);
        sell.setGameKey(gameKey);
        mSellRepository.save(sell);

        User buyer = createUser();
        mUserRepository.save(buyer);

        long[] listingId = {sell.getId()};
        mBuyListingsPOJO.setListingsId(listingId);
        mBuyListingsPOJO.setUserId(buyer.getId());
        mBuyListingsPOJO.setWithFunds(false);

        mMockMvc.perform(post("/grid/buy-listing")
                .content(asJsonString(mBuyListingsPOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
        ;

    }

    @Test
    @WithMockUser(username = "spring")
    void whenPostingValidBuyListing_AndItemHasBeenBought_ThrowException() throws Exception {
        User seller = createUser();
        mUserRepository.save(seller);

        Game game = new Game();
        game.setName("Game");
        mGameRepository.save(game);

        GameKey gameKey = new GameKey();
        gameKey.setRealKey("key");
        gameKey.setGame(game);
        mGameKeyRepository.save(gameKey);

        mUserRepository.save(seller);
        Sell sell = new Sell();
        sell.setGameKey(gameKey);
        sell.setUser(seller);

        User buyer = createUser();
        mUserRepository.save(buyer);

        Buy buy = new Buy();
        buy.setUser(buyer);
        sell.setPurchased(buy);
        mSellRepository.save(sell);

        long[] listingId = {sell.getId()};
        mBuyListingsPOJO.setListingsId(listingId);
        mBuyListingsPOJO.setUserId(buyer.getId());
        mBuyListingsPOJO.setWithFunds(false);

        mMockMvc.perform(post("/grid/buy-listing")
                .content(asJsonString(mBuyListingsPOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("This listing has been bought by another user"));
    }

    @Test
    @WithMockUser(username = "spring")
    void whenPostingValidBuyListing_AndListingHasBeenRemoved_ThrowException() throws Exception {

        Sell sell = this.createSellListing();

        User buyer = createUser();
        mUserRepository.save(buyer);

        long[] listingId = {sell.getId()};

        Optional<User> user = mUserRepository.findById(sell.getUserId());
        user.get().getSells().remove(sell);
        mUserRepository.save(user.get());

        Optional<GameKey> gameKey = mGameKeyRepository.findById(sell.getGameKey().getId());
        gameKey.get().setSell(null);
        mGameKeyRepository.save(gameKey.get());

        mSellRepository.delete(mSellRepository.findById(sell.getId()).get());
        Optional<Sell> sell1 = mSellRepository.findById(sell.getId());
        mBuyListingsPOJO.setListingsId(listingId);
        mBuyListingsPOJO.setUserId(buyer.getId());
        mBuyListingsPOJO.setWithFunds(false);

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
        User buyer = createUser();
        buyer.setFunds(0);
        mUserRepository.save(buyer);

        Sell sell = this.createSellListing();
        sell.setPrice(50);
        mSellRepository.save(sell);

        long[] listingId = {sell.getId()};
        mBuyListingsPOJO.setListingsId(listingId);
        mBuyListingsPOJO.setUserId(buyer.getId());
        mBuyListingsPOJO.setWithFunds(true);

        mMockMvc.perform(post("/grid/buy-listing")
                .content(asJsonString(mBuyListingsPOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("This user doesn't have enough funds"))
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


        mMockMvc.perform(post("/grid/wishlist")
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
        mMockMvc.perform(post("/grid/wishlist")
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

        mMockMvc.perform(post("/grid/reviews/game")
                .content(asJsonString(mReviewGamePOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].comment", is(mReviewGamePOJO.getComment())))
                .andExpect(jsonPath("$.[0].score", is(mReviewGamePOJO.getScore())))
                .andExpect(jsonPath("$.[0].authorUsername", is(user.getUsername())));
    }

    /*
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
        user.setReportsOnGameReview(new HashSet<>());
        user.setReportsOnUser(new HashSet<>());
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

        mUserRepository.save(user);
        mGameRepository.save(game);

        mMockMvc.perform(post("/grid/reviews/game")
                .content(asJsonString(mReviewGamePOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
     */

    @Test
    @WithMockUser(username = "spring")
    void whenPostingInvalidGameReview_ReturnException() throws Exception {

        mMockMvc.perform(post("/grid/reviews/game")
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

        mMockMvc.perform(post("/grid/reviews/user")
                .content(asJsonString(mReviewUserPOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].comment", is(mReviewUserPOJO.getComment())))
                .andExpect(jsonPath("$[0].score", is(mReviewUserPOJO.getScore())))
                .andExpect(jsonPath("$[0].authorUsername", is(author.getUsername())))
                .andExpect(jsonPath("$[0].targetUsername", is(target.getUsername())));
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

        mMockMvc.perform(post("/grid/reviews/user")
                .content(asJsonString(mReviewUserPOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

    }

    @Test
    @WithMockUser(username = "spring")
    void whenPostingInvalidUserReview_ReturnException() throws Exception {
        mReviewUserPOJO.setAuthor(1);
        mReviewUserPOJO.setTarget(2);
        mMockMvc.perform(post("/grid/reviews/user")
                .content(asJsonString(mReviewUserPOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

    }

    @Test
    @WithMockUser(username = "spring")
    void whenGetValidGameReviews_ReturnSuccess() throws Exception {
        Game game = new Game();
        User user = new User();
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
        review.setDate(new SimpleDateFormat("dd/MM/yyyy").parse("17/10/2010"));

        Set<ReviewGame> reviews = new HashSet<>();
        reviews.add(review);

        game.setReviews(reviews);

        this.mGameRepository.save(game);


        mMockMvc.perform(get("/grid/reviews/game")
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
    void whenGetInvalidGameReviews_ReturnException() throws Exception {

        mMockMvc.perform(get("/grid/reviews/game")
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
        review.setComment("comment");
        review.setScore(1);
        review.setAuthor(user);
        review.setGame(game);
        review.setDate(new SimpleDateFormat("dd/MM/yyyy").parse("17/10/2010"));

        Set<ReviewGame> reviews = new HashSet<>();
        reviews.add(review);

        user.setReviewGames(reviews);

        this.mUserRepository.save(user);

        mMockMvc.perform(get("/grid/reviews/user")
                .param("user_id", String.valueOf(user.getId()))
                .param("page", "0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.[0].comment", is(review.getComment())))
                .andExpect(jsonPath("$.content.[0].score", is(review.getScore())));
    }



    @Test
    @WithMockUser(username = "spring")
    void whenGetInvalidUserReviews_ReturnException() throws Exception {

        mMockMvc.perform(get("/grid/reviews/user")
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
        review.setComment("comment");
        review.setScore(1);
        review.setAuthor(user);
        review.setGame(game);
        review.setDate(new SimpleDateFormat("dd/MM/yyyy").parse("17/10/2010"));

        Set<ReviewGame> reviews = new HashSet<>();
        reviews.add(review);

        user.setReviewGames(reviews);

        this.mUserRepository.save(user);


        mMockMvc.perform(get("/grid/reviews/all")
                .param("page", "0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.[0].comment", is(review.getComment())))
                .andExpect(jsonPath("$.content.[0].score", is(review.getScore())));
    }

    @Test
    @WithMockUser(username = "spring")
    void whenGetValidAllUserReviews_ReturnSuccessEmpty() throws Exception {
        mMockMvc.perform(get("/grid/reviews/all")
                .param("page", "0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)));
    }


    @Test
    @WithMockUser(username = "spring")
    void whenGetInvalidAllUserReviews_ReturnException() throws Exception {

        mMockMvc.perform(get("/grid/reviews/all")
                .param("page", "0")
                .param("sort","incorrect")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void whenDeletingValidListing_andIsOwner_ReturnOkListing() throws Exception {
        Sell sell = createSellListing();
        mMockMvc.perform(delete("/grid/sell-listing")
                .with(httpBasic("mUsername0", "mPassword0"))
                .param("id", ""+sell.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price", is(sell.getPrice())))
        ;
        User owner = mUserRepository.findByUsername("mUsername0");
        assertFalse(owner.getSells().contains(sell));
        assertFalse(mSellRepository.existsById(sell.getId()));
    }

    @Test
    void whenDeletingValidListing_andIsAdmin_ReturnOkListing() throws Exception {
        Sell sell = createSellListing();
        User admin = createUser();
        admin.setAdmin(true);
        mUserRepository.save(admin);

        mMockMvc.perform(delete("/grid/sell-listing")
                .with(httpBasic("mUsername1", "mPassword1"))
                .param("id", ""+sell.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price", is(sell.getPrice())))
        ;
        User owner = mUserRepository.findByUsername("mUsername0");
        assertFalse(owner.getSells().contains(sell));
        assertFalse(mSellRepository.existsById(sell.getId()));
    }

    @Test
    void whenDeletingValidListing_andIsNotOwner_norAdmin_Return401Exception() throws Exception {
        Sell sell = createSellListing();
        User admin = createUser();
        mUserRepository.save(admin);

        mMockMvc.perform(delete("/grid/sell-listing")
                .with(httpBasic("mUsername1", "mPassword1"))
                .param("id", ""+sell.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("Don't have permission to delete this listing"))
        ;
    }

    @Test
    void whenDeletingBoughtListing_andIsOwner_Return403Listing() throws Exception {
        Sell sell = createSellListing();
        Buy buy = new Buy();
        buy.setSell(sell);
        mSellRepository.save(sell);

        mMockMvc.perform(delete("/grid/sell-listing")
                .with(httpBasic("mUsername0", "mPassword0"))
                .param("id", ""+sell.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("Listing has already been bought"))
        ;
    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static User createUser() throws ParseException {
        User user = new User();
        user.setUsername("mUsername" + mNumberUser);
        user.setName("mName" + mNumberUser);
        user.setEmail("mEmail" + mNumberUser);
        user.setPassword(mPasswordEncoder.encode("mPassword" + mNumberUser));
        user.setCountry("mCountry" + mNumberUser);
        user.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse("17/10/2010"));
        mNumberUser++;
        return user;
    }

    @SneakyThrows
    private Sell createSellListing() {

        User seller = createUser();
        mUserRepository.save(seller);

        Game game = new Game();
        game.setName("Game");
        mGameRepository.save(game);

        GameKey gameKey = new GameKey();
        gameKey.setRealKey("key");
        gameKey.setGame(game);
        mGameKeyRepository.save(gameKey);

        Sell sell = new Sell();
        sell.setGameKey(gameKey);
        sell.setUser(seller);
        mSellRepository.save(sell);

        return sell;
    }
}
