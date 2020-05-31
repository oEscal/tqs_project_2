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
import org.springframework.transaction.annotation.Transactional;

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
        return mAuctionRepository.findByGameKey_RealKey(key);
    }

    public Auction addAuction(AuctionPOJO auctionPOJO) throws ExceptionDetails {

        String gameKeyStr = auctionPOJO.getGameKey();

        // verify if there is a price
        if (auctionPOJO.getPrice() <= 0) {
            throw new ExceptionDetails("The auction must begin in a price above 0");
        }

        // verify if the game key is already in some auction
        if (this.getAuctionByGameKey(gameKeyStr) != null) {
            throw new ExceptionDetails("There is already an auction for that game key");
        }

        // verify if the game key is already in some sell
        if (this.mSellRepository.findByGameKey_RealKey(gameKeyStr) != null) {
            throw new ExceptionDetails("There is already a sell for that game key");
        }

        // verify the auctioneer exists
        User auctioneer = mUserRepository.findByUsername(auctionPOJO.getAuctioneer());
        if (auctioneer == null) {
            throw new ExceptionDetails("The Auctioneer doesn't exists");
        }

        GameKey gameKey;
        Optional<GameKey> possibleGameKey = mGameKeyRepository.findByRealKey(gameKeyStr);

        // verify if the game key exists
        if (possibleGameKey.isPresent()) {
            gameKey = possibleGameKey.get();
        } else {
            throw new ExceptionDetails("The Game key doesn't exists");
        }

        Auction auctionSave = new Auction();
        auctionSave.setAuctioneer(auctioneer);
        auctionSave.setGameKey(gameKey);
        auctionSave.setPrice(auctionPOJO.getPrice());
        auctionSave.setEndDate(auctionPOJO.getEndDate());

        return mAuctionRepository.save(auctionSave);
    }

    @Transactional
    public Auction addBidding(String user, String gameKey, double price) throws ExceptionDetails {

        Auction auction = mAuctionRepository.findByGameKey_RealKey(gameKey);
        User buyer = mUserRepository.findByUsername(user);

        // verify the buyer exists
        if (buyer == null) {
            throw new ExceptionDetails("The Buyer doesn't exists");
        }

        // verify the auction exists
        if (auction == null) {
            throw new ExceptionDetails("The Auction doesn't exists");
        }

        // verify the buyer is the same as the current
        if (auction.getBuyer() != null && auction.getBuyer().getUsername().equals(user)) {
            throw new ExceptionDetails("The new buyer can't be the same as the current");
        }

        // verify the buyer is the same as the auctioneer
        if (auction.getAuctioneer().getUsername().equals(user)) {
            throw new ExceptionDetails("The buyer can't be the same as the auctioneer");
        }

        // verify the current price
        double currentPrice = auction.getPrice();
        if (currentPrice >= price) {
            throw new ExceptionDetails("The new price must be higher than the current");
        }

        auction.setBuyer(buyer);
        auction.setPrice(price);

        return mAuctionRepository.save(auction);
    }
}
