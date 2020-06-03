package frontend.webapp;

import com.api.demo.DemoApplication;
import com.api.demo.grid.service.GridService;
import com.api.demo.grid.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(classes= DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class GamePageTest {
    WebAppPageObject controller;

    private final int port = 3000;

    @Autowired
    private UserService mUserService;

    @Autowired
    private GridService mGridService;


    @BeforeEach
    void setUp() {
        controller = new WebAppPageObject(mUserService, mGridService);
        //Login
        controller.login(port);
        controller.waitForLoad("processing");
        controller.navigate("http://localhost:" + port + "/games/info/1");
        controller.waitForLoad("firstLoad");

    }

    @AfterEach
    void tearDown() {
        controller.tear();
    }

    @Test
    void whenLoadingGame_andNoError_showGameMinimizedInfo() {
        assertTrue(controller.checkText("gameNameHeader", "game0"));
        assertTrue(controller.checkText("descriptionHeader", ""));
    }

    @Test
    void whenErrorLoadingGame_redirectGamesPage_andShowError() {
        controller.navigate("http://localhost:" + port + "/games/info/0");
        controller.waitForLoad("firstLoad");
        controller.waitSeconds(2);

        String url = "http://localhost:" + port + "/games";
        assertTrue(controller.checkURL(url));
        assertTrue(controller.checkExistance("errorToast"));
        assertTrue(controller.checkText("errorToast", "Sorry, an unexpected error has occurred while loading that game's information..."));
    }

    @Test
    void whenLoadingGame_andNoError_showGameInfo() {
        assertTrue(controller.checkText("fullName", "game0"));
        assertTrue(controller.checkText("fullDescription", ""));
        assertTrue(controller.checkText("fullPlatforms", "This game isn't currently available on any platforms"));
        assertTrue(controller.checkText("fullDevelopers", "developer0"));
        assertTrue(controller.checkText("fullPublisher", "publisher0"));
        assertTrue(controller.checkText("fullGenres", "genre0"));
    }

    @Test
    void whenLoggedIn_showAddToWishlist() {
        assertTrue(controller.checkExistance("wishlistButton"));
    }

    @Test
    void whenLoggedIn_onClickAddToWishlist_AddToWishlist() {
        controller.clickButton("wishlistButton");

        controller.waitForLoad("firstLoad");
        controller.waitSeconds(2);

        assertTrue(controller.checkExistance("successWishlistAdd"));
        assertTrue(controller.checkText("successWishlistAdd", "Game successfully added to your wishlist!"));
    }

    /*
    @Test
    void whenNotLoggedIn_dontShowWishlist() {
        controller.navigate("http://localhost:" + port + "/login");
        controller.waitForLoad("firstLoad");

        controller.waitSeconds(3);

        controller.navigate("http://localhost:" + port + "/games/info/1");
        controller.waitForLoad("firstLoad");

        assertFalse(controller.checkEnabled("wishlistButton"));
    }
    */

    @Test
    void whenSaleOneSaleIsAvailable_showBestSale() {
        this.createSellListing();
        controller.waitForLoad("firstLoad");

        controller.navigate("http://localhost:" + port + "/games/info/1");
        controller.waitForLoad("firstLoad");

        assertTrue(controller.checkText("bestOfferSign", "BEST OFFER"));
        assertTrue(controller.checkText("bestOfferPrice", "1€"));
    }

    @Test
    void whenNoSale_dontShowBestSale() {
        assertFalse(controller.checkExistance("bestOfferSign"));
    }

    @Test
    void whenNoSale_showNoSaleMessage() {
        assertTrue(controller.checkExistance("noSell"));
        assertTrue(controller.checkText("noSell", "It seems like no one's selling this game at the moment :("));
    }

    @Test
    void whenNoReview_showNoReviewMessage() {
        assertTrue(controller.checkExistance("noReviews"));
        assertTrue(controller.checkText("noReviews", "It seems like no one's reviewed this game yet :("));
    }

    @Test
    void whenAddToCart_changeButton_andAddToCart() {
        this.createSellListing();
        controller.waitForLoad("firstLoad");

        controller.navigate("http://localhost:" + port + "/games/info/1");
        controller.waitForLoad("firstLoad");

        controller.clickButton("addToCartButtonBest");

        controller.waitForLoad("firstLoad");
        controller.waitSeconds(2);

        assertTrue(controller.checkExistance("addToCartToast"));
        assertTrue(controller.checkText("addToCartToast", "Sale successfully added to shopping cart!"));
        assertTrue(controller.checkExistance("removeFromCartButtonBest"));
    }

    @Test
    void whenRemoveFromCart_changeButton() {
        this.createSellListing();
        controller.waitForLoad("firstLoad");

        controller.navigate("http://localhost:" + port + "/games/info/1");
        controller.waitForLoad("firstLoad");

        controller.clickButton("addToCartButtonBest");

        controller.waitForLoad("firstLoad");
        controller.waitSeconds(2);

        controller.clickButton("removeFromCartButtonBest");

        controller.waitForLoad("firstLoad");
        controller.waitSeconds(2);

        assertTrue(controller.checkExistance("removeFromCartToast"));
        assertTrue(controller.checkText("removeFromCartToast", "Sale successfully removed to shopping cart!"));
        assertTrue(controller.checkExistance("addToCartButtonBest"));
    }

    /*
    @Test
    void whenNotLoggedIn_deactivateAddAndRemoveButtons() {
        this.createSellListing();
        controller.waitForLoad("firstLoad");

        controller.navigate("http://localhost:" + port + "/games/info/1");
        controller.waitForLoad("firstLoad");

        controller.navigate("http://localhost:" + port + "/login");
        controller.waitForLoad("firstLoad");

        controller.waitSeconds(3);

        controller.navigate("http://localhost:" + port + "/games/info/1");
        controller.waitForLoad("firstLoad");

        assertFalse(controller.checkEnabled("addToCartButtonBest"));
        assertFalse(controller.checkEnabled("addToCartButton0"));

    }
    */

    private void createSellListing(){
        controller.navigate("http://localhost:" + port + "/sell-game");
        controller.waitForLoad("firstLoad");

        String randomString = this.randomString(10);

        controller.writeInput(randomString, "key");
        controller.writeInput("1", "price");

        controller.pickSelect(0, "game");
        controller.clickPlatform();

        controller.clickButton("confirm");

        controller.waitForLoad("firstLoad");
        controller.waitSeconds(2);
    }

    static String randomString(int n)
    {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }
}