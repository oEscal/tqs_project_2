package com.api.demo.grid.repository;

import com.api.demo.grid.models.GameKey;
import com.api.demo.grid.models.Sell;
import com.api.demo.grid.models.User;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class SellRepositoryTest {
    @Autowired
    private TestEntityManager mEntityManager;

    @Autowired
    private SellRepository mRepository;

    @Test
    @SneakyThrows
    void whenFindById_getSell(){
        User user = new User();
        user.setUsername("mUsername1");
        user.setName("mName1");
        user.setEmail("mEmail1");
        user.setPassword("mPassword1");
        user.setCountry("mCountry1");
        user.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse("17/10/2010"));
        user.setStartDate(new SimpleDateFormat("dd/MM/yyyy").parse("25/05/2020"));
        mEntityManager.persistAndFlush(user);

        Sell sell = new Sell();
        sell.setDate(new Date());
        sell.setUser(user);

        mEntityManager.persistAndFlush(sell);

        assertEquals(sell, mRepository.findById(sell.getId()).get());
    }

    @Test
    void whenInvalidId_ReceiveEmpty(){
        assertEquals(Optional.empty(), mRepository.findById(2L));
    }

    @Test
    void whenFindByKey_GetSell(){
        GameKey gameKey = new GameKey();
        mEntityManager.persistAndFlush(gameKey);

        Sell sell = new Sell();
        sell.setGameKey(gameKey);

        mEntityManager.persistAndFlush(sell);

        assertEquals(sell, mRepository.findByGameKey(gameKey).get());
    }

    @Test
    void whenInvalidKey_ReceiveEmpty(){
        GameKey gameKey = mEntityManager.persistAndFlush(new GameKey());

        assertEquals(Optional.empty(), mRepository.findByGameKey(gameKey));
    }
}