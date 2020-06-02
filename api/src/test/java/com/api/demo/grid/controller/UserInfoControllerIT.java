package com.api.demo.grid.controller;

import com.api.demo.DemoApplication;
import com.api.demo.grid.exception.ExceptionDetails;
import com.api.demo.grid.exception.UserNotFoundException;
import com.api.demo.grid.models.Game;
import com.api.demo.grid.models.GameKey;
import com.api.demo.grid.models.Sell;
import com.api.demo.grid.models.User;
import com.api.demo.grid.pojos.UserUpdatePOJO;
import com.api.demo.grid.repository.GameKeyRepository;
import com.api.demo.grid.repository.GameRepository;
import com.api.demo.grid.repository.SellRepository;
import com.api.demo.grid.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DemoApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
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
    private UserUpdatePOJO mUserUpdatePOJO;
    private BCryptPasswordEncoder mPasswordEncoder;

    @BeforeEach
    @SneakyThrows
    void setup(){
        mPasswordEncoder = new BCryptPasswordEncoder();

        mUser = new User();
        mUser.setUsername(mUsername1);
        mUser.setName(mName1);
        mUser.setEmail(mEmail1);
        mUser.setCountry(mCountry1);
        mUser.setPassword(mPasswordEncoder.encode(mPassword1));
        mUser.setPhotoUrl(mPhotoUrl);
        mUser.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse(mBirthDateStr));
        mUser.setStartDate(new SimpleDateFormat("dd/MM/yyyy").parse(mStartDateStr));

        mUser2 = new User();
        mUser2.setUsername("spring");
        mUser2.setName("admin");
        mUser2.setEmail(mEmail1 + "2");
        mUser2.setCountry(mCountry1);
        mUser2.setPassword(mPasswordEncoder.encode(mPassword1));
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
        mGameKey.setRealKey("key");

        mSell = new Sell();
        mSell.setDate(new Date());
        mSell.setPrice(2.4);

        mUserUpdatePOJO = new UserUpdatePOJO();
    }

    @Test
    @SneakyThrows
    void whenSearchingForValidUsername_getValidProxy(){
        mUserRepo.save(mUser);

        mGameKey.setGame(mGame);

        mSell.setGameKey(mGameKey);
        mGameRepo.save(mGame);
        mSell.setUser(mUser);
        mUserRepo.save(mUser);

        mMockMvc.perform(get("/grid/public/user-info")
                .param("username", mUsername1)
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
    void whenSearchingForValidUsername_andIsOwner_getValidPrivateInfo() throws Exception {
        mUserRepo.save(mUser);

        mMockMvc.perform(get("/grid/private/user-info")
                .with(httpBasic(mUsername1, mPassword1))
                .param("username", mUsername1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(mUsername1)))
                .andExpect(jsonPath("$.name", is(mName1)))
                .andExpect(jsonPath("$.country", is(mCountry1)))
                .andExpect(jsonPath("$.birthDateStr", is(mBirthDateStr)))
                .andExpect(jsonPath("$.startDateStr", is(mStartDateStr)))
                .andExpect(jsonPath("$.wishList[0].name", is("wish")))
                ;

        //TODO check for wishlist, buys and reviews once endpoints are done
    }


    @Test
    @SneakyThrows
    void whenSearchingForValidUsername_andIsAdmin_getValidPrivateInfo(){
        mGameRepo.save(mGame);
        mUserRepo.save(mUser);

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
                .andExpect(jsonPath("$.wishList[0].name", is("wish")))
        ;
    }


    @Test
    @SneakyThrows
    void whenSearchingForValidUsername_andIsNotOwnerNorAdmin_getException(){
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
    void whenSearchingForInvalidUsername_andIsOwnerOrAdmin_getException() throws Exception {
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

    @Test
    @SneakyThrows
    void whenUpdatingValidUserInfo_returnValidUser(){
        mUserRepo.save(mUser);
        Optional<User> user = mUserRepo.findById(mUser.getId());
        String name = "newName";
        mUserUpdatePOJO.setName(name);

        mMockMvc.perform(put("/grid/user")
                .with(httpBasic(mUsername1, mPassword1))
                .content(asJsonString(mUserUpdatePOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
        ;
    }

    @Test
    @SneakyThrows
    void whenUpdatingUserInfo_withInvalidEmail_return4xxError(){
        mUserRepo.save(mUser);
        mUserUpdatePOJO.setEmail(mUser.getEmail());

        mMockMvc.perform(put("/grid/user")
                .with(httpBasic(mUsername1, mPassword1))
                .content(asJsonString(mUserUpdatePOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("There is already a user with that email"))
        ;
    }

    @Test
    @SneakyThrows
    void whenUpdatingUserInfo_withInvalidCC_return4xxError(){
        mUserRepo.save(mUser);
        mUserUpdatePOJO.setEmail("name");

        mMockMvc.perform(put("/grid/user")
                .with(httpBasic(mUsername1, mPassword1))
                .content(asJsonString(mUserUpdatePOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("There is already a user with that email"))
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
