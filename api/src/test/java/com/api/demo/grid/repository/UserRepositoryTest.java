package com.api.demo.grid.repository;

import com.api.demo.grid.models.User;
import lombok.SneakyThrows;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private TestEntityManager mEntityManager;

    @Autowired
    private UserRepository mUserRepository;

    // specifications for user1
    private User mUser1;
    private String mUsername1 = "username1",
            mName1 = "name1",
            mEmail1 = "email1",
            mCountry1 = "country1",
            mPassword1 = "password1",
            mBirthDateStr = "17/10/2010";

    // credit card details limits
    private static final int CREDIT_CARD_NUMBER_MAX_LENGTH = 19;
    private static final int CREDIT_CARD_NUMBER_MIN_LENGTH = 8;
    private static final int CREDIT_CARD_CSC_MAX_LENGTH = 4;
    private static final int CREDIT_CARD_CSC_MIN_LENGTH = 3;

    // for credit card and user
    private static final String FUTURE_DATE = "17/10/2100",
                                PAST_DATE = "17/10/1900";


    @BeforeEach
    @SneakyThrows
    void setup() {

        mUser1 = new User();
        mUser1.setUsername(mUsername1);
        mUser1.setName(mName1);
        mUser1.setEmail(mEmail1);
        mUser1.setPassword(mPassword1);
        mUser1.setCountry(mCountry1);
        mUser1.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse(mBirthDateStr));

        mEntityManager.persistAndFlush(mUser1);
    }


    /***
     *  Required User info get and set tests
     ***/
    @Test
    void whenUserNameExists_receiveCorrectUser() {

        assertEquals(mUser1, mUserRepository.findByUsername(mUsername1));
    }

    @Test
    @SneakyThrows
    void whenUserNameExists_receiveUserWithSameName() {

        User userExpected = new User();
        userExpected.setUsername(mUsername1);
        userExpected.setName(mName1);
        userExpected.setEmail(mEmail1);
        userExpected.setCountry(mCountry1);
        userExpected.setPassword(mPassword1);
        userExpected.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse(mBirthDateStr));

        assertEquals(userExpected, mUserRepository.findByUsername(mUsername1));
    }

    @Test
    void whenUserNameNotExists_receiveNothing() {

        assertNull(mUserRepository.findByUsername("non_exist_user"));
    }

    @Test
    void whenGetDefaultUser_receiveUserNotAdmin() {

        assertFalse(mUserRepository.findByUsername(mUsername1).isAdmin());
    }

    @Test
    void whenSetUserAdmin_receiveUserAdmin() {

        mUser1.setAdmin(true);
        mEntityManager.persistAndFlush(mUser1);

        assertTrue(mUserRepository.findByUsername(mUsername1).isAdmin());
    }

    @Test
    @SneakyThrows
    void whenSetUserFutureBirthDate_setIsUnsuccessful() {

        mUser1.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse(FUTURE_DATE));

        assertThrows(ConstraintViolationException.class, () -> mEntityManager.persistAndFlush(mUser1));
    }


    /***
     *  User card's number, csc and expiration date limits tests
     ***/
    @Test
    void whenSetCreditCardNumberMaxLength_setIsSuccessful() {

        mUser1.setCreditCardNumber(RandomStringUtils.randomNumeric(CREDIT_CARD_NUMBER_MAX_LENGTH));
        mEntityManager.persistAndFlush(mUser1);

        assertEquals(CREDIT_CARD_NUMBER_MAX_LENGTH,
                mUserRepository.findByUsername(mUsername1).getCreditCardNumber().length());
    }

    @Test
    void whenSetCreditCardNumberMinLength_setIsSuccessful() {

        mUser1.setCreditCardNumber(RandomStringUtils.randomNumeric(CREDIT_CARD_NUMBER_MIN_LENGTH));
        mEntityManager.persistAndFlush(mUser1);

        assertEquals(CREDIT_CARD_NUMBER_MIN_LENGTH,
                mUserRepository.findByUsername(mUsername1).getCreditCardNumber().length());
    }

    @Test
    void whenSetCSCMaxLength_setIsSuccessful() {

        mUser1.setCreditCardCSC(RandomStringUtils.randomNumeric(CREDIT_CARD_CSC_MAX_LENGTH));
        mEntityManager.persistAndFlush(mUser1);

        assertEquals(CREDIT_CARD_CSC_MAX_LENGTH,
                mUserRepository.findByUsername(mUsername1).getCreditCardCSC().length());
    }

    @Test
    void whenSetCSCMinLength_setIsSuccessful() {

        mUser1.setCreditCardCSC(RandomStringUtils.randomNumeric(CREDIT_CARD_CSC_MIN_LENGTH));
        mEntityManager.persistAndFlush(mUser1);

        assertEquals(CREDIT_CARD_CSC_MIN_LENGTH,
                mUserRepository.findByUsername(mUsername1).getCreditCardCSC().length());
    }

    @Test
    @SneakyThrows
    void whenSetNonExpiredCardExpirationDate_setIsSuccessful() {

        Date expectedExpirationDate = new SimpleDateFormat("dd/MM/yyyy").parse(FUTURE_DATE);

        mUser1.setCreditCardExpirationDate(expectedExpirationDate);
        mEntityManager.persistAndFlush(mUser1);

        assertEquals(expectedExpirationDate.clone(),
                mUserRepository.findByUsername(mUsername1).getCreditCardExpirationDate());
    }

    @Test
    void whenSetCreditCardNumberMoreMaxLength_setIsUnsuccessful() {

        mUser1.setCreditCardNumber(RandomStringUtils.randomNumeric(CREDIT_CARD_NUMBER_MAX_LENGTH + 1));

        assertThrows(ConstraintViolationException.class, () -> mEntityManager.persistAndFlush(mUser1));
    }

    @Test
    void whenSetCreditCardNumberLessMinLength_setIsUnsuccessful() {

        mUser1.setCreditCardNumber(RandomStringUtils.randomNumeric(CREDIT_CARD_NUMBER_MIN_LENGTH - 1));

        assertThrows(ConstraintViolationException.class, () -> mEntityManager.persistAndFlush(mUser1));
    }

    @Test
    void whenSetCSCMoreMaxLength_setIsUnsuccessful() {

        mUser1.setCreditCardCSC(RandomStringUtils.randomNumeric(CREDIT_CARD_CSC_MAX_LENGTH + 1));

        assertThrows(ConstraintViolationException.class, () -> mEntityManager.persistAndFlush(mUser1));
    }

    @Test
    void whenSetCSCLessMinLength_setIsUnsuccessful() {

        mUser1.setCreditCardCSC(RandomStringUtils.randomNumeric(CREDIT_CARD_CSC_MIN_LENGTH - 1));

        assertThrows(ConstraintViolationException.class, () -> mEntityManager.persistAndFlush(mUser1));
    }

    @Test
    @SneakyThrows
    void whenSetExpiredCardExpirationDate_setIsUnsuccessful() {

        mUser1.setCreditCardExpirationDate(new SimpleDateFormat("dd/MM/yyyy").parse(PAST_DATE));

        assertThrows(ConstraintViolationException.class, () -> mEntityManager.persistAndFlush(mUser1));
    }


    /***
     *  User's card number and csc are just numbers numbers (non alphabetic chars allowed) tests
     ***/
    @Test
    void whenSetCardNumberWithAlphanumericChars_setIsUnsuccessful() {

        mUser1.setCreditCardNumber(RandomStringUtils.randomAlphanumeric(CREDIT_CARD_NUMBER_MAX_LENGTH));

        assertThrows(ConstraintViolationException.class, () -> mEntityManager.persistAndFlush(mUser1));
    }

    @Test
    void whenSetCSCNumberWithAlphanumericChars_setIsUnsuccessful() {

        mUser1.setCreditCardNumber(RandomStringUtils.randomAlphanumeric(CREDIT_CARD_CSC_MAX_LENGTH));

        assertThrows(ConstraintViolationException.class, () -> mEntityManager.persistAndFlush(mUser1));
    }

    /***
     *  User's username and email params are in fact unique
     ***/
    @Test
    @SneakyThrows
    void whenSetTwoUsersWithSameUsername_setIsUnsuccessful() {

        User userInsert = new User();
        userInsert.setUsername(mUsername1);
        userInsert.setName(mName1);
        userInsert.setEmail("email_test");
        userInsert.setPassword(mPassword1);
        userInsert.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse(mBirthDateStr));

        assertThrows(PersistenceException.class, () -> mEntityManager.persistAndFlush(userInsert));
    }

    @Test
    @SneakyThrows
    void whenSetTwoUsersWithSameEmail_setIsUnsuccessful() {

        User userInsert = new User();
        userInsert.setUsername("username_test");
        userInsert.setName(mName1);
        userInsert.setEmail(mEmail1);
        userInsert.setPassword(mPassword1);
        userInsert.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse(mBirthDateStr));

        assertThrows(PersistenceException.class, () -> mEntityManager.persistAndFlush(userInsert));
    }
}
