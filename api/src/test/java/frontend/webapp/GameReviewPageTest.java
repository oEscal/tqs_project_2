package frontend.webapp;

import com.api.demo.DemoApplication;
import com.api.demo.grid.service.GridService;
import com.api.demo.grid.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(classes= DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class GameReviewPageTest {
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

        this.createSellListing();
        this.addToCart(0);
        this.confirmBuy();

        controller.navigate("http://localhost:" + port + "/user/admin");
        controller.waitForLoad("firstLoad");

        controller.clickButton("3");
        controller.clickButton("reviewGameButton2");
    }

    @AfterEach
    void tearDown() {
        controller.tear();
    }

    @Test
    void whenCommentOver250Chars_showError() {
        controller.writeInput("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "review");
        controller.clickButton("confirmReviewButton");

        assertTrue(controller.checkExistance("errorMaximum"));
        assertTrue(controller.checkText("errorMaximum", "Sorry, your review can have a maximum of 250 characters!"));
    }

    @Test
    void whenNoScore_showError() {
        controller.clickButton("confirmReviewButton");

        assertTrue(controller.checkExistance("errorStars"));
        assertTrue(controller.checkText("errorStars", "Please select a valid score!"));
    }

    @Test
    void whenReviewingGame_Twice_showError() {
        controller.clickStars();
        controller.clickButton("confirmReviewButton");

        controller.waitForLoad("firstLoad");
        controller.waitSeconds(2);

        controller.navigate("http://localhost:" + port + "/user/admin");
        controller.waitForLoad("firstLoad");

        controller.clickButton("3");
        controller.clickButton("reviewGameButton2");

        controller.clickStars();
        controller.clickButton("confirmReviewButton");

        controller.waitForLoad("firstLoad");
        controller.waitSeconds(2);

        assertTrue(controller.checkExistance("errorBlowjob"));
        assertTrue(controller.checkText("errorBlowjob", "Oops, seems like you've already reviewed this game!"));
    }

    @Test
    void whenValidScore_goBackToProfile() {
        controller.clickStars();
        controller.clickButton("confirmReviewButton");

        controller.waitForLoad("firstLoad");
        controller.waitSeconds(2);

        String url = "http://localhost:" + port + "/user/admin";
        assertTrue(controller.checkURL(url));
    }



    private void addToCart(int i){
        controller.navigate("http://localhost:" + port + "/games/info/2");

        controller.waitForLoad("firstLoad");

        controller.scrollToElement("addToCartButton" + i);
        if(controller.checkExistance("addToCartButton"+i)){
            controller.clickButton("addToCartButton" + i);
        }
        controller.waitForLoad("processing");
    }

    private void createSellListing(){
        controller.navigate("http://localhost:" + port + "/sell-game");
        controller.waitForLoad("firstLoad");

        String randomString = this.randomString(10);

        controller.writeInput(randomString, "key");
        controller.writeInput("1", "price");

        controller.pickSelect(1, "game");
        controller.clickPlatform();

        controller.clickButton("confirm");

        controller.waitForLoad("firstLoad");
        controller.waitSeconds(2);
    }

    private void confirmBuy(){
        controller.navigate("http://localhost:" + port + "/cart");
        controller.waitForLoad("firstLoad");

        controller.writeInput("05/30/2020", "cardExpiration");
        controller.writeInput("1234", "cardNumber");
        controller.writeInput("oof", "cardName");
        controller.writeInput("123", "cardCVC");


        controller.clickButton("buyWithNewCard");
        controller.waitForLoad("firstLoad");
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