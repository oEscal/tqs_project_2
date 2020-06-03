package com.api.demo.grid.service;

import com.api.demo.grid.exception.ExceptionDetails;
import com.api.demo.grid.exception.UnavailableListingException;
import com.api.demo.grid.exception.UnsufficientFundsException;
import com.api.demo.grid.exception.GameNotFoundException;

import com.api.demo.grid.models.Buy;
import com.api.demo.grid.models.Developer;
import com.api.demo.grid.models.Game;
import com.api.demo.grid.models.GameGenre;
import com.api.demo.grid.models.GameKey;
import com.api.demo.grid.models.Publisher;
import com.api.demo.grid.models.ReportReviewGame;
import com.api.demo.grid.models.ReportUser;
import com.api.demo.grid.models.ReviewGame;
import com.api.demo.grid.models.ReviewUser;
import com.api.demo.grid.models.Sell;
import com.api.demo.grid.models.User;
import com.api.demo.grid.pagination.Pagination;
import com.api.demo.grid.pojos.BuyListingsPOJO;
import com.api.demo.grid.pojos.DeveloperPOJO;
import com.api.demo.grid.pojos.GameGenrePOJO;
import com.api.demo.grid.pojos.GameKeyPOJO;
import com.api.demo.grid.pojos.GamePOJO;
import com.api.demo.grid.pojos.PublisherPOJO;
import com.api.demo.grid.pojos.ReviewGamePOJO;
import com.api.demo.grid.pojos.ReviewUserPOJO;
import com.api.demo.grid.pojos.SearchGamePOJO;
import com.api.demo.grid.pojos.SellPOJO;
import com.api.demo.grid.repository.DeveloperRepository;
import com.api.demo.grid.repository.GameGenreRepository;
import com.api.demo.grid.repository.GameKeyRepository;
import com.api.demo.grid.repository.GameRepository;
import com.api.demo.grid.repository.PublisherRepository;
import com.api.demo.grid.repository.ReviewGameRepository;
import com.api.demo.grid.repository.ReviewUserRepository;
import com.api.demo.grid.repository.SellRepository;
import com.api.demo.grid.repository.UserRepository;
import com.api.demo.grid.utils.ReviewJoiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;


