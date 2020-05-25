package com.api.demo.grid.service;

import com.api.demo.grid.dtos.UserDTO;
import com.api.demo.grid.exception.ExceptionDetails;
import com.api.demo.grid.exception.UserNotFoundException;
import com.api.demo.grid.models.User;
import com.api.demo.grid.proxy.UserInfoProxy;
import com.api.demo.grid.repository.UserRepository;
import lombok.SneakyThrows;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock(lenient = true)
    private UserRepository mUserRepository;

    @Mock(lenient = true)
    private BCryptPasswordEncoder passwordEncoder;

    @Mock(lenient = true)
    private ModelMapper mModelMapper;

    @InjectMocks
    private UserService mUserService;

    // specifications for user1
    private User mUser1;
    private UserDTO mSimpleUserDTO;
    private String mUsername1 = "username1",
            mName1 = "name1",
            mEmail1 = "email1",
            mCountry1 = "country1",
            mPassword1 = "password1",
            mPasswordEncrypted1 = "password_encrypted1",
            mBirthDateStr = "17/10/2010",
            mStartDateStr = "25/05/2020";


    @BeforeEach
    @SneakyThrows
    void setup() {

        mUser1 = new User();
        mUser1.setUsername(mUsername1);
        mUser1.setName(mName1);
        mUser1.setEmail(mEmail1);
        mUser1.setCountry(mCountry1);
        mUser1.setPassword(mPasswordEncrypted1);
        mUser1.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse(mBirthDateStr));
        mUser1.setStartDate(new SimpleDateFormat("dd/MM/yyyy").parse(mStartDateStr));

        mSimpleUserDTO = new UserDTO(mUsername1, mName1, mEmail1, mCountry1, mPassword1,
                new SimpleDateFormat("dd/MM/yyyy").parse(mBirthDateStr));

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
        userExpected.setCountry(mCountry1);
        userExpected.setPassword(mPasswordEncrypted1);
        userExpected.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse(mBirthDateStr));
        userExpected.setStartDate(new SimpleDateFormat("dd/MM/yyyy").parse(mStartDateStr));

        userExpected.setPassword(null);

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

        // mock model mapper
        given(mModelMapper.map(mSimpleUserDTO, User.class)).willReturn(mUser1);

        // mock user repository
        given(mUserRepository.save(mUser1)).willReturn(mUser1);

        assertDoesNotThrow(() -> mUserService.saveUser(mSimpleUserDTO));
    }

    @Test
    @SneakyThrows
    void whenSaveUserDontExists_ReturnUser() {

        // mock model mapper
        given(mModelMapper.map(mSimpleUserDTO, User.class)).willReturn(mUser1);

        // mock user repository
        given(mUserRepository.save(mUser1)).willReturn(mUser1);

        assertEquals(mUser1, mUserService.saveUser(mSimpleUserDTO));
    }


    /***
     *  Save User with photo or with admin
     ***/
    @Test
    @SneakyThrows
    void whenSaveUserWithPhoto_returnUserWithPhoto() {

        String photo_url = "photo_test";
        mUser1.setPhotoUrl(photo_url);
        mSimpleUserDTO.setPhotoUrl(photo_url);

        // mock model mapper
        given(mModelMapper.map(mSimpleUserDTO, User.class)).willReturn(mUser1);

        // mock user repository
        given(mUserRepository.save(mUser1)).willReturn(mUser1);

        User savedUser = mUserService.saveUser(mSimpleUserDTO);
        assertEquals(mUser1, savedUser);
        assertEquals(photo_url, savedUser.getPhotoUrl());
    }

    @Test
    @SneakyThrows
    void whenSaveUserWithAdminTrue_returnUserWithAdminTrue() {

        mUser1.setAdmin(true);
        mSimpleUserDTO.setAdmin(true);

        // mock model mapper
        given(mModelMapper.map(mSimpleUserDTO, User.class)).willReturn(mUser1);

        // mock user repository
        given(mUserRepository.save(mUser1)).willReturn(mUser1);

        User savedUser = mUserService.saveUser(mSimpleUserDTO);
        assertEquals(mUser1, savedUser);
        assertTrue(savedUser.isAdmin());
    }

    @Test
    @SneakyThrows
    void whenNormalUser_returnUserWithAdminFalse() {

        // mock model mapper
        given(mModelMapper.map(mSimpleUserDTO, User.class)).willReturn(mUser1);

        // mock user repository
        given(mUserRepository.save(mUser1)).willReturn(mUser1);

        User savedUser = mUserService.saveUser(mSimpleUserDTO);
        assertFalse(savedUser.isAdmin());
    }


    /***
     *  Save User with card details
     ***/
    @Test
    @SneakyThrows
    void whenSaveUserWithAllCardInfo_returnUserWithAllCardInfo() {

        String creditCardNumber = RandomStringUtils.randomNumeric(10);
        String creditCardCSC = RandomStringUtils.randomNumeric(3);
        String creditCardOwner = "Test user";
        Date creditCardExpirationDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/10/2030");

        // set card info for User entity
        mUser1.setCreditCardNumber(creditCardNumber);
        mUser1.setCreditCardCSC(creditCardCSC);
        mUser1.setCreditCardOwner(creditCardOwner);
        mUser1.setCreditCardExpirationDate(creditCardExpirationDate);

        // set card info for the DTO User
        mSimpleUserDTO.setCreditCardNumber(creditCardNumber);
        mSimpleUserDTO.setCreditCardCSC(creditCardCSC);
        mSimpleUserDTO.setCreditCardOwner(creditCardOwner);
        mSimpleUserDTO.setCreditCardExpirationDate(creditCardExpirationDate);

        // mock model mapper
        given(mModelMapper.map(mSimpleUserDTO, User.class)).willReturn(mUser1);

        // mock user repository
        given(mUserRepository.save(mUser1)).willReturn(mUser1);

        User savedUser = mUserService.saveUser(mSimpleUserDTO);
        assertEquals(mUser1, savedUser);
        assertEquals(creditCardNumber, savedUser.getCreditCardNumber());
        assertEquals(creditCardCSC, savedUser.getCreditCardCSC());
        assertEquals(creditCardOwner, savedUser.getCreditCardOwner());
        assertEquals(creditCardExpirationDate, savedUser.getCreditCardExpirationDate());
    }

    @Test
    @SneakyThrows
    void whenSaveUserWithJustOneCardInfo_saveIsUnsuccessful() {

        String creditCardCSC = RandomStringUtils.randomNumeric(3);

        mUser1.setCreditCardCSC(creditCardCSC);
        mSimpleUserDTO.setCreditCardCSC(creditCardCSC);

        // mock model mapper
        given(mModelMapper.map(mSimpleUserDTO, User.class)).willReturn(mUser1);

        // mock user repository
        given(mUserRepository.save(mUser1)).willReturn(mUser1);

        assertThrows(ExceptionDetails.class, () -> mUserService.saveUser(mSimpleUserDTO));
    }

    @Test
    @SneakyThrows
    void whenSaveUserWithJustTwoCardInfo_saveIsUnsuccessful() {

        String creditCardOwner = "Test user";
        Date creditCardExpirationDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/10/2030");

        // set card info for User entity
        mUser1.setCreditCardOwner(creditCardOwner);
        mUser1.setCreditCardExpirationDate(creditCardExpirationDate);

        // set card info for the DTO User
        mSimpleUserDTO.setCreditCardOwner(creditCardOwner);
        mSimpleUserDTO.setCreditCardExpirationDate(creditCardExpirationDate);

        // mock model mapper
        given(mModelMapper.map(mSimpleUserDTO, User.class)).willReturn(mUser1);

        // mock user repository
        given(mUserRepository.save(mUser1)).willReturn(mUser1);

        assertThrows(ExceptionDetails.class, () -> mUserService.saveUser(mSimpleUserDTO));
    }

    @Test
    @SneakyThrows
    void whenSaveUserWithJustThreeCardInfo_saveIsUnsuccessful() {

        String creditCardNumber = RandomStringUtils.randomNumeric(10);
        String creditCardCSC = RandomStringUtils.randomNumeric(3);
        Date creditCardExpirationDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/10/2030");

        // set card info for User entity
        mUser1.setCreditCardNumber(creditCardNumber);
        mUser1.setCreditCardCSC(creditCardCSC);
        mUser1.setCreditCardExpirationDate(creditCardExpirationDate);

        // set card info for the DTO User
        mSimpleUserDTO.setCreditCardNumber(creditCardNumber);
        mSimpleUserDTO.setCreditCardCSC(creditCardCSC);
        mSimpleUserDTO.setCreditCardExpirationDate(creditCardExpirationDate);

        // mock model mapper
        given(mModelMapper.map(mSimpleUserDTO, User.class)).willReturn(mUser1);

        // mock user repository
        given(mUserRepository.save(mUser1)).willReturn(mUser1);

        assertThrows(ExceptionDetails.class, () -> mUserService.saveUser(mSimpleUserDTO));
    }

    @Test
    void whenSearchingValidUser_getValidUserProxy() {
        given(mUserRepository.findByUsername("username1")).willReturn(mUser1);

        UserInfoProxy userProxy = null;
        try {
            userProxy = mUserService.getUserInfo("username1");
        } catch (UserNotFoundException e) {
            fail();
        }

        assertEquals(mUsername1, userProxy.getUsername());
        assertEquals(mName1, userProxy.getName());
        assertEquals(mBirthDateStr, userProxy.getBirthDate());
        assertEquals(mCountry1, userProxy.getCountry());
    }

    @Test
    void whenSearchignInvalidUser_throwUserNotFoundException(){
        given(mUserRepository.findByUsername("username1")).willReturn(null);

        assertThrows(UserNotFoundException.class, () -> mUserService.getUserInfo("username1"));
    }
}
