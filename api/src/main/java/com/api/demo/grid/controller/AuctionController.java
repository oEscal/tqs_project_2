package com.api.demo.grid.controller;


import com.api.demo.grid.exception.ForbiddenException;
import com.api.demo.grid.models.Auction;
import com.api.demo.grid.models.User;
import com.api.demo.grid.pojos.AuctionPOJO;
import com.api.demo.grid.proxy.AuctionProxy;
import com.api.demo.grid.service.AuctionService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
}
