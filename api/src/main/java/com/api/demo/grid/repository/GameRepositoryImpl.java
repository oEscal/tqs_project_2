package com.api.demo.grid.repository;

import com.api.demo.grid.models.Game;
import com.api.demo.grid.models.GameGenre;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class GameRepositoryImpl implements GameRepository {

    @Override
    public <S extends Game> S save(S s) {
        return null;
    }

    @Override
    public <S extends Game> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<Game> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public List<Game> findAll() {
        return null;
    }

    @Override
    public Iterable<Game> findAllById(Iterable<Integer> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void delete(Game game) {

    }

    @Override
    public void deleteAll(Iterable<? extends Game> iterable) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Game> findAllByGameGenres(Set<GameGenre> genres) {
        return null;
    }

    @Override
    public List<Game> findAllByPlatform(String platform) {
        return null;
    }

    @Override
    public List<Game> findAllById(int id) {
        return null;
    }

    @Override
    public List<Game> findAllByName(String name) {
        return null;
    }
}
