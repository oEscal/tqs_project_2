package com.api.demo.grid.service;

import com.api.demo.grid.dtos.UserDTO;
import com.api.demo.grid.exception.ExceptionDetails;
import com.api.demo.grid.exception.UserNotFoundException;
import com.api.demo.grid.models.GameKey;
import com.api.demo.grid.models.Sell;
import com.api.demo.grid.models.User;
import com.api.demo.grid.pojos.UserUpdatePOJO;
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
import org.springframework.security.test.context.support.WithMockUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
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
        GameKey gameKey = new GameKey();
        gameKey.setRealKey("key");
        gameKey.setId(1l);
        Sell sell = new Sell();
        sell.setId(2l);
        sell.setGameKey(gameKey);
        mUser1.addSell(sell);
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
        assertEquals(2l, userProxy.getListings().get(0).getId());
        assertEquals(1l, userProxy.getListings().get(0).getGameKey().getId());
    }

    @Test
    void whenSearchingInvalidUser_throwUserNotFoundException(){
        given(mUserRepository.findByUsername("username1")).willReturn(null);

        assertThrows(UserNotFoundException.class, () -> mUserService.getUserInfo("username1"));
    }

    @Test
    @WithMockUser(username = "spring")
    void whenSearchingValidUser_andAmUser_getFullInformation() {
        //Added sell
        GameKey gameKey = new GameKey();
        gameKey.setRealKey("key");
        gameKey.setId(1l);
        Sell sell = new Sell();
        sell.setId(2l);
        sell.setGameKey(gameKey);
        mUser1.addSell(sell);
        given(mUserRepository.findByUsername("username1")).willReturn(mUser1);

        User user = null;
        try {
            user = mUserService.getFullUserInfo("username1");
        } catch (UserNotFoundException e) {
            fail();
        }

        assertEquals(mUsername1, user.getUsername());
        assertEquals(mName1, user.getName());
        assertEquals(mBirthDateStr, user.getBirthDateStr());
        assertEquals(mCountry1, user.getCountry());
        assertEquals(2l, new ArrayList<>(user.getSells()).get(0).getId());
        assertEquals(1l, new ArrayList<>(user.getSells()).get(0).getGameKey().getId());
        //TODO check for buys and wishlist when endpoints are done
    }

    @Test
    @WithMockUser(username = "spring")
    void whenSearchingInvalidUser_andAmUser_throwUserNotFoundException(){
        given(mUserRepository.findByUsername("username1")).willReturn(null);

        assertThrows(UserNotFoundException.class, () -> mUserService.getUserInfo("username1"));
    }

    @Test
    @WithMockUser(username = "spring")
    @SneakyThrows
    void whenAddingFundsToUser_returnUpdatedUser(){
        given(mUserRepository.findById(anyLong())).willReturn(Optional.ofNullable(mUser1));

        User user = mUserService.addFundsToUser(4l, 5);

        assertEquals(5, user.getFunds());
    }

    @Test
    @WithMockUser(username = "spring")
    @SneakyThrows
    void whenUpdatingAllUserInfo_returnFullyUpdatedUser(){
        String newName1 = "springname",
                newEmail1 = "springemail",
                newCountry1 = "springcountry",
                newPassword1 = "springpassword",
                newPasswordEncrypted1 = "springpassword_encrypted1",
                newBirthDateStr = "25/10/2010";
        given(passwordEncoder.encode(newPassword1)).willReturn(newPasswordEncrypted1);
        given(mUserRepository.findById(anyLong())).willReturn(Optional.ofNullable(mUser1));
        given(mUserRepository.findByEmail(anyString())).willReturn(null);

        String creditCardNumber = RandomStringUtils.randomNumeric(10);
        String creditCardCSC = RandomStringUtils.randomNumeric(3);
        String creditCardOwner = "Test user";
        Date creditCardExpirationDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/10/2030");
        Date newBirthDate = new SimpleDateFormat("dd/MM/yyyy").parse(newBirthDateStr);

        UserUpdatePOJO userUpdatePOJO = new UserUpdatePOJO();
        userUpdatePOJO.setName(newName1);
        userUpdatePOJO.setEmail(newEmail1);
        userUpdatePOJO.setCountry(newCountry1);
        userUpdatePOJO.setBirthDate(newBirthDate);
        userUpdatePOJO.setPassword(newPassword1);
        userUpdatePOJO.setCreditCardNumber(creditCardNumber);
        userUpdatePOJO.setCreditCardCSC(creditCardCSC);
        userUpdatePOJO.setCreditCardOwner(creditCardOwner);
        userUpdatePOJO.setCreditCardExpirationDate(creditCardExpirationDate);

        User newUser = mUserService.updateUser(1l, userUpdatePOJO);

        assertEquals(newName1, newUser.getName());
        assertEquals(newEmail1, newUser.getEmail());
        assertEquals(newCountry1, newUser.getCountry());
        assertEquals(newPasswordEncrypted1, newUser.getPassword());
        assertEquals(newBirthDate, newUser.getBirthDate());
        assertEquals(creditCardNumber, newUser.getCreditCardNumber());
        assertEquals(creditCardCSC, newUser.getCreditCardCSC());
        assertEquals(creditCardOwner, newUser.getCreditCardOwner());
        assertEquals(creditCardExpirationDate, newUser.getCreditCardExpirationDate());
    }

    @Test
    @WithMockUser(username = "spring")
    @SneakyThrows
    void whenUpdatingUser_returnsUserUpdated_withNonNullChanged_andNullKeepsSame(){
        String newName = "springname";
        String newDescription = "this is a spring description";
        UserUpdatePOJO userUpdatePOJO = new UserUpdatePOJO();
        userUpdatePOJO.setName(newName);
        userUpdatePOJO.setDescription(newDescription);
        given(mUserRepository.findById(anyLong())).willReturn(Optional.ofNullable(mUser1));
        given(mUserRepository.findByEmail(anyString())).willReturn(null);

        User newUser = mUserService.updateUser(1l, userUpdatePOJO);
        assertEquals(newName, newUser.getName());
        assertEquals(newDescription, newUser.getDescription());
        assertEquals(mPasswordEncrypted1, newUser.getPassword());
    }

    @Test
    @WithMockUser(username = "spring")
    void whenAddingFundToUser_AndUserDoesNotExist_throwsException(){
        given(mUserRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> mUserService.addFundsToUser(4l, 5));
    }

    @Test
    @WithMockUser(username = "spring")
    void whenUpdatingUser_withInvalidUsername_throwUserNotFoundException(){
        given(mUserRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> mUserService.updateUser(1l, new UserUpdatePOJO())
        );
    }

    @Test
    @WithMockUser(username = "spring")
    @SneakyThrows
    void whenUpdatingUser_andEmailIsRepeated_throwsException(){
        UserUpdatePOJO userUpdatePOJO = new UserUpdatePOJO();
        userUpdatePOJO.setEmail(mEmail1);
        given(mUserRepository.findById(anyLong())).willReturn(Optional.ofNullable(mUser1));
        given(mUserRepository.findByEmail(anyString())).willReturn(mUser1);

        assertThrows(ExceptionDetails.class,
                () -> mUserService.updateUser(1l, userUpdatePOJO)
        );
    }
}
