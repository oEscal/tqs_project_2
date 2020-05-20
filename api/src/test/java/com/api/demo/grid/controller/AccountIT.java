package com.api.demo.grid.controller;

import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.RequestBuilder;

import com.api.demo.grid.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountIT {

    @Autowired
    private MockMvc mMvc;

    @MockBean
    private UserRepository userRepository;

    // specifications for user1
    private String mUsername1 = "username1",
            mName1 = "name1",
            mEmail1 = "email1",
            mPassword1 = "password1",
            mBirthDateStr = "17/10/2010";


    /***
     * Create simple User (with the required params)
     ***/
    @Test
    @SneakyThrows
    void whenCreateUserWithDefaultParams_thenReturnSuccessAndCreatedUser() {

        RequestBuilder request = post("/grid/sign-up").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "        \"username\": \"ola\",\n" +
                        "        \"password\": \"adeus\",\n" +
                        "        \"birthDate\": \"1999-10-10\",\n" +
                        "        \"email\": \"ola@adeus.com\",\n" +
                        "        \"name\": \"Mr eheh uhuh\"\n" +
                        "}");



        System.out.println(mMvc.perform(request).toString());
        mMvc.perform(request).andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(mUsername1)));
    }
}
