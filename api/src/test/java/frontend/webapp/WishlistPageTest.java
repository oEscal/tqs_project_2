package frontend.webapp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class WishlistPageTest {
    WebAppPageObject controller;

    private final int port = 3000;

    @BeforeEach
    public void setUp() {
        controller = new WebAppPageObject();

        //Login
        controller.navigate("http://localhost:" + port + "/login-page");
        String username = "admin";
        String password = "admin";
        controller.writeInput(username,"username");
        controller.writeInput(password,"password");

        controller.clickButton("confirm");

        controller.waitForLoad("processing");
        controller.navigate("http://localhost:" + port + "/wishlist");
    }

    @AfterEach
    public void tearDown() {
        controller.tear();
    }


    @Test
    //Needs user to have the first game wishlisted
    public void whenClickingGameOnWishlist_goToGame() {
        controller.waitForLoad("firstLoad");
        controller.waitSeconds(2);

        controller.clickCard();
        controller.waitForLoad("processing");
        controller.waitSeconds(2);
        String url = "http://localhost:" + port + "/games/info/1";
        assertTrue(controller.checkURL(url));
    }

}