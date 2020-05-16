package com.api.demo.grid;

import com.api.demo.grid.models.Game;
import com.api.demo.grid.models.GameGenre;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class GridRepositoryImpl implements GridRepository{

    @Override
    public <S extends Game> S save(S s) {
        return null;
    }

    @Override
    public <S extends Game> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<Game> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<Game> findAll() {
        return null;
    }

    @Override
    public Iterable<Game> findAllById(Iterable<Long> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

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
    public List<Game> findAllByPriceRange(double initialPrice, double finalPrice) {
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
}
