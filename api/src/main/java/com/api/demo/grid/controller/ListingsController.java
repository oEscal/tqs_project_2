package com.api.demo.grid.controller;

import com.api.demo.grid.exception.GameNotFoundException;
import com.api.demo.grid.exception.UnavailableListingException;
import com.api.demo.grid.exception.UnsufficientFundsException;
import com.api.demo.grid.models.Buy;
import com.api.demo.grid.models.GameKey;
import com.api.demo.grid.models.Sell;
import com.api.demo.grid.pojos.BuyListingsPOJO;
import com.api.demo.grid.pojos.GameKeyPOJO;
import com.api.demo.grid.pojos.SellPOJO;
import com.api.demo.grid.service.GridService;
import com.api.demo.grid.service.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/grid")
public class ListingsController {
    @Autowired
    private ListingService mListingService;

    @PostMapping("/gamekey")
    public ResponseEntity<GameKey> saveSellAndGameKey(@RequestBody GameKeyPOJO gameKeyPOJO) {
        GameKey gameKey = mListingService.saveGameKey(gameKeyPOJO);
        if (gameKey == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not save Game Key");
        }
        return new ResponseEntity<>(gameKey, HttpStatus.OK);
    }

    @PostMapping("/add-sell-listing")
    public ResponseEntity<Sell> saveSell(@RequestBody SellPOJO sellPOJO) {
        Sell sell = mListingService.saveSell(sellPOJO);
        if (sell == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not save Sell Listing");
        }
        return new ResponseEntity<>(sell, HttpStatus.OK);
    }

    @DeleteMapping(value ="/remove/sell-listing", params={"sell_id"})
    public ResponseEntity<Sell> deleteSell(@RequestHeader("Authorization") String auth,
                                           @RequestParam("sell_id") long sellId){
        return null;
    }

    @PostMapping("/buy-listing")
    public ResponseEntity<List<Buy>> saveBuy(@RequestBody @Valid BuyListingsPOJO buyListingsPOJO){
        List<Buy> buys;
        try {
            buys = mListingService.saveBuy(buyListingsPOJO);
        } catch (UnavailableListingException | UnsufficientFundsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return new ResponseEntity<>(buys, HttpStatus.OK);
    }

    @GetMapping("/sell-listing")
    public ResponseEntity<Page<Sell>> getListingsByGame(@RequestParam long gameId, @RequestParam int page){
        try{
            return new ResponseEntity<>(mListingService.getAllSellListings(gameId, page), HttpStatus.OK);
        } catch (GameNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found in Database");
        }
    }
}
