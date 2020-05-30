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
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
    private User mAuctioneer,
            mBuyer1,
            mBuyer2;

    private AuctionPOJO mAuctionPOJO;

    private double mPrice = 10.20;
    private String mEndDate = "10/11/2030";

    // auctioneer info
    private String mAuctioneerUsername = "username1",
            mAuctioneerName = "name1",
            mAuctioneerEmail = "email1",
            mAuctioneerCountry = "country1",
            mAuctioneerPassword = "password1",
            mAuctioneerBirthDateStr = "17/10/2010",
            mAuctioneerStartDateStr = "25/05/2020";

    // buyer 1 info
    private String mBuyer1Username = "buyer1",
            mBuyer1Name = "name1",
            mBuyer1Email = "buyer_email1",
            mBuyer1Country = "country1",
            mBuyer1Password = "password1",
            mBuyer1BirthDateStr = "17/10/2010",
            mBuyer1StartDateStr = "25/05/2020";

    // buyer 2 info
    private String mBuyer2Username = "buyer2",
            mBuyer2Name = "name2",
            mBuyer2Email = "buyer_email2",
            mBuyer2Country = "country1",
            mBuyer2Password = "password1",
            mBuyer2BirthDateStr = "17/10/2010",
            mBuyer2StartDateStr = "25/05/2020";

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

        // create buyer 1
        mBuyer1 = new User();
        mBuyer1.setUsername(mBuyer1Username);
        mBuyer1.setName(mBuyer1Name);
        mBuyer1.setEmail(mBuyer1Email);
        mBuyer1.setPassword(mBuyer1Password);
        mBuyer1.setCountry(mBuyer1Country);
        mBuyer1.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse(mBuyer1BirthDateStr));
        mBuyer1.setStartDate(new SimpleDateFormat("dd/MM/yyyy").parse(mBuyer1StartDateStr));

        // create buyer 2
        mBuyer2 = new User();
        mBuyer2.setUsername(mBuyer2Username);
        mBuyer2.setName(mBuyer2Name);
        mBuyer2.setEmail(mBuyer2Email);
        mBuyer2.setPassword(mBuyer2Password);
        mBuyer2.setCountry(mBuyer2Country);
        mBuyer2.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse(mBuyer2BirthDateStr));
        mBuyer2.setStartDate(new SimpleDateFormat("dd/MM/yyyy").parse(mBuyer2StartDateStr));

        // create game
        mGame = new Game();
        mGame.setName(mGameName);

        // create game key
        mGameKey = new GameKey();
        mGameKey.setRKey(mGameKeyRKey);
        mGameKey.setGame(mGame);

        // set auction pojo
        mAuctionPOJO = new AuctionPOJO(mAuctioneerUsername, mGameKeyRKey, mPrice,
                new SimpleDateFormat("dd/MM/yyyy").parse(mEndDate));
    }


    /***
     *  Add Auction
     ***/
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


    /***
     *  Add Bid
     ***/
    @Test
    @SneakyThrows
    void whenSetBidUpperThanInitialPrice_setIsSuccessful() {

        // save auctioneer, buyer, game and game key
        mUserRepository.save(mAuctioneer);
        mUserRepository.save(mBuyer1);
        mGameRepository.save(mGame);
        mGameKeyRepository.save(mGameKey);

        // insertion auction
        mAuctionService.addAuction(mAuctionPOJO);

        double newPrice = mPrice + 1.3;
        Auction resultantAuction = mAuctionService.addBidding(mBuyer1Username, mGameKeyRKey, newPrice);

        // verify the buyer
        assertEquals(mBuyer1Username, resultantAuction.getBuyer().getUsername());

        // verify price
        assertEquals(newPrice, resultantAuction.getPrice());
    }

    @Test
    @SneakyThrows
    void whenSetBidUpperThanCurrentPriceFromOtherUser_setIsSuccessful() {

        // save auctioneer, buyer 1, buyer 2, game and game key
        mUserRepository.save(mAuctioneer);
        mUserRepository.save(mBuyer1);
        mUserRepository.save(mBuyer2);
        mGameRepository.save(mGame);
        mGameKeyRepository.save(mGameKey);

        // insertion auction
        mAuctionService.addAuction(mAuctionPOJO);

        // first bid
        mAuctionService.addBidding(mBuyer1Username, mGameKeyRKey, mPrice + 1.3);

        // second bid
        Auction resultantAuction = mAuctionService.addBidding(mBuyer2Username, mGameKeyRKey, mPrice + 2.5);

        double newPrice = mPrice + 2.5;

        // verify the buyer
        assertEquals(mBuyer2Username, resultantAuction.getBuyer().getUsername());

        // verify price
        assertEquals(newPrice, resultantAuction.getPrice());
    }

    @Test
    @SneakyThrows
    void whenSetBidLowerThanCurrentPrice_setIsUnsuccessful() {

        // save auctioneer, buyer 1, buyer 2, game and game key
        mUserRepository.save(mAuctioneer);
        mUserRepository.save(mBuyer1);
        mUserRepository.save(mBuyer2);
        mGameRepository.save(mGame);
        mGameKeyRepository.save(mGameKey);

        // insertion auction
        mAuctionService.addAuction(mAuctionPOJO);

        double newPrice = mPrice + 2.5;

        // first bid
        mAuctionService.addBidding(mBuyer1Username, mGameKeyRKey, newPrice);

        assertThrows(ExceptionDetails.class, () -> mAuctionService.addBidding(mBuyer2Username,
                mGameKeyRKey, newPrice - 1.3));

        Auction resultantAuction = mAuctionRepository.findByGameKey_rKey(mGameKeyRKey);

        // verify the buyer
        assertNotEquals(mBuyer2Username, resultantAuction.getBuyer().getUsername());

        // verify price
        assertEquals(newPrice, resultantAuction.getPrice());
    }

    @Test
    @SneakyThrows
    void whenSetBidEqualsCurrentPrice_setIsUnsuccessful() {

        // save auctioneer, buyer 1, buyer 2, game and game key
        mUserRepository.save(mAuctioneer);
        mUserRepository.save(mBuyer1);
        mUserRepository.save(mBuyer2);
        mGameRepository.save(mGame);
        mGameKeyRepository.save(mGameKey);

        // insertion auction
        mAuctionService.addAuction(mAuctionPOJO);

        double newPrice = mPrice + 2.5;

        // first bid
        mAuctionService.addBidding(mBuyer1Username, mGameKeyRKey, newPrice);

        assertThrows(ExceptionDetails.class, () -> mAuctionService.addBidding(mBuyer2Username, mGameKeyRKey, newPrice));

        Auction resultantAuction = mAuctionRepository.findByGameKey_rKey(mGameKeyRKey);

        // verify the buyer
        assertNotEquals(mBuyer2Username, resultantAuction.getBuyer().getUsername());

        // verify price
        assertEquals(newPrice, resultantAuction.getPrice());
    }

    @Test
    @SneakyThrows
    void whenSetBidUpperWithNonexistentBuyer_setIsUnsuccessful() {

        // save auctioneer, game and game key
        mUserRepository.save(mAuctioneer);
        mGameRepository.save(mGame);
        mGameKeyRepository.save(mGameKey);

        // insertion auction
        mAuctionService.addAuction(mAuctionPOJO);

        assertThrows(ExceptionDetails.class, () -> mAuctionService.addBidding(mBuyer1Username, mGameKeyRKey,
                mPrice + 1.3));
    }

    @Test
    @SneakyThrows
    void whenSetBidUpperForNonexistentAuction_setIsUnsuccessful() {

        // save auctioneer and buyer
        mUserRepository.save(mAuctioneer);
        mUserRepository.save(mBuyer1);

        assertThrows(ExceptionDetails.class, () -> mAuctionService.addBidding(mBuyer1Username, mGameKeyRKey,
                mPrice + 1.3));
    }

    @Test
    @SneakyThrows
    void whenSetBidWithSameBuyerAsCurrent_setIsUnsuccessful() {

        // save auctioneer, buyer 1, game and game key
        mUserRepository.save(mAuctioneer);
        mUserRepository.save(mBuyer1);
        mGameRepository.save(mGame);
        mGameKeyRepository.save(mGameKey);

        // insertion auction
        mAuctionService.addAuction(mAuctionPOJO);

        double newPrice = mPrice + 2.5;

        // first bid
        mAuctionService.addBidding(mBuyer1Username, mGameKeyRKey, newPrice);

        assertThrows(ExceptionDetails.class, () -> mAuctionService.addBidding(mBuyer1Username, mGameKeyRKey,
                newPrice + 1.3));
    }

    @Test
    @SneakyThrows
    void whenSetBidWithSameBuyerAsAuctioneer_setIsUnsuccessful() {

        // save auctioneer, game and game key
        mUserRepository.save(mAuctioneer);
        mGameRepository.save(mGame);
        mGameKeyRepository.save(mGameKey);

        // insertion auction
        mAuctionService.addAuction(mAuctionPOJO);

        double newPrice = mPrice + 2.5;

        assertThrows(ExceptionDetails.class, () -> mAuctionService.addBidding(mAuctioneerUsername, mGameKeyRKey,
                newPrice + 1.3));
    }
}
