package com.api.demo.grid.service;

import com.api.demo.grid.models.Developer;
import com.api.demo.grid.models.Game;
import com.api.demo.grid.models.GameGenre;
import com.api.demo.grid.models.Publisher;
import com.api.demo.grid.pojos.DeveloperPOJO;
import com.api.demo.grid.pojos.GameGenrePOJO;
import com.api.demo.grid.pojos.GamePOJO;
import com.api.demo.grid.pojos.PublisherPOJO;
import com.api.demo.grid.repository.DeveloperRepository;
import com.api.demo.grid.repository.GameGenreRepository;
import com.api.demo.grid.repository.GameRepository;
import com.api.demo.grid.repository.PublisherRepository;
import com.api.demo.grid.utils.Pagination;
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

    @InjectMocks
    private GridServiceImpl gridService;

    private Game game;
    private Game game2;
    private GameGenre gameGenre;
    private Developer developer;
    private Publisher publisher;

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
}