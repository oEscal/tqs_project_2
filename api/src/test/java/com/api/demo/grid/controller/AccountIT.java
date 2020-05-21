package com.api.demo.grid.controller;

import com.api.demo.DemoApplication;
import com.api.demo.grid.dtos.UserDTO;
import com.api.demo.grid.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.RequestBuilder;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DemoApplication.class)
class AccountIT {

    @Autowired
    private MockMvc mMvc;

    // @Autowired
    // private UserService mUserService;

    // specifications for user1
    private User mSimpleUser;
    private UserDTO mSimpleUserDTO;
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
        this.mSimpleUser.setPassword(mPassword1);
        this.mSimpleUser.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse(mBirthDateStr));

        this.mSimpleUserDTO = new UserDTO(mUsername1, mName1, mEmail1, mPassword1, mCountry,
                new SimpleDateFormat("dd/MM/yyyy").parse(mBirthDateStr));
    }

    /***
     * Create simple User (with the required params)
     ***/
    @Test
    @SneakyThrows
    void whenCreateUserWithDefaultParams_thenReturnSuccessAndCreatedUser() {

        RequestBuilder request = post("/grid/sign-up").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(mSimpleUserDTO));
        System.out.println(asJsonString(mSimpleUserDTO));

        mMvc.perform(request).andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(mUsername1)))
                .andExpect(jsonPath("$.name", is(mName1)))
                .andExpect(jsonPath("$.email", is(mEmail1)))
                .andExpect(jsonPath("$.country", is(mCountry)))
                .andExpect(jsonPath("$.birth", is(mBirthDateStr)))
                .andExpect(jsonPath("$.password", is(null)));
    }

    // TODO -> this method can be shared between classes (create an utils class)
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
