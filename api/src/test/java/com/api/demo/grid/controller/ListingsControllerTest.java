package com.api.demo.grid.controller;

import com.api.demo.grid.exception.GameNotFoundException;
import com.api.demo.grid.exception.UnavailableListingException;
import com.api.demo.grid.exception.UnsufficientFundsException;
import com.api.demo.grid.models.*;
import com.api.demo.grid.pojos.BuyListingsPOJO;
import com.api.demo.grid.pojos.GameKeyPOJO;
import com.api.demo.grid.pojos.GamePOJO;
import com.api.demo.grid.pojos.SellPOJO;
import com.api.demo.grid.service.ListingService;
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

import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class ListingsControllerTest {
    @Autowired
    private MockMvc mMockMvc;

    @MockBean
    private ListingService mListingService;

    private Game mGame;
    private Sell mSell;
    private User mUser;
    private Buy mBuy;
    private User mBuyer;
    private GameKey mGameKey;
    private SellPOJO mSellPOJO;
    private GameKeyPOJO mGameKeyPOJO;
    private BuyListingsPOJO mBuyListingsPOJO;

    @BeforeEach
    void setUp(){
        mGame = new Game();
        mGame.setDescription("");
        mGame.setName("game");
        mGame.setId(1L);
        mGame.setCoverUrl("");

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

        mBuyer = new User();
        mBuyer.setId(5L);

        mBuy = new Buy();
        mBuy.setSell(mSell);
        mBuy.setUser(mBuyer);
        mBuy.setDate(new Date());
        mBuy.setId(6l);

        long[] buyList = {6};
        mBuyListingsPOJO = new BuyListingsPOJO(5l, buyList, false);
    }

    @Test
    @WithMockUser(username="spring")
    void whenPostingValidSellListing_ReturnValidSellObject() throws Exception{
        Mockito.when(mListingService.saveSell(Mockito.any(SellPOJO.class))).thenReturn(mSell);
        mMockMvc.perform(post("/grid/add-sell-listing")
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
        Mockito.when(mListingService.saveSell(Mockito.any(SellPOJO.class))).thenReturn(null);
        mMockMvc.perform(post("/grid/add-sell-listing")
                .content(asJsonString(mSellPOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("Could not save Sell Listing"))
        ;
    }

    @Test
    @WithMockUser(username="spring")
    void whenPostingValidBuylisting_ReturnBuyList() throws Exception{
        Mockito.when(mListingService.saveBuy(Mockito.any(BuyListingsPOJO.class))).thenReturn(Arrays.asList(mBuy));
        mMockMvc.perform(post("/grid/buy-listing")
                .content(asJsonString(mBuyListingsPOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
        ;
    }

    @Test
    @WithMockUser(username="spring")
    void whenPostingValidBuylisting_AndItemHasBeenBought_ThrowException() throws Exception{
        Mockito.when(mListingService.saveBuy(Mockito.any(BuyListingsPOJO.class)))
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
    @WithMockUser(username="spring")
    void whenPostingValidBuylisting_AndListingHasBeenRemoved_ThrowException() throws Exception{
        Mockito.when(mListingService.saveBuy(Mockito.any(BuyListingsPOJO.class)))
                .thenThrow(new UnavailableListingException("This listing has been removed by the user"));
        mMockMvc.perform(post("/grid/buy-listing")
                .content(asJsonString(mBuyListingsPOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("This listing has been removed by the user"))
        ;
    }

    @Test
    @WithMockUser(username="spring")
    void whenPostingValidBuylisting_AndUserHasNoFunds_ThrowException() throws Exception{
        Mockito.when(mListingService.saveBuy(Mockito.any(BuyListingsPOJO.class)))
                .thenThrow(new UnsufficientFundsException("This user doesn't have enough funds"));
        mMockMvc.perform(post("/grid/buy-listing")
                .content(asJsonString(mBuyListingsPOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("This user doesn't have enough funds"))
        ;
    }

    @Test
    @WithMockUser(username="spring")
    void whenPostingValidGameKey_ReturnValidGameKeyObject() throws Exception{
        Mockito.when(mListingService.saveGameKey(Mockito.any(GameKeyPOJO.class))).thenReturn(mGameKey);
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
        Mockito.when(mListingService.saveGameKey(Mockito.any(GameKeyPOJO.class))).thenReturn(null);
        mMockMvc.perform(post("/grid/gamekey")
                .content(asJsonString(mGameKeyPOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("Could not save Game Key"))
        ;
    }

    @Test
    void whenRequestSellListings_ReturnPagedListings() throws Exception {
        Pagination<Sell> pagination = new Pagination<>(Arrays.asList(mSell));
        Page<Sell> sells = pagination.pageImpl(1, 1);

        int page = 1;
        Mockito.when(mListingService.getAllSellListings(1, page)).thenReturn(sells);

        mMockMvc.perform(get("/grid/sell-listing")
                .param("gameId", String.valueOf(1))
                .param("page", String.valueOf(page))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].id", is(4)));

        Mockito.verify(mListingService, Mockito.times(1)).getAllSellListings(anyLong(), anyInt());
    }

    @Test
    void whenRequestSellListings_AndSearchIsInvalid_ThrowException() throws Exception {
        int page = 1;
        Mockito.when(mListingService.getAllSellListings(1, page))
                .thenThrow(new GameNotFoundException("Game not found in the database"));

        mMockMvc.perform(get("/grid/sell-listing")
                .param("gameId", String.valueOf(1))
                .param("page", String.valueOf(page))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("Game not found in Database"));

    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
