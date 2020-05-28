package frontend.webapp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SellPageTest {
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
        controller.navigate("http://localhost:" + port + "/sell-game");
    }

    @AfterEach
    public void tearDown() {
        controller.tear();
    }

    @Test
    //Check that when the necessary input isn't fed, an error is displayed
    public void whenNoMinimumInput_thenErrorMessage() {
        controller.clickButton("confirm");

        controller.waitForLoad("processing");
        controller.waitSeconds(2);

        assertTrue(controller.checkVisibility("errorMinimum"));
        assertTrue(controller.checkText("errorMinimum", "Oops, you've got to specify, all fields!"));
    }

    @Test
    //Check that when the Price is not a number
    public void whenBadPrice_thenErrorMessage() {
        controller.writeInput("o4f", "price");

        controller.waitSeconds(5);
        controller.clickButton("confirm");

        controller.waitForLoad("processing");
        controller.waitSeconds(2);

        assertTrue(controller.checkExistance("errorPrice"));
        assertTrue(controller.checkText("errorPrice", "You must specify a valid selling price!"));
    }

    @Test
    //Check that when an invalid expiration date is given an error is displayed
    public void whenInvalidExpirationDate_thenErrorMessage() {
        controller.writeInput("oof", "cardExpiration");

        controller.clickButton("confirm");

        controller.waitForLoad("processing");
        controller.waitSeconds(1);

        assertTrue(controller.checkExistance("errorCardExpiration"));
        assertTrue(controller.checkText("errorCardExpiration", "Please use a valid expiration date..."));
    }

    //Test when everything goes right
    @Test
    public void whenValidInput_thenNoError() {
        String randomString = this.randomString(10);

        controller.writeInput(randomString, "key");
        controller.writeInput("10", "price");

        controller.pickSelect(1, "game");
        controller.clickPlatform();

        controller.clickButton("confirm");

        controller.waitForLoad("processing");
        controller.waitSeconds(5);

        assertFalse(controller.checkExistance("errorCardExpiration"));
        assertFalse(controller.checkExistance("errorMinimum"));
        assertFalse(controller.checkExistance("errorToast"));

        String url = "http://localhost:" + port + "/user/admin";
        assertTrue(controller.checkURL(url));
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