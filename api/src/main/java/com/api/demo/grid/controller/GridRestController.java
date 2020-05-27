package com.api.demo.grid.controller;
import com.api.demo.grid.exception.UnavailableListingException;
import com.api.demo.grid.exception.UnsufficientFundsException;
import com.api.demo.grid.exception.GameNotFoundException;
import com.api.demo.grid.models.Developer;
import com.api.demo.grid.models.Game;
import com.api.demo.grid.models.GameGenre;
import com.api.demo.grid.models.GameKey;
import com.api.demo.grid.models.Publisher;
import com.api.demo.grid.models.Sell;
import com.api.demo.grid.models.Buy;
import com.api.demo.grid.pojos.DeveloperPOJO;
import com.api.demo.grid.pojos.GameGenrePOJO;
import com.api.demo.grid.pojos.GameKeyPOJO;
import com.api.demo.grid.pojos.GamePOJO;
import com.api.demo.grid.pojos.PublisherPOJO;
import com.api.demo.grid.pojos.SearchGamePOJO;
import com.api.demo.grid.pojos.SellPOJO;
import com.api.demo.grid.pojos.BuyListingsPOJO;
import com.api.demo.grid.service.GridService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/grid")
@CrossOrigin
public class GridRestController {
    public static final String ERROR = "No Game found with Id ";

    @Autowired
    private GridService mGridService;

    @GetMapping(value="/all", params = { "page" })
    public ResponseEntity<Page<Game>> getAllGames(@RequestParam("page") int page){
        return ResponseEntity.ok(mGridService.getAllGames(page));
    }

    @GetMapping("/game")
    public ResponseEntity<Game> getGameInfo(@RequestParam long id){
        Game gameResponse = mGridService.getGameById(id);
        if (gameResponse == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR + id);
        }
        return ResponseEntity.ok(gameResponse);
    }

    @GetMapping("/genre")
    public ResponseEntity<List<Game>> getGameByGenre(@RequestParam String genre){
        List<Game> gameList = mGridService.getAllGamesWithGenre(genre);
        if (gameList == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR + genre);
        }
        return new ResponseEntity<>(gameList, HttpStatus.OK);
    }

    @GetMapping("/name")
    public ResponseEntity<List<Game>> getGameByName(@RequestParam String name){
        List<Game> gameList = mGridService.getAllGamesByName(name);
        if (gameList == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR + name);
        }
        return new ResponseEntity<>(gameList, HttpStatus.OK);
    }

    @GetMapping("/developer")
    public ResponseEntity<List<Game>> getGameByDev(@RequestParam String dev){
        List<Game> gameList = mGridService.getAllGamesByDev(dev);
        if (gameList == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR + dev);
        }
        return new ResponseEntity<>(gameList, HttpStatus.OK);
    }

    @GetMapping("/publisher")
    public ResponseEntity<List<Game>> getGameByPub(@RequestParam String pub){
        List<Game> gameList = mGridService.getAllGamesByPublisher(pub);
        if (gameList == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR + pub);
        }
        return new ResponseEntity<>(gameList, HttpStatus.OK);
    }

    @GetMapping("/sell-listing")
    public ResponseEntity<Page<Sell>> getListingsByGame(@RequestParam long gameId, @RequestParam int page){
        try{
            return new ResponseEntity<>(mGridService.getAllSellListings(gameId, page), HttpStatus.OK);
        } catch (GameNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found in Database");
        }
    }
    
    @PostMapping("/search")
    public ResponseEntity<Page<Game>> getGamesFromSearch(@RequestBody SearchGamePOJO searchGamePOJO){
        return ResponseEntity.ok(mGridService.pageSearchGames(searchGamePOJO));
    }

    @PostMapping("/add-game")
    public ResponseEntity<Game> saveGame(@RequestBody GamePOJO gamePOJO){
        Game game = mGridService.saveGame(gamePOJO);
        if (game == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not save Game");
        }
        return new ResponseEntity<>(game, HttpStatus.OK);
    }

    @PostMapping("/add-genre")
    public ResponseEntity<GameGenre> saveGameGenre(@RequestBody GameGenrePOJO gameGenrePOJO){
        return new ResponseEntity<>(mGridService.saveGameGenre(gameGenrePOJO), HttpStatus.OK);
    }

    @PostMapping("/add-publisher")
    public ResponseEntity<Publisher> savePublisher(@RequestBody PublisherPOJO publisherPOJO){
        return new ResponseEntity<>(mGridService.savePublisher(publisherPOJO), HttpStatus.OK);
    }

    @PostMapping("/add-developer")
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

    @PostMapping("/add-sell-listing")
    public ResponseEntity<Sell> saveSell(@RequestBody SellPOJO sellPOJO){
        Sell sell = mGridService.saveSell(sellPOJO);
        if (sell == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not save Sell Listing");
        }
        return new ResponseEntity<>(sell, HttpStatus.OK);
    }

    @PostMapping("/buy-listing")
    public ResponseEntity<List<Buy>> saveBuy(@RequestBody @Valid BuyListingsPOJO buyListingsPOJO){
        List<Buy> buys;
        try {
            buys = mGridService.saveBuy(buyListingsPOJO);
        } catch (UnavailableListingException | UnsufficientFundsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return new ResponseEntity<>(buys, HttpStatus.OK);
    }
    
    @PostMapping(value = "/add-wish-list", params = {"game_id", "user_id"})
    public ResponseEntity<Set<Game>> addWishList(@RequestParam("game_id") long gameID, @RequestParam("user_id") long userID) {
        Set<Game> games = mGridService.addWishListByUserID(gameID, userID);
        if (games == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not add game to wish List");

        return new ResponseEntity<>(games, HttpStatus.OK);
    }
}
