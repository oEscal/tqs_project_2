package com.api.demo.grid.controller;

import com.api.demo.DemoApplication;
import com.api.demo.grid.models.User;
import com.api.demo.grid.repository.UserRepository;
import lombok.SneakyThrows;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.RequestBuilder;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.util.Base64Utils;

import java.text.SimpleDateFormat;
import java.util.List;

import static com.api.demo.grid.utils.UserJson.simpleUserJson;
import static com.api.demo.grid.utils.UserJson.userCreditCardJson;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.assertEquals;


@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DemoApplication.class)
class AccountIT {

    @Autowired
    private MockMvc mMvc;

    @Autowired
    private UserRepository mUserRepository;

    // specifications for user1
    private User mSimpleUser;
    private String mSimpleUserJson;
    private String mUsername1 = "username1",
            mName1 = "name1",
            mEmail1 = "email1",
            mCountry = "country1",
            mPassword1 = "password1",
            mBirthDateStr = "17/10/2010";


    @BeforeEach
    @SneakyThrows
    void setup() {
        this.mSimpleUser = new User();
        this.mSimpleUser.setUsername(mUsername1);
        this.mSimpleUser.setName(mName1);
        this.mSimpleUser.setEmail(mEmail1);
        this.mSimpleUser.setCountry(mCountry);
        this.mSimpleUser.setPassword(mPassword1);
        this.mSimpleUser.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse(mBirthDateStr));

        this.mSimpleUserJson = simpleUserJson(mUsername1, mPassword1, mBirthDateStr, mEmail1, mCountry, mName1);
    }

    @AfterEach
    void afterEach() {
        mUserRepository.deleteAll();
    }

    /***
     * Create simple User (with the required params)
     ***/
    @Test
    @SneakyThrows
    void whenCreateUserWithDefaultParams_returnSuccessAndCreatedUser() {

        RequestBuilder request = post("/grid/sign-up").contentType(MediaType.APPLICATION_JSON)
                .content(mSimpleUserJson);

        mMvc.perform(request).andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(mUsername1)))
                .andExpect(jsonPath("$.name", is(mName1)))
                .andExpect(jsonPath("$.email", is(mEmail1)))
                .andExpect(jsonPath("$.country", is(mCountry)))
                .andExpect(jsonPath("$.birthDate", is(mBirthDateStr)))
                .andExpect(jsonPath("$.password", is(nullValue())));
        assertEquals(1, mUserRepository.findAll().size());
    }

    @Test
    @SneakyThrows
    void whenCreateUserWithExistentUserName_returnError() {

        // first save user
        RequestBuilder request = post("/grid/sign-up").contentType(MediaType.APPLICATION_JSON)
                .content(mSimpleUserJson);
        mMvc.perform(request).andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(mUsername1)))
                .andExpect(jsonPath("$.name", is(mName1)))
                .andExpect(jsonPath("$.email", is(mEmail1)))
                .andExpect(jsonPath("$.country", is(mCountry)))
                .andExpect(jsonPath("$.birthDate", is(mBirthDateStr)))
                .andExpect(jsonPath("$.password", is(nullValue())));

        // second save user
        request = post("/grid/sign-up").contentType(MediaType.APPLICATION_JSON)
                .content(simpleUserJson(mUsername1, "password_test", mBirthDateStr, "test_email", mCountry,
                        "name_test"));

        mMvc.perform(request).andExpect(status().isBadRequest());
        assertEquals(1, mUserRepository.findAll().size());
        assertEquals(mName1, mUserRepository.findByUsername(mUsername1).getName());
    }

    @Test
    @SneakyThrows
    void whenCreateUserWithExistentEmail_returnError() {

        // first save user
        RequestBuilder request = post("/grid/sign-up").contentType(MediaType.APPLICATION_JSON)
                .content(mSimpleUserJson);
        mMvc.perform(request).andExpect(status().isOk());

        // second save user
        request = post("/grid/sign-up").contentType(MediaType.APPLICATION_JSON)
                .content(simpleUserJson("test_username", "password_test", mBirthDateStr, mEmail1, mCountry,
                        "name_test"));

        mMvc.perform(request).andExpect(status().isBadRequest());
        assertEquals(1, mUserRepository.findAll().size());
        assertEquals(mName1, mUserRepository.findByUsername(mUsername1).getName());
    }

    /***
     * Create User with all credit card details
     ***/
    @Test
    @SneakyThrows
    void whenCreateUserWithCardDetails_returnAllCreditCardDetails() {

        String creditCardNumber = RandomStringUtils.randomNumeric(10);
        String creditCardCSC = RandomStringUtils.randomNumeric(3);
        String creditCardOwner = "Test user";
        String creditCardExpirationDate = "10/10/2030";

        RequestBuilder request = post("/grid/sign-up").contentType(MediaType.APPLICATION_JSON)
                .content(userCreditCardJson(mUsername1, mPassword1, mBirthDateStr, mEmail1, mCountry, mName1,
                        creditCardNumber, creditCardCSC, creditCardOwner, creditCardExpirationDate));

        mMvc.perform(request).andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(mUsername1)))
                .andExpect(jsonPath("$.name", is(mName1)))
                .andExpect(jsonPath("$.email", is(mEmail1)))
                .andExpect(jsonPath("$.country", is(mCountry)))
                .andExpect(jsonPath("$.birthDate", is(mBirthDateStr)))
                .andExpect(jsonPath("$.password", is(nullValue())))
                .andExpect(jsonPath("$.creditCardNumber", is(creditCardNumber)))
                .andExpect(jsonPath("$.creditCardCSC", is(creditCardCSC)))
                .andExpect(jsonPath("$.creditCardOwner", is(creditCardOwner)))
                .andExpect(jsonPath("$.creditCardExpirationDate", is(creditCardExpirationDate)));
        assertEquals(1, mUserRepository.findAll().size());
    }

    @Autowired
    private TestRestTemplate template;

    /***
     * Create User with all credit card details
     ***/
    @Test
    @SneakyThrows
    void whenLoginWithExistentSimpleUser_returnSimpleUser() {

        // add the user to database
        mUserRepository.save(mSimpleUser);

        String token = "Basic " + Base64Utils.encodeToString((mUsername1 + ":" + mPassword1).getBytes());

        RequestBuilder request = post("/grid/login").header(HttpHeaders.AUTHORIZATION, token);

        mMvc.perform(request).andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(mUsername1)))
                .andExpect(jsonPath("$.name", is(mName1)))
                .andExpect(jsonPath("$.email", is(mEmail1)))
                .andExpect(jsonPath("$.country", is(mCountry)))
                .andExpect(jsonPath("$.birthDate", is(mBirthDateStr)))
                .andExpect(jsonPath("$.password", is(nullValue())))
                .andExpect(jsonPath("$.creditCardNumber", nullValue()))
                .andExpect(jsonPath("$.creditCardCSC", nullValue()))
                .andExpect(jsonPath("$.creditCardOwner", nullValue()))
                .andExpect(jsonPath("$.creditCardExpirationDate", nullValue()));
        assertEquals(1, mUserRepository.findAll().size());
    }
}
