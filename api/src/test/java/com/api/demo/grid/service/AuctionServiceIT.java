package com.api.demo.grid.service;


import com.api.demo.DemoApplication;
import com.api.demo.grid.exception.ExceptionDetails;
import com.api.demo.grid.models.Auction;
import com.api.demo.grid.models.Game;
import com.api.demo.grid.models.GameKey;
import com.api.demo.grid.models.Sell;
import com.api.demo.grid.models.User;
import com.api.demo.grid.pojos.AuctionPOJO;
import com.api.demo.grid.repository.AuctionRepository;
import com.api.demo.grid.repository.SellRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DemoApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class AuctionServiceIT {

    @Autowired
    private AuctionRepository mAuctionRepository;

    @Autowired
    private SellRepository mSellRepository;

    @Autowired
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


    @Test
    @SneakyThrows
    void whenSetExistentAuctionGameKey_setIsUnsuccessful() {

        // first insertion
        mAuctionService.addAuction(mAuctionPOJO);

        // second insertion
        mAuction.setPrice(2000);
        assertThrows(ExceptionDetails.class, () -> mAuctionService.addAuction(mAuctionPOJO));
        assertEquals(1, mAuctionRepository.findAll().size());
    }

    @Test
    @SneakyThrows
    void whenSetAuctionOfSameSellGameKey_setIsUnsuccessful() {

        // insert game key on sell
        Sell sell = new Sell();
        sell.setGameKey(mGameKey);
        mSellRepository.save(sell);

        // second insertion
        assertThrows(ExceptionDetails.class, () -> mAuctionService.addAuction(mAuctionPOJO));
        assertEquals(1, mAuctionRepository.findAll().size());
    }
}
