package com.api.demo.grid.service;

import com.api.demo.grid.models.Game;

import java.util.List;

public interface GridService {
    Game getGameById(long id);
    List<Game> getAllGames();
    List<Game> getAllGamesWithGenre(String genre);
    List<Game> getAllGamesByName(String name);
    List<Game> getAllGamesByDev(String developer);
    List<Game> getAllGamesByPublisher(String publisher);
}
