package com.api.demo.grid.service;

import com.api.demo.grid.exception.UnavailableListingException;
import com.api.demo.grid.exception.UnsufficientFundsException;
import com.api.demo.grid.exception.GameNotFoundException;
import com.api.demo.grid.exception.ListingNotFoundException;

import com.api.demo.grid.models.Buy;
import com.api.demo.grid.models.Developer;
import com.api.demo.grid.models.Game;
import com.api.demo.grid.models.GameGenre;
import com.api.demo.grid.models.GameKey;
import com.api.demo.grid.models.Publisher;
import com.api.demo.grid.models.Sell;
import com.api.demo.grid.models.User;

import com.api.demo.grid.pojos.BuyListingsPOJO;
import com.api.demo.grid.pojos.DeveloperPOJO;
import com.api.demo.grid.pojos.GameGenrePOJO;
import com.api.demo.grid.pojos.GameKeyPOJO;
import com.api.demo.grid.pojos.GamePOJO;
import com.api.demo.grid.pojos.PublisherPOJO;
import com.api.demo.grid.pojos.SearchGamePOJO;
import com.api.demo.grid.pojos.SellPOJO;
import com.api.demo.grid.repository.BuyRepository;
import com.api.demo.grid.repository.DeveloperRepository;
import com.api.demo.grid.repository.GameGenreRepository;
import com.api.demo.grid.repository.GameKeyRepository;
import com.api.demo.grid.repository.GameRepository;
import com.api.demo.grid.repository.PublisherRepository;
import com.api.demo.grid.repository.SellRepository;
import com.api.demo.grid.repository.UserRepository;
import com.api.demo.grid.utils.Pagination;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Arrays;
import java.util.Optional;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GridServiceTest {
    @Mock(lenient = true)
    private GameRepository mMockGameRepo;

    @Mock(lenient = true)
    private GameGenreRepository mMockGameGenreRepo;

    @Mock(lenient = true)
    private DeveloperRepository mMockDevRepo;

    @Mock(lenient = true)
    private PublisherRepository mMockPubRepo;

    @Mock(lenient = true)
    private UserRepository mMockUserRepo;

    @InjectMocks
    private GridServiceImpl gridService;

    private Game game;
    private Game game2;
    private GameGenre gameGenre;
    private Developer developer;
    private Publisher publisher;
    private User user;
    private GameKey gameKey;
    private Sell sell;

    @BeforeEach
    public void setUp(){
        mGame = new Game();
        mGame.setId(1L);
        mGame.setName("Game");

        mGameGenre = new GameGenre();
        mGameGenre.setId(2L);
        mGameGenre.setName("Genre");

        mDeveloper = new Developer();
        mDeveloper.setId(3L);
        mDeveloper.setName("Dev");

        mPublisher = new Publisher();
        mPublisher.setId(4L);
        mPublisher.setName("Pub");

        mGame2 = new Game();
        mGame2.setId(5L);
        mGame2.setName("Game 2");

        mGame.setGameGenres(new HashSet<>(Arrays.asList(mGameGenre)));
        mGame.setDevelopers(new HashSet<>(Arrays.asList(mDeveloper)));
        mGame.setPublisher(mPublisher);

        mUser = new User();
        mUser.setId(6L);

        mBuyer = new User();
        mBuyer.setId(11l);

        mGameKey = new GameKey();
        mGameKey.setRKey("key1");
        mGameKey.setId(7L);

        mSell1 = new Sell();
        mSell1.setGameKey(mGameKey);
        mSell1.setUser(mUser);
        mSell1.setId(8l);
        mSell1.setPrice(5.3);

        sell = new Sell();
        sell.setId(8l);
        sell.setGameKey(gameKey);
    }

    @Test
    void whenSearchingAll_ReturnAllGame() {
        List<Game> gamesList = Arrays.asList(mGame, mGame2);
        Pagination<Game> pagination = new Pagination<>(gamesList);
        int page = 1;
        int entriesPerPage = 18;
        PageRequest pageRequest = PageRequest.of(page, entriesPerPage);

        Page<Game> games = pagination.pageImpl(page, entriesPerPage);
        Mockito.when(mMockGameRepo.findAll(pageRequest)).thenReturn(games);

        assertEquals(games, mGridService.getAllGames(page));
        Mockito.verify(mMockGameRepo, Mockito.times(1)).findAll(pageRequest);
    }

    @Test
    void whenSearchingId_ReturnRightGame() {
        Mockito.when(mMockGameRepo.findById(1L)).thenReturn(java.util.Optional.ofNullable(mGame));

        assertEquals(mGame, mGridService.getGameById(1L));
        Mockito.verify(mMockGameRepo, Mockito.times(1)).findById(Mockito.anyLong());
    }

    @Test
    void whenSearchingInvalidId_ReturnNull(){
        Mockito.when(mMockGameRepo.findById(2L)).thenReturn(Optional.empty());

        assertNull(mGridService.getGameById(2L));
        Mockito.verify(mMockGameRepo, Mockito.times(1)).findById(Mockito.anyLong());
    }

    @Test
    void whenSearchingName_ReturnRightGame(){
        List<Game> games = Arrays.asList(mGame);
        Mockito.when(mMockGameRepo.findAllByNameContaining("Game")).thenReturn(games);

        assertEquals(games, mGridService.getAllGamesByName("Game"));
        Mockito.verify(mMockGameRepo, Mockito.times(1)).findAllByNameContaining(Mockito.anyString());
    }

    @Test
    void whenSearchingInvalidName_ReturnNull(){
        Mockito.when(mMockGameRepo.findAllByNameContaining("Game2")).thenReturn(new ArrayList<Game>());

        assertEquals(new ArrayList<Game>(), mGridService.getAllGamesByName("Game2"));
        Mockito.verify(mMockGameRepo, Mockito.times(1)).findAllByNameContaining(Mockito.anyString());
    }

    @Test
    void whenSearchingGameGenre_ReturnValidList(){
        List<Game> games = Arrays.asList(mGame);
        Mockito.when(mMockGameGenreRepo.findByName("Genre")).thenReturn(Optional.ofNullable(mGameGenre));
        Mockito.when(mMockGameRepo.findAllByGameGenresContains(mGameGenre)).thenReturn(games);

        assertEquals(games, mGridService.getAllGamesWithGenre("Genre"));
        Mockito.verify(mMockGameGenreRepo, Mockito.times(1)).findByName(Mockito.anyString());
        Mockito.verify(mMockGameRepo, Mockito.times(1))
                .findAllByGameGenresContains(Mockito.any(GameGenre.class));
    }

    @Test
    void whenSearchingInvalidGenre_ReturnEmptyList(){
        Mockito.when(mMockGameGenreRepo.findByName("Genre2")).thenReturn(Optional.empty());

        assertNull(mGridService.getAllGamesWithGenre("Genre2"));
        Mockito.verify(mMockGameGenreRepo, Mockito.times(1)).findByName(Mockito.anyString());
        Mockito.verify(mMockGameRepo, Mockito.times(0))
                .findAllByGameGenresContains(Mockito.any(GameGenre.class));

    }

    @Test
    void whenSearchingValidDev_ReturnValidList(){
        List<Game> games = Arrays.asList(mGame);
        Mockito.when(mMockDevRepo.findByName("Dev")).thenReturn(Optional.ofNullable(mDeveloper));
        Mockito.when(mMockGameRepo.findAllByDevelopersContaining(mDeveloper)).thenReturn(games);

        assertEquals(games, mGridService.getAllGamesByDev("Dev"));
        Mockito.verify(mMockDevRepo, Mockito.times(1)).findByName(Mockito.anyString());
        Mockito.verify(mMockGameRepo, Mockito.times(1))
                .findAllByDevelopersContaining(Mockito.any(Developer.class));
    }

    @Test
    void whenSearchingInvalidDev_ReturnEmptyList() {
        Mockito.when(mMockGameGenreRepo.findByName("Dev2")).thenReturn(Optional.empty());

        assertNull(mGridService.getAllGamesByDev("Dev2"));
        Mockito.verify(mMockDevRepo, Mockito.times(1)).findByName(Mockito.anyString());
        Mockito.verify(mMockGameRepo, Mockito.times(0))
                .findAllByDevelopersContaining(Mockito.any(Developer.class));

    }

    @Test
    void whenSearchingValidPublisher_ReturnValidList() {
        List<Game> games = Arrays.asList(mGame);
        Mockito.when(mMockPubRepo.findByName("Pub")).thenReturn(Optional.ofNullable(mPublisher));
        Mockito.when(mMockGameRepo.findAllByPublisher(mPublisher)).thenReturn(games);

        assertEquals(games, mGridService.getAllGamesByPublisher("Pub"));
        Mockito.verify(mMockPubRepo, Mockito.times(1)).findByName(Mockito.anyString());
        Mockito.verify(mMockGameRepo, Mockito.times(1))
                .findAllByPublisher(Mockito.any(Publisher.class));
    }

    @Test
    void whenSearchingInvalidPublisher_ReturnEmptyList() {
        Mockito.when(mMockGameGenreRepo.findByName("Pub2")).thenReturn(Optional.empty());

        assertNull(mGridService.getAllGamesByPublisher("Pub2"));
        Mockito.verify(mMockPubRepo, Mockito.times(1)).findByName(Mockito.anyString());
        Mockito.verify(mMockGameRepo, Mockito.times(0))
                .findAllByPublisher(Mockito.any(Publisher.class));
    }

    @Test
    void whenSavingGameGenrePojo_ReturnValidGameGenre() {
        GameGenrePOJO gameGenrePOJO = new GameGenrePOJO("Genre", null);

        GameGenre savedGameGenre = mGridService.saveGameGenre(gameGenrePOJO);

        assertEquals(mGameGenre.getName(), mGameGenre.getName());
        assertEquals(mGameGenre.getDescription(), savedGameGenre.getDescription());
    }


    @Test
    void whenSavingDevPOJO_ReturnValidDeveloper() {
        DeveloperPOJO developerPOJO = new DeveloperPOJO("Dev");

        Developer savedDev = mGridService.saveDeveloper(developerPOJO);

        assertEquals(mDeveloper.getName(), savedDev.getName());
    }

    @Test
    void whenSavingPubPOJO_ReturnValidPublisher() {
        PublisherPOJO publisherPOJO = new PublisherPOJO("Pub", null);

        Publisher savedPub = mGridService.savePublisher(publisherPOJO);

        assertEquals(mPublisher.getName(), savedPub.getName());
        assertEquals(mPublisher.getDescription(), mPublisher.getDescription());
    }

    @Test
    void whenSavingGamePOJO_ReturnValidGame(){
        Mockito.when(mMockGameGenreRepo.findByName("Genre")).thenReturn(Optional.ofNullable(mGameGenre));
        Mockito.when(mMockDevRepo.findByName("Dev")).thenReturn(Optional.ofNullable(mDeveloper));
        Mockito.when(mMockPubRepo.findByName("Pub")).thenReturn(Optional.ofNullable(mPublisher));

        Set<String> gameGenrePOJOSet = new HashSet<>(Arrays.asList("Genre"));

        Set<String> developerPOJOSet = new HashSet<>(Arrays.asList("Dev"));

        GamePOJO gamePOJO = new GamePOJO("Game", null, gameGenrePOJOSet, "Pub", developerPOJOSet, null, null);

        Game savedGame = mGridService.saveGame(gamePOJO);
        Mockito.verify(mMockPubRepo, Mockito.times(1)).findByName("Pub");
        Mockito.verify(mMockDevRepo, Mockito.times(1)).findByName("Dev");
        Mockito.verify(mMockGameGenreRepo, Mockito.times(1)).findByName("Genre");
        assertEquals(mGame.getName(), savedGame.getName());
        assertEquals(mGame.getDescription(), savedGame.getDescription());
        assertEquals(mGame.getPublisher(), savedGame.getPublisher());
        assertEquals(mGame.getGameGenres(), savedGame.getGameGenres());
    }

    @Test
    void whenSavingGamePOJOWithInvalidGenre_ReturnNull() {
        Mockito.when(mMockGameGenreRepo.findByName("Genre")).thenReturn(Optional.empty());
        Mockito.when(mMockDevRepo.findByName("Dev")).thenReturn(Optional.ofNullable(mDeveloper));
        Mockito.when(mMockPubRepo.findByName("Pub")).thenReturn(Optional.ofNullable(mPublisher));

        Set<String> gameGenrePOJOSet = new HashSet<>(Arrays.asList("Genre"));

        Set<String> developerPOJOSet = new HashSet<>(Arrays.asList("Dev"));

        GamePOJO gamePOJO = new GamePOJO("Game", null, gameGenrePOJOSet, "Pub", developerPOJOSet, null, null);

        Game savedGame = mGridService.saveGame(gamePOJO);
        Mockito.verify(mMockPubRepo, Mockito.times(0)).findByName("Pub");
        Mockito.verify(mMockDevRepo, Mockito.times(0)).findByName("Dev");
        Mockito.verify(mMockGameGenreRepo, Mockito.times(1)).findByName("Genre");
        assertNull(savedGame);
    }

    @Test
    void whenSavingGamePOJOWithInvalidPub_ReturnNull() {
        Mockito.when(mMockGameGenreRepo.findByName("Genre")).thenReturn(Optional.ofNullable(mGameGenre));
        Mockito.when(mMockDevRepo.findByName("Dev")).thenReturn(Optional.ofNullable(mDeveloper));
        Mockito.when(mMockPubRepo.findByName("Pub")).thenReturn(Optional.empty());

        Set<String> gameGenrePOJOSet = new HashSet<>(Arrays.asList("Genre"));

        Set<String> developerPOJOSet = new HashSet<>(Arrays.asList("Dev"));

        GamePOJO gamePOJO = new GamePOJO("Game", null, gameGenrePOJOSet, "Pub", developerPOJOSet, null, null);

        Game savedGame = mGridService.saveGame(gamePOJO);
        Mockito.verify(mMockPubRepo, Mockito.times(1)).findByName("Pub");
        Mockito.verify(mMockDevRepo, Mockito.times(0)).findByName("Dev");
        Mockito.verify(mMockGameGenreRepo, Mockito.times(1)).findByName("Genre");
        assertNull(savedGame);
    }

    @Test
    void whenSavingGamePOJOWithInvalidDev_ReturnNull(){
        Mockito.when(mMockGameGenreRepo.findByName("Genre")).thenReturn(Optional.ofNullable(mGameGenre));
        Mockito.when(mMockDevRepo.findByName("Dev")).thenReturn(Optional.empty());
        Mockito.when(mMockPubRepo.findByName("Pub")).thenReturn(Optional.ofNullable(mPublisher));

        Set<String> gameGenrePOJOSet = new HashSet<>(Arrays.asList("Genre"));

        Set<String> developerPOJOSet = new HashSet<>(Arrays.asList("Dev"));

        GamePOJO gamePOJO = new GamePOJO("Game", null, gameGenrePOJOSet, "Pub", developerPOJOSet, null, null);

        Game savedGame = mGridService.saveGame(gamePOJO);
        Mockito.verify(mMockPubRepo, Mockito.times(1)).findByName("Pub");
        Mockito.verify(mMockDevRepo, Mockito.times(1)).findByName("Dev");
        Mockito.verify(mMockGameGenreRepo, Mockito.times(1)).findByName("Genre");
        assertNull(savedGame);
    }
    
    @Test
    void whenPostingValidWishList_ReturnWishList() {
        long userID = 1L;
        long gameID = 1L;


        Set<Game> expected = new HashSet<>();
        expected.add(mGame);
        Set<User> users = new HashSet<>();
        users.add(mUser);
        mUser.setWishList(expected);
        mGame.setUserWish(users);

        Mockito.when(mMockUserRepo.findById(userID)).thenReturn(Optional.ofNullable(mUser));
        Mockito.when(mMockGameRepo.findById(gameID)).thenReturn(Optional.ofNullable(mGame));

        Set<Game> games = mGridService.addWishListByUserID(gameID, userID);

        Mockito.verify(mMockUserRepo, Mockito.times(1)).findById(userID);
        Mockito.verify(mMockGameRepo, Mockito.times(1)).findById(gameID);

        assertEquals(expected, games);
    }


}