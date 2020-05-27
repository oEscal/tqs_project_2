package com.api.demo.grid.service;


import com.api.demo.grid.dtos.UserDTO;
import com.api.demo.grid.models.Auction;
import com.api.demo.grid.models.GameKey;
import com.api.demo.grid.models.User;
import com.api.demo.grid.pojos.AuctionPOJO;
import com.api.demo.grid.repository.AuctionRepository;
import com.api.demo.grid.repository.GameKeyRepository;
import com.api.demo.grid.repository.UserRepository;
import org.modelmapper.ModelMapper;
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
    private ModelMapper mModelMapper;


    public Auction getAuctionByGameKey(String key) {
        return mAuctionRepository.findByGameKey_rKey(key);
    }

    public Auction addAuction(AuctionPOJO auctionPOJO) {

        User auctioneer = mUserRepository.findByUsername(auctionPOJO.getAuctioneer());
        GameKey gameKey = null;

        Optional<GameKey> possibleGameKey = mGameKeyRepository.findByrKey(auctionPOJO.getGameKey());
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


    private Auction convertToEntity(AuctionPOJO auctionPOJO) {
        return mModelMapper.map(auctionPOJO, Auction.class);
    }
}
