package com.api.demo.grid.controller;
import com.api.demo.grid.models.Developer;
import com.api.demo.grid.models.Game;
import com.api.demo.grid.models.GameGenre;
import com.api.demo.grid.models.Publisher;
import com.api.demo.grid.pojos.*;
import com.api.demo.grid.service.GridService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/grid")
public class GridRestController {
    public static final String ERROR = "No Game found with Id ";

    @Autowired
    private GridService gridService;

    @GetMapping("/all")
    public ResponseEntity<List<Game>> getAllGames(){
        return ResponseEntity.ok(gridService.getAllGames());
    }

    @GetMapping("/game")
    public ResponseEntity<Game> getGameInfo(@RequestParam long id){
        Game gameResponse = gridService.getGameById(id);
        if (gameResponse == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR + id);
        }
        return ResponseEntity.ok(gameResponse);
    }

    @GetMapping("/genre")
    public ResponseEntity<List<Game>> getGameByGenre(@RequestParam String genre){
        List<Game> gameList = gridService.getAllGamesWithGenre(genre);
        if (gameList == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR + genre);
        }
        return new ResponseEntity<>(gameList, HttpStatus.OK);
    }

    @GetMapping("/name")
    public ResponseEntity<List<Game>> getGameByName(@RequestParam String name){
        List<Game> gameList = gridService.getAllGamesByName(name);
        if (gameList == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR + name);
        }
        return new ResponseEntity<>(gameList, HttpStatus.OK);
    }

    @GetMapping("/developer")
    public ResponseEntity<List<Game>> getGameByDev(@RequestParam String dev){
        List<Game> gameList = gridService.getAllGamesByDev(dev);
        if (gameList == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR + dev);
        }
        return new ResponseEntity<>(gameList, HttpStatus.OK);
    }

    @GetMapping("/publisher")
    public ResponseEntity<List<Game>> getGameByPub(@RequestParam String pub){
        List<Game> gameList = gridService.getAllGamesByPublisher(pub);
        if (gameList == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR + pub);
        }
        return new ResponseEntity<>(gameList, HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Game>> getGamesFromSearch(@RequestBody SearchGamePOJO searchGamePOJO){
        return null;
    }

    @PostMapping("/game")
    public ResponseEntity<Game> saveGame(@RequestBody GamePOJO gamePOJO){
        Game game = gridService.saveGame(gamePOJO);
        if (game == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not save Game");
        }
        return new ResponseEntity<>(game, HttpStatus.OK);
    }

    @PostMapping("/genre")
    public ResponseEntity<GameGenre> saveGameGenre(@RequestBody GameGenrePOJO gameGenrePOJO){
        return new ResponseEntity<>(gridService.saveGameGenre(gameGenrePOJO), HttpStatus.OK);
    }

    @PostMapping("/publisher")
    public ResponseEntity<Publisher> savePublisher(@RequestBody PublisherPOJO publisherPOJO){
        return new ResponseEntity<>(gridService.savePublisher(publisherPOJO), HttpStatus.OK);
    }

    @PostMapping("/developer")
    public ResponseEntity<Developer> saveDeveloper(@RequestBody DeveloperPOJO developerPOJO){
        return new ResponseEntity<>(gridService.saveDeveloper(developerPOJO), HttpStatus.OK);
    }
}
