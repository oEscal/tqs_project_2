package com.api.demo.grid.service;

import com.api.demo.grid.pojos.*;
import com.api.demo.grid.models.*;
import com.api.demo.grid.repository.*;
import com.api.demo.grid.utils.Pagination;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
    private GridServiceImpl mGridService;

    private Game mGame;
    private Game mGame2;
    private GameGenre mGameGenre;
    private Developer mDeveloper;
    private Publisher mPublisher;
    private User mUser;
    private GameKey mGameKey;
    private GameKey mGameKey2;
    private Sell mSell1;
    private Sell mSell2;
    private SearchGamePOJO mSearchGamePOJO;

    @BeforeEach
    void setUp() {
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

        mGameKey = new GameKey();
        mGameKey.setId(7L);

        mGameKey2 = new GameKey();
        mGameKey2.setId(8l);

        mSell1 = new Sell();
        mSell1.setGameKey(mGameKey);
        mSell1.setPrice(5.3);

        mSell2 = new Sell();
        mSell2.setGameKey(mGameKey2);
        mSell2.setPrice(50.3);

        mSearchGamePOJO = new SearchGamePOJO();
    }

    @Test
    void whenSearchingAll_ReturnAllGame() {
        List<Game> gamesList = Arrays.asList(mGame, mGame2);
        Pagination<Game> pagination = new Pagination<>(gamesList);
        int page = 1;
        int entriesPerPage = 18;
        PageRequest pageRequest = PageRequest.of(page, entriesPerPage);

        Page<Game> games = pagination.pageImpl(page, entriesPerPage);
        Mockito.when(mockGameRepo.findAll(pageRequest)).thenReturn(games);

        assertEquals(games, mGridService.getAllGames(page));
        Mockito.verify(mockGameRepo, Mockito.times(1)).findAll(pageRequest);
    }

    @Test
    void whenSearchingId_ReturnRightGame() {
        Mockito.when(mockGameRepo.findById(1L)).thenReturn(java.util.Optional.ofNullable(mGame));

        assertEquals(mGame, mGridService.getGameById(1L));
        Mockito.verify(mockGameRepo, Mockito.times(1)).findById(Mockito.anyLong());
    }

    @Test
    void whenSearchingInvalidId_ReturnNull() {
        Mockito.when(mockGameRepo.findById(2L)).thenReturn(Optional.empty());

        assertNull(mGridService.getGameById(2L));
        Mockito.verify(mockGameRepo, Mockito.times(1)).findById(Mockito.anyLong());
    }

    @Test
    void whenSearchingName_ReturnRightGame() {
        List<Game> games = Arrays.asList(mGame);
        Mockito.when(mockGameRepo.findAllByNameContaining("Game")).thenReturn(games);

        assertEquals(games, mGridService.getAllGamesByName("Game"));
        Mockito.verify(mockGameRepo, Mockito.times(1)).findAllByNameContaining(Mockito.anyString());
    }

    @Test
    void whenSearchingInvalidName_ReturnNull() {
        Mockito.when(mockGameRepo.findAllByNameContaining("Game2")).thenReturn(new ArrayList<Game>());

        assertEquals(new ArrayList<Game>(), mGridService.getAllGamesByName("Game2"));
        Mockito.verify(mockGameRepo, Mockito.times(1)).findAllByNameContaining(Mockito.anyString());
    }

    @Test
    void whenSearchingGameGenre_ReturnValidList() {
        List<Game> games = Arrays.asList(mGame);
        Mockito.when(mockGameGenreRepo.findByName("Genre")).thenReturn(Optional.ofNullable(mGameGenre));
        Mockito.when(mockGameRepo.findAllByGameGenresContains(mGameGenre)).thenReturn(games);

        assertEquals(games, mGridService.getAllGamesWithGenre("Genre"));
        Mockito.verify(mockGameGenreRepo, Mockito.times(1)).findByName(Mockito.anyString());
        Mockito.verify(mockGameRepo, Mockito.times(1))
                .findAllByGameGenresContains(Mockito.any(GameGenre.class));
    }

    @Test
    void whenSearchingInvalidGenre_ReturnEmptyList() {
        Mockito.when(mockGameGenreRepo.findByName("Genre2")).thenReturn(Optional.empty());

        assertNull(mGridService.getAllGamesWithGenre("Genre2"));
        Mockito.verify(mockGameGenreRepo, Mockito.times(1)).findByName(Mockito.anyString());
        Mockito.verify(mockGameRepo, Mockito.times(0))
                .findAllByGameGenresContains(Mockito.any(GameGenre.class));

    }

    @Test
    void whenSearchingValidDev_ReturnValidList() {
        List<Game> games = Arrays.asList(mGame);
        Mockito.when(mockDevRepo.findByName("Dev")).thenReturn(Optional.ofNullable(mDeveloper));
        Mockito.when(mockGameRepo.findAllByDevelopersContaining(mDeveloper)).thenReturn(games);

        assertEquals(games, mGridService.getAllGamesByDev("Dev"));
        Mockito.verify(mockDevRepo, Mockito.times(1)).findByName(Mockito.anyString());
        Mockito.verify(mockGameRepo, Mockito.times(1))
                .findAllByDevelopersContaining(Mockito.any(Developer.class));
    }

    @Test
    void whenSearchingInvalidDev_ReturnEmptyList() {
        Mockito.when(mockGameGenreRepo.findByName("Dev2")).thenReturn(Optional.empty());

        assertNull(mGridService.getAllGamesByDev("Dev2"));
        Mockito.verify(mockDevRepo, Mockito.times(1)).findByName(Mockito.anyString());
        Mockito.verify(mockGameRepo, Mockito.times(0))
                .findAllByDevelopersContaining(Mockito.any(Developer.class));

    }

    @Test
    void whenSearchingValidPublisher_ReturnValidList() {
        List<Game> games = Arrays.asList(mGame);
        Mockito.when(mockPubRepo.findByName("Pub")).thenReturn(Optional.ofNullable(mPublisher));
        Mockito.when(mockGameRepo.findAllByPublisher(mPublisher)).thenReturn(games);

        assertEquals(games, mGridService.getAllGamesByPublisher("Pub"));
        Mockito.verify(mockPubRepo, Mockito.times(1)).findByName(Mockito.anyString());
        Mockito.verify(mockGameRepo, Mockito.times(1))
                .findAllByPublisher(Mockito.any(Publisher.class));
    }

    @Test
    void whenSearchingInvalidPublisher_ReturnEmptyList() {
        Mockito.when(mockGameGenreRepo.findByName("Pub2")).thenReturn(Optional.empty());

        assertNull(mGridService.getAllGamesByPublisher("Pub2"));
        Mockito.verify(mockPubRepo, Mockito.times(1)).findByName(Mockito.anyString());
        Mockito.verify(mockGameRepo, Mockito.times(0))
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
    void whenSavingGamePOJO_ReturnValidGame() {
        Mockito.when(mockGameGenreRepo.findByName("Genre")).thenReturn(Optional.ofNullable(mGameGenre));
        Mockito.when(mockDevRepo.findByName("Dev")).thenReturn(Optional.ofNullable(mDeveloper));
        Mockito.when(mockPubRepo.findByName("Pub")).thenReturn(Optional.ofNullable(mPublisher));

        Set<String> gameGenrePOJOSet = new HashSet<>(Arrays.asList("Genre"));

        Set<String> developerPOJOSet = new HashSet<>(Arrays.asList("Dev"));

        GamePOJO gamePOJO = new GamePOJO("Game", null, gameGenrePOJOSet, "Pub", developerPOJOSet, null, null);

        Game savedGame = mGridService.saveGame(gamePOJO);
        Mockito.verify(mockPubRepo, Mockito.times(1)).findByName("Pub");
        Mockito.verify(mockDevRepo, Mockito.times(1)).findByName("Dev");
        Mockito.verify(mockGameGenreRepo, Mockito.times(1)).findByName("Genre");
        assertEquals(mGame.getName(), savedGame.getName());
        assertEquals(mGame.getDescription(), savedGame.getDescription());
        assertEquals(mGame.getPublisher(), savedGame.getPublisher());
        assertEquals(mGame.getGameGenres(), savedGame.getGameGenres());
    }

    @Test
    void whenSavingGamePOJOWithInvalidGenre_ReturnNull() {
        Mockito.when(mockGameGenreRepo.findByName("Genre")).thenReturn(Optional.empty());
        Mockito.when(mockDevRepo.findByName("Dev")).thenReturn(Optional.ofNullable(mDeveloper));
        Mockito.when(mockPubRepo.findByName("Pub")).thenReturn(Optional.ofNullable(mPublisher));

        Set<String> gameGenrePOJOSet = new HashSet<>(Arrays.asList("Genre"));

        Set<String> developerPOJOSet = new HashSet<>(Arrays.asList("Dev"));

        GamePOJO gamePOJO = new GamePOJO("Game", null, gameGenrePOJOSet, "Pub", developerPOJOSet, null, null);

        Game savedGame = mGridService.saveGame(gamePOJO);
        Mockito.verify(mockPubRepo, Mockito.times(0)).findByName("Pub");
        Mockito.verify(mockDevRepo, Mockito.times(0)).findByName("Dev");
        Mockito.verify(mockGameGenreRepo, Mockito.times(1)).findByName("Genre");
        assertNull(savedGame);
    }

    @Test
    void whenSavingGamePOJOWithInvalidPub_ReturnNull() {
        Mockito.when(mockGameGenreRepo.findByName("Genre")).thenReturn(Optional.ofNullable(mGameGenre));
        Mockito.when(mockDevRepo.findByName("Dev")).thenReturn(Optional.ofNullable(mDeveloper));
        Mockito.when(mockPubRepo.findByName("Pub")).thenReturn(Optional.empty());

        Set<String> gameGenrePOJOSet = new HashSet<>(Arrays.asList("Genre"));

        Set<String> developerPOJOSet = new HashSet<>(Arrays.asList("Dev"));

        GamePOJO gamePOJO = new GamePOJO("Game", null, gameGenrePOJOSet, "Pub", developerPOJOSet, null, null);

        Game savedGame = mGridService.saveGame(gamePOJO);
        Mockito.verify(mockPubRepo, Mockito.times(1)).findByName("Pub");
        Mockito.verify(mockDevRepo, Mockito.times(0)).findByName("Dev");
        Mockito.verify(mockGameGenreRepo, Mockito.times(1)).findByName("Genre");
        assertNull(savedGame);
    }

    @Test
    void whenSavingGamePOJOWithInvalidDev_ReturnNull() {
        Mockito.when(mockGameGenreRepo.findByName("Genre")).thenReturn(Optional.ofNullable(mGameGenre));
        Mockito.when(mockDevRepo.findByName("Dev")).thenReturn(Optional.empty());
        Mockito.when(mockPubRepo.findByName("Pub")).thenReturn(Optional.ofNullable(mPublisher));

        Set<String> gameGenrePOJOSet = new HashSet<>(Arrays.asList("Genre"));

        Set<String> developerPOJOSet = new HashSet<>(Arrays.asList("Dev"));

        GamePOJO gamePOJO = new GamePOJO("Game", null, gameGenrePOJOSet, "Pub", developerPOJOSet, null, null);

        Game savedGame = mGridService.saveGame(gamePOJO);
        Mockito.verify(mockPubRepo, Mockito.times(1)).findByName("Pub");
        Mockito.verify(mockDevRepo, Mockito.times(1)).findByName("Dev");
        Mockito.verify(mockGameGenreRepo, Mockito.times(1)).findByName("Genre");
        assertNull(savedGame);
    }
    
    @Test
    void whenSavingValidGameKeyPOJO_ReturnValidGameKey(){
        Mockito.when(mockGameRepo.findById( 2L)).thenReturn(Optional.ofNullable(mGame2));

        GameKeyPOJO gameKeyPOJO = new GameKeyPOJO("key", 2L, "steam", "ps3");

        GameKey savedGameKey = mGridService.saveGameKey(gameKeyPOJO);

        Mockito.verify(mockGameRepo, Mockito.times(1)).findById(2L);
        assertEquals("key", savedGameKey.getRKey());
        assertEquals("ps3", savedGameKey.getPlatform());
        assertEquals("steam", savedGameKey.getRetailer());
        assertEquals(mGame2.getName(), savedGameKey.getGame().getName());
    }

    @Test
    void whenSavingInvalidGameKeyPOJO_ReturnNullGameKey() {
        Mockito.when(mockGameRepo.findById(2L)).thenReturn(Optional.empty());

        GameKeyPOJO gameKeyPOJO = new GameKeyPOJO("key", 2L, "steam", "ps3");

        GameKey savedGameKey = mGridService.saveGameKey(gameKeyPOJO);

        Mockito.verify(mockGameRepo, Mockito.times(1)).findById(2L);
        assertNull(savedGameKey);
    }

    @Test
    void whenSavingValidSellPOJO_ReturnValidSell(){
        Mockito.when(mockUserRepo.findById(6L)).thenReturn(Optional.ofNullable(mUser));
        Mockito.when(mockGameKeyRepo.findByrKey("key")).thenReturn(Optional.ofNullable(mGameKey));

        SellPOJO sellPOJO = new SellPOJO("key", 6L, 2.3, null);

        Sell savedSell = mGridService.saveSell(sellPOJO);
        Mockito.verify(mockUserRepo, Mockito.times(1)).findById(6L);
        Mockito.verify(mockGameKeyRepo, Mockito.times(1)).findByrKey("key");
        assertEquals(2.3, savedSell.getPrice());
    }

    @Test
    void whenSavingInvalidUser_ReturnNullSell() {
        Mockito.when(mockUserRepo.findById(6L)).thenReturn(Optional.empty());
        Mockito.when(mockGameKeyRepo.findByrKey("key")).thenReturn(Optional.ofNullable(mGameKey));

        SellPOJO sellPOJO = new SellPOJO("key", 6L, 2.3, null);

        Sell savedSell = mGridService.saveSell(sellPOJO);
        Mockito.verify(mockUserRepo, Mockito.times(1)).findById(6L);
        Mockito.verify(mockGameKeyRepo, Mockito.times(0)).findByrKey("key");
        assertNull(savedSell);
    }

    @Test
    void whenSavingInvalidGameKey_ReturnNullSell(){
        Mockito.when(mockUserRepo.findById(6L)).thenReturn(Optional.ofNullable(mUser));
        Mockito.when(mockGameKeyRepo.findByrKey("key")).thenReturn(Optional.empty());

        SellPOJO sellPOJO = new SellPOJO("key", 6L, 2.3, null);

        Sell savedSell = mGridService.saveSell(sellPOJO);
        Mockito.verify(mockUserRepo, Mockito.times(1)).findById(6L);
        Mockito.verify(mockGameKeyRepo, Mockito.times(1)).findByrKey("key");
        assertNull(savedSell);
    }

    @Test
    void whenReceivingSearchGame_withValidName_returnValidList(){
        Mockito.when(mockGameRepo.findAllByNameContaining(Mockito.anyString()))
                .thenReturn(new ArrayList<>(Arrays.asList(mGame, mGame2)));
        mSearchGamePOJO.setName("g");

        List<Game> games = mGridService.searchGames(mSearchGamePOJO);
        assertEquals(2, games.size());
        assertEquals(mGame, games.get(0));
        assertEquals(mGame2, games.get(1));
    }

    @Test
    void whenReceivingSearchGame_withValidGenre_returnValidList(){
        Mockito.when(mockGameGenreRepo.findByName(Mockito.anyString())).thenReturn(Optional.of(mGameGenre));
        Mockito.when(mockGameRepo.findAllByGameGenresContains(Mockito.any(GameGenre.class)))
                .thenReturn(new ArrayList<>(Arrays.asList(mGame)));
        Mockito.when(mockGameRepo.findAll()).thenReturn(new ArrayList<>(Arrays.asList(mGame, mGame2)));
        String[] genres = {"adventure"};
        mSearchGamePOJO.setGenres(genres);

        List<Game> games = mGridService.searchGames(mSearchGamePOJO);
        assertEquals(1, games.size());
        assertEquals(mGame, games.get(0));
    }

    @Test
    void whenReceivingSearchGame_withValidPlatforms_returnValidList(){
        Mockito.when(mockGameRepo.findAll()).thenReturn(new ArrayList<>(Arrays.asList(mGame, mGame2)));
        mGameKey.setPlatform("ps4");
        mGame2.addGameKey(mGameKey);
        String[] platforms = {"ps4"};
        mSearchGamePOJO.setPlataforms(platforms);

        List<Game> games = mGridService.searchGames(mSearchGamePOJO);
        assertEquals(1, games.size());
        assertEquals(mGame2, games.get(0));
    }

    @Test
    void whenReceivingSearchGame_withValidBeginPrice_andNullEndPrice_returnListWithStartingPrice(){
        Mockito.when(mockGameRepo.findAll()).thenReturn(new ArrayList<>(Arrays.asList(mGame, mGame2)));
        mGame.addGameKey(mGameKey);
        mGame2.addGameKey(mGameKey2);
        mSearchGamePOJO.setStartPrice(5);

        List<Game> games = mGridService.searchGames(mSearchGamePOJO);
        assertEquals(Arrays.asList(mGame, mGame2), games);
    }

    @Test
    void whenReceivingSearchGame_withValidBeginPriceAndEndPrice_returnListWithStartingPrice(){
        Mockito.when(mockGameRepo.findAll()).thenReturn(new ArrayList<>(Arrays.asList(mGame, mGame2)));
        mGame.addGameKey(mGameKey);
        mGame2.addGameKey(mGameKey2);
        mSearchGamePOJO.setStartPrice(5);
        mSearchGamePOJO.setEndPrice(10);

        List<Game> games = mGridService.searchGames(mSearchGamePOJO);
        assertEquals(Arrays.asList(mGame), games);
    }

    @Test
    void whenReceivingSeargGame_withMoreParameters_returnIntersectionOfList(){
        Mockito.when(mockGameRepo.findAllByNameContaining(Mockito.anyString()))
                .thenReturn(new ArrayList<>(Arrays.asList(mGame, mGame2)));
        mSearchGamePOJO.setName("g");
        Mockito.when(mockGameGenreRepo.findByName(Mockito.anyString())).thenReturn(Optional.of(mGameGenre));
        Mockito.when(mockGameRepo.findAllByGameGenresContains(Mockito.any(GameGenre.class)))
                .thenReturn(new ArrayList<>(Arrays.asList(mGame)));
        String[] genres = {"adventure"};
        mSearchGamePOJO.setGenres(genres);
        mGameKey.setPlatform("ps4");
        mGame2.addGameKey(mGameKey2);
        String[] platforms = {"ps4"};
        mSearchGamePOJO.setPlataforms(platforms);
        mSearchGamePOJO.setStartPrice(5);
        mSearchGamePOJO.setEndPrice(10);
        assertTrue(mGridService.searchGames(mSearchGamePOJO).isEmpty());
    }

    @Test
    void whenSearchingGame_ReturnPaginatedSearchGame() {
        Mockito.when(mockGameRepo.findAll()).thenReturn(new ArrayList<>(Arrays.asList(mGame, mGame2)));

        List<Game> gamesList = Arrays.asList(mGame, mGame2);

        assertEquals(gamesList, mGridService.pageSearchGames(mSearchGamePOJO).getContent());
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

        Mockito.when(mockUserRepo.findById(userID)).thenReturn(Optional.ofNullable(mUser));
        Mockito.when(mockGameRepo.findById(gameID)).thenReturn(Optional.ofNullable(mGame));

        Set<Game> games = mGridService.addWishListByUserID(gameID, userID);

        Mockito.verify(mockUserRepo, Mockito.times(1)).findById(userID);
        Mockito.verify(mockGameRepo, Mockito.times(1)).findById(gameID);

        assertEquals(expected, games);
    }
}