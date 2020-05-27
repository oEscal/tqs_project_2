package com.api.demo.grid.repository;

import com.api.demo.grid.models.Auction;
import com.api.demo.grid.models.Game;
import com.api.demo.grid.models.GameKey;
import com.api.demo.grid.models.User;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.validation.ConstraintViolationException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
class AuctionRepositoryTest {

    @Autowired
    private TestEntityManager mEntityManager;

    @Autowired
    private AuctionRepository mAuctionRepository;


    private Auction mAuction;
    private GameKey mGameKey;
    private Game mGame;
    private User mAuctioneer,
            mBuyer;

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
        mEntityManager.persistAndFlush(mAuctioneer);

        // create game
        mGame = new Game();
        mGame.setName(mGameName);
        mEntityManager.persistAndFlush(mGame);

        // create game key
        mGameKey = new GameKey();
        mGameKey.setRKey(mGameKeyRKey);
        mGameKey.setGame(mGame);
        mEntityManager.persistAndFlush(mGameKey);

        // create auction
        mAuction = new Auction();
        mAuction.setGameKey(mGameKey);
        mAuction.setAuctioneer(mAuctioneer);
        mAuction.setPrice(mPrice);
        mAuction.setEndDate(new SimpleDateFormat("dd/MM/yyyy").parse(mEndDate));

        mEntityManager.persistAndFlush(mAuction);
    }

    @Test
    void whenAuctionGameKeyExists_receiveCorrectAuction() {

        assertEquals(mAuction, mAuctionRepository.findByGameKey_rKey(mGameKeyRKey));
    }

    @Test
    void whenAuctionGameKeyNotExists_receiveNothing() {

        assertNull(mAuctionRepository.findByGameKey_rKey("test_key"));
    }

    @Test
    @SneakyThrows
    void whenSetPastAuctionEndDate_setIsUnsuccessful() {

        mAuction.setEndDate(new SimpleDateFormat("dd/MM/yyyy").parse("10/10/1999"));

        assertThrows(ConstraintViolationException.class, () -> mEntityManager.persistAndFlush(mAuction));
    }

    @Test
    @SneakyThrows
    void whenSetStartDate_setIsUnsuccessful() {

        mAuction.setStartDate((new SimpleDateFormat("dd/MM/yyyy").parse("10/10/1999")));
        assertThrows(ConstraintViolationException.class, () -> mEntityManager.persistAndFlush(mAuction));
    }

    @Test
    void whenSetAuctionNegativePrice_setIsUnsuccessful() {

        mAuction.setPrice(-10.3);
        assertThrows(ConstraintViolationException.class, () -> mEntityManager.persistAndFlush(mAuction));
    }
}
