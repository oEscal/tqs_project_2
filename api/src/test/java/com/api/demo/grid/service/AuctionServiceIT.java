package com.api.demo.grid.service;


import com.api.demo.DemoApplication;
import com.api.demo.grid.exception.ExceptionDetails;
import com.api.demo.grid.models.Game;
import com.api.demo.grid.models.GameKey;
import com.api.demo.grid.models.Sell;
import com.api.demo.grid.models.User;
import com.api.demo.grid.pojos.AuctionPOJO;
import com.api.demo.grid.repository.AuctionRepository;
import com.api.demo.grid.repository.GameKeyRepository;
import com.api.demo.grid.repository.GameRepository;
import com.api.demo.grid.repository.SellRepository;
import com.api.demo.grid.repository.UserRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DemoApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AuctionServiceIT {

    @Autowired
    private AuctionRepository mAuctionRepository;

    @Autowired
    private SellRepository mSellRepository;

    @Autowired
    private UserRepository mUserRepository;

    @Autowired
    private GameKeyRepository mGameKeyRepository;

    @Autowired
    private GameRepository mGameRepository;

    @Autowired
    private AuctionService mAuctionService;


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
        mGameKey.setRealKey(mGameKeyRKey);
        mGameKey.setGame(mGame);

        // set auction pojo
        mAuctionPOJO = new AuctionPOJO(mAuctioneerUsername, mGameKeyRKey, mPrice,
                new SimpleDateFormat("dd/MM/yyyy").parse(mEndDate));
    }


    @Test
    @SneakyThrows
    void whenSetExistentAuctionGameKey_setIsUnsuccessful() {

        // save auctioneer, game and game key
        mUserRepository.save(mAuctioneer);
        mGameRepository.save(mGame);
        mGameKeyRepository.save(mGameKey);

        // first insertion
        mAuctionService.addAuction(mAuctionPOJO);

        // second insertion
        mAuctionPOJO.setPrice(2000);
        assertThrows(ExceptionDetails.class, () -> mAuctionService.addAuction(mAuctionPOJO));
        assertEquals(1, mAuctionRepository.findAll().size());
    }

    @Test
    @SneakyThrows
    void whenSetAuctionOfSameSellGameKey_setIsUnsuccessful() {

        // save auctioneer, game and game key
        mUserRepository.save(mAuctioneer);
        mGameRepository.save(mGame);
        mGameKeyRepository.save(mGameKey);

        // insert game key on sell
        Sell sell = new Sell();
        sell.setGameKey(mGameKey);
        mSellRepository.save(sell);

        // second insertion
        assertThrows(ExceptionDetails.class, () -> mAuctionService.addAuction(mAuctionPOJO));
        assertEquals(0, mAuctionRepository.findAll().size());
    }

    @Test
    @SneakyThrows
    void whenSetAuctionWithNonExistentAuctioneer_setIsUnsuccessful() {

        // save game and game key
        mGameRepository.save(mGame);
        mGameKeyRepository.save(mGameKey);


        assertThrows(ExceptionDetails.class, () -> mAuctionService.addAuction(mAuctionPOJO));
        assertEquals(0, mAuctionRepository.findAll().size());
    }

    @Test
    @SneakyThrows
    void whenSetAuctionWithNonExistentGameKey_setIsUnsuccessful() {

        // save auctioneer
        mUserRepository.save(mAuctioneer);


        assertThrows(ExceptionDetails.class, () -> mAuctionService.addAuction(mAuctionPOJO));
        assertEquals(0, mAuctionRepository.findAll().size());
    }
}
