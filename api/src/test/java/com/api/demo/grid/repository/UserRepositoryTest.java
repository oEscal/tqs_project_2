package com.api.demo.grid.repository;

import com.api.demo.grid.models.User;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


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
            mPassword1 = "password_test1",
            mBirthDateStr = "17/10/2010";


    @SneakyThrows
    @BeforeEach
    void setup() {

        mUser1 = new User();
        mUser1.setUsername(mUsername1);
        mUser1.setName(mName1);
        mUser1.setEmail(mEmail1);
        mUser1.setPassword(mPassword1);
        mUser1.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse(mBirthDateStr));

        mEntityManager.persistAndFlush(mUser1);
    }

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
        userExpected.setPassword(mPassword1);
        userExpected.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse(mBirthDateStr));

        assertEquals(userExpected, mUserRepository.findByUsername(mUsername1));
    }

    @Test
    void whenUserNameNotExists_receiveNothing() {

        assertNull(mUserRepository.findByUsername("non_exist_user"));
    }

    // TODO -> testar também inserção de roles que não são permitidas
}
