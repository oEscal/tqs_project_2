package com.api.demo.grid.service;

import com.api.demo.grid.models.Developer;
import com.api.demo.grid.models.Game;
import com.api.demo.grid.models.GameGenre;
import com.api.demo.grid.models.Publisher;
import com.api.demo.grid.repository.DeveloperRepository;
import com.api.demo.grid.repository.GameGenreRepository;
import com.api.demo.grid.repository.GameRepository;
import com.api.demo.grid.repository.PublisherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mockito;

import java.util.*;

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

    @InjectMocks
    private GridServiceImpl gridService;

    private Game game;
    private Game game2;
    private GameGenre gameGenre;
    private Developer developer;
    private Publisher publisher;

    @BeforeEach
    public void setUp(){
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
        game.setId(5L);
        game.setName("Game 2");

        game.setGameGenres(new HashSet<>(Arrays.asList(gameGenre)));
        game.setDevelopers(new HashSet<>(Arrays.asList(developer)));
        game.setPublisher(publisher);

    }

    @Test
    public void whenSearchingAll_ReturnAllGame(){
        List<Game> games = Arrays.asList(game, game2);
        Mockito.when(mockGameRepo.findAll()).thenReturn(games);

        assertEquals(games, gridService.getAllGames());
        Mockito.verify(mockGameRepo, Mockito.times(1)).findAll();
    }

    @Test
    public void whenSearchingId_ReturnRightGame(){
        Mockito.when(mockGameRepo.findById(1L)).thenReturn(java.util.Optional.ofNullable(game));

        assertEquals(game, gridService.getGameById(1L));
        Mockito.verify(mockGameRepo, Mockito.times(1)).findById(Mockito.anyLong());
    }

    @Test
    public void whenSearchingInvalidId_ReturnNull(){
        Mockito.when(mockGameRepo.findById(2L)).thenReturn(Optional.empty());

        assertNull(gridService.getGameById(2L));
        Mockito.verify(mockGameRepo, Mockito.times(1)).findById(Mockito.anyLong());
    }

    @Test
    public void whenSearchingName_ReturnRightGame(){
        List<Game> games = Arrays.asList(game);
        Mockito.when(mockGameRepo.findAllByName("Game")).thenReturn(games);

        assertEquals(games, gridService.getAllGamesByName("Game"));
        Mockito.verify(mockGameRepo, Mockito.times(1)).findAllByName(Mockito.anyString());
    }

    @Test
    public void whenSearchingInvalidName_ReturnNull(){
        Mockito.when(mockGameRepo.findAllByName("Game2")).thenReturn(new ArrayList<Game>());

        assertEquals(new ArrayList<Game>(), gridService.getAllGamesByName("Game2"));
        Mockito.verify(mockGameRepo, Mockito.times(1)).findAllByName(Mockito.anyString());
    }

    @Test
    public void whenSearchingGameGenre_ReturnValidList(){
        List<Game> games = Arrays.asList(game);
        Mockito.when(mockGameGenreRepo.findByName("Genre")).thenReturn(Optional.ofNullable(gameGenre));
        Mockito.when(mockGameRepo.findAllByGameGenresContains(gameGenre)).thenReturn(games);

        assertEquals(games, gridService.getAllGamesWithGenre("Genre"));
        Mockito.verify(mockGameGenreRepo, Mockito.times(1)).findByName(Mockito.anyString());
        Mockito.verify(mockGameRepo, Mockito.times(1))
                .findAllByGameGenresContains(Mockito.any(GameGenre.class));
    }

    @Test
    public void whenSearchingInvalidGenre_ReturnEmptyList(){
        Mockito.when(mockGameGenreRepo.findByName("Genre2")).thenReturn(Optional.empty());

        assertNull(gridService.getAllGamesWithGenre("Genre2"));
        Mockito.verify(mockGameGenreRepo, Mockito.times(1)).findByName(Mockito.anyString());
        Mockito.verify(mockGameRepo, Mockito.times(0))
                .findAllByGameGenresContains(Mockito.any(GameGenre.class));

    }

    @Test
    public void whenSearchingValidDev_ReturnValidList(){
        List<Game> games = Arrays.asList(game);
        Mockito.when(mockDevRepo.findByName("Dev")).thenReturn(Optional.ofNullable(developer));
        Mockito.when(mockGameRepo.findAllByDevelopersContaining(developer)).thenReturn(games);

        assertEquals(games, gridService.getAllGamesByDev("Dev"));
        Mockito.verify(mockDevRepo, Mockito.times(1)).findByName(Mockito.anyString());
        Mockito.verify(mockGameRepo, Mockito.times(1))
                .findAllByDevelopersContaining(Mockito.any(Developer.class));
    }

    @Test
    public void whenSearchingInvalidDev_ReturnEmptyList(){
        Mockito.when(mockGameGenreRepo.findByName("Dev2")).thenReturn(Optional.empty());

        assertNull(gridService.getAllGamesByDev("Dev2"));
        Mockito.verify(mockDevRepo, Mockito.times(1)).findByName(Mockito.anyString());
        Mockito.verify(mockGameRepo, Mockito.times(0))
                .findAllByDevelopersContaining(Mockito.any(Developer.class));

    }

    @Test
    public void whenSearchingValidPublisher_ReturnValidList(){
        List<Game> games = Arrays.asList(game);
        Mockito.when(mockPubRepo.findByName("Pub")).thenReturn(Optional.ofNullable(publisher));
        Mockito.when(mockGameRepo.findAllByPublisher(publisher)).thenReturn(games);

        assertEquals(games, gridService.getAllGamesByPublisher("Pub"));
        Mockito.verify(mockPubRepo, Mockito.times(1)).findByName(Mockito.anyString());
        Mockito.verify(mockGameRepo, Mockito.times(1))
                .findAllByPublisher(Mockito.any(Publisher.class));
    }

    @Test
    public void whenSearchingInvalidPublisher_ReturnEmptyList(){
        Mockito.when(mockGameGenreRepo.findByName("Pub2")).thenReturn(Optional.empty());

        assertNull(gridService.getAllGamesByPublisher("Pub2"));
        Mockito.verify(mockPubRepo, Mockito.times(1)).findByName(Mockito.anyString());
        Mockito.verify(mockGameRepo, Mockito.times(0))
                .findAllByPublisher(Mockito.any(Publisher.class));

    }
}