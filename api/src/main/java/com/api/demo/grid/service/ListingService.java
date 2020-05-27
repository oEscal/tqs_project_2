package com.api.demo.grid.service;

import com.api.demo.grid.exception.ListingNotFoundException;
import com.api.demo.grid.models.GameKey;
import com.api.demo.grid.models.Sell;
import com.api.demo.grid.pojos.GameKeyPOJO;
import com.api.demo.grid.pojos.SellPOJO;
import org.springframework.stereotype.Service;

@Service
public interface ListingService {
    GameKey saveGameKey(GameKeyPOJO gameKeyPOJO);

    Sell saveSell(SellPOJO sellPOJO);

    Sell deleteSell(long sellId) throws ListingNotFoundException;

}
