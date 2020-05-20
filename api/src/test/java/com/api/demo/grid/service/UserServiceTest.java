package com.api.demo.grid.service;

import com.api.demo.grid.dtos.UserDTO;
import com.api.demo.grid.models.User;
import com.api.demo.grid.repository.UserRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock(lenient = true)
    private UserRepository mUserRepository;

    @Mock(lenient = true)
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService mUserService;

    // specifications for user1
    private User mUser1;
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

        // mock password encryption
        given(passwordEncoder.encode(mPassword1)).willReturn(mPasswordEncrypted1);
    }

    @AfterEach
    void afterEach() {
        mUserRepository.deleteAll();
    }


    /***
     *  Get User
     ***/
    @Test
    void whenUserNameExists_receiveCorrectUser() {

        given(mUserRepository.findByUsername(mUsername1)).willReturn(mUser1);

        assertEquals(mUser1, mUserService.getUser(mUsername1));
    }

    @Test
    @SneakyThrows
    void whenUserNameExists_receiveUserWithSameName() {

        given(mUserRepository.findByUsername(mUsername1)).willReturn(mUser1);

        User userExpected = new User();
        userExpected.setUsername(mUsername1);
        userExpected.setName(mName1);
        userExpected.setEmail(mEmail1);
        userExpected.setPassword(mPasswordEncrypted1);
        userExpected.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse(mBirthDateStr));

        assertEquals(userExpected, mUserService.getUser(mUsername1));
    }

    @Test
    void whenUserNameNotExists_receiveNothing() {

        assertNull(mUserService.getUser("non_exist_user"));
    }


    /***
     *  Save simple User (User with the required params)
     ***/
    @Test
    @SneakyThrows
    void whenSaveUserDontExists_saveIsSuccessful() {

        given(mUserRepository.save(mUser1)).willReturn(mUser1);

        UserDTO simpleUserDTO = new UserDTO(mUsername1, mName1, mEmail1, mPassword1,
                new SimpleDateFormat("dd/MM/yyyy").parse(mBirthDateStr));

        assertDoesNotThrow(() -> mUserService.saveUser(simpleUserDTO));
        assertEquals(mUser1, mUserRepository.findByUsername(mUsername1));
    }

    @Test
    @SneakyThrows
    void whenSaveUserDontExists_ReturnUser() {

        given(mUserRepository.save(mUser1)).willReturn(mUser1);

        UserDTO simpleUserDTO = new UserDTO(mUsername1, mName1, mEmail1, mPassword1,
                new SimpleDateFormat("dd/MM/yyyy").parse(mBirthDateStr));

        assertEquals(mUser1, mUserService.saveUser(simpleUserDTO));
    }

    // @Test
    // @SneakyThrows
    // void whenSaveUserDontExists_ReturnUser() {
//
    //     given(mUserRepository.save(mUser1)).willReturn(mUser1);
//
    //     assertEquals(mUser1, mUserService.saveUser(mSimpleUserDTO));
    // }
}
