package com.api.demo.grid.controller;

import com.api.demo.grid.models.GameKey;
import com.api.demo.grid.models.Sell;
import com.api.demo.grid.pojos.GameKeyPOJO;
import com.api.demo.grid.pojos.SellPOJO;
import com.api.demo.grid.service.GridService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/grid")
public class ListingsController {
    @Autowired
    private GridService gridService;

    @PostMapping("/gamekey")
    public ResponseEntity<GameKey> saveSellAndGameKey(@RequestBody GameKeyPOJO gameKeyPOJO) {
        GameKey gameKey = gridService.saveGameKey(gameKeyPOJO);
        if (gameKey == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not save Game Key");
        }
        return new ResponseEntity<>(gameKey, HttpStatus.OK);
    }

    @PostMapping("/sell-listing")
    public ResponseEntity<Sell> saveSell(@RequestBody SellPOJO sellPOJO) {
        Sell sell = gridService.saveSell(sellPOJO);
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
}
