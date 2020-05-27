package com.api.demo.grid.service;

import com.api.demo.grid.models.*;
import com.api.demo.grid.pojos.*;
import com.api.demo.grid.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;

@Service
public class GridServiceImpl implements GridService {

    @Autowired
    private DeveloperRepository mDeveloperRepository;

    @Autowired
    private GameGenreRepository mGameGenreRepository;

    @Autowired
    private PublisherRepository mPublisherRepository;

    @Autowired
    private GameRepository mGameRepository;

    @Autowired
    private GameKeyRepository mGameKeyRepository;

    @Autowired
    private SellRepository mSellRepository;

    @Autowired
    private UserRepository mUserRepository;

    @Autowired
    private ReviewGameRepository mReviewGameRepository;

    @Autowired
    private ReviewUserRepository mReviewUserRepository;

    @Override
    public Game getGameById(long id) {
        Optional<Game> gameResponse = mGameRepository.findById(id);

        if (gameResponse.isEmpty()) return null;

        return gameResponse.get();
    }

    @Override
    public Page<Game> getAllGames(int page) {
        Page<Game> games = mGameRepository.findAll(PageRequest.of(page, 18));
        return games;
    }

    @Override
    public List<Game> getAllGamesWithGenre(String genre) {
        Optional<GameGenre> gameGenre = mGameGenreRepository.findByName(genre);

        if (gameGenre.isEmpty()) return null;

        return mGameRepository.findAllByGameGenresContains(gameGenre.get());
    }

    @Override
    public List<Game> getAllGamesByName(String name) {
        return mGameRepository.findAllByNameContaining(name);
    }

    @Override
    public List<Game> getAllGamesByDev(String developer) {
        Optional<Developer> dev = mDeveloperRepository.findByName(developer);

        if (dev.isEmpty()) return null;

        return mGameRepository.findAllByDevelopersContaining(dev.get());
    }

    @Override
    public List<Game> getAllGamesByPublisher(String publisher) {
        Optional<Publisher> pub = mPublisherRepository.findByName(publisher);

        if (pub.isEmpty()) return null;

        return mGameRepository.findAllByPublisher(pub.get());
    }

    @Override
    public Game saveGame(GamePOJO gamePOJO) {
        Game game = new Game();
        game.setName(gamePOJO.getName());
        game.setCoverUrl(gamePOJO.getCoverUrl());
        game.setDescription(gamePOJO.getDescription());
        game.setReleaseDate((Date) gamePOJO.getReleaseDate());

        //Get Game genres
        Set<GameGenre> gameGenreSet = new HashSet<>();
        Optional<GameGenre> gameGenre;
        for (String gameGenrePOJO : gamePOJO.getGameGenres()) {
            gameGenre = mGameGenreRepository.findByName(gameGenrePOJO);
            if (gameGenre.isEmpty()) return null;
            gameGenreSet.add(gameGenre.get());
        }
        game.setGameGenres(gameGenreSet);

        // Get Publisher
        Optional<Publisher> publisher = mPublisherRepository.findByName(gamePOJO.getPublisher());
        if (publisher.isEmpty()) return null;
        game.setPublisher(publisher.get());

        //Get Game Developers
        Set<Developer> developerSet = new HashSet<>();
        Optional<Developer> developer;
        for (String developerPOJO : gamePOJO.getDevelopers()) {
            developer = mDeveloperRepository.findByName(developerPOJO);
            if (developer.isEmpty()) return null;
            developerSet.add(developer.get());
        }
        game.setDevelopers(developerSet);

        this.mGameRepository.save(game);
        return game;
    }

    @Override
    public Publisher savePublisher(PublisherPOJO publisherPOJO) {
        Publisher publisher = new Publisher();
        publisher.setName(publisherPOJO.getName());
        publisher.setDescription(publisherPOJO.getDescription());
        this.mPublisherRepository.save(publisher);
        return publisher;
    }

    @Override
    public Developer saveDeveloper(DeveloperPOJO developerPOJO) {
        Developer developer = new Developer();
        developer.setName(developerPOJO.getName());
        this.mDeveloperRepository.save(developer);
        return developer;
    }

    @Override
    public GameGenre saveGameGenre(GameGenrePOJO gameGenrePOJO) {
        GameGenre gameGenre = new GameGenre();
        gameGenre.setName(gameGenrePOJO.getName());
        this.mGameGenreRepository.save(gameGenre);
        return gameGenre;
    }

