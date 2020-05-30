package frontend.webapp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SellPageTest {
    WebAppPageObject controller;

    private final int port = 3000;

    @BeforeEach
    void setUp() {
        controller = new WebAppPageObject();
        //Login
        controller.login(port);
        controller.waitForLoad("processing");
        controller.navigate("http://localhost:" + port + "/sell-game");
        controller.waitForLoad("firstLoad");
    }

    @AfterEach
    void tearDown() {
        controller.tear();
    }

    @Test
    //Check that when the necessary input isn't fed, an error is displayed
    void whenNoMinimumInput_thenErrorMessage() {
        controller.clickButton("confirm");

        controller.waitForLoad("firstLoad");
        controller.waitSeconds(2);

        assertTrue(controller.checkVisibility("errorMinimum"));
        assertTrue(controller.checkText("errorMinimum", "Oops, you've got to specify, all fields!"));
    }

    @Test
    //Check that when the Price is not a number
    void whenBadPrice_thenErrorMessage() {
        controller.writeInput("o4f", "price");

        controller.waitSeconds(5);
        controller.clickButton("confirm");

        controller.waitForLoad("firstLoad");
        controller.waitSeconds(2);

        assertTrue(controller.checkExistance("errorPrice"));
        assertTrue(controller.checkText("errorPrice", "You must specify a valid selling price!"));
    }


    //Test when everything goes right
    @Test
    void whenValidInput_thenNoError() {
        String randomString = this.randomString(10);

        controller.writeInput(randomString, "key");
        controller.writeInput("10", "price");

        controller.pickSelect(1, "game");
        controller.clickPlatform();

        controller.clickButton("confirm");

        controller.waitForLoad("firstLoad");
        controller.waitSeconds(2);

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