package com.api.demo.grid.service;

import com.api.demo.grid.models.Auction;
import com.api.demo.grid.models.Game;
import com.api.demo.grid.models.GameKey;
import com.api.demo.grid.models.User;
import com.api.demo.grid.pojos.AuctionPOJO;
import com.api.demo.grid.repository.AuctionRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class AuctionServiceTest {

    @Mock(lenient = true)
    private AuctionRepository mAuctionRepository;

    @InjectMocks
    private AuctionService mAuctionService;


    private Auction mAuction;
    private GameKey mGameKey;
    private Game mGame;
    private User mAuctioneer;

    private AuctionPOJO mAuctionPOJO;

    private double mPrice = 10.20;
    private String mEndDate = "10/11/2020";

    // auctioneer info
    private String mAuctioneerUsername = "username1",
            mAuctioneerName = "name1",
            mAuctioneerEmail = "email1",
            mAuctioneerCountry = "country1",
            mAuctioneerPassword = "password1",
            mAuctioneerBirthDateStr = "17/10/2010",
            mAuctioneerStartDateStr = "25/05/2020";

    // game info
    private String mGameName = "game1",
            mGameKeyRKey = "game_key1";


    @BeforeEach
    @SneakyThrows
    void setup() {

        // create auctioneer
        mAuctioneer = new User();
        mAuctioneer.setUsername(mAuctioneerUsername);
        mAuctioneer.setName(mAuctioneerName);
        mAuctioneer.setEmail(mAuctioneerEmail);
        mAuctioneer.setPassword(mAuctioneerPassword);
        mAuctioneer.setCountry(mAuctioneerCountry);
        mAuctioneer.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse(mAuctioneerBirthDateStr));
        mAuctioneer.setStartDate(new SimpleDateFormat("dd/MM/yyyy").parse(mAuctioneerStartDateStr));

        // create game
        mGame = new Game();
        mGame.setName(mGameName);

        // create game key
        mGameKey = new GameKey();
        mGameKey.setRKey(mGameKeyRKey);
        mGameKey.setGame(mGame);

        // create auction
        mAuction = new Auction();
        mAuction.setGameKey(mGameKey);
        mAuction.setAuctioneer(mAuctioneer);
        mAuction.setPrice(mPrice);
        mAuction.setEndDate(new SimpleDateFormat("dd/MM/yyyy").parse(mEndDate));

        // set auction pojo
        mAuctionPOJO = new AuctionPOJO(mAuctioneerUsername, mGameKeyRKey, mPrice,
                new SimpleDateFormat("dd/MM/yyyy").parse(mEndDate));
    }

    @AfterEach
    void afterEach() {
        mAuctionRepository.deleteAll();
    }


    /***
     *  Get Auction
     ***/
    @Test
    void whenAuctionGameKeyExists_receiveCorrectAuction() {

        given(mAuctionRepository.findByGameKey_rKey(mGameKeyRKey)).willReturn(mAuction);

        assertEquals(mAuction, mAuctionService.getAuctionByGameKey(mGameKeyRKey));
    }

    @Test
    void whenAuctionGameKeyNotExists_receiveNothing() {

        assertNull(mAuctionService.getAuctionByGameKey(mGameKeyRKey));
    }


    /***
     *  Save Auction
     ***/
    @Test
    void whenSaveNewAuction_insertIsSuccessful() {

        given(mAuctionRepository.save(mAuction)).willReturn(mAuction);

        assertDoesNotThrow(() -> mAuctionService.addAuction(mAuctionPOJO));
    }

    @Test
    void whenSaveNewAuction_insertReceiveAuction() {

        given(mAuctionRepository.save(mAuction)).willReturn(mAuction);

        assertEquals(mAuction, mAuctionService.addAuction(mAuctionPOJO));
    }
}