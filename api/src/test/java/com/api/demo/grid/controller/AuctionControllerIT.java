package com.api.demo.grid.controller;


import com.api.demo.DemoApplication;
import com.api.demo.grid.dtos.UserDTO;
import com.api.demo.grid.models.Game;
import com.api.demo.grid.models.GameKey;
import com.api.demo.grid.models.User;
import com.api.demo.grid.pojos.AuctionPOJO;
import com.api.demo.grid.repository.AuctionRepository;
import com.api.demo.grid.repository.GameKeyRepository;
import com.api.demo.grid.repository.GameRepository;
import com.api.demo.grid.service.UserService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.text.SimpleDateFormat;

import static com.api.demo.grid.utils.AuctionJson.addAuctionJson;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DemoApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AuctionControllerIT {

    @Autowired
    private MockMvc mMvc;

    @Autowired
    private UserService mUserService;

    @Autowired
    private AuctionRepository mAuctionRepository;

    @Autowired
    private GameKeyRepository mGameKeyRepository;

    @Autowired
    private GameRepository mGameRepository;


    private GameKey mGameKey;
    private Game mGame;
    private User mAuctioneer;
    private UserDTO mAuctioneerDTO;

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

    // auction json
    private String mAuctionJson;


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

        // create dto auctioneer
        this.mAuctioneerDTO = new UserDTO(mAuctioneerUsername, mAuctioneerName, mAuctioneerEmail, mAuctioneerCountry,
                mAuctioneerPassword, new SimpleDateFormat("dd/MM/yyyy").parse(mAuctioneerBirthDateStr));

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

        // auction json
        mAuctionJson = addAuctionJson(mAuctioneerUsername, mGameKeyRKey, mEndDate, mPrice);
    }


    @Test
    @SneakyThrows
    void whenCreateCompleteFormAuction_creationIsSuccessful() {

        // save auctioneer, game and game key
        mUserService.saveUser(mAuctioneerDTO);
        mGameRepository.save(mGame);
        mGameKeyRepository.save(mGameKey);

        RequestBuilder request = post("/grid/create-auction").contentType(MediaType.APPLICATION_JSON)
                .content(mAuctionJson).with(httpBasic(mAuctioneerUsername, mAuctioneerPassword));

        mMvc.perform(request).andExpect(status().isOk())
                .andExpect(jsonPath("$.auctioneer", is(mAuctioneerUsername)))
                .andExpect(jsonPath("$.buyer", is(nullValue())))
                .andExpect(jsonPath("$.gameKey", is(mGameKeyRKey)))
                .andExpect(jsonPath("$.endDate", is(mEndDate)))
                .andExpect(jsonPath("$.price", is(mPrice)));
        assertEquals(1, mAuctionRepository.findAll().size());
    }

    @Test
    @SneakyThrows
    void whenCreateAuctionWithoutPrice_creationIsUnsuccessful() {

        // save auctioneer, game and game key
        mUserService.saveUser(mAuctioneerDTO);
        mGameRepository.save(mGame);
        mGameKeyRepository.save(mGameKey);

        mAuctionJson = addAuctionJson(mAuctioneerUsername, mGameKeyRKey, mEndDate);

        RequestBuilder request = post("/grid/create-auction").contentType(MediaType.APPLICATION_JSON)
                .content(mAuctionJson).with(httpBasic(mAuctioneerUsername, mAuctioneerPassword));

        mMvc.perform(request).andExpect(status().isBadRequest());
        assertEquals(0, mAuctionRepository.findAll().size());
    }

    @Test
    @SneakyThrows
    void whenCreateAuctionWithoutGameKey_creationIsUnsuccessful() {

        // save auctioneer, game and game key
        mUserService.saveUser(mAuctioneerDTO);
        mGameRepository.save(mGame);
        mGameKeyRepository.save(mGameKey);

        mAuctionJson = addAuctionJson(mAuctioneerUsername, null, mEndDate, mPrice);

        RequestBuilder request = post("/grid/create-auction").contentType(MediaType.APPLICATION_JSON)
                .content(mAuctionJson).with(httpBasic(mAuctioneerUsername, mAuctioneerPassword));

        mMvc.perform(request).andExpect(status().isBadRequest());
        assertEquals(0, mAuctionRepository.findAll().size());
    }

    @Test
    @SneakyThrows
    void whenCreateAuctionWithoutEndDate_creationIsUnsuccessful() {

        // save auctioneer, game and game key
        mUserService.saveUser(mAuctioneerDTO);
        mGameRepository.save(mGame);
        mGameKeyRepository.save(mGameKey);

        mAuctionJson = addAuctionJson(mAuctioneerUsername, mGameKeyRKey, null, mPrice);

        RequestBuilder request = post("/grid/create-auction").contentType(MediaType.APPLICATION_JSON)
                .content(mAuctionJson).with(httpBasic(mAuctioneerUsername, mAuctioneerPassword));

        mMvc.perform(request).andExpect(status().isBadRequest());
        assertEquals(0, mAuctionRepository.findAll().size());
    }

    @Test
    @SneakyThrows
    void whenCreateAuctionWithoutAuctioneer_creationIsUnsuccessful() {

        // save auctioneer, game and game key
        mUserService.saveUser(mAuctioneerDTO);
        mGameRepository.save(mGame);
        mGameKeyRepository.save(mGameKey);

        mAuctionJson = addAuctionJson(null, mGameKeyRKey, mEndDate, mPrice);

        RequestBuilder request = post("/grid/create-auction").contentType(MediaType.APPLICATION_JSON)
                .content(mAuctionJson).with(httpBasic(mAuctioneerUsername, mAuctioneerPassword));

        mMvc.perform(request).andExpect(status().isBadRequest());
        assertEquals(0, mAuctionRepository.findAll().size());
    }

    @Test
    @SneakyThrows
    void whenCreateAuctionWithAuctioneerDifferentFromAuthenticatedUser_creationIsUnsuccessful() {

        // create fake auctioneer
        String fakeAuctioneerUsername = "fake_username";
        UserDTO fakeAuctioneer = new UserDTO(fakeAuctioneerUsername, mAuctioneerName, "fake_email", mAuctioneerCountry,
                mAuctioneerPassword, new SimpleDateFormat("dd/MM/yyyy").parse(mAuctioneerBirthDateStr));

        // save auctioneer, fake auctioneer, game and game key
        mUserService.saveUser(mAuctioneerDTO);
        mUserService.saveUser(fakeAuctioneer);
        mGameRepository.save(mGame);
        mGameKeyRepository.save(mGameKey);


        RequestBuilder request = post("/grid/create-auction").contentType(MediaType.APPLICATION_JSON)
                .content(mAuctionJson).with(httpBasic(fakeAuctioneerUsername, mAuctioneerPassword));

        mMvc.perform(request).andExpect(status().isForbidden());
        assertEquals(0, mAuctionRepository.findAll().size());
    }
}
