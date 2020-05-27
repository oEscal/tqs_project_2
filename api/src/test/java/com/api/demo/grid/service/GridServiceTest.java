package com.api.demo.grid.service;

import com.api.demo.grid.models.Developer;
import com.api.demo.grid.models.Game;
import com.api.demo.grid.models.GameGenre;
import com.api.demo.grid.models.GameKey;
import com.api.demo.grid.models.Publisher;
import com.api.demo.grid.models.ReviewGame;
import com.api.demo.grid.models.Sell;
import com.api.demo.grid.models.User;
import com.api.demo.grid.pojos.DeveloperPOJO;
import com.api.demo.grid.pojos.GameGenrePOJO;
import com.api.demo.grid.pojos.GameKeyPOJO;
import com.api.demo.grid.pojos.GamePOJO;
import com.api.demo.grid.pojos.PublisherPOJO;
import com.api.demo.grid.pojos.ReviewGamePOJO;
import com.api.demo.grid.pojos.SellPOJO;
import com.api.demo.grid.repository.DeveloperRepository;
import com.api.demo.grid.repository.GameGenreRepository;
import com.api.demo.grid.repository.GameKeyRepository;
import com.api.demo.grid.repository.GameRepository;
import com.api.demo.grid.repository.PublisherRepository;
import com.api.demo.grid.repository.SellRepository;
import com.api.demo.grid.repository.UserRepository;
import com.api.demo.grid.utils.Pagination;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class GridServiceTest {
    @Mock(lenient = true)
    private GameRepository mockGameRepo;

    @Mock(lenient = true)
    private GameGenreRepository mockGameGenreRepo;

    @Mock(lenient = true)
    private DeveloperRepository mockDevRepo;

    @Mock(lenient = true)
    private PublisherRepository mockPubRepo;

    @Mock(lenient = true)
    private UserRepository mockUserRepo;

    @Mock(lenient = true)
    private GameKeyRepository mockGameKeyRepo;

    @Mock(lenient = true)
    private SellRepository mockSellRepo;

    @InjectMocks
    private GridServiceImpl gridService;

    private Game game;
    private Game game2;
    private GameGenre gameGenre;
    private Developer developer;
    private Publisher publisher;
    private User user;
    private GameKey gameKey;
    private ReviewGame reviewGame;

    private static final Date now = new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime();

    @BeforeEach
    void setUp() {
        game = new Game();
        game.setId(1L);
        game.setName("Game");

        gameGenre = new GameGenre();
        gameGenre.setId(2L);
        gameGenre.setName("Genre");

        developer = new Developer();
        developer.setId(3L);
        developer.setName("Dev");

        publisher = new Publisher();
        publisher.setId(4L);
        publisher.setName("Pub");

        game2 = new Game();
        game2.setId(5L);
        game2.setName("Game 2");

        game.setGameGenres(new HashSet<>(Arrays.asList(gameGenre)));
        game.setDevelopers(new HashSet<>(Arrays.asList(developer)));
        game.setPublisher(publisher);
        game.setReviews(new HashSet<>());

        user = new User();
        user.setId(6L);
        user.setReviewGames(new HashSet<>());

        gameKey = new GameKey();
        gameKey.setId(7L);

        reviewGame = new ReviewGame();

        reviewGame.setComment("comment");
        reviewGame.setScore(1);
        reviewGame.setAuthor(user);
        reviewGame.setGame(game);
        reviewGame.setDate(now);

    }

    @Test
    void whenSearchingAll_ReturnAllGame() {
        List<Game> gamesList = Arrays.asList(game, game2);
        Pagination<Game> pagination = new Pagination<>(gamesList);
        int page = 1;
        int entriesPerPage = 18;
        PageRequest pageRequest = PageRequest.of(page, entriesPerPage);

        Page<Game> games = pagination.pageImpl(page, entriesPerPage);
        Mockito.when(mockGameRepo.findAll(pageRequest)).thenReturn(games);

        assertEquals(games, gridService.getAllGames(page));
        Mockito.verify(mockGameRepo, Mockito.times(1)).findAll(pageRequest);
    }

    @Test
    void whenSearchingId_ReturnRightGame() {
        Mockito.when(mockGameRepo.findById(1L)).thenReturn(java.util.Optional.ofNullable(game));

        assertEquals(game, gridService.getGameById(1L));
        Mockito.verify(mockGameRepo, Mockito.times(1)).findById(Mockito.anyLong());
    }

    @Test
    void whenSearchingInvalidId_ReturnNull() {
        Mockito.when(mockGameRepo.findById(2L)).thenReturn(Optional.empty());

        assertNull(gridService.getGameById(2L));
        Mockito.verify(mockGameRepo, Mockito.times(1)).findById(Mockito.anyLong());
    }

    @Test
    void whenSearchingName_ReturnRightGame() {
        List<Game> games = Arrays.asList(game);
        Mockito.when(mockGameRepo.findAllByNameContaining("Game")).thenReturn(games);

        assertEquals(games, gridService.getAllGamesByName("Game"));
        Mockito.verify(mockGameRepo, Mockito.times(1)).findAllByNameContaining(Mockito.anyString());
    }

    @Test
    void whenSearchingInvalidName_ReturnNull() {
        Mockito.when(mockGameRepo.findAllByNameContaining("Game2")).thenReturn(new ArrayList<Game>());

        assertEquals(new ArrayList<Game>(), gridService.getAllGamesByName("Game2"));
        Mockito.verify(mockGameRepo, Mockito.times(1)).findAllByNameContaining(Mockito.anyString());
    }

    @Test
    void whenSearchingGameGenre_ReturnValidList() {
        List<Game> games = Arrays.asList(game);
        Mockito.when(mockGameGenreRepo.findByName("Genre")).thenReturn(Optional.ofNullable(gameGenre));
        Mockito.when(mockGameRepo.findAllByGameGenresContains(gameGenre)).thenReturn(games);

        assertEquals(games, gridService.getAllGamesWithGenre("Genre"));
        Mockito.verify(mockGameGenreRepo, Mockito.times(1)).findByName(Mockito.anyString());
        Mockito.verify(mockGameRepo, Mockito.times(1))
                .findAllByGameGenresContains(Mockito.any(GameGenre.class));
    }

    @Test
    void whenSearchingInvalidGenre_ReturnEmptyList() {
        Mockito.when(mockGameGenreRepo.findByName("Genre2")).thenReturn(Optional.empty());

        assertNull(gridService.getAllGamesWithGenre("Genre2"));
        Mockito.verify(mockGameGenreRepo, Mockito.times(1)).findByName(Mockito.anyString());
        Mockito.verify(mockGameRepo, Mockito.times(0))
                .findAllByGameGenresContains(Mockito.any(GameGenre.class));

    }

    @Test
    void whenSearchingValidDev_ReturnValidList() {
        List<Game> games = Arrays.asList(game);
        Mockito.when(mockDevRepo.findByName("Dev")).thenReturn(Optional.ofNullable(developer));
        Mockito.when(mockGameRepo.findAllByDevelopersContaining(developer)).thenReturn(games);

        assertEquals(games, gridService.getAllGamesByDev("Dev"));
        Mockito.verify(mockDevRepo, Mockito.times(1)).findByName(Mockito.anyString());
        Mockito.verify(mockGameRepo, Mockito.times(1))
                .findAllByDevelopersContaining(Mockito.any(Developer.class));
    }

    @Test
    void whenSearchingInvalidDev_ReturnEmptyList() {
        Mockito.when(mockGameGenreRepo.findByName("Dev2")).thenReturn(Optional.empty());

        assertNull(gridService.getAllGamesByDev("Dev2"));
        Mockito.verify(mockDevRepo, Mockito.times(1)).findByName(Mockito.anyString());
        Mockito.verify(mockGameRepo, Mockito.times(0))
                .findAllByDevelopersContaining(Mockito.any(Developer.class));

    }

    @Test
    void whenSearchingValidPublisher_ReturnValidList() {
        List<Game> games = Arrays.asList(game);
        Mockito.when(mockPubRepo.findByName("Pub")).thenReturn(Optional.ofNullable(publisher));
        Mockito.when(mockGameRepo.findAllByPublisher(publisher)).thenReturn(games);

        assertEquals(games, gridService.getAllGamesByPublisher("Pub"));
        Mockito.verify(mockPubRepo, Mockito.times(1)).findByName(Mockito.anyString());
        Mockito.verify(mockGameRepo, Mockito.times(1))
                .findAllByPublisher(Mockito.any(Publisher.class));
    }

    @Test
    void whenSearchingInvalidPublisher_ReturnEmptyList() {
        Mockito.when(mockGameGenreRepo.findByName("Pub2")).thenReturn(Optional.empty());

        assertNull(gridService.getAllGamesByPublisher("Pub2"));
        Mockito.verify(mockPubRepo, Mockito.times(1)).findByName(Mockito.anyString());
        Mockito.verify(mockGameRepo, Mockito.times(0))
                .findAllByPublisher(Mockito.any(Publisher.class));

    }

    @Test
    void whenSavingGameGenrePojo_ReturnValidGameGenre() {
        GameGenrePOJO gameGenrePOJO = new GameGenrePOJO("Genre", null);

        GameGenre savedGameGenre = gridService.saveGameGenre(gameGenrePOJO);

        assertEquals(gameGenre.getName(), gameGenre.getName());
        assertEquals(gameGenre.getDescription(), savedGameGenre.getDescription());
    }

    @Test
    void whenSavingDevPOJO_ReturnValidDeveloper() {
        DeveloperPOJO developerPOJO = new DeveloperPOJO("Dev");

        Developer savedDev = gridService.saveDeveloper(developerPOJO);

        assertEquals(developer.getName(), savedDev.getName());
    }

    @Test
    void whenSavingPubPOJO_ReturnValidPublisher() {
        PublisherPOJO publisherPOJO = new PublisherPOJO("Pub", null);

        Publisher savedPub = gridService.savePublisher(publisherPOJO);

        assertEquals(publisher.getName(), savedPub.getName());
        assertEquals(publisher.getDescription(), publisher.getDescription());
    }

    @Test
    void whenSavingGamePOJO_ReturnValidGame() {
        Mockito.when(mockGameGenreRepo.findByName("Genre")).thenReturn(Optional.ofNullable(gameGenre));
        Mockito.when(mockDevRepo.findByName("Dev")).thenReturn(Optional.ofNullable(developer));
        Mockito.when(mockPubRepo.findByName("Pub")).thenReturn(Optional.ofNullable(publisher));

        Set<String> gameGenrePOJOSet = new HashSet<>(Arrays.asList("Genre"));

        Set<String> developerPOJOSet = new HashSet<>(Arrays.asList("Dev"));

        GamePOJO gamePOJO = new GamePOJO("Game", null, gameGenrePOJOSet, "Pub", developerPOJOSet, null, null);

        Game savedGame = gridService.saveGame(gamePOJO);
        Mockito.verify(mockPubRepo, Mockito.times(1)).findByName("Pub");
        Mockito.verify(mockDevRepo, Mockito.times(1)).findByName("Dev");
        Mockito.verify(mockGameGenreRepo, Mockito.times(1)).findByName("Genre");
        assertEquals(game.getName(), savedGame.getName());
        assertEquals(game.getDescription(), savedGame.getDescription());
        assertEquals(game.getPublisher(), savedGame.getPublisher());
        assertEquals(game.getGameGenres(), savedGame.getGameGenres());
    }

    @Test
    void whenSavingGamePOJOWithInvalidGenre_ReturnNull() {
        Mockito.when(mockGameGenreRepo.findByName("Genre")).thenReturn(Optional.empty());
        Mockito.when(mockDevRepo.findByName("Dev")).thenReturn(Optional.ofNullable(developer));
        Mockito.when(mockPubRepo.findByName("Pub")).thenReturn(Optional.ofNullable(publisher));

        Set<String> gameGenrePOJOSet = new HashSet<>(Arrays.asList("Genre"));

        Set<String> developerPOJOSet = new HashSet<>(Arrays.asList("Dev"));

        GamePOJO gamePOJO = new GamePOJO("Game", null, gameGenrePOJOSet, "Pub", developerPOJOSet, null, null);

        Game savedGame = gridService.saveGame(gamePOJO);
        Mockito.verify(mockPubRepo, Mockito.times(0)).findByName("Pub");
        Mockito.verify(mockDevRepo, Mockito.times(0)).findByName("Dev");
        Mockito.verify(mockGameGenreRepo, Mockito.times(1)).findByName("Genre");
        assertNull(savedGame);
    }

    @Test
    void whenSavingGamePOJOWithInvalidPub_ReturnNull() {
        Mockito.when(mockGameGenreRepo.findByName("Genre")).thenReturn(Optional.ofNullable(gameGenre));
        Mockito.when(mockDevRepo.findByName("Dev")).thenReturn(Optional.ofNullable(developer));
        Mockito.when(mockPubRepo.findByName("Pub")).thenReturn(Optional.empty());

        Set<String> gameGenrePOJOSet = new HashSet<>(Arrays.asList("Genre"));

        Set<String> developerPOJOSet = new HashSet<>(Arrays.asList("Dev"));

        GamePOJO gamePOJO = new GamePOJO("Game", null, gameGenrePOJOSet, "Pub", developerPOJOSet, null, null);

        Game savedGame = gridService.saveGame(gamePOJO);
        Mockito.verify(mockPubRepo, Mockito.times(1)).findByName("Pub");
        Mockito.verify(mockDevRepo, Mockito.times(0)).findByName("Dev");
        Mockito.verify(mockGameGenreRepo, Mockito.times(1)).findByName("Genre");
        assertNull(savedGame);
    }

    @Test
    void whenSavingGamePOJOWithInvalidDev_ReturnNull() {
        Mockito.when(mockGameGenreRepo.findByName("Genre")).thenReturn(Optional.ofNullable(gameGenre));
        Mockito.when(mockDevRepo.findByName("Dev")).thenReturn(Optional.empty());
        Mockito.when(mockPubRepo.findByName("Pub")).thenReturn(Optional.ofNullable(publisher));

        Set<String> gameGenrePOJOSet = new HashSet<>(Arrays.asList("Genre"));

        Set<String> developerPOJOSet = new HashSet<>(Arrays.asList("Dev"));

        GamePOJO gamePOJO = new GamePOJO("Game", null, gameGenrePOJOSet, "Pub", developerPOJOSet, null, null);

        Game savedGame = gridService.saveGame(gamePOJO);
        Mockito.verify(mockPubRepo, Mockito.times(1)).findByName("Pub");
        Mockito.verify(mockDevRepo, Mockito.times(1)).findByName("Dev");
        Mockito.verify(mockGameGenreRepo, Mockito.times(1)).findByName("Genre");
        assertNull(savedGame);
    }

    @Test
    void whenSavingValidGameKeyPOJO_ReturnValidGameKey() {
        Mockito.when(mockGameRepo.findById(2L)).thenReturn(Optional.ofNullable(game2));

        GameKeyPOJO gameKeyPOJO = new GameKeyPOJO("key", 2L, "steam", "ps3");

        GameKey savedGameKey = gridService.saveGameKey(gameKeyPOJO);

        Mockito.verify(mockGameRepo, Mockito.times(1)).findById(2L);
        assertEquals("key", savedGameKey.getRKey());
        assertEquals("ps3", savedGameKey.getPlatform());
        assertEquals("steam", savedGameKey.getRetailer());
        assertEquals(game2.getName(), savedGameKey.getGame().getName());
    }

    @Test
    void whenSavingInvalidGameKeyPOJO_ReturnNullGameKey() {
        Mockito.when(mockGameRepo.findById(2L)).thenReturn(Optional.empty());

        GameKeyPOJO gameKeyPOJO = new GameKeyPOJO("key", 2L, "steam", "ps3");

        GameKey savedGameKey = gridService.saveGameKey(gameKeyPOJO);

        Mockito.verify(mockGameRepo, Mockito.times(1)).findById(2L);
        assertNull(savedGameKey);
    }

    @Test
    void whenSavingValidSellPOJO_ReturnValidSell() {
        Mockito.when(mockUserRepo.findById(6L)).thenReturn(Optional.ofNullable(user));
        Mockito.when(mockGameKeyRepo.findByrKey("key")).thenReturn(Optional.ofNullable(gameKey));

        SellPOJO sellPOJO = new SellPOJO("key", 6L, 2.3, null);

        Sell savedSell = gridService.saveSell(sellPOJO);
        Mockito.verify(mockUserRepo, Mockito.times(1)).findById(6L);
        Mockito.verify(mockGameKeyRepo, Mockito.times(1)).findByrKey("key");
        assertEquals(2.3, savedSell.getPrice());
    }

    @Test
    void whenSavingInvalidUser_ReturnNullSell() {
        Mockito.when(mockUserRepo.findById(6L)).thenReturn(Optional.empty());
        Mockito.when(mockGameKeyRepo.findByrKey("key")).thenReturn(Optional.ofNullable(gameKey));

        SellPOJO sellPOJO = new SellPOJO("key", 6L, 2.3, null);

        Sell savedSell = gridService.saveSell(sellPOJO);
        Mockito.verify(mockUserRepo, Mockito.times(1)).findById(6L);
        Mockito.verify(mockGameKeyRepo, Mockito.times(0)).findByrKey("key");
        assertNull(savedSell);
    }

    @Test
    void whenSavingInvalidGameKey_ReturnNullSell() {
        Mockito.when(mockUserRepo.findById(6L)).thenReturn(Optional.ofNullable(user));
        Mockito.when(mockGameKeyRepo.findByrKey("key")).thenReturn(Optional.empty());

        SellPOJO sellPOJO = new SellPOJO("key", 6L, 2.3, null);

        Sell savedSell = gridService.saveSell(sellPOJO);
        Mockito.verify(mockUserRepo, Mockito.times(1)).findById(6L);
        Mockito.verify(mockGameKeyRepo, Mockito.times(1)).findByrKey("key");
        assertNull(savedSell);
    }

    @Test
    void whenPostingValidWishList_ReturnWishList() {
        long userID = 1L;
        long gameID = 1L;


        Set<Game> expected = new HashSet<>();
        expected.add(game);
        Set<User> users = new HashSet<>();
        users.add(user);
        user.setWishList(expected);
        game.setUserWish(users);

        Mockito.when(mockUserRepo.findById(userID)).thenReturn(Optional.ofNullable(user));
        Mockito.when(mockGameRepo.findById(gameID)).thenReturn(Optional.ofNullable(game));

        Set<Game> games = gridService.addWishListByUserID(gameID, userID);

        Mockito.verify(mockUserRepo, Mockito.times(1)).findById(userID);
        Mockito.verify(mockGameRepo, Mockito.times(1)).findById(gameID);


        assertEquals(expected, games);
    }

    @Test
    void whenPostingValidGameReview_ReturnReviews() {
        long userID = 1L;
        long gameID = 1L;

        Mockito.when(mockUserRepo.findById(userID)).thenReturn(Optional.ofNullable(user));
        Mockito.when(mockGameRepo.findById(gameID)).thenReturn(Optional.ofNullable(game));

        ReviewGamePOJO review = new ReviewGamePOJO("comment", 1, null, 1, 1, now);
        Set<ReviewGame> expected = new HashSet<>();
        expected.add(reviewGame);

        Set<ReviewGame> reviewGames = gridService.addGameReview(review);


        Mockito.verify(mockUserRepo, Mockito.times(1)).findById(userID);
        Mockito.verify(mockGameRepo, Mockito.times(1)).findById(gameID);

        assertEquals(expected,reviewGames);
    }

    @Test
    void whenPostingInvalidUserGameReview_ReturnNULL() {
        long userID = 1L;


        Mockito.when(mockUserRepo.findById(userID)).thenReturn(Optional.empty());


        ReviewGamePOJO review = new ReviewGamePOJO("comment", 1, null, 1, 1, now);

        Set<ReviewGame> reviewGames = gridService.addGameReview(review);


        Mockito.verify(mockUserRepo, Mockito.times(1)).findById(userID);


        assertNull(reviewGames);
    }

    @Test
    void whenPostingInvalidGameGameReview_ReturnNULL() {
        long gameID = 1L;
        long userID = 1L;

        Mockito.when(mockUserRepo.findById(userID)).thenReturn(Optional.ofNullable(user));
        Mockito.when(mockGameRepo.findById(gameID)).thenReturn(Optional.empty());


        ReviewGamePOJO review = new ReviewGamePOJO("comment", 1, null, 1, 1, now);

        Set<ReviewGame> reviewGames = gridService.addGameReview(review);


        Mockito.verify(mockGameRepo, Mockito.times(1)).findById(gameID);


        assertNull(reviewGames);
    }

    @Test
    void whenPostingRepeatedGameReview_ReturnNULL() {


        Mockito.when(mockUserRepo.save(Mockito.any(User.class))).thenThrow(DataIntegrityViolationException.class);
        Mockito.when(mockGameRepo.save(Mockito.any(Game.class))).thenThrow(DataIntegrityViolationException.class);

        ReviewGamePOJO review = new ReviewGamePOJO("comment", 1, null, 1, 1, now);

        Set<ReviewGame> reviewGames = gridService.addGameReview(review);


        assertNull(reviewGames);
    }
}