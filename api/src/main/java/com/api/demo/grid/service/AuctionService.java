package com.api.demo.grid.service;


import com.api.demo.grid.exception.ExceptionDetails;
import com.api.demo.grid.models.Auction;
import com.api.demo.grid.models.GameKey;
import com.api.demo.grid.models.User;
import com.api.demo.grid.pojos.AuctionPOJO;
import com.api.demo.grid.repository.AuctionRepository;
import com.api.demo.grid.repository.GameKeyRepository;
import com.api.demo.grid.repository.SellRepository;
import com.api.demo.grid.repository.UserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AuctionService {

    @Autowired
    private AuctionRepository mAuctionRepository;

    @Autowired
    private UserRepository mUserRepository;

    @Autowired
    private GameKeyRepository mGameKeyRepository;

    @Autowired
    private SellRepository mSellRepository;


    public Auction getAuctionByGameKey(String key) {
        return mAuctionRepository.findByGameKey_rKey(key);
    }

    @SneakyThrows
    public Auction addAuction(AuctionPOJO auctionPOJO) {

        String gameKeyStr = auctionPOJO.getGameKey();

        // verify if the game key is already in some auction
        if (this.getAuctionByGameKey(gameKeyStr) != null) {
            throw new ExceptionDetails("There is already an auction for that game key");
        }

        // verify if the game key is already in some sell
        if (this.mSellRepository.findByGameKey_rKey(gameKeyStr) != null) {
            throw new ExceptionDetails("There is already a sell for that game key");
        }

        User auctioneer = mUserRepository.findByUsername(auctionPOJO.getAuctioneer());
        GameKey gameKey = null;

        Optional<GameKey> possibleGameKey = mGameKeyRepository.findByrKey(gameKeyStr);
        if (possibleGameKey.isPresent()) {
            gameKey = possibleGameKey.get();
        }

        Auction auctionSave = new Auction();
        auctionSave.setAuctioneer(auctioneer);
        auctionSave.setGameKey(gameKey);
        auctionSave.setPrice(auctionPOJO.getPrice());
        auctionSave.setEndDate(auctionPOJO.getEndDate());
        return mAuctionRepository.save(auctionSave);
    }
}
