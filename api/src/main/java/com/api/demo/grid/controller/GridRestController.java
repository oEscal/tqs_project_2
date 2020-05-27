package com.api.demo.grid.controller;
import com.api.demo.grid.models.Developer;
import com.api.demo.grid.models.Game;
import com.api.demo.grid.models.GameGenre;
import com.api.demo.grid.models.GameKey;
import com.api.demo.grid.models.Publisher;
import com.api.demo.grid.models.Sell;
import com.api.demo.grid.pojos.DeveloperPOJO;
import com.api.demo.grid.pojos.GameGenrePOJO;
import com.api.demo.grid.pojos.GameKeyPOJO;
import com.api.demo.grid.pojos.GamePOJO;
import com.api.demo.grid.pojos.PublisherPOJO;
import com.api.demo.grid.pojos.SellPOJO;
import com.api.demo.grid.service.GridService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<Game> saveGame(@RequestBody GamePOJO gamePOJO){
        Game game = gridService.saveGame(gamePOJO);
        if (game == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not save Game");
        }
        return new ResponseEntity<>(game, HttpStatus.OK);
    }

    @PostMapping("/add-genre")
    public ResponseEntity<GameGenre> saveGameGenre(@RequestBody GameGenrePOJO gameGenrePOJO){
        return new ResponseEntity<>(gridService.saveGameGenre(gameGenrePOJO), HttpStatus.OK);
    }

    @PostMapping("/add-publisher")
    public ResponseEntity<Publisher> savePublisher(@RequestBody PublisherPOJO publisherPOJO){
        return new ResponseEntity<>(gridService.savePublisher(publisherPOJO), HttpStatus.OK);
    }

    @PostMapping("/add-developer")
    public ResponseEntity<Developer> saveDeveloper(@RequestBody DeveloperPOJO developerPOJO){
        return new ResponseEntity<>(gridService.saveDeveloper(developerPOJO), HttpStatus.OK);
    }

    @PostMapping(value = "/add-wish-list", params = {"game_id", "user_id"})
    public ResponseEntity<Set<Game>> addWishList(@RequestParam("game_id") long gameID, @RequestParam("user_id") long userID) {
        Set<Game> games = gridService.addWishListByUserID(gameID, userID);
        if (games == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not add game to wish List");

        return new ResponseEntity<>(games, HttpStatus.OK);
    }

}
