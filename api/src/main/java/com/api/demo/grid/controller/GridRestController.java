package com.api.demo.grid.controller;
import com.api.demo.grid.exceptions.UnavailableListingException;
import com.api.demo.grid.exceptions.UnsufficientFundsException;
import com.api.demo.grid.models.*;
import com.api.demo.grid.pojos.*;
import com.api.demo.grid.service.GridService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/grid")
public class GridRestController {
    private static final String sERRORSEARCHER = "No Game found with Id ";

    @Autowired
    private GridService mGridService;

    @GetMapping("/all")
    public ResponseEntity<List<Game>> getAllGames(){
        return ResponseEntity.ok(mGridService.getAllGames());
    }

    @GetMapping("/game")
    public ResponseEntity<Game> getGameInfo(@RequestParam long id){
        Game gameResponse = mGridService.getGameById(id);
        if (gameResponse == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, sERRORSEARCHER + id);
        }
        return ResponseEntity.ok(gameResponse);
    }

    @GetMapping("/genre")
    public ResponseEntity<List<Game>> getGameByGenre(@RequestParam String genre){
        List<Game> gameList = mGridService.getAllGamesWithGenre(genre);
        if (gameList == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, sERRORSEARCHER + genre);
        }
        return new ResponseEntity<>(gameList, HttpStatus.OK);
    }

    @GetMapping("/name")
    public ResponseEntity<List<Game>> getGameByName(@RequestParam String name){
        List<Game> gameList = mGridService.getAllGamesByName(name);
        if (gameList == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, sERRORSEARCHER + name);
        }
        return new ResponseEntity<>(gameList, HttpStatus.OK);
    }

    @GetMapping("/developer")
    public ResponseEntity<List<Game>> getGameByDev(@RequestParam String dev){
        List<Game> gameList = mGridService.getAllGamesByDev(dev);
        if (gameList == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, sERRORSEARCHER + dev);
        }
        return new ResponseEntity<>(gameList, HttpStatus.OK);
    }

    @GetMapping("/publisher")
    public ResponseEntity<List<Game>> getGameByPub(@RequestParam String pub){
        List<Game> gameList = mGridService.getAllGamesByPublisher(pub);
        if (gameList == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, sERRORSEARCHER + pub);
        }
        return new ResponseEntity<>(gameList, HttpStatus.OK);
    }

    @PostMapping("/game")
    public ResponseEntity<Game> saveGame(@RequestBody GamePOJO gamePOJO){
        Game game = mGridService.saveGame(gamePOJO);
        if (game == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not save Game");
        }
        return new ResponseEntity<>(game, HttpStatus.OK);
    }

    @PostMapping("/genre")
    public ResponseEntity<GameGenre> saveGameGenre(@RequestBody GameGenrePOJO gameGenrePOJO){
        return new ResponseEntity<>(mGridService.saveGameGenre(gameGenrePOJO), HttpStatus.OK);
    }

    @PostMapping("/publisher")
    public ResponseEntity<Publisher> savePublisher(@RequestBody PublisherPOJO publisherPOJO){
        return new ResponseEntity<>(mGridService.savePublisher(publisherPOJO), HttpStatus.OK);
    }

    @PostMapping("/developer")
    public ResponseEntity<Developer> saveDeveloper(@RequestBody DeveloperPOJO developerPOJO){
        return new ResponseEntity<>(mGridService.saveDeveloper(developerPOJO), HttpStatus.OK);
    }

    @PostMapping("/gamekey")
    public ResponseEntity<GameKey> saveSellAndGameKey(@RequestBody GameKeyPOJO gameKeyPOJO){
        GameKey gameKey = mGridService.saveGameKey(gameKeyPOJO);
        if (gameKey == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not save Game Key");
        }
        return new ResponseEntity<>(gameKey, HttpStatus.OK);
    }

    @PostMapping("/sell-listing")
    public ResponseEntity<Sell> saveSell(@RequestBody SellPOJO sellPOJO){
        Sell sell = mGridService.saveSell(sellPOJO);
        if (sell == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not save Sell Listing");
        }
        return new ResponseEntity<>(sell, HttpStatus.OK);
    }

    @PostMapping("/buy-listing")
    public ResponseEntity<List<Buy>> saveBuy(@RequestBody @Valid BuyListingsPOJO buyListingsPOJO){
        List<Buy> buys = new ArrayList<>();
        try {
            buys = mGridService.saveBuy(buyListingsPOJO);
        } catch (UnavailableListingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (UnsufficientFundsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return new ResponseEntity<>(buys, HttpStatus.OK);
    }
}
