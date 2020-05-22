package com.api.demo.grid.repository;

import com.api.demo.grid.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private TestEntityManager mEntityManager;

    @Autowired
    private UserRepository mRepository;

    @Test
    void whenFindById_getGameKey(){
        User user = new User();

        mEntityManager.persistAndFlush(user);

        assertEquals(user, mRepository.findById(user.getId()).get());
    }

    @Test
    void whenInvalidId_ReceiveEmpty(){
        assertEquals(Optional.empty(), mRepository.findById(2L));
    }
}