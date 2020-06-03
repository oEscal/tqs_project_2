package com.api.demo.grid.service;

import com.api.demo.grid.exception.GameNotFoundException;
import com.api.demo.grid.models.Auction;
import com.api.demo.grid.models.Game;
import com.api.demo.grid.models.GameKey;
import com.api.demo.grid.models.User;
import com.api.demo.grid.pojos.AuctionPOJO;
import com.api.demo.grid.repository.*;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class AuctionServiceTest {

    @Mock(lenient = true)
    private AuctionRepository mAuctionRepository;

    @Mock(lenient = true)
    private UserRepository mUserRepository;

    @Mock(lenient = true)
    private GameKeyRepository mGameKeyRepository;

    @Mock(lenient = true)
    private GameRepository mGameRepository;

    @Mock(lenient = true)
    private SellRepository mSellRepository;

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
        mGameKey.setRealKey(mGameKeyRKey);
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

        given(mAuctionRepository.findByGameKey_RealKey(mGameKeyRKey)).willReturn(mAuction);

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

        // mock user repository
        given(mUserRepository.findByUsername(mAuctioneerUsername)).willReturn(mAuctioneer);

        // mock game key repository
        given(mGameKeyRepository.findByRealKey(mGameKeyRKey)).willReturn(java.util.Optional.ofNullable(mGameKey));

        // mock sell repository
        given(mSellRepository.findByGameKey_RealKey(mGameKeyRKey)).willReturn(null);

        // mock auction repository
        given(mAuctionRepository.save(mAuction)).willReturn(mAuction);

        assertDoesNotThrow(() -> mAuctionService.addAuction(mAuctionPOJO));
    }

    @Test
    @SneakyThrows
    void whenSaveNewAuction_insertReceiveAuction() {

        // mock user repository
        given(mUserRepository.findByUsername(mAuctioneerUsername)).willReturn(mAuctioneer);

        // mock game key repository
        given(mGameKeyRepository.findByRealKey(mGameKeyRKey)).willReturn(java.util.Optional.ofNullable(mGameKey));

        // mock sell repository
        given(mSellRepository.findByGameKey_RealKey(mGameKeyRKey)).willReturn(null);

        // mock auction repository
        given(mAuctionRepository.save(mAuction)).willReturn(mAuction);

        assertEquals(mAuction, mAuctionService.addAuction(mAuctionPOJO));
    }


    /***
     *  Get all current auctions
     ***/
    @Test
    @SneakyThrows
    void whenGetAllCurrentAuctions_getIsSuccessful() {

        List<Auction> auctions = new ArrayList<>();
        auctions.add(mAuction);

        given(mGameRepository.findById((long) 1)).willReturn(java.util.Optional.of(new Game()));

        given(mAuctionRepository.findAllByGameWithEndDateAfterCurrent(1)).willReturn(auctions);

        assertEquals(auctions, mAuctionService.getAllAuctionsListings(1));
    }

    @Test
    @SneakyThrows
    void whenGetAllCurrentAuctionsAndThereAreNoAuctions_getEmptyList() {

        given(mGameRepository.findById((long) 1)).willReturn(java.util.Optional.of(new Game()));

        assertEquals(0, mAuctionService.getAllAuctionsListings(1).size());
    }

    @Test
    @SneakyThrows
    void whenGetAllCurrentAuctionsAndTheGameDoesNotExist_getError() {

        List<Auction> auctions = new ArrayList<>();
        auctions.add(mAuction);

        given(mAuctionRepository.findAllByGameWithEndDateAfterCurrent(1)).willReturn(auctions);

        assertThrows(GameNotFoundException.class, () -> mAuctionService.getAllAuctionsListings(1));
    }
}
