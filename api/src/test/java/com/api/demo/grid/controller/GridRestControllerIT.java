package com.api.demo.grid.controller;

import com.api.demo.DemoApplication;
import com.api.demo.grid.models.Buy;
import com.api.demo.grid.models.Developer;
import com.api.demo.grid.models.Game;
import com.api.demo.grid.models.GameGenre;
import com.api.demo.grid.models.GameKey;
import com.api.demo.grid.models.Publisher;
import com.api.demo.grid.models.Sell;
import com.api.demo.grid.models.User;
import com.api.demo.grid.pojos.BuyListingsPOJO;
import com.api.demo.grid.exception.GameNotFoundException;
import com.api.demo.grid.pojos.DeveloperPOJO;
import com.api.demo.grid.pojos.GameGenrePOJO;
import com.api.demo.grid.pojos.GameKeyPOJO;
import com.api.demo.grid.pojos.GamePOJO;
import com.api.demo.grid.pojos.PublisherPOJO;
import com.api.demo.grid.pojos.SellPOJO;
import com.api.demo.grid.repository.BuyRepository;
import com.api.demo.grid.repository.DeveloperRepository;
import com.api.demo.grid.repository.GameGenreRepository;
import com.api.demo.grid.repository.GameKeyRepository;
import com.api.demo.grid.repository.GameRepository;
import com.api.demo.grid.repository.PublisherRepository;
import com.api.demo.grid.repository.SellRepository;
import com.api.demo.grid.repository.UserRepository;
import com.api.demo.grid.utils.Pagination;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashSet;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DemoApplication.class)
class GridRestControllerIT {

    @Autowired
    private GameRepository mGameRepository;

    @Autowired
    private GameGenreRepository mGameGenreRepository;

    @Autowired
    private DeveloperRepository mDeveloperRepository;

    @Autowired
    private PublisherRepository mPublisherRepository;

    @Autowired
    private UserRepository mUserRepository;

    @Autowired
    private MockMvc mMockMvc;

    private GameGenrePOJO mGameGenrePOJO;
    private GamePOJO mGamePOJO;
    private PublisherPOJO mPublisherPOJO;
    private DeveloperPOJO mDeveloperPOJO;


    @BeforeEach
    public void setUp(){
        mGameGenrePOJO = new GameGenrePOJO("genre", "");
        mPublisherPOJO = new PublisherPOJO("publisher", "");
        mDeveloperPOJO = new DeveloperPOJO("developer");
        mGamePOJO = new GamePOJO("game", "", null, null, null, null, "");
        mGamePOJO.setDevelopers(new HashSet<String>(Arrays.asList("developer")));
        mGamePOJO.setGameGenres(new HashSet<String>(Arrays.asList("genre")));
        mGamePOJO.setPublisher("publisher");

        mUserRepository.deleteAll();
        mGameRepository.deleteAll();
        mGameGenreRepository.deleteAll();
        mPublisherRepository.deleteAll();
        mDeveloperRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "spring", authorities = "ADMIN")
    void whenPostingValidGenre_ReturnValidResponse() throws Exception{

        mMockMvc.perform(post("/grid/add-genre")
                .content(asJsonString(mGameGenrePOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(mGameGenrePOJO.getName()))).andReturn();

        assertFalse(mGameGenreRepository.findByName(mGameGenrePOJO.getName()).isEmpty());

    }

    @Test
    @WithMockUser(username = "spring", authorities = "ADMIN")
    void whenPostingValidPub_ReturnValidResponse() throws Exception{
        mMockMvc.perform(post("/grid/add-publisher")
                .content(asJsonString(mPublisherPOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(mPublisherPOJO.getName())));
        assertFalse(mPublisherRepository.findByName(mPublisherPOJO.getName()).isEmpty());

    }

    @Test
    @WithMockUser(username = "spring", authorities = "ADMIN")
    void whenPostingValidDeveloper_ReturnValidResponse() throws Exception{
        mMockMvc.perform(post("/grid/add-developer")
                .content(asJsonString(mDeveloperPOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(mDeveloperPOJO.getName())));
        assertFalse(mDeveloperRepository.findByName(mDeveloperPOJO.getName()).isEmpty());
    }

    @Test
    @WithMockUser(username = "spring", authorities = "ADMIN")
    void whenPostingValidGame_ReturnValidResponse() throws Exception{
        Developer developer = new Developer();
        developer.setName("dev");
        mDeveloperRepository.save(developer);

        Publisher publisher = new Publisher();
        publisher.setName("pub");
        mPublisherRepository.save(publisher);

        GameGenre gameGenre = new GameGenre();
        gameGenre.setName("genre");
        mGameGenreRepository.save(gameGenre);

        mGamePOJO.setPublisher("pub");
        mGamePOJO.setDevelopers(new HashSet<>(Arrays.asList("dev")));
        mGamePOJO.setGameGenres(new HashSet<>(Arrays.asList("genre")));
        mMockMvc.perform(post("/grid/add-game")
                .content(asJsonString(mGamePOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(mGamePOJO.getName())))
        ;
        assertFalse(mGameRepository.findAllByNameContaining(mGamePOJO.getName()).isEmpty());
    }

    @Test
    @WithMockUser(username = "spring", authorities = "ADMIN")
    void whenPostingInvalidGame_ReturnErrorResponse() throws Exception{

        mGamePOJO.setPublisher(null);

        mMockMvc.perform(post("/grid/add-game")
                .content(asJsonString(mGamePOJO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("Could not save Game"));
        assertTrue(mGameRepository.findAllByNameContaining(mGamePOJO.getName()).isEmpty());
    }


    /*
    @Test
    @WithMockUser(username = "spring")
    void whenPostingValidWishList_ReturnSuccess() throws  Exception {

        Game game = new Game();
        mGameRepository.save(game);
        User user = new User();
        user.setUsername("mUsername1");
        user.setName("mName1");
        user.setEmail("mEmail1");
        user.setPassword("mPassword1");
        user.setCountry("mCountry1");
        user.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse("17/10/2010"));
        mUserRepository.save(user);


        mMockMvc.perform(post("/grid/add-wish-list")
                .param("user_id", String.valueOf(user.getId()))
                .param("game_id", String.valueOf(game.getId()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", is((int)game.getId())));
    }
    */
    

    @Test
    @WithMockUser(username = "spring")
    void whenPostingInvalidWishList_ReturnException() throws  Exception {
        mMockMvc.perform(post("/grid/add-wish-list")
                .param("user_id", String.valueOf(1))
                .param("game_id", String.valueOf(1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
