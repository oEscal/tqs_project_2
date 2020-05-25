package com.api.demo.grid.config;

import com.api.demo.DemoApplication;
import com.api.demo.grid.dtos.UserDTO;
import com.api.demo.grid.models.User;
import com.api.demo.grid.repository.UserRepository;
import com.api.demo.grid.service.UserService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.*;
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


    @Test
    @SneakyThrows
    void whenAccessOpenUrlWithoutLoggedIn_accessIsOk() {

        String[] auth_whitelist = (String[]) ReflectionTestUtils.getField(mSpringSecurityConfig, "AUTH_WHITELIST");

        for (String endPoint : auth_whitelist) {
            RequestBuilder request = post(endPoint).contentType(MediaType.APPLICATION_JSON);

            int returnedStatus = mMvc.perform(request).andReturn().getResponse().getStatus();
            assertNotEquals(401, returnedStatus, "Endpoint " + endPoint + " should be authorized for " +
                    "any user");
        }
    }

}
