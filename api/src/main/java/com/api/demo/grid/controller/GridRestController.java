package com.api.demo.grid.controller;

import com.api.demo.grid.models.*;
import com.api.demo.grid.pojos.*;
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


import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/grid")
@CrossOrigin
public class GridRestController {
    public static final String ERROR = "No Game found with Id ";

    @Autowired
    private GridService gridService;

    @GetMapping(value = "/all", params = {"page"})
    public ResponseEntity<Page<Game>> getAllGames(@RequestParam("page") int page) {
        return ResponseEntity.ok(gridService.getAllGames(page));
    }

    @GetMapping("/game")
    public ResponseEntity<Game> getGameInfo(@RequestParam long id) {
        Game gameResponse = gridService.getGameById(id);
        if (gameResponse == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR + id);
        }
        return ResponseEntity.ok(gameResponse);
    }

    @GetMapping("/genre")
    public ResponseEntity<List<Game>> getGameByGenre(@RequestParam String genre) {
        List<Game> gameList = gridService.getAllGamesWithGenre(genre);
        if (gameList == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR + genre);
        }
        return new ResponseEntity<>(gameList, HttpStatus.OK);
    }

    @GetMapping("/name")
    public ResponseEntity<List<Game>> getGameByName(@RequestParam String name) {
        List<Game> gameList = gridService.getAllGamesByName(name);
        if (gameList == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR + name);
        }
        return new ResponseEntity<>(gameList, HttpStatus.OK);
    }

    @GetMapping("/developer")
    public ResponseEntity<List<Game>> getGameByDev(@RequestParam String dev) {
        List<Game> gameList = gridService.getAllGamesByDev(dev);
        if (gameList == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR + dev);
        }
        return new ResponseEntity<>(gameList, HttpStatus.OK);
    }

    @GetMapping("/publisher")
    public ResponseEntity<List<Game>> getGameByPub(@RequestParam String pub) {
        List<Game> gameList = gridService.getAllGamesByPublisher(pub);
        if (gameList == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR + pub);
        }
        return new ResponseEntity<>(gameList, HttpStatus.OK);
    }

    @PostMapping("/add-game")
    public ResponseEntity<Game> saveGame(@RequestBody GamePOJO gamePOJO) {
        Game game = gridService.saveGame(gamePOJO);
        if (game == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not save Game");
        }
        return new ResponseEntity<>(game, HttpStatus.OK);
    }

    @PostMapping("/add-genre")
    public ResponseEntity<GameGenre> saveGameGenre(@RequestBody GameGenrePOJO gameGenrePOJO) {
        return new ResponseEntity<>(gridService.saveGameGenre(gameGenrePOJO), HttpStatus.OK);
    }

    @PostMapping("/add-publisher")
    public ResponseEntity<Publisher> savePublisher(@RequestBody PublisherPOJO publisherPOJO) {
        return new ResponseEntity<>(gridService.savePublisher(publisherPOJO), HttpStatus.OK);
    }

    @PostMapping("/add-developer")
    public ResponseEntity<Developer> saveDeveloper(@RequestBody DeveloperPOJO developerPOJO) {
        return new ResponseEntity<>(gridService.saveDeveloper(developerPOJO), HttpStatus.OK);
    }

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

    @PostMapping(value = "/add-wish-list", params = {"game_id", "user_id"})
    public ResponseEntity<Set<Game>> addWishList(@RequestParam("game_id") long gameID, @RequestParam("user_id") long userID) {
        Set<Game> games = gridService.addWishListByUserID(gameID, userID);
        if (games == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not add game to wish List");

        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    @PostMapping(value = "/add-game-review")
    public ResponseEntity<Set<ReviewGame>> addGameReview(@RequestBody ReviewGamePOJO reviewGamePOJO) {
        Set<ReviewGame> reviewGames = gridService.addGameReview(reviewGamePOJO);
        if (reviewGames == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not add game review");

        return new ResponseEntity<>(reviewGames, HttpStatus.OK);
    }

    @PostMapping(value = "/add-user-review")
    public ResponseEntity<Set<ReviewUser>> addUserReview(@RequestBody ReviewUserPOJO reviewUserPOJO) {
        Set<ReviewUser> reviewUsers = gridService.addUserReview(reviewUserPOJO);
        if (reviewUsers == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not add user review");

        return new ResponseEntity<>(reviewUsers, HttpStatus.OK);
    }

    @GetMapping(value = "/game-review" , params = {"game_id"})
    public ResponseEntity<Set<ReviewGame>> GameReviews(@RequestParam("game_id") long gameID) {
       Set<ReviewGame> reviews = gridService.getGameReviews(gameID);
        if (reviews == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not obtain game review");

        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

}
