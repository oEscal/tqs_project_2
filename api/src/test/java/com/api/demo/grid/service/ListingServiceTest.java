package com.api.demo.grid.service;

import com.api.demo.grid.exception.GameNotFoundException;
import com.api.demo.grid.exception.ListingNotFoundException;
import com.api.demo.grid.models.Game;
import com.api.demo.grid.models.GameKey;
import com.api.demo.grid.models.Sell;
import com.api.demo.grid.models.User;
import com.api.demo.grid.pojos.GameKeyPOJO;
import com.api.demo.grid.pojos.SellPOJO;
import com.api.demo.grid.repository.GameKeyRepository;
import com.api.demo.grid.repository.GameRepository;
import com.api.demo.grid.repository.SellRepository;
import com.api.demo.grid.repository.UserRepository;
import com.api.demo.grid.utils.Pagination;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ListingServiceTest {
    @Mock(lenient = true)
    private SellRepository mMockSellRepo;

    @Mock(lenient = true)
    private GameRepository mMockGameRepo;

    @Mock(lenient = true)
    private UserRepository mMockUserRepo;

    @Mock(lenient = true)
    private GameKeyRepository mMockGameKeyRepo;

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

        mSell = new Sell();
        mSell.setId(4L);
        mSell.setGameKey(mGameKey);
    }

    @Test
    void whenSavingValidGameKeyPOJO_ReturnValidGameKey() {
        Mockito.when(mMockGameRepo.findById(1L)).thenReturn(Optional.ofNullable(mGame));

        GameKeyPOJO gameKeyPOJO = new GameKeyPOJO("key", 1L, "steam", "ps3");

        GameKey savedGameKey = mListingService.saveGameKey(gameKeyPOJO);

        Mockito.verify(mMockGameRepo, Mockito.times(1)).findById(1L);
        assertEquals("key", savedGameKey.getRKey());
        assertEquals("ps3", savedGameKey.getPlatform());
        assertEquals("steam", savedGameKey.getRetailer());
        assertEquals(mGame.getName(), savedGameKey.getGame().getName());
    }

    @Test
    void whenSavingInvalidGameKeyPOJO_ReturnNullGameKey() {
        Mockito.when(mMockGameRepo.findById(2L)).thenReturn(Optional.empty());

        GameKeyPOJO gameKeyPOJO = new GameKeyPOJO("key", 2L, "steam", "ps3");

        GameKey savedGameKey = mListingService.saveGameKey(gameKeyPOJO);

        Mockito.verify(mMockGameRepo, Mockito.times(1)).findById(2L);
        assertNull(savedGameKey);
    }

    @Test
    void whenSavingValidSellPOJO_ReturnValidSell() {
        Mockito.when(mMockUserRepo.findById(6L)).thenReturn(Optional.ofNullable(mUser));
        Mockito.when(mMockGameKeyRepo.findByrKey("key")).thenReturn(Optional.ofNullable(mGameKey));

        SellPOJO sellPOJO = new SellPOJO("key", 6L, 2.3, null);

        Sell savedSell = mListingService.saveSell(sellPOJO);
        Mockito.verify(mMockUserRepo, Mockito.times(1)).findById(6L);
        Mockito.verify(mMockGameKeyRepo, Mockito.times(1)).findByrKey("key");
        assertEquals(2.3, savedSell.getPrice());
    }

    @Test
    void whenSavingInvalidUser_ReturnNullSell() {
        Mockito.when(mMockUserRepo.findById(6L)).thenReturn(Optional.empty());
        Mockito.when(mMockGameKeyRepo.findByrKey("key")).thenReturn(Optional.ofNullable(mGameKey));

        SellPOJO sellPOJO = new SellPOJO("key", 6L, 2.3, null);

        Sell savedSell = mListingService.saveSell(sellPOJO);
        Mockito.verify(mMockUserRepo, Mockito.times(1)).findById(6L);
        Mockito.verify(mMockGameKeyRepo, Mockito.times(0)).findByrKey("key");
        assertNull(savedSell);
    }

    @Test
    void whenSavingInvalidGameKey_ReturnNullSell() {
        Mockito.when(mMockUserRepo.findById(6L)).thenReturn(Optional.ofNullable(mUser));
        Mockito.when(mMockGameKeyRepo.findByrKey("key")).thenReturn(Optional.empty());

        SellPOJO sellPOJO = new SellPOJO("key", 6L, 2.3, null);

        Sell savedSell = mListingService.saveSell(sellPOJO);
        Mockito.verify(mMockUserRepo, Mockito.times(1)).findById(6L);
        Mockito.verify(mMockGameKeyRepo, Mockito.times(1)).findByrKey("key");
        assertNull(savedSell);
    }

    @Test
    @SneakyThrows
    void whenDeletingValidSellListing_DeleteSuccessful_AndReturnSell(){
        Mockito.when(mMockSellRepo.findById(Mockito.anyLong()))
                .thenReturn(Optional.ofNullable(mSell));

        assertEquals(mSell, mListingService.deleteSell(8l));
        Mockito.verify(mMockSellRepo, Mockito.times(1))
                .delete(Mockito.any(Sell.class));
    }

    @Test
    void whenDeletingInvalidSellListing_DeleteUnsuccessful_AndThrowsException(){
        Mockito.when(mMockSellRepo.findById(Mockito.anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(ListingNotFoundException.class
                ,() -> mListingService.deleteSell(8l));
        Mockito.verify(mMockSellRepo, Mockito.times(0))
                .delete(Mockito.any(Sell.class));
    }

    @Test
    @SneakyThrows
    void whenSearchingSell_ByGame_ReturnPage() {
        List<Sell> gamesList = Arrays.asList(mSell1, mSell2);
        Pagination<Sell> pagination = new Pagination<>(gamesList);
        int page = 1;
        int entriesPerPage = 6;
        PageRequest pageRequest = PageRequest.of(page, entriesPerPage);

        Page<Sell> sells = pagination.pageImpl(page, entriesPerPage);
        Mockito.when(mMockGameRepo.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(mGame));
        Mockito.when(mMockSellRepo.findAllByGames(1l, pageRequest)).thenReturn(sells);

        assertEquals(sells, mGridService.getAllSellListings(1l, page));
        Mockito.verify(mMockSellRepo, Mockito.times(1)).findAllByGames(1l, pageRequest);
    }

    @Test
    @SneakyThrows
    void whenSearchingSell_andGameIsInvalid_ThrowsException(){
        List<Sell> gamesList = Arrays.asList(mSell1, mSell2);
        Pagination<Sell> pagination = new Pagination<>(gamesList);
        int page = 1;
        int entriesPerPage = 6;
        PageRequest pageRequest = PageRequest.of(page, entriesPerPage);

        Page<Sell> sells = pagination.pageImpl(page, entriesPerPage);
        Mockito.when(mMockGameRepo.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Mockito.when(mMockSellRepo.findAllByGames(2l, pageRequest)).thenReturn(sells);

        assertThrows(GameNotFoundException.class, () -> mGridService.getAllSellListings(2L, 1));
    }
}
