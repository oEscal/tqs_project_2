package com.api.demo.grid.controller;

import com.api.demo.DemoApplication;
import com.api.demo.grid.dtos.UserDTO;
import com.api.demo.grid.models.User;
import com.api.demo.grid.repository.UserRepository;
import com.api.demo.grid.service.UserService;
import lombok.SneakyThrows;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.RequestBuilder;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.api.demo.grid.utils.UserJson.simpleUserJson;
import static com.api.demo.grid.utils.UserJson.userCreditCardJson;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.assertEquals;


@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DemoApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AccountIT {

    @Autowired
    private MockMvc mMvc;

    @Autowired
    private UserRepository mUserRepository;

    @Autowired
    private UserService mUserService;

    // specifications for user1
    private User mSimpleUser;
    private UserDTO mSimpleUserDTO;
    private String mSimpleUserJson;
    private String mUsername1 = "username1",
            mName1 = "name1",
            mEmail1 = "email1",
            mCountry1 = "country1",
            mPassword1 = "password1",
            mBirthDateStr = "17/10/2010";


    @BeforeEach
    @SneakyThrows
    void setup() {
        this.mSimpleUser = new User();
        this.mSimpleUser.setUsername(mUsername1);
        this.mSimpleUser.setName(mName1);
        this.mSimpleUser.setEmail(mEmail1);
        this.mSimpleUser.setCountry(mCountry1);
        this.mSimpleUser.setPassword(mPassword1);
        this.mSimpleUser.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse(mBirthDateStr));

        this.mSimpleUserJson = simpleUserJson(mUsername1, mPassword1, mBirthDateStr, mEmail1, mCountry1, mName1);

        this.mSimpleUserDTO = new UserDTO(mUsername1, mName1, mEmail1, mCountry1, mPassword1,
                new SimpleDateFormat("dd/MM/yyyy").parse(mBirthDateStr));
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
                .andExpect(jsonPath("$.country", is(mCountry1)))
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
                .andExpect(jsonPath("$.country", is(mCountry1)))
                .andExpect(jsonPath("$.birthDate", is(mBirthDateStr)))
                .andExpect(jsonPath("$.password", is(nullValue())));

        // second save user
        request = post("/grid/sign-up").contentType(MediaType.APPLICATION_JSON)
                .content(simpleUserJson(mUsername1, "password_test", mBirthDateStr, "test_email", mCountry1,
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
                .content(simpleUserJson("test_username", "password_test", mBirthDateStr, mEmail1, mCountry1,
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
                .content(userCreditCardJson(mUsername1, mPassword1, mBirthDateStr, mEmail1, mCountry1, mName1,
                        creditCardNumber, creditCardCSC, creditCardOwner, creditCardExpirationDate));

        mMvc.perform(request).andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(mUsername1)))
                .andExpect(jsonPath("$.name", is(mName1)))
                .andExpect(jsonPath("$.email", is(mEmail1)))
                .andExpect(jsonPath("$.country", is(mCountry1)))
                .andExpect(jsonPath("$.birthDate", is(mBirthDateStr)))
                .andExpect(jsonPath("$.password", is(nullValue())))
                .andExpect(jsonPath("$.creditCardNumber", is(creditCardNumber)))
                .andExpect(jsonPath("$.creditCardCSC", is(creditCardCSC)))
                .andExpect(jsonPath("$.creditCardOwner", is(creditCardOwner)))
                .andExpect(jsonPath("$.creditCardExpirationDate", is(creditCardExpirationDate)));
        assertEquals(1, mUserRepository.findAll().size());
    }


    /***
     * Login tests
     ***/
    @Test
    @SneakyThrows
    void whenLoginWithExistentSimpleUser_returnSimpleUser() {

        // add the user to database
        mUserService.saveUser(mSimpleUserDTO);

        RequestBuilder request = post("/grid/login").with(httpBasic(mUsername1, mPassword1));

        mMvc.perform(request).andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(mUsername1)))
                .andExpect(jsonPath("$.name", is(mName1)))
                .andExpect(jsonPath("$.email", is(mEmail1)))
                .andExpect(jsonPath("$.country", is(mCountry1)))
                .andExpect(jsonPath("$.birthDate", is(mBirthDateStr)))
                .andExpect(jsonPath("$.password", is(nullValue())))
                .andExpect(jsonPath("$.creditCardNumber", nullValue()))
                .andExpect(jsonPath("$.creditCardCSC", nullValue()))
                .andExpect(jsonPath("$.creditCardOwner", nullValue()))
                .andExpect(jsonPath("$.creditCardExpirationDate", nullValue()));
    }

    @Test
    @SneakyThrows
    void whenLoginWithExistentUserWithCardDetails_returnUserWithCardDetails() {

        String creditCardNumber = RandomStringUtils.randomNumeric(10);
        String creditCardCSC = RandomStringUtils.randomNumeric(3);
        String creditCardOwner = "Test user";
        String creditCardExpirationDateStr = "10/10/2030";
        Date creditCardExpirationDate = new SimpleDateFormat("dd/MM/yyyy").parse(creditCardExpirationDateStr);

        // set card info for the DTO User
        mSimpleUserDTO.setCreditCardNumber(creditCardNumber);
        mSimpleUserDTO.setCreditCardCSC(creditCardCSC);
        mSimpleUserDTO.setCreditCardOwner(creditCardOwner);
        mSimpleUserDTO.setCreditCardExpirationDate(creditCardExpirationDate);

        // add the user to database
        mUserService.saveUser(mSimpleUserDTO);

        RequestBuilder request = post("/grid/login").with(httpBasic(mUsername1, mPassword1));

        mMvc.perform(request).andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(mUsername1)))
                .andExpect(jsonPath("$.name", is(mName1)))
                .andExpect(jsonPath("$.email", is(mEmail1)))
                .andExpect(jsonPath("$.country", is(mCountry1)))
                .andExpect(jsonPath("$.birthDate", is(mBirthDateStr)))
                .andExpect(jsonPath("$.password", is(nullValue())))
                .andExpect(jsonPath("$.creditCardNumber", is(creditCardNumber)))
                .andExpect(jsonPath("$.creditCardCSC", is(creditCardCSC)))
                .andExpect(jsonPath("$.creditCardOwner", is(creditCardOwner)))
                .andExpect(jsonPath("$.creditCardExpirationDate", is(creditCardExpirationDateStr)));
    }

    @Test
    @SneakyThrows
    void whenBadLoginWithExistentSimpleUser_returnLoginError() {

        // add the user to database
        mSimpleUserDTO.setPassword("test_password");
        mUserService.saveUser(mSimpleUserDTO);

        RequestBuilder request = post("/grid/login").with(httpBasic(mUsername1, mPassword1));

        mMvc.perform(request).andExpect(status().isUnauthorized());
    }


    /***
     * Logout tests
     ***/
    @Test
    @SneakyThrows
    void whenLogoutWhenLoggedIn_returnLogoutSuccess() {

        // add the user to database
        mUserService.saveUser(mSimpleUserDTO);

        // login
        RequestBuilder request = post("/grid/login").with(httpBasic(mUsername1, mPassword1));
        mMvc.perform(request).andExpect(status().isOk());

        // logout
        request = post("/grid/logout").with(httpBasic(mUsername1, mPassword1));

        mMvc.perform(request).andExpect(status().is2xxSuccessful());
    }


    /***
     * Delete account tests
     ***/
    @Test
    @SneakyThrows
    void whenRemoveUserWithSameUserLoggedIn_removeUserWithSuccess() {

        // add the user to database
        mUserService.saveUser(mSimpleUserDTO);

        // remove user
        RequestBuilder request = post("/grid/remove-user").param("username", mUsername1)
                .with(httpBasic(mUsername1, mPassword1));
        mMvc.perform(request).andExpect(status().isOk());

        // verify if user was removed
        assertNull(mUserRepository.findByUsername(mUsername1));
    }
}
