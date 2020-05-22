package com.api.demo.grid.service;

import com.api.demo.grid.models.Game;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GridService {
    Game getGameById(long id);
    List<Game> getAllGames();
    List<Game> getAllGamesWithGenre(String genre);
    List<Game> getAllGamesByName(String name);
    List<Game> getAllGamesByDev(String developer);
    List<Game> getAllGamesByPublisher(String publisher);
}
