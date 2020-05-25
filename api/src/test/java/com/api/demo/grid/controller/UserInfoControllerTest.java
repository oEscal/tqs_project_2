package com.api.demo.grid.controller;

import com.api.demo.grid.exception.UserNotFoundException;
import com.api.demo.grid.models.Game;
import com.api.demo.grid.models.GameKey;
import com.api.demo.grid.models.Sell;
import com.api.demo.grid.models.User;
import com.api.demo.grid.proxy.UserInfoProxy;
import com.api.demo.grid.service.UserService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class UserInfoControllerTest {
    @Autowired
    private MockMvc mMockMvc;

    @MockBean
    private UserService mMockUserService;

    private User mUser;
    private Sell mSell;
    private GameKey mGameKey;
    private Game mGame;
    private UserInfoProxy mUserInfoProxy;
    private String mUsername1 = "username1",
            mName1 = "name1",
            mEmail1 = "email1",
            mCountry1 = "country1",
            mBirthDateStr = "17/10/2010",
            mStartDateStr = "25/05/2020";

    @BeforeEach
    @SneakyThrows
    void setup(){
        mUser = new User();
        mUser.setId(4l);
        mUser.setUsername(mUsername1);
        mUser.setName(mName1);
        mUser.setEmail(mEmail1);
        mUser.setCountry(mCountry1);
        mUser.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse(mBirthDateStr));
        mUser.setStartDate(new SimpleDateFormat("dd/MM/yyyy").parse(mStartDateStr));
        mGame = new Game();
        mGame.setId(0l);
        mGame.setName("nam");
        mGameKey = new GameKey();
        mGameKey.setGame(mGame);
        mGameKey.setRKey("key");
        mGameKey.setId(1l);
        mSell = new Sell();
        mSell.setId(2l);
        mSell.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(mBirthDateStr));
        mSell.setGameKey(mGameKey);
        mUser.addSell(mSell);

        mUserInfoProxy = new UserInfoProxy(mUser);
    }

    @Test
    @SneakyThrows
    void whenSearchingForValidUsername_getValidProxy(){
        Mockito.when(mMockUserService.getUserInfo(Mockito.anyString()))
                .thenReturn(mUserInfoProxy);

        mMockMvc.perform(get("/grid/user-info")
                .param("username", "user")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(mUsername1)))
                .andExpect(jsonPath("$.name", is(mName1)))
                .andExpect(jsonPath("$.country", is(mCountry1)))
                .andExpect(jsonPath("$.birthDate", is(mBirthDateStr)))
                .andExpect(jsonPath("$.startDate", is(mStartDateStr)))
                .andExpect(jsonPath("$.listings[0].id", is(2)))
                .andExpect(jsonPath("$.listings[0].gameKey.id", is(1)))

        ;
    }

    @Test
    @SneakyThrows
    void whenSearchingForInvalidUsername_getException(){
        Mockito.when(mMockUserService.getUserInfo(Mockito.anyString()))
                .thenThrow(new UserNotFoundException("Username not found in the database"));

        mMockMvc.perform(get("/grid/user-info")
                .param("username", "user")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason(is("Username not found in the database")))
        ;

    }
}
