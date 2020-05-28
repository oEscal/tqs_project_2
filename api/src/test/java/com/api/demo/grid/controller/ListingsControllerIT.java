package com.api.demo.grid.controller;

import com.api.demo.DemoApplication;
import com.api.demo.grid.models.*;
import com.api.demo.grid.pojos.BuyListingsPOJO;
import com.api.demo.grid.pojos.GameKeyPOJO;
import com.api.demo.grid.pojos.SellPOJO;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DemoApplication.class)
public class ListingsControllerIT {

    @Autowired
    private MockMvc mMockMvc;

    @Autowired
    private GameRepository mGameRepository;

    @Autowired
    private UserRepository mUserRepository;

    @Autowired
    private SellRepository mSellRepository;

    @Autowired
    private GameKeyRepository mGameKeyRepository;

    @Autowired
    private BuyRepository mBuyRepository;

    private SellPOJO mSellPOJO;
    private GameKeyPOJO mGameKeyPOJO;
    private BuyListingsPOJO mBuyListingsPOJO;
    private static int mNumberUser = 0;

    @BeforeEach
    void setUp(){
        mGameKeyPOJO = new GameKeyPOJO("key", 2L, "steam", "ps3");
        mSellPOJO = new SellPOJO("key", 6L, 2.3, null);

        mBuyListingsPOJO = new BuyListingsPOJO();

        mUserRepository.deleteAll();
        mGameRepository.deleteAll();
        mGameKeyRepository.deleteAll();
        mSellRepository.deleteAll();
        mBuyRepository.deleteAll();
    }

    @Test
    void whenRequestSellListings_ReturnPagedListings() throws Exception {
        Game game = new Game();
        GameKey gameKey = new GameKey();
        gameKey.setGame(game);
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
        User user = createUser();
        mUserRepository.save(user);
        GameKey gameKey = new GameKey();
        gameKey.setRKey("key");
        mGameKeyRepository.save(gameKey);
        mSellPOJO.setUserId(user.getId());
        mSellPOJO.setGameKey("key");
        mMockMvc.perform(post("/grid/add-sell-listing")
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
        User user = createUser();
        mUserRepository.save(user);
        mSellPOJO.setUserId(user.getId());
        mSellPOJO.setPrice(2.4);
        mSellPOJO.setGameKey("key");
        mMockMvc.perform(post("/grid/add-sell-listing")
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
    void whenPostingInvalidSellListing_Return404Exception() throws Exception{

        mMockMvc.perform(post("/grid/add-sell-listing")
                .content(asJsonString(mSellPOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("Could not save Sell Listing"))
        ;
    }

    @Test
    void whenPostingValidBuylisting_ReturnBuyList() throws Exception{
        User seller = createUser();
        mUserRepository.save(seller);
        Sell sell = new Sell();
        sell.setUser(mUserRepository.findById(seller.getId()).get());
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
    void whenPostingValidBuylisting_AndItemHasBeenBought_ThrowException() throws Exception{
        User seller = createUser();
        mUserRepository.save(seller);
        Sell sell = new Sell();
        sell.setUser(seller);
        mSellRepository.save(sell);
        User buyer = createUser();
        mUserRepository.save(buyer);

        Buy buy = new Buy();
        sell.setPurchased(buy);
        mBuyRepository.save(buy);

        Buy buy1 = new Buy();
        buy1.setUser(buyer);
        mBuyRepository.save(buy1);

        long[] listingId = {sell.getId()};
        mBuyListingsPOJO.setListingsId(listingId);
        mBuyListingsPOJO.setUserId(buyer.getId());
        mBuyListingsPOJO.setWithFunds(false);
        mMockMvc.perform(post("/grid/buy-listing")
                .content(asJsonString(mBuyListingsPOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("This listing has been bought by another user"))
        ;
        System.out.println();
    }

    @Test
    void whenPostingValidBuylisting_AndListingHasBeenRemoved_ThrowException() throws Exception{
        User seller = createUser();
        mUserRepository.save(seller);
        User buyer = createUser();
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
        User seller = createUser();
        mUserRepository.save(seller);
        User buyer = createUser();
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

    private static User createUser() throws ParseException {
        User user = new User();
        user.setUsername("mUsername" + mNumberUser);
        user.setName("mName" + mNumberUser);
        user.setEmail("mEmail" + mNumberUser);
        user.setPassword("mPassword" + mNumberUser);
        user.setCountry("mCountry" + mNumberUser);
        user.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse("17/10/2010"));
        mNumberUser++;
        return user;
    }
}
