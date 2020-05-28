package com.api.demo.grid.controller;

import com.api.demo.grid.exception.UnavailableListingException;
import com.api.demo.grid.exception.UnsufficientFundsException;
import com.api.demo.grid.pojos.BuyListingsPOJO;
import com.api.demo.grid.pojos.GameKeyPOJO;
import com.api.demo.grid.pojos.GamePOJO;
import com.api.demo.grid.pojos.SellPOJO;
import com.api.demo.grid.service.ListingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ListingsControllerTest {
    @Autowired
    private MockMvc mMockMvc;

    @MockBean
    private ListingService mListingService;

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
    @WithMockUser(username="spring")
    void whenPostingValidSellListing_ReturnValidSellObject() throws Exception{
        Mockito.when(mListingService.saveSell(Mockito.any(SellPOJO.class))).thenReturn(mSell);
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
        Mockito.when(mListingService.saveSell(Mockito.any(SellPOJO.class))).thenReturn(null);
        mMockMvc.perform(post("/grid/sell-listing")
                .content(asJsonString(mSellPOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("Could not save Sell Listing"))
        ;
    }

    @Test
    @WithMockUser(username="spring")
    void whenPostingValidBuylisting_ReturnBuyList() throws Exception{
        Mockito.when(mGridService.saveBuy(Mockito.any(BuyListingsPOJO.class))).thenReturn(Arrays.asList(mBuy));
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
    @WithMockUser(username="spring")
    void whenPostingValidBuylisting_AndListingHasBeenRemoved_ThrowException() throws Exception{
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
    @WithMockUser(username="spring")
    void whenPostingValidBuylisting_AndUserHasNoFunds_ThrowException() throws Exception{
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
        Mockito.when(mGridService.saveSell(Mockito.any(SellPOJO.class))).thenReturn(null);
        mMockMvc.perform(post("/grid/add-sell-listing")
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
