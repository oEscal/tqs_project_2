package com.api.demo.grid.service;

import com.api.demo.DemoApplication;
import com.api.demo.grid.dtos.UserDTO;
import com.api.demo.grid.exception.ExceptionDetails;
import com.api.demo.grid.models.User;
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
    private User mUser1;
    private UserDTO mSimpleUserDTO;
    private String mUsername1 = "username1",
            mName1 = "name1",
            mEmail1 = "email1",
            mPassword1 = "password1",
            mPasswordEncrypted1 = "password_encrypted1",
            mBirthDateStr = "17/10/2010";


    @BeforeEach
    @SneakyThrows
    void setup() {

        mUser1 = new User();
        mUser1.setUsername(mUsername1);
        mUser1.setName(mName1);
        mUser1.setEmail(mEmail1);
        mUser1.setPassword(mPasswordEncrypted1);
        mUser1.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse(mBirthDateStr));

        mSimpleUserDTO = new UserDTO(mUsername1, mName1, mEmail1, mPassword1,
                new SimpleDateFormat("dd/MM/yyyy").parse(mBirthDateStr));
    }

    @AfterEach
    void afterEach() {
        mUserRepository.deleteAll();
    }


    /***
     *  Save simple User (User with the required params)
     ***/
    @Test
    @SneakyThrows
    void whenSaveExistentUser_saveIsUnsuccessful() {

        // first insertion
        mUserService.saveUser(mSimpleUserDTO);

        // second insertion
        assertThrows(ExceptionDetails.class, () -> mUserService.saveUser(mSimpleUserDTO));
    }

    // TODO -> test if the encrypted password is returned -> on integration tests
}
