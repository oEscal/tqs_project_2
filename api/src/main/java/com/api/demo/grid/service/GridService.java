package com.api.demo.grid.service;

import com.api.demo.grid.models.*;
import com.api.demo.grid.pojos.*;
import com.api.demo.grid.utils.ReviewJoiner;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
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
    Set<Game> addWishListByUserID(long gameID, long userID);
    Set<ReviewGame> addGameReview(ReviewGamePOJO reviewGamePOJO);
    Set<ReviewUser> addUserReview(ReviewUserPOJO reviewUserPOJO);
    Page<ReviewGame> getGameReviews(long gameID,int page);
    Page<ReviewJoiner> getUserReviews(long userID, int page);
}