    @Override
    public GameKey saveGameKey(GameKeyPOJO gameKeyPOJO) {
        Optional<Game> game = this.mGameRepository.findById(gameKeyPOJO.getGameId());
        if (game.isEmpty()) return null;
        Game realGame = game.get();

        GameKey gameKey = new GameKey();
        gameKey.setRKey(gameKeyPOJO.getKey());
        gameKey.setGame(realGame);
        gameKey.setRetailer(gameKeyPOJO.getRetailer());
        gameKey.setPlatform(gameKeyPOJO.getPlatform());
        this.mGameKeyRepository.save(gameKey);
        return gameKey;
    }

    @Override
    public Sell saveSell(SellPOJO sellPOJO) {
        Optional<User> user = this.mUserRepository.findById(sellPOJO.getUserId());
        if (user.isEmpty()) return null;
        User realUser = user.get();

        Optional<GameKey> gameKey = this.mGameKeyRepository.findByrKey(sellPOJO.getGameKey());
        if (gameKey.isEmpty()) return null;
        GameKey realGameKey = gameKey.get();

        Sell sell = new Sell();
        sell.setUser(realUser);
        sell.setGameKey(realGameKey);
        sell.setPrice(sellPOJO.getPrice());
        sell.setDate(sellPOJO.getDate());
        this.mSellRepository.save(sell);
        return sell;
    }

    @Override
    public Set<Game> addWishListByUserID(long gameID, long userID) {
        Optional<User> user = this.mUserRepository.findById(userID);
        if (user.isEmpty()) return null;

        Optional<Game> game = this.mGameRepository.findById(gameID);
        if (game.isEmpty()) return null;

        User realUser = user.get();
        Game realGame = game.get();
        Set<Game> wishList = realUser.getWishList();
        wishList.add(realGame);
        Set<User> users = realGame.getUserWish();
        users.add(realUser);
        realUser.setWishList(wishList);
        realGame.setUserWish(users);
        this.mUserRepository.save(realUser);
        this.mGameRepository.save(realGame);
        return wishList;
    }

    @Override
    public Set<ReviewGame> addGameReview(ReviewGamePOJO reviewGamePOJO) {
        Optional<User> user = this.mUserRepository.findById(reviewGamePOJO.getAuthor());
        if (user.isEmpty()) return null;

        Optional<Game> game = this.mGameRepository.findById(reviewGamePOJO.getGame());
        if (game.isEmpty()) return null;


        User realUser = user.get();
        Game realGame = game.get();

        Set<ReviewGame> gameReviews = realGame.getReviews();
        Set<ReviewGame> userGameReviews = realUser.getReviewGames();


        ReviewGame review = new ReviewGame();

        review.setComment(reviewGamePOJO.getComment());
        review.setScore(reviewGamePOJO.getScore());
        review.setAuthor(realUser);
        review.setGame(realGame);
        review.setDate(reviewGamePOJO.getDate());

        gameReviews.add(review);
        userGameReviews.add(review);

        realUser.setReviewGames(userGameReviews);
        realGame.setReviews(gameReviews);

        try {
            this.mGameRepository.save(realGame);
            this.mUserRepository.save(realUser);
        } catch (Exception e) {
            return null;
        }
        return gameReviews;
    }

    @Override
    public Set<ReviewUser> addUserReview(ReviewUserPOJO reviewUserPOJO) {
        long authorID = reviewUserPOJO.getAuthor();
        long targetID = reviewUserPOJO.getTarget();

        if (authorID == targetID) return null;

        Optional<User> author = this.mUserRepository.findById(authorID);
        if (author.isEmpty()) return null;

        Optional<User> target = this.mUserRepository.findById(targetID);
        if (target.isEmpty()) return null;


        User realAuthor = author.get();
        User realTarget = target.get();

        Set<ReviewUser> authorReviews = realAuthor.getReviewedUsers();
        Set<ReviewUser> targetReviews = realTarget.getReviewUsers();

        ReviewUser review = new ReviewUser();

        review.setComment(reviewUserPOJO.getComment());
        review.setScore(reviewUserPOJO.getScore());
        review.setAuthor(realAuthor);
        review.setTarget(realTarget);
        review.setDate(reviewUserPOJO.getDate());

        authorReviews.add(review);
        targetReviews.add(review);

        realAuthor.setReviewedUsers(authorReviews);
        realTarget.setReviewUsers(targetReviews);

        try {
            this.mReviewUserRepository.save(review);
        } catch (Exception e) {
            return null;
        }

        return targetReviews;
    }

    @Override
    public Set<ReviewGame> getGameReviews(long gameID) {
        Optional<Game> game = this.mGameRepository.findById(gameID);
        if (game.isEmpty()) return null;

        Set<ReviewGame> reviews = game.get().getReviews();
        return reviews == null ? new HashSet<>() : reviews;
    }

}
