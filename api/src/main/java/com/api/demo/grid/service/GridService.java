package com.api.demo.grid.service;

import com.api.demo.grid.exceptions.UnavailableListingException;
import com.api.demo.grid.exceptions.UnsufficientFundsException;
import com.api.demo.grid.models.*;
import com.api.demo.grid.pojos.*;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface GridService {
    Game getGameById(long id);
    Page<Game> getAllGames(int page);
    List<Game> getAllGamesWithGenre(String genre);
    List<Game> getAllGamesByName(String name);
    List<Game> getAllGamesByDev(String developer);
    List<Game> getAllGamesByPublisher(String publisher);
    Game saveGame(GamePOJO gamePOJO);
    Publisher savePublisher(PublisherPOJO publisherPOJO);
    Developer saveDeveloper(DeveloperPOJO developerPOJO);
    GameGenre saveGameGenre(GameGenrePOJO gameGenrePOJO);
    GameKey saveGameKey(GameKeyPOJO gameKeyPOJO);
    Sell saveSell(SellPOJO sellPOJO);
    List<Buy> saveBuy(BuyListingsPOJO buyListingsPojo) throws UnavailableListingException, UnsufficientFundsException;
    Set<Game> addWishListByUserID(long gameID, long userID);
}
