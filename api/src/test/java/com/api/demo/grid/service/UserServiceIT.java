package com.api.demo.grid.service;

import com.api.demo.DemoApplication;
import com.api.demo.grid.dtos.UserDTO;
import com.api.demo.grid.exception.ExceptionDetails;
import com.api.demo.grid.repository.UserRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DemoApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class UserServiceIT {

    @Autowired
    private UserRepository mUserRepository;

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

        mSimpleUserDTO = new UserDTO(mUsername1, mName1, mEmail1, mCountry1, mPassword1,
                new SimpleDateFormat("dd/MM/yyyy").parse(mBirthDateStr));
    }

    @AfterEach
    void afterEach() {
        mUserRepository.deleteAll();
    }


    @Test
    @SneakyThrows
    void whenSaveExistentUser_saveIsUnsuccessful() {

        // first insertion
        mUserService.saveUser(mSimpleUserDTO);

        // second insertion
        mSimpleUserDTO.setEmail("test_email");
        assertThrows(ExceptionDetails.class, () -> mUserService.saveUser(mSimpleUserDTO));
        assertEquals(1, mUserRepository.findAll().size());
    }

    @Test
    @SneakyThrows
    void whenSaveUserWithExistentEmail_saveIsUnsuccessful() {

        // first insertion
        mUserService.saveUser(mSimpleUserDTO);

        // second insertion
        mSimpleUserDTO.setUsername("test_username");
        assertThrows(ExceptionDetails.class, () -> mUserService.saveUser(mSimpleUserDTO));
        assertEquals(1, mUserRepository.findAll().size());
    }

    @Test
    @SneakyThrows
    void whenSaveUser_returnUserWithoutPassword() {

        assertNull(mUserService.saveUser(mSimpleUserDTO).getPassword());
        assertEquals(1, mUserRepository.findAll().size());
        assertNotNull(mUserRepository.findByUsername(mUsername1).getPassword());
    }

    @Test
    @SneakyThrows
    void whenSaveUser_getUserWithoutPassword() {

        mUserService.saveUser(mSimpleUserDTO);
        assertNull(mUserService.getUser(mUsername1).getPassword());
        assertNotNull(mUserRepository.findByUsername(mUsername1).getPassword());
    }
}
