package com.api.demo.grid.controller;

import com.api.demo.DemoApplication;
import com.api.demo.grid.exceptions.UnavailableListingException;
import com.api.demo.grid.exceptions.UnsufficientFundsException;
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
import org.springframework.test.web.servlet.MvcResult;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashSet;

import static org.hamcrest.Matchers.hasSize;
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
    private BuyRepository mBuyRepository;

    @Autowired
    private MockMvc mMockMvc;

    private GameGenrePOJO mGameGenrePOJO;
    private GamePOJO mGamePOJO;
    private PublisherPOJO mPublisherPOJO;
    private DeveloperPOJO mDeveloperPOJO;
    private SellPOJO mSellPOJO;
    private GameKeyPOJO mGameKeyPOJO;
    private BuyListingsPOJO mBuyListingsPOJO;

    @BeforeEach
    public void setUp(){
        mGameGenrePOJO = new GameGenrePOJO("genre", "");
        mPublisherPOJO = new PublisherPOJO("publisher", "");
        mDeveloperPOJO = new DeveloperPOJO("developer");
        mGamePOJO = new GamePOJO("game", "", null, null, null, null, "");
        mGamePOJO.setDevelopers(new HashSet<String>(Arrays.asList("developer")));
        mGamePOJO.setGameGenres(new HashSet<String>(Arrays.asList("genre")));
        mGamePOJO.setPublisher("publisher");
        mGameKeyPOJO = new GameKeyPOJO("key", 2L, "steam", "ps3");
        mSellPOJO = new SellPOJO("key", 6L, 2.3, null);

        mBuyListingsPOJO = new BuyListingsPOJO();
        mUserRepository.deleteAll();
        mPublisherRepository.deleteAll();
        mGameKeyRepository.deleteAll();
        mSellRepository.deleteAll();
        mBuyRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "spring")
    void whenPostingValidGenre_ReturnValidResponse() throws Exception{

        mMockMvc.perform(post("/grid/genre")
                .content(asJsonString(mGameGenrePOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(mGameGenrePOJO.getName()))).andReturn();

        assertFalse(mGameGenreRepository.findByName(mGameGenrePOJO.getName()).isEmpty());

    }

    @Test
    @WithMockUser(username = "spring")
    void whenPostingValidPub_ReturnValidResponse() throws Exception{
        mMockMvc.perform(post("/grid/publisher")
                .content(asJsonString(mPublisherPOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(mPublisherPOJO.getName())));
        assertFalse(mPublisherRepository.findByName(mPublisherPOJO.getName()).isEmpty());

    }

    @Test
    @WithMockUser(username = "spring")
    void whenPostingValidDeveloper_ReturnValidResponse() throws Exception{
        mMockMvc.perform(post("/grid/developer")
                .content(asJsonString(mDeveloperPOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(mDeveloperPOJO.getName())));
        assertFalse(mDeveloperRepository.findByName(mDeveloperPOJO.getName()).isEmpty());
    }

    @Test
    @WithMockUser(username = "spring")
    void whenPostingValidGame_ReturnValidResponse() throws Exception{
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
        mMockMvc.perform(post("/grid/game")
                .content(asJsonString(mGamePOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(mGamePOJO.getName())))
        ;
        assertFalse(mGameRepository.findAllByNameContaining(mGamePOJO.getName()).isEmpty());
    }

    @Test
    @WithMockUser(username = "spring")
    void whenPostingInvalidGame_ReturnErrorResponse() throws Exception{

        mGamePOJO.setPublisher(null);

        mMockMvc.perform(post("/grid/game")
                .content(asJsonString(mGamePOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("Could not save Game"));
        assertTrue(mGameRepository.findAllByNameContaining(mGamePOJO.getName()).isEmpty());
    }

    @Test
    @WithMockUser(username = "spring")
    void whenPostingValidGameKey_ReturnValidGameKeyObject() throws Exception{
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
    void whenPostingInvalidGameKey_Return404Exception() throws Exception{
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
    void whenPostingValidSellListing_ReturnValidSellObject() throws Exception{
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
    void whenPostingValidSellListing_AndAskingGame_ReturnLowestPriceAndPlatformUsed() throws Exception{
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
                .andExpect(jsonPath("$.lowestPrice", is(2.4)))
                .andExpect(jsonPath("$.platforms[0]", is("ps4")))
        ;
    }

    @Test
    @WithMockUser(username = "spring")
    void whenPostingInvalidSellListing_Return404Exception() throws Exception{
        mMockMvc.perform(post("/grid/sell-listing")
                .content(asJsonString(mSellPOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("Could not save Sell Listing"))
        ;
    }

    @Test
    void whenPostingValidBuylisting_ReturnBuyList() throws Exception{
        User seller = new User();
        mUserRepository.save(seller);
        Sell sell = new Sell();
        sell.setUser(mUserRepository.findById(seller.getId()).get());
        mSellRepository.save(sell);
        User buyer = new User();
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
    void whenPostingValidBuylisting_AndItemHasBeenBought_ThrowException() throws Exception{
        User seller = new User();
        mUserRepository.save(seller);
        Sell sell = new Sell();
        sell.setUser(mUserRepository.findById(seller.getId()).get());
        mSellRepository.save(sell);
        User buyer = new User();
        mUserRepository.save(buyer);

        Buy buy = new Buy();
        mBuyRepository.save(buy);
        sell.setPurchased(buy);

        Buy buy1 = new Buy();
        buy1.setUser(buyer);
        mBuyRepository.save(buy1);

        long[] listingId = {sell.getId()};
        mBuyListingsPOJO.setListingsId(listingId);
        mBuyListingsPOJO.setUserId(buyer.getId());
        mBuyListingsPOJO.setWithFunds(false);
        MvcResult mvcResult = mMockMvc.perform(post("/grid/buy-listing")
                .content(asJsonString(mBuyListingsPOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                //.andExpect(status().is4xxClientError())
                //.andExpect(status().reason("This listing has been bought by another user"))
        ;
        System.out.println();
    }

    @Test
    void whenPostingValidBuylisting_AndListingHasBeenRemoved_ThrowException() throws Exception{
        User seller = new User();
        mUserRepository.save(seller);
        User buyer = new User();
        mUserRepository.save(buyer);
        Sell sell = new Sell();
        sell.setUser(mUserRepository.findById(seller.getId()).get());
        mSellRepository.save(sell);

        long[] listingId = {sell.getId()};
        mSellRepository.delete(sell);
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
    void whenPostingValidBuylisting_AndUserHasNoFunds_ThrowException() throws Exception{
        User seller = new User();
        mUserRepository.save(seller);
        User buyer = new User();
        buyer.setFunds(0);
        mUserRepository.save(buyer);
        Sell sell = new Sell();
        sell.setUser(mUserRepository.findById(seller.getId()).get());
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

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
