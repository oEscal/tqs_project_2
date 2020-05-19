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
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class GridServiceImpl implements GridService{
    @Autowired
    private DeveloperRepository developerRepository;

    @Autowired
    private GameGenreRepository gameGenreRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private GameRepository gameRepository;

    @Override
    public Game getGameById(long id) {
        Optional<Game> gameResponse = gameRepository.findById(id);

        if (gameResponse.isEmpty()) return null;

        return gameResponse.get();
    }

    @Override
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    @Override
    public List<Game> getAllGamesWithGenre(String genre) {
        Optional<GameGenre> gameGenre = gameGenreRepository.findByName(genre);

        if (gameGenre.isEmpty()) return null;

        return gameRepository.findAllByGameGenresContains(gameGenre.get());
    }

    @Override
    public List<Game> getAllGamesByName(String name) {
        return gameRepository.findAllByNameContains(name);
    }

    @Override
    public List<Game> getAllGamesByDev(String developer) {
        Optional<Developer> dev = developerRepository.findByName(developer);

        if (dev.isEmpty()) return null;

        return gameRepository.findAllByDevelopersContaining(dev.get());
    }

    @Override
    public List<Game> getAllGamesByPublisher(String publisher) {
        Optional<Publisher> pub = publisherRepository.findByName(publisher);

        if (pub.isEmpty()) return null;

        return gameRepository.findAllByPublisher(pub.get());
    }

    @Override
    public Game saveGame(GamePOJO gamePOJO){
        Game game = new Game();
        game.setName(gamePOJO.getName());
        game.setCoverUrl(gamePOJO.getCoverUrl());
        game.setDescription(gamePOJO.getDescription());
        game.setReleaseDate(gamePOJO.getReleaseDate());

        //Get Game genres
        Set<GameGenre> gameGenreSet = new HashSet<>();
        Optional<GameGenre> gameGenre;
        for (GameGenrePOJO gameGenrePOJO: gamePOJO.getGameGenres()) {
            gameGenre = gameGenreRepository.findByName(gameGenrePOJO.getName());
            if (gameGenre.isEmpty()) return null;
            gameGenreSet.add(gameGenre.get());
        }
        game.setGameGenres(gameGenreSet);

        // Get Publisher
        Optional<Publisher> publisher = publisherRepository.findByName(gamePOJO.getPublisher().getName());
        if (publisher.isEmpty()) return null;
        game.setPublisher(publisher.get());

        //Get Game Developers
        Set<Developer> developerSet = new HashSet<>();
        Optional<Developer> developer;
        for (DeveloperPOJO developerPOJO: gamePOJO.getDevelopers()) {
            developer = developerRepository.findByName(developerPOJO.getName());
            if (developer.isEmpty()) return null;
            developerSet.add(developer.get());
        }
        game.setDevelopers(developerSet);

        this.gameRepository.save(game);
        return game;
    }

    @Override
    public Publisher savePublisher(PublisherPOJO publisherPOJO) {
        Publisher publisher = new Publisher();
        publisher.setName(publisherPOJO.getName());
        publisher.setDescription(publisherPOJO.getDescription());
        this.publisherRepository.save(publisher);
        return publisher;
    }

    @Override
    public Developer saveDeveloper(DeveloperPOJO developerPOJO) {
        Developer developer = new Developer();
        developer.setName(developerPOJO.getName());
        this.developerRepository.save(developer);
        return developer;
    }

    @Override
    public GameGenre saveGameGenre(GameGenrePOJO gameGenrePOJO) {
        GameGenre gameGenre = new GameGenre();
        gameGenre.setName(gameGenrePOJO.getName());
        this.gameGenreRepository.save(gameGenre);
        return gameGenre;
    }

}
