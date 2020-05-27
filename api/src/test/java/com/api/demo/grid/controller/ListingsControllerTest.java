package com.api.demo.grid.controller;

import com.api.demo.grid.pojos.GameKeyPOJO;
import com.api.demo.grid.pojos.SellPOJO;
import com.api.demo.grid.service.ListingService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

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
}
