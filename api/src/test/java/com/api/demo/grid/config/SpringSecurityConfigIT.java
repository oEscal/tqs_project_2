package com.api.demo.grid.config;

import com.api.demo.DemoApplication;
import com.api.demo.grid.dtos.UserDTO;
import com.api.demo.grid.models.User;
import com.api.demo.grid.repository.UserRepository;
import com.api.demo.grid.service.UserService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.text.SimpleDateFormat;

import static com.api.demo.grid.utils.UserJson.simpleUserJson;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DemoApplication.class)
class SpringSecurityConfigIT {

    @Autowired
    private MockMvc mMvc;

    @Autowired
    private UserRepository mUserRepository;

    @Autowired
    private SpringSecurityConfig mSpringSecurityConfig;

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

    @AfterEach
    void afterEach() {
        mUserRepository.deleteAll();
    }


    /***
     * Non logged in user accesses
     ***/
    @Test
    @SneakyThrows
    void whenAccessOpenUrlWithoutLoggedIn_accessIsNotUnauthorized() {

        String[] authWhitelist = (String[]) ReflectionTestUtils.getField(mSpringSecurityConfig, "AUTH_WHITELIST");

        verifyEndpointsAreAuthorized(authWhitelist);
    }

    @Test
    @SneakyThrows
    void whenAccessClosedUrlWithoutLoggedIn_accessIsUnauthorized() {

        String[] adminWhitelist = (String[]) ReflectionTestUtils.getField(mSpringSecurityConfig, "ADMIN_WHITELIST");

        String[] userWhitelist = (String[]) ReflectionTestUtils.getField(mSpringSecurityConfig, "USER_WHITELIST");

        // verify for admin endpoints
        verifyEndpointsAreUnauthorized(adminWhitelist);

        // verify for user endpoints
        verifyEndpointsAreUnauthorized(userWhitelist);
    }


    /***
     * Logged in typical user accesses
     ***/
    @Test
    @SneakyThrows
    void whenAccessNonAdminUrlWithTypicalUser_accessIsUnauthorized() {

        // login with typical user (non admin)
        mUserService.saveUser(mSimpleUserDTO);
        RequestBuilder request = post("/grid/login").with(httpBasic(mUsername1, mPassword1));
        mMvc.perform(request).andExpect(status().isOk())
                .andExpect(jsonPath("$.admin", is(false)));

        String[] authWhitelist = (String[]) ReflectionTestUtils.getField(mSpringSecurityConfig, "AUTH_WHITELIST");
        String[] userWhitelist = (String[]) ReflectionTestUtils.getField(mSpringSecurityConfig, "USER_WHITELIST");

        // verify for open endpoints
        verifyEndpointsAreAuthorized(authWhitelist);

        // verify for user endpoints
        verifyEndpointsAreAuthorized(userWhitelist);
    }


    @SneakyThrows
    private void verifyEndpointsAreAuthorized(String[] authList) {

        for (String endPoint : authList) {
            RequestBuilder request = post(endPoint).contentType(MediaType.APPLICATION_JSON);

            int returnedStatus = mMvc.perform(request).andReturn().getResponse().getStatus();
            assertNotEquals(401, returnedStatus, "Endpoint " + endPoint + " should be unauthorized");
        }
    }

    @SneakyThrows
    private void verifyEndpointsAreUnauthorized(String[] authList) {

        for (String endPoint : authList) {
            RequestBuilder request = post(endPoint).contentType(MediaType.APPLICATION_JSON);

            int returnedStatus = mMvc.perform(request).andReturn().getResponse().getStatus();
            assertEquals(401, returnedStatus, "Endpoint " + endPoint + " should be unauthorized");
        }
    }
}
