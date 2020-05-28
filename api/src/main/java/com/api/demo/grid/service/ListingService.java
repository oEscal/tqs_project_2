package com.api.demo.grid.service;

import com.api.demo.grid.exception.GameNotFoundException;
import com.api.demo.grid.exception.ListingNotFoundException;
import com.api.demo.grid.exception.UnavailableListingException;
import com.api.demo.grid.exception.UnsufficientFundsException;
import com.api.demo.grid.models.Buy;
import com.api.demo.grid.models.GameKey;
import com.api.demo.grid.models.Sell;
import com.api.demo.grid.pojos.BuyListingsPOJO;
import com.api.demo.grid.pojos.GameKeyPOJO;
import com.api.demo.grid.pojos.SellPOJO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ListingService {
    GameKey saveGameKey(GameKeyPOJO gameKeyPOJO);

    Sell saveSell(SellPOJO sellPOJO);

    Sell deleteSell(long sellId) throws ListingNotFoundException;

    List<Buy> saveBuy(BuyListingsPOJO buyListingsPOJO) throws UnavailableListingException,
            UnsufficientFundsException;

    Page<Sell> getAllSellListings(long gameId, int page) throws GameNotFoundException;
}
