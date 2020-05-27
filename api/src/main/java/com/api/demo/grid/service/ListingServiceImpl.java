package com.api.demo.grid.service;

import com.api.demo.grid.exception.ListingNotFoundException;
import com.api.demo.grid.models.Game;
import com.api.demo.grid.models.GameKey;
import com.api.demo.grid.models.Sell;
import com.api.demo.grid.models.User;
import com.api.demo.grid.pojos.GameKeyPOJO;
import com.api.demo.grid.pojos.SellPOJO;
import com.api.demo.grid.repository.GameKeyRepository;
import com.api.demo.grid.repository.GameRepository;
import com.api.demo.grid.repository.SellRepository;
import com.api.demo.grid.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ListingServiceImpl implements ListingService{

    @Autowired
    private SellRepository mSellRepository;

    @Autowired
    private UserRepository mUserRepository;

    @Autowired
    private GameKeyRepository mGameKeyRepository;

    @Autowired
    private GameRepository mGameRepository;

    @Override
    public GameKey saveGameKey(GameKeyPOJO gameKeyPOJO) {
        Optional<Game> game = this.mGameRepository.findById(gameKeyPOJO.getGameId());
        if (game.isEmpty()) return null;
        Game realGame = game.get();

        GameKey gameKey = new GameKey();
        gameKey.setRKey(gameKeyPOJO.getKey());
        gameKey.setGame(realGame);
        gameKey.setRetailer(gameKeyPOJO.getRetailer());
        gameKey.setPlatform(gameKeyPOJO.getPlatform());
        this.mGameKeyRepository.save(gameKey);
        return gameKey;
    }

    @Override
    public Sell saveSell(SellPOJO sellPOJO) {
        Optional<User> user = this.mUserRepository.findById(sellPOJO.getUserId());
        if (user.isEmpty()) return null;
        User realUser = user.get();

        Optional<GameKey> gameKey = this.mGameKeyRepository.findByrKey(sellPOJO.getGameKey());
        if (gameKey.isEmpty()) return null;
        GameKey realGameKey = gameKey.get();

        Sell sell = new Sell();
        sell.setUser(realUser);
        sell.setGameKey(realGameKey);
        sell.setPrice(sellPOJO.getPrice());
        sell.setDate(sellPOJO.getDate());
        this.mSellRepository.save(sell);
        return sell;
    }

    @Override
    public Sell deleteSell(long sellId) throws ListingNotFoundException{
        Optional<Sell> sell = this.mSellRepository.findById(sellId);
        if (sell.isEmpty()) throw new ListingNotFoundException("This sell listing was not found");
        Sell realSell = sell.get();
        this.mSellRepository.delete(realSell);
        return realSell;
    }
}
