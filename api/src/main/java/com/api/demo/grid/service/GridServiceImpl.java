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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GridServiceImpl implements GridService{

    @Autowired
    private DeveloperRepository mDeveloperRepository;

    @Autowired
    private GameGenreRepository mGameGenreRepository;

    @Autowired
    private PublisherRepository mPublisherRepository;

    @Autowired
    private GameRepository mGameRepository;

    @Override
    public Game getGameById(long id) {
        Optional<Game> gameResponse = mGameRepository.findById(id);

        if (gameResponse.isEmpty()) return null;

        return gameResponse.get();
    }

    @Override
    public List<Game> getAllGames() {
        return mGameRepository.findAll();
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
}
