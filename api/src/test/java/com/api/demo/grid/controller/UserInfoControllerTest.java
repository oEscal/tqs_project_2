package com.api.demo.grid.controller;

import com.api.demo.grid.exception.UserNotFoundException;
import com.api.demo.grid.models.Game;
import com.api.demo.grid.models.GameKey;
import com.api.demo.grid.models.Sell;
import com.api.demo.grid.models.User;
import com.api.demo.grid.proxy.UserInfoProxy;
import com.api.demo.grid.repository.UserRepository;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashSet;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class UserInfoControllerTest {
    @Autowired
    private MockMvc mMockMvc;

    @MockBean
    private UserService mMockUserService;

    @MockBean
    private UserRepository mUserRepository;

    private BCryptPasswordEncoder mPasswordEncoder = new BCryptPasswordEncoder();

    private User mUser;
    private User mUser2;
    private Sell mSell;
    private GameKey mGameKey;
    private Game mGame;
    private Game mWishGame;
    private UserInfoProxy mUserInfoProxy;
    private String mUsername1 = "username1",
            mName1 = "name1",
            mEmail1 = "email1",
            mCountry1 = "country1",
            mPassword1 = "mPassword1",
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
        mUser.setPassword(mPasswordEncoder.encode(mPassword1));
        mUser.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse(mBirthDateStr));
        mUser.setStartDate(new SimpleDateFormat("dd/MM/yyyy").parse(mStartDateStr));

        mUser2 = new User();
        mUser2.setUsername("spring");
        mUser2.setName("admin");
        mUser2.setEmail(mEmail1 + "2");
        mUser2.setCountry(mCountry1);
        mUser2.setPassword(mPasswordEncoder.encode(mPassword1));
        mUser2.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse(mBirthDateStr));
        mUser2.setStartDate(new SimpleDateFormat("dd/MM/yyyy").parse(mStartDateStr));

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

        mWishGame = new Game();
        mWishGame.setName("wish");
        mUser.setWishList(new HashSet<>(Arrays.asList(mWishGame)));
        mWishGame.setUserWish(new HashSet<>(Arrays.asList(mUser)));

        mUserInfoProxy = new UserInfoProxy(mUser);
    }

    @Test
    @SneakyThrows
    void whenSearchingForValidUsername_getValidProxy(){
        Mockito.when(mMockUserService.getUserInfo(Mockito.anyString()))
                .thenReturn(mUserInfoProxy);

        mMockMvc.perform(get("/grid/public/user-info")
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
                .andExpect(jsonPath("$.score", is(-1.0)))

        ;
    }

    @Test
    @SneakyThrows
    void whenSearchingForInvalidUsername_getException(){
        Mockito.when(mMockUserService.getUserInfo(Mockito.anyString()))
                .thenThrow(new UserNotFoundException("Username not found in the database"));

        mMockMvc.perform(get("/grid/public/user-info")
                .param("username", "user")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason(is("Username not found in the database")))
        ;

    }

    @Test
    @SneakyThrows
    void whenSearchingForValidUsername_andIsUser_getValidPrivateInfo(){
        Mockito.when(mMockUserService.getUser(Mockito.anyString()))
                .thenReturn(mUser);
        Mockito.when(mMockUserService.getFullUserInfo("username1")).thenReturn(mUser);

        Mockito.when(mUserRepository.findByUsername("username1"))
                .thenReturn(mUser);


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
    }

    @Test
    @SneakyThrows
    void whenSearchingForValidUsername_andIsAdmin_getValidPrivateInfo(){
        Mockito.when(mMockUserService.getUser(Mockito.anyString()))
                .thenReturn(mUser2);
        Mockito.when(mMockUserService.getFullUserInfo("username1")).thenReturn(mUser);
        mUser2.setAdmin(true);

        Mockito.when(mUserRepository.findByUsername("spring"))
                .thenReturn(mUser2);

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
    void whenSearchingForValidUsername_andIsNotUserNorAdmin_getException(){

        Mockito.when(mUserRepository.findByUsername("spring"))
                .thenReturn(mUser2);
        Mockito.when(mMockUserService.getUser("spring"))
                .thenReturn(mUser2);

        Mockito.when(mMockUserService.getUser("username1")).thenReturn(mUser);

        mMockMvc.perform(get("/grid/private/user-info")
                .with(httpBasic("spring", mPassword1))
                .param("username", "username1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason(is("You are not allowed to see this user's private info")))
        ;
    }

    @Test
    @SneakyThrows
    void whenSearchingForInvalidUsername_andIsUserOrAdmin_getException(){
        mUser2.setAdmin(true);
        Mockito.when(mMockUserService.getUser("spring"))
                .thenReturn(mUser2);

        Mockito.when(mMockUserService.getFullUserInfo(Mockito.anyString()))
                .thenThrow(new UserNotFoundException("Username not found in the database"));

        Mockito.when(mUserRepository.findByUsername("spring"))
                .thenReturn(mUser2);

        mMockMvc.perform(get("/grid/private/user-info")
                .with(httpBasic("spring", mPassword1))
                .param("username", "user")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason(is("Username not found in the database")))
        ;

    }
}
