package com.api.demo.grid.controller;


import com.api.demo.grid.models.Auction;
import com.api.demo.grid.pojos.AuctionPOJO;
import com.api.demo.grid.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/grid")
@CrossOrigin
public class AuctionController {

    @Autowired
    private AuctionService mAuctionService;


    @PostMapping("/create-auction")
    public Auction createUser(@Valid @RequestBody AuctionPOJO auction) {

        return mAuctionService.addAuction(auction);
    }
}