import java.util.*;

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
    private ReviewUserRepository mReviewUserRepository;

    @Autowired
    private ReviewGameRepository mReviewGameRepository;

    private SearchGamePOJO mPreviousGamePojo;

    private List<Game> mPreviousSearch;

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
    public Page<Sell> getAllSellListings(long gameId, int page) throws GameNotFoundException {
        Optional<Game> game = mGameRepository.findById(gameId);
        if (game.isEmpty()) throw new GameNotFoundException("Game not found in the database");
        return mSellRepository.findAllByGames(gameId, PageRequest.of(page, 6));
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
    @Transactional
    public Game saveGame(GamePOJO gamePOJO) {
        Game game = new Game();
        game.setName(gamePOJO.getName());
        game.setCoverUrl(gamePOJO.getCoverUrl());
        game.setDescription(gamePOJO.getDescription());
        game.setReleaseDate(gamePOJO.getReleaseDate());


        //Get Game genres
        Optional<GameGenre> gameGenre;
        for (String gameGenrePOJO : gamePOJO.getGameGenres()) {
            gameGenre = mGameGenreRepository.findByName(gameGenrePOJO);
            if (gameGenre.isEmpty()) return null;
            game.addGenre(gameGenre.get());
        }

        // Get Publisher
        Optional<Publisher> publisher = mPublisherRepository.findByName(gamePOJO.getPublisher());
        if (publisher.isEmpty()) return null;
        game.setPublisher(publisher.get());

        //Get Game Developers
        Optional<Developer> developer;
        for (String developerPOJO : gamePOJO.getDevelopers()) {
            developer = mDeveloperRepository.findByName(developerPOJO);
            if (developer.isEmpty()) return null;
            game.addDeveloper(developer.get());
        }

        return this.mGameRepository.save(game);
    }

    @Override
    public Publisher savePublisher(PublisherPOJO publisherPOJO) {
        Publisher publisher = new Publisher();
        publisher.setName(publisherPOJO.getName());
        publisher.setDescription(publisherPOJO.getDescription());
        return this.mPublisherRepository.save(publisher);
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

    public GameKey saveGameKey(GameKeyPOJO gameKeyPOJO) throws ExceptionDetails {
        Game game = this.mGameRepository.findById(gameKeyPOJO.getGameId()).orElse(null);
        if (game == null) return null;

        Optional<GameKey> optionalGameKey = this.mGameKeyRepository.findByRealKey(gameKeyPOJO.getKey());
        if (!optionalGameKey.isEmpty()) throw new ExceptionDetails("Game Key already exists");

        GameKey gameKey = new GameKey();
        gameKey.setRealKey(gameKeyPOJO.getKey());
        gameKey.setGame(game);
        gameKey.setRetailer(gameKeyPOJO.getRetailer());
        gameKey.setPlatform(gameKeyPOJO.getPlatform());
        this.mGameKeyRepository.save(gameKey);
        return gameKey;
    }

    @Override
    public Sell saveSell(SellPOJO sellPOJO) throws ExceptionDetails {
        Optional<User> user = this.mUserRepository.findById(sellPOJO.getUserId());
        if (user.isEmpty()) return null;
        User realUser = user.get();

        Optional<GameKey> gameKey = this.mGameKeyRepository.findByRealKey(sellPOJO.getGameKey());
        if (gameKey.isEmpty()) return null;
        GameKey realGameKey = gameKey.get();

        if (realGameKey.getSell() != null || realGameKey.getAuction() != null){
            throw new ExceptionDetails("This key is already in a different listing");
        }

        Sell sell = new Sell();
        sell.setUser(realUser);
        sell.setGameKey(realGameKey);
        sell.setPrice(sellPOJO.getPrice());
        sell.setDate(sellPOJO.getDate());
        this.mUserRepository.save(realUser);
        this.mGameKeyRepository.save(realGameKey);
        return sell;
    }

    @Override
    public Sell getSell(long id) {
        Optional<Sell> optionalSell = mSellRepository.findById(id);
        if (optionalSell.isEmpty()) return null;
        return optionalSell.get();
    }

    @Override
    public List<Buy> saveBuy(BuyListingsPOJO buyListingsPOJO) throws UnavailableListingException,
            UnsufficientFundsException {
        List<Buy> buyList = new ArrayList<>();
        double bill = 0;
        Buy buy;

        Optional<User> optionalUser = mUserRepository.findById(buyListingsPOJO.getUserId());
        User user;
        if (optionalUser.isEmpty()) return null;
        user = optionalUser.get();

        Optional<Sell> sell;
        for (long sellId : buyListingsPOJO.getListingsId()) {
            sell = mSellRepository.findById(sellId);
            if (sell.isEmpty()) throw new UnavailableListingException("This listing has been removed by the user");
            else if (sell.get().getPurchased() != null) throw new UnavailableListingException(
                    "This listing has been bought by another user");
            buy = new Buy();
            buy.setDate(new Date());
            buy.setSell(sell.get());
            buyList.add(buy);
            bill += sell.get().getPrice();
        }

        if (buyListingsPOJO.isWithFunds()) {
            if (bill > user.getFunds()) throw new UnsufficientFundsException("This user doesn't have enough funds");
            user.payWithFunds(bill);
        }

        for (Buy buy1 : buyList) {
            user.addBuy(buy1);
            mSellRepository.save(buy1.getSell());
        }
        mUserRepository.save(user);
        return buyList;
    }

    @Override
    public Sell deleteSell(long id) throws UnavailableListingException, ExceptionDetails {
        Optional<Sell> optionalSell = mSellRepository.findById(id);
        if (optionalSell.isEmpty()) throw new UnavailableListingException("This listing has already been removed");
        Sell sell = optionalSell.get();

        if (sell.getPurchased() != null) throw new ExceptionDetails("This listing has been bought already");

        GameKey gameKey = sell.getGameKey();
        gameKey.setSell(null);

        User user = sell.getUser();
        user.removeListing(sell);

        mGameKeyRepository.save(sell.getGameKey());
        mUserRepository.save(sell.getUser());
        mSellRepository.delete(sell);

        return sell;
    }

    @Override
    public List<Game> searchGames(SearchGamePOJO searchGamePOJO) {
        String queryName = searchGamePOJO.getName();
        List<Game> games;
        if (!queryName.isEmpty()) {
            games = this.mGameRepository.findAllByNameContaining(queryName);
        } else {
            games = this.mGameRepository.findAll();
        }

        String[] genres = searchGamePOJO.getGenres();
        Optional<GameGenre> genre;
        ArrayList<GameGenre> realGenres = new ArrayList<>();
        if (genres.length > 0) {
            for (String gen : genres) {
                genre = this.mGameGenreRepository.findByName(gen);
                if (genre.isEmpty()) continue;
                realGenres.add(genre.get());
            }
            games.removeIf(game -> !game.getGameGenres().containsAll(realGenres));
        }
        //Filter by platform
        String[] platforms = searchGamePOJO.getPlataforms();
        if (platforms.length > 0) {
            games.removeIf(game -> !CollectionUtils.containsAny(game.getPlatforms(), Arrays.asList(platforms)));
        }

        double begin = searchGamePOJO.getStartPrice();
        double end = searchGamePOJO.getEndPrice();

        if (begin != 0 || end != 0) {
            games.removeIf(game -> game.getBestSell() == null);
        }
        
        if (begin != 0 && end > begin) {
            games.removeIf(game -> game.getBestSell().getPrice() <= begin || game.getBestSell().getPrice() >= end);
        } else if (begin != 0) {
            games.removeIf(game -> game.getBestSell().getPrice() <= begin);
        }
        return games;
    }

    @Override
    public Page<Game> pageSearchGames(SearchGamePOJO searchGamePOJO) {
        if (!searchGamePOJO.equals(mPreviousGamePojo)) {
            mPreviousGamePojo = searchGamePOJO;
            mPreviousSearch = searchGames(searchGamePOJO);
        }
        int page = searchGamePOJO.getPage();
        Pageable pageable = PageRequest.of(page, 18);
        long start = pageable.getOffset();
        long end = (start + 18 > mPreviousSearch.size()) ? mPreviousSearch.size() : start + 18;
        return new PageImpl<>(mPreviousSearch.subList((int) start, (int) end),
                PageRequest.of(page, 18), mPreviousSearch.size());
    }

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
    public Page<ReviewGame> getGameReviews(long gameID, int page) {
        Optional<Game> game = this.mGameRepository.findById(gameID);
        if (game.isEmpty()) return null;

        Set<ReviewGame> reviews = game.get().getReviews();
        reviews = (reviews == null) ? new HashSet<>() : reviews;
        List<ReviewGame> reviewsList = new ArrayList<>();
        reviewsList.addAll(reviews);

        Pagination<ReviewGame> pagination = new Pagination<>();


        return pagination.convertToPage(reviewsList, page, 18);
    }

    @Override
    public Page<ReviewJoiner> getUserReviews(long userID, int page) {
        Optional<User> user = this.mUserRepository.findById(userID);
        if (user.isEmpty()) return null;

        User realUser = user.get();

        Set<ReviewUser> reviewsUser = realUser.getReviewedUsers();
        Set<ReviewGame> reviewsGame = realUser.getReviewGames();

        reviewsUser = (reviewsUser == null) ? new HashSet<>() : reviewsUser;
        reviewsGame = (reviewsGame == null) ? new HashSet<>() : reviewsGame;

        List<ReviewJoiner> reviews = new ArrayList<>();

        for (ReviewUser currentReview : reviewsUser) {
            long id = currentReview.getId();
            String comment = currentReview.getComment();
            int score = currentReview.getScore();
            Date date = currentReview.getDate();
            Set<ReportUser> reports = currentReview.getReports();
            User author = currentReview.getAuthor();
            User target = currentReview.getTarget();
            reviews.add(new ReviewJoiner(id, comment, score, date, reports, author, target));
        }

        for (ReviewGame currentReview : reviewsGame) {
            long id = currentReview.getId();
            String comment = currentReview.getComment();
            int score = currentReview.getScore();
            Date date = currentReview.getDate();
            Set<ReportReviewGame> reports = currentReview.getReports();
            User author = currentReview.getAuthor();
            Game game = currentReview.getGame();
            reviews.add(new ReviewJoiner(id, comment, score, date, reports, author, game));
        }

        Pagination<ReviewJoiner> pagination = new Pagination<>();

        return pagination.convertToPage(reviews, page, 18);
    }

    @Override
    public Page<ReviewJoiner> getAllReviews(int page, String sort) {
        List<ReviewJoiner> reviews = new ArrayList<>();

        List<String> sortedKeywords = Arrays.asList("score", "user", "date");
        if (!sortedKeywords.contains(sort)) return null;

        List<ReviewUser> userReviews = mReviewUserRepository.findAll();
        for (ReviewUser currentReview : userReviews) {
            long id = currentReview.getId();
            String comment = currentReview.getComment();
            int score = currentReview.getScore();
            Date date = currentReview.getDate();
            Set<ReportUser> reports = currentReview.getReports();
            User author = currentReview.getAuthor();
            User target = currentReview.getTarget();
            reviews.add(new ReviewJoiner(id, comment, score, date, reports, author, target));
        }

        List<ReviewGame> userGames = mReviewGameRepository.findAll();
        for (ReviewGame currentReview : userGames) {
            long id = currentReview.getId();
            String comment = currentReview.getComment();
            int score = currentReview.getScore();
            Date date = currentReview.getDate();
            Set<ReportReviewGame> reports = currentReview.getReports();
            User author = currentReview.getAuthor();
            Game game = currentReview.getGame();
            reviews.add(new ReviewJoiner(id, comment, score, date, reports, author, game));
        }


        reviews.sort((t1, t2) -> {
            if (sort.equalsIgnoreCase("score")) {
                return t2.getScore() - t1.getScore();

            } else if (sort.equalsIgnoreCase("user")) {
                return t1.getAuthor().getUsername().compareTo(t2.getAuthor().getUsername());

            } else if (sort.equalsIgnoreCase("date")) {
                return t1.getDate().compareTo(t2.getDate());

            }
            return 0;
        });

        Pagination<ReviewJoiner> pagination = new Pagination<>();
        return pagination.convertToPage(reviews, page, 18);
    }

}
