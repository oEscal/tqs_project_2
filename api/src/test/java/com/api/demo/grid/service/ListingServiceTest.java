package com.api.demo.grid.service;

import com.api.demo.grid.exception.ListingNotFoundException;
import com.api.demo.grid.models.*;
import com.api.demo.grid.pojos.GameKeyPOJO;
import com.api.demo.grid.pojos.SellPOJO;
import com.api.demo.grid.repository.GameKeyRepository;
import com.api.demo.grid.repository.GameRepository;
import com.api.demo.grid.repository.SellRepository;
import com.api.demo.grid.repository.UserRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ListingServiceTest {
    @Mock(lenient = true)
    private SellRepository mMockSellRepo;

    @Mock(lenient = true)
    private GameRepository mMockGameRepo;

    @Mock(lenient = true)
    private UserRepository mMockUserRepo;

    @Mock(lenient = true)
    private GameKeyRepository mGameKeyRepo;

    @InjectMocks
    private ListingServiceImpl mListingService;

    private Game mGame;
    private User mUser;
    private GameKey mGameKey;
    private Sell mSell;

    @BeforeEach
    void setUp() {
        mGame = new Game();
        mGame.setId(1L);
        mGame.setName("Game");

        mUser = new User();
        mUser.setId(2L);

        mGameKey = new GameKey();
        mGameKey.setId(3L);

        sell = new Sell();
        sell.setId(4L);
        sell.setGameKey(mGameKey);
    }

    @Test
    void whenSavingValidGameKeyPOJO_ReturnValidGameKey() {
        Mockito.when(mMockGameRepo.findById(1L)).thenReturn(Optional.ofNullable(mGame));

        GameKeyPOJO gameKeyPOJO = new GameKeyPOJO("key", 1L, "steam", "ps3");

        GameKey savedGameKey = gridService.saveGameKey(gameKeyPOJO);

        Mockito.verify(mMockGameRepo, Mockito.times(1)).findById(1L);
        assertEquals("key", savedGameKey.getRKey());
        assertEquals("ps3", savedGameKey.getPlatform());
        assertEquals("steam", savedGameKey.getRetailer());
        assertEquals(game2.getName(), savedGameKey.getGame().getName());
    }

    @Test
    void whenSavingInvalidGameKeyPOJO_ReturnNullGameKey() {
        Mockito.when(mMockGameRepo.findById(2L)).thenReturn(Optional.empty());

        GameKeyPOJO gameKeyPOJO = new GameKeyPOJO("key", 2L, "steam", "ps3");

        GameKey savedGameKey = gridService.saveGameKey(gameKeyPOJO);

        Mockito.verify(mMockGameRepo, Mockito.times(1)).findById(2L);
        assertNull(savedGameKey);
    }

    @Test
    void whenSavingValidSellPOJO_ReturnValidSell() {
        Mockito.when(mockUserRepo.findById(6L)).thenReturn(Optional.ofNullable(mUser));
        Mockito.when(mockGameKeyRepo.findByrKey("key")).thenReturn(Optional.ofNullable(mGameKey));

        SellPOJO sellPOJO = new SellPOJO("key", 6L, 2.3, null);

        Sell savedSell = gridService.saveSell(sellPOJO);
        Mockito.verify(mockUserRepo, Mockito.times(1)).findById(6L);
        Mockito.verify(mockGameKeyRepo, Mockito.times(1)).findByrKey("key");
        assertEquals(2.3, savedSell.getPrice());
    }

    @Test
    void whenSavingInvalidUser_ReturnNullSell() {
        Mockito.when(mockUserRepo.findById(6L)).thenReturn(Optional.empty());
        Mockito.when(mockGameKeyRepo.findByrKey("key")).thenReturn(Optional.ofNullable(mGameKey));

        SellPOJO sellPOJO = new SellPOJO("key", 6L, 2.3, null);

        Sell savedSell = gridService.saveSell(sellPOJO);
        Mockito.verify(mockUserRepo, Mockito.times(1)).findById(6L);
        Mockito.verify(mockGameKeyRepo, Mockito.times(0)).findByrKey("key");
        assertNull(savedSell);
    }

    @Test
    void whenSavingInvalidGameKey_ReturnNullSell() {
        Mockito.when(mockUserRepo.findById(6L)).thenReturn(Optional.ofNullable(mUser));
        Mockito.when(mockGameKeyRepo.findByrKey("key")).thenReturn(Optional.empty());

        SellPOJO sellPOJO = new SellPOJO("key", 6L, 2.3, null);

        Sell savedSell = gridService.saveSell(sellPOJO);
        Mockito.verify(mockUserRepo, Mockito.times(1)).findById(6L);
        Mockito.verify(mockGameKeyRepo, Mockito.times(1)).findByrKey("key");
        assertNull(savedSell);
    }

    @Test
    @SneakyThrows
    void whenDeletingValidSellListing_DeleteSuccessful_AndReturnSell(){
        Mockito.when(mMockSellRepo.findById(Mockito.anyLong()))
                .thenReturn(Optional.ofNullable(sell));

        assertEquals(sell, gridService.deleteSell(8l));
        Mockito.verify(mMockSellRepo, Mockito.times(1))
                .delete(Mockito.any(Sell.class));
    }

    @Test
    void whenDeletingInvalidSellListing_DeleteUnsuccessful_AndThrowsException(){
        Mockito.when(mMockSellRepo.findById(Mockito.anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(ListingNotFoundException.class
                ,() -> gridService.deleteSell(8l));
        Mockito.verify(mMockSellRepo, Mockito.times(0))
                .delete(Mockito.any(Sell.class));
    }
}
