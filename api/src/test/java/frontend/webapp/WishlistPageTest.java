package frontend.webapp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class WishlistPageTest {
    WebAppPageObject controller;

    private final int port = 3000;

    @BeforeEach
    void setUp() {
        controller = new WebAppPageObject();
    }

    @AfterEach
    void tearDown() {
        controller.tear();
    }


    @Test
    //Needs user to have the first game wishlisted
    void whenClickingGameOnWishlist_goToGame() {
        controller.login(port);
        this.addToWishlist();

        controller.waitForLoad("firstLoad");
        controller.waitSeconds(2);

        controller.clickCard();
        controller.waitForLoad("processing");
        controller.waitSeconds(2);
        String url = "http://localhost:" + port + "/games/info/1";
        assertTrue(controller.checkURL(url));
    }



    private void addToWishlist(){
        controller.navigate("http://localhost:" + port + "/games/info/1");

        controller.waitForLoad("firstLoad");
        controller.clickButton("wishlistButton");
        controller.waitForLoad("processing");

        controller.navigate("http://localhost:" + port + "/wishlist");
    }


}