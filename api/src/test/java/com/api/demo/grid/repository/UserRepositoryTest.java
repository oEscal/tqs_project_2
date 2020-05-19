package com.api.demo.grid.repository;

import com.api.demo.grid.models.User;
import com.api.demo.grid.models.UserRole;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;


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
            mBirthDateStr = "17/10/2010",
            mRole1 = "USER";


    @SneakyThrows
    @BeforeEach
    void setup() {

        mUser1 = new User();
        mUser1.setUsername(mUsername1);
        mUser1.setName(mName1);
        mUser1.setEmail(mEmail1);
        mUser1.setPassword(mPassword1);
        UserRole userRole = new UserRole();
        userRole.setName(mRole1);
        mUser1.setRole(userRole);
        mUser1.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse(mBirthDateStr));

        mEntityManager.persistAndFlush(mUser1);
    }

    @SneakyThrows
    @Test
    void whenUserExists_receiveCorrectUser() {

        User userExpected = new User();
        userExpected.setUsername(mUsername1);
        userExpected.setName(mName1);
        userExpected.setEmail(mEmail1);
        userExpected.setPassword(mPassword1);
        UserRole userRole = new UserRole();
        userRole.setName(mRole1);
        userExpected.setRole(userRole);
        userExpected.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse(mBirthDateStr));

        assertEquals(userExpected, mEntityManager.persistAndFlush(mUser1));
    }

    // TODO -> testar também inserção de roles que não são permitidas
}
