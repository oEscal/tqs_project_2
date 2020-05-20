package com.api.demo.grid.service;

import com.api.demo.grid.models.Developer;
import com.api.demo.grid.models.Game;
import com.api.demo.grid.models.GameGenre;
import com.api.demo.grid.models.Publisher;
import com.api.demo.grid.repository.DeveloperRepository;
import com.api.demo.grid.repository.GameGenreRepository;
import com.api.demo.grid.repository.GameRepository;
import com.api.demo.grid.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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

        if (!gameResponse.isEmpty()) return gameResponse.get();

        return null;
    }

    @Override
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    @Override
    public List<Game> getAllGamesWithGenre(String genre) {
        Optional<GameGenre> gameGenre = gameGenreRepository.findByName(genre);

        if (gameGenre.isEmpty()) return new ArrayList<>();

        return gameRepository.findAllByGameGenresContains(gameGenre.get());
    }

    @Override
    public List<Game> getAllGamesByName(String name) {
        return gameRepository.findAllByName(name);
    }

    @Override
    public List<Game> getAllGamesByDev(String developer) {
        Optional<Developer> dev = developerRepository.findByName(developer);

        if (dev.isEmpty()) return new ArrayList<>();

        return gameRepository.findAllByDevelopersContaining(dev.get());
    }

    @Override
    public List<Game> getAllGamesByPublisher(String publisher) {
        Optional<Publisher> pub = publisherRepository.findByName(publisher);

        if (pub.isEmpty()) return new ArrayList<>();

        return gameRepository.findAllByPublisher(pub.get());
    }
}
