package com.api.demo.grid.service;


import com.api.demo.grid.dtos.UserDTO;
import com.api.demo.grid.models.Auction;
import com.api.demo.grid.models.User;
import com.api.demo.grid.pojos.AuctionPOJO;
import com.api.demo.grid.repository.AuctionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AuctionService {

    @Autowired
    private AuctionRepository mAuctionRepository;

    @Autowired
    private ModelMapper mModelMapper;


    public Auction getAuctionByGameKey(String key) {
        return mAuctionRepository.findByGameKey_rKey(key);
    }

    public Auction addAuction(AuctionPOJO auctionPOJO) {

        Auction auctionSave = convertToEntity(auctionPOJO);
        return mAuctionRepository.save(auctionSave);
    }


    private Auction convertToEntity(AuctionPOJO auctionPOJO) {
        return mModelMapper.map(auctionPOJO, Auction.class);
    }
}
