package com.api.demo.grid.service;

import com.api.demo.grid.exception.GameNotFoundException;
import com.api.demo.grid.exception.ListingNotFoundException;
import com.api.demo.grid.exception.UnavailableListingException;
import com.api.demo.grid.exception.UnsufficientFundsException;
import com.api.demo.grid.models.*;
import com.api.demo.grid.pojos.BuyListingsPOJO;
import com.api.demo.grid.pojos.GameKeyPOJO;
import com.api.demo.grid.pojos.SellPOJO;
import com.api.demo.grid.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    private BuyRepository mBuyRepository;

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

    @Override
    public List<Buy> saveBuy(BuyListingsPOJO buyListingsPOJO) throws UnavailableListingException,
            UnsufficientFundsException {
        List<Buy> buyList = new ArrayList<>();
        double bill = 0;
        Optional<Sell> sell;
        Buy buy;
        Optional<User> optionalUser = mUserRepository.findById(buyListingsPOJO.getUserId());
        User user;
        if (optionalUser.isEmpty()) return null;
        user = optionalUser.get();
        for (long sellId : buyListingsPOJO.getListingsId()){
            sell = mSellRepository.findById(sellId);
            if (sell.isEmpty()) throw new UnavailableListingException("This listing has been removed by the user");
            else if (sell.get().getPurchased() != null) throw new UnavailableListingException(
                    "This listing has been bought by another user");
            buy = new Buy();
            buy.setDate(new Date());
            buy.setSell(sell.get());
            buyList.add(buy);
            bill += sell.get().getPrice();
        }
        if (buyListingsPOJO.isWithFunds()) {
            if (bill > user.getFunds()) throw new UnsufficientFundsException("This user doesn't have enough funds");
            user.payWithFunds(bill);
        }
        for (Buy buy1: buyList) {
            user.addBuy(buy1);
            mBuyRepository.save(buy1);
        }
        return buyList;
    }

    @Override
    public Page<Sell> getAllSellListings(long gameId, int page) throws GameNotFoundException {
        Optional<Game> game = mGameRepository.findById(gameId);
        if (game.isEmpty()) throw new GameNotFoundException("Game not found in the database");
        return mSellRepository.findAllByGames(gameId, PageRequest.of(page, 6));
    }
}
