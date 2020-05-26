package com.api.demo.grid.config;

import com.api.demo.DemoApplication;
import com.api.demo.grid.dtos.UserDTO;
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

import static org.hamcrest.Matchers.is;
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
    private UserDTO mSimpleUserDTO;
    private String mUsername1 = "username1",
            mName1 = "name1",
            mEmail1 = "email1",
            mCountry1 = "country1",
            mPassword1 = "password1",
            mBirthDateStr = "17/10/2010";


    @BeforeEach
    @SneakyThrows
    void setup() {
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

        verifyEndpointsAreAuthorized(authWhitelist, false);
    }

    @Test
    @SneakyThrows
    void whenAccessClosedUrlWithoutLoggedIn_accessIsUnauthorized() {

        String[] adminWhitelist = (String[]) ReflectionTestUtils.getField(mSpringSecurityConfig, "ADMIN_WHITELIST");

        String[] userWhitelist = (String[]) ReflectionTestUtils.getField(mSpringSecurityConfig, "USER_WHITELIST");

        // verify for admin endpoints
        verifyEndpointsAreUnauthorizedOrForbidden(adminWhitelist, false, false);

        // verify for user endpoints
        verifyEndpointsAreUnauthorizedOrForbidden(userWhitelist, false, false);
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
        verifyEndpointsAreAuthorized(authWhitelist, true);

        // verify for user endpoints
        verifyEndpointsAreAuthorized(userWhitelist, true);
    }

    @Test
    @SneakyThrows
    void whenAccessAdminUrlWithTypicalUser_accessIsUnauthorized() {

        // login with typical user (non admin)
        mUserService.saveUser(mSimpleUserDTO);
        RequestBuilder request = post("/grid/login").with(httpBasic(mUsername1, mPassword1));
        mMvc.perform(request).andExpect(status().isOk())
                .andExpect(jsonPath("$.admin", is(false)));

        String[] adminWhitelist = (String[]) ReflectionTestUtils.getField(mSpringSecurityConfig, "ADMIN_WHITELIST");

        // verify for admin endpoints
        verifyEndpointsAreUnauthorizedOrForbidden(adminWhitelist, true, true);
    }


    /***
     * Logged in admin accesses
     ***/
    @Test
    @SneakyThrows
    void whenAccessAllUrlWithAdmin_accessIsAuthorized() {

        // login with admin user
        mSimpleUserDTO.setAdmin(true);
        mUserService.saveUser(mSimpleUserDTO);
        RequestBuilder request = post("/grid/login").with(httpBasic(mUsername1, mPassword1));
        mMvc.perform(request).andExpect(status().isOk())
                .andExpect(jsonPath("$.admin", is(true)));

        String[] authWhitelist = (String[]) ReflectionTestUtils.getField(mSpringSecurityConfig, "AUTH_WHITELIST");
        String[] userWhitelist = (String[]) ReflectionTestUtils.getField(mSpringSecurityConfig, "USER_WHITELIST");
        String[] adminWhitelist = (String[]) ReflectionTestUtils.getField(mSpringSecurityConfig, "ADMIN_WHITELIST");

        // verify for open endpoints
        verifyEndpointsAreAuthorized(authWhitelist, true);

        // verify for user endpoints
        verifyEndpointsAreAuthorized(userWhitelist, true);

        // verify for admin endpoints
        verifyEndpointsAreAuthorized(adminWhitelist, true);
    }


    @SneakyThrows
    private void verifyEndpointsAreAuthorized(String[] authList, boolean authenticate) {

        for (String endPoint : authList) {
            RequestBuilder request;
            if (authenticate) {
                request = post(endPoint).with(httpBasic(mUsername1, mPassword1)).contentType(MediaType.APPLICATION_JSON);
            } else {
                request = post(endPoint).contentType(MediaType.APPLICATION_JSON);
            }

            int returnedStatus = mMvc.perform(request).andReturn().getResponse().getStatus();
            assertNotEquals(401, returnedStatus, "Endpoint " + endPoint + " should be unauthorized");
        }
    }

    @SneakyThrows
    private void verifyEndpointsAreUnauthorizedOrForbidden(String[] authList, boolean authenticate, boolean forbidden) {

        for (String endPoint : authList) {
            RequestBuilder request;
            if (authenticate) {
                request = post(endPoint).with(httpBasic(mUsername1, mPassword1)).contentType(MediaType.APPLICATION_JSON);
            } else {
                request = post(endPoint).contentType(MediaType.APPLICATION_JSON);
            }

            int returnedStatus = mMvc.perform(request).andReturn().getResponse().getStatus();

            if (forbidden) {
                assertEquals(403, returnedStatus, "Endpoint " + endPoint + " should be or forbidden");
            } else {
                assertEquals(401, returnedStatus, "Endpoint " + endPoint + " should be unauthorized");
            }
        }
    }
}
