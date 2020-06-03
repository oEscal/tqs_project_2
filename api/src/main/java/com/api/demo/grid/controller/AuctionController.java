package com.api.demo.grid.controller;


import com.api.demo.grid.exception.ForbiddenException;
import com.api.demo.grid.models.Auction;
import com.api.demo.grid.pojos.AuctionPOJO;
import com.api.demo.grid.pojos.BiddingPOJO;
import com.api.demo.grid.proxy.AuctionProxy;
import com.api.demo.grid.service.AuctionService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/grid")
@CrossOrigin
public class AuctionController {

    @Autowired
    private AuctionService mAuctionService;


    @SneakyThrows
    @PostMapping("/create-auction")
    public AuctionProxy createAuction(@RequestHeader("Authorization") String auth,
                                      @Valid @RequestBody AuctionPOJO auction) {

        // verify if the user requesting is the same as the auctioneer
        String username = ControllerUtils.getUserFromAuth(auth);
        if (!Objects.equals(username, auction.getAuctioneer())) {
            throw new ForbiddenException("The user requesting must be the same as the auctioneer");
        }

        return new AuctionProxy(mAuctionService.addAuction(auction));
    }

    @SneakyThrows
    @PostMapping("/create-bidding")
    public AuctionProxy createBidding(@RequestHeader("Authorization") String auth,
                                      @Valid @RequestBody BiddingPOJO bidding) {

        // verify if the user requesting is the same as the buyer
        String username = ControllerUtils.getUserFromAuth(auth);
        if (!Objects.equals(username, bidding.getUser())) {
            throw new ForbiddenException("The user requesting must be the same as the buyer");
        }

        return new AuctionProxy(mAuctionService.addBidding(bidding.getUser(), bidding.getGameKey(), bidding.getPrice()),
                true);
    }

    @SneakyThrows
    @GetMapping("/auctions")
    public List<AuctionProxy> getAllAuctions(@RequestParam Long gameId) {

        List<Auction> auctions = mAuctionService.getAllAuctionsListings(gameId);
        List<AuctionProxy> allAuctions = new ArrayList<>();
        for (Auction auction : auctions) {
            allAuctions.add(new AuctionProxy(auction));
        }

        return allAuctions;
    }
}
