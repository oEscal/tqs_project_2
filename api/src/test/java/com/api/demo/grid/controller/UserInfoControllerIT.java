package com.api.demo.grid.controller;

import com.api.demo.DemoApplication;
import com.api.demo.grid.models.Game;
import com.api.demo.grid.models.GameKey;
import com.api.demo.grid.models.Sell;
import com.api.demo.grid.models.User;
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
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DemoApplication.class)
class UserInfoControllerIT {
    @Autowired
    private MockMvc mMockMvc;

    @Autowired
    private UserRepository mUserRepo;

    @Autowired
    private SellRepository mSellRepo;

    @Autowired
    private GameRepository mGameRepo;

    @Autowired
    private GameKeyRepository mGameKeyRepo;

    private User mUser;
    private User mUser2;
    private Game mGame;
    private Game mWishGame;
    private GameKey mGameKey;
    private Sell mSell;
    private String mUsername1 = "username1",
            mPassword1 = "password1",
            mName1 = "name1",
            mEmail1 = "email1",
            mCountry1 = "country1",
            mBirthDateStr = "17/10/2010",
            mStartDateStr = "25/05/2020",
            mPhotoUrl = "photo.jpg";

    @BeforeEach
    @SneakyThrows
    void setup(){
        mUser = new User();
        mUser.setUsername(mUsername1);
        mUser.setName(mName1);
        mUser.setEmail(mEmail1);
        mUser.setCountry(mCountry1);
        mUser.setPassword(mPassword1);
        mUser.setPhotoUrl(mPhotoUrl);
        mUser.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse(mBirthDateStr));
        mUser.setStartDate(new SimpleDateFormat("dd/MM/yyyy").parse(mStartDateStr));

        mUser2 = new User();
        mUser2.setUsername("spring");
        mUser2.setName("admin");
        mUser2.setEmail(mEmail1 + "2");
        mUser2.setCountry(mCountry1);
        mUser2.setPassword(mPassword1);
        mUser2.setPhotoUrl(mPhotoUrl);
        mUser2.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse(mBirthDateStr));
        mUser2.setStartDate(new SimpleDateFormat("dd/MM/yyyy").parse(mStartDateStr));

        mGame = new Game();
        mGame.setName("name");

        mWishGame = new Game();
        mWishGame.setName("wish");
        mUser.setWishList(new HashSet<>(Arrays.asList(mWishGame)));
        mWishGame.setUserWish(new HashSet<>(Arrays.asList(mUser)));

        mGameKey = new GameKey();
        mGameKey.setGame(mGame);
        mGameKey.setRKey("key");

        mSell = new Sell();
        mSell.setGameKey(mGameKey);
        mSell.setDate(new Date());
        mSell.setPrice(2.4);
        mSell.setUser(mUser);

        mUserRepo.deleteAll();
        mGameRepo.deleteAll();
        mGameKeyRepo.deleteAll();
        mSellRepo.deleteAll();
    }

    @Test
    @SneakyThrows
    void whenSearchingForValidUsername_getValidProxy(){
        mGameRepo.save(mGame);

        mMockMvc.perform(get("/grid/public/user-info")
                .param("username", "username1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(mUsername1)))
                .andExpect(jsonPath("$.name", is(mName1)))
                .andExpect(jsonPath("$.country", is(mCountry1)))
                .andExpect(jsonPath("$.birthDate", is(mBirthDateStr)))
                .andExpect(jsonPath("$.startDate", is(mStartDateStr)))
                .andExpect(jsonPath("$.listings[0].id", is((int)mSell.getId())))
                .andExpect(jsonPath("$.listings[0].gameKey.id", is((int)mGameKey.getId())))
        ;

    }

    @Test
    @SneakyThrows
    void whenSearchingForInvalidUsername_getException(){

        mMockMvc.perform(get("/grid/public/user-info")
                .param("username", "user")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason(is("Username not found in the database")))
        ;
    }

    @Test
    @SneakyThrows
    void whenSearchingForValidUsername_andIsOwner_getValidPrivateInfo(){
        mGameRepo.save(mGame);

        mMockMvc.perform(get("/grid/private/user-info")
                .with(httpBasic(mUsername1, mPassword1))
                .param("username", "username1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(mUsername1)))
                .andExpect(jsonPath("$.name", is(mName1)))
                .andExpect(jsonPath("$.country", is(mCountry1)))
                .andExpect(jsonPath("$.birthDateStr", is(mBirthDateStr)))
                .andExpect(jsonPath("$.startDateStr", is(mStartDateStr)))
                .andExpect(jsonPath("$.sells[0].id", is((int)mSell.getId())))
                .andExpect(jsonPath("$.sells[0].gameKey.id", is((int)mGameKey.getId())))
                .andExpect(jsonPath("$.wishList[0].name", is("wish")))
        ;
        //TODO check for wishlist, buys and reviews once endpoints are done
    }

    @Test
    @SneakyThrows
    void whenSearchingForValidUsername_andIsAdmin_getValidPrivateInfo(){
        mGameRepo.save(mGame);

        mUser2.setAdmin(true);
        mUserRepo.save(mUser2);

        mMockMvc.perform(get("/grid/private/user-info")
                .with(httpBasic("spring", mPassword1))
                .param("username", "username1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(mUsername1)))
                .andExpect(jsonPath("$.name", is(mName1)))
                .andExpect(jsonPath("$.country", is(mCountry1)))
                .andExpect(jsonPath("$.birthDateStr", is(mBirthDateStr)))
                .andExpect(jsonPath("$.startDateStr", is(mStartDateStr)))
                .andExpect(jsonPath("$.sells[0].id", is((int)mSell.getId())))
                .andExpect(jsonPath("$.sells[0].gameKey.id", is((int)mGameKey.getId())))
                .andExpect(jsonPath("$.wishList[0].name", is("wish")))
        ;
        //TODO check for wishlist, buys and reviews once endpoints are done
    }

    @Test
    @SneakyThrows
    void whenSearchingForValidUsername_andIsNotOwnerNorAdmin_getException(){
        mGameRepo.save(mGame);
        mUserRepo.save(mUser2);

        mMockMvc.perform(get("/grid/private/user-info")
                .with(httpBasic("spring", mPassword1))
                .param("username", "user")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason(is("You are not allowed to see this user's private info")))
        ;

    }

    @Test
    @SneakyThrows
    void whenSearchingForInvalidUsername_andIsOwnerOrAdmin_getException(){
        mUser2.setAdmin(true);
        mUserRepo.save(mUser2);

        mMockMvc.perform(get("/grid/private/user-info")
                .with(httpBasic("spring", mPassword1))
                .param("username", "user")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason(is("Username not found in the database")))
        ;

    }
}
