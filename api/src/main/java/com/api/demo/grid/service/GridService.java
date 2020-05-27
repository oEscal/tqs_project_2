package com.api.demo.grid.service;

import com.api.demo.grid.exception.UnavailableListingException;
import com.api.demo.grid.exception.UnsufficientFundsException;
import com.api.demo.grid.exception.GameNotFoundException;

import com.api.demo.grid.models.Buy;
import com.api.demo.grid.models.Developer;
import com.api.demo.grid.models.Game;
import com.api.demo.grid.models.GameGenre;
import com.api.demo.grid.models.GameKey;
import com.api.demo.grid.models.Publisher;
import com.api.demo.grid.models.Sell;

import com.api.demo.grid.pojos.BuyListingsPOJO;
import com.api.demo.grid.pojos.DeveloperPOJO;
import com.api.demo.grid.pojos.GameGenrePOJO;
import com.api.demo.grid.pojos.GameKeyPOJO;
import com.api.demo.grid.pojos.GamePOJO;
import com.api.demo.grid.pojos.PublisherPOJO;
import com.api.demo.grid.pojos.SearchGamePOJO;
import com.api.demo.grid.pojos.SellPOJO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface GridService {
    Game getGameById(long id);
    Page<Game> getAllGames(int page);
    Page<Sell> getAllSellListings(long gameId, int page) throws GameNotFoundException;
    Page<Game> pageSearchGames(SearchGamePOJO searchGamePOJO);
    List<Game> searchGames(SearchGamePOJO searchGamePOJO);
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
