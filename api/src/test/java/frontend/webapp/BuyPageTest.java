package frontend.webapp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BuyPageTest {
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

        this.createSellListing();
        this.createSellListing();

        this.addToCart(0);
        this.addToCart(1);
    }

    @AfterEach
    void tearDown() {
        controller.tear();
    }

    @Test
    void whenAddGame_showGameOnCart() {
        controller.navigate("http://localhost:" + port + "/cart");
        controller.waitForLoad("firstLoad");
        assertTrue(controller.checkExistance("cartItem0"));
        assertTrue(controller.checkExistance("cartItem1"));
    }

    @Test
    void whenRemoveClickCart_thenRemoveFromCart() {
        controller.navigate("http://localhost:" + port + "/cart");
        controller.waitForLoad("firstLoad");

        controller.clickButton("removeFromCartButton0");
        controller.waitForLoad("firstLoad");

        assertTrue(controller.checkExistance("cartItem0"));
        assertFalse(controller.checkExistance("cartItem1"));
    }

    @Test
    void whenNoGames_dontShowPayment() {
        controller.navigate("http://localhost:" + port + "/cart");
        controller.waitForLoad("firstLoad");

        controller.clickButton("removeFromCartButton0");
        controller.waitForLoad("firstLoad");

        controller.clickButton("removeFromCartButton0");
        controller.waitForLoad("firstLoad");

        assertFalse(controller.checkExistance("paymentStuff"));

    }

    @Test
    void whenBuyWithRegisteredCard_andNoCard_showError() {
        controller.navigate("http://localhost:" + port + "/cart");
        controller.waitForLoad("firstLoad");

        controller.clickButton("buyWithCard");
        controller.waitForLoad("firstLoad");

        assertTrue(controller.checkExistance("errorCardNon"));
        assertTrue(controller.checkText("errorCardNon", "Oops, seems like you haven't registered a credit card to your account!"));
    }

    @Test
    void whenBuyWithNewCard_andNotAllFields_showError() {
        controller.navigate("http://localhost:" + port + "/cart");
        controller.waitForLoad("firstLoad");

        controller.clickButton("buyWithNewCard");
        controller.waitForLoad("firstLoad");

        assertTrue(controller.checkExistance("errorCardAll"));
        assertTrue(controller.checkText("errorCardAll", "Oops, you have to specify all card fields!"));
    }

    @Test
    void whenBuyWithNewCard_andInvalidCVC_showError() {
        controller.navigate("http://localhost:" + port + "/cart");
        controller.waitForLoad("firstLoad");

        controller.writeInput("05/30/2020", "cardExpiration");
        controller.writeInput("1234", "cardNumber");
        controller.writeInput("oof", "cardName");
        controller.writeInput("1234", "cardCVC");


        controller.clickButton("buyWithNewCard");
        controller.waitForLoad("firstLoad");

        assertTrue(controller.checkExistance("errorCardCVC"));
        assertTrue(controller.checkText("errorCardCVC", "Oops, the CVC must contain only numbers and have 3 digits!"));
    }

    @Test
    void whenBuyWithNewCard_andInvalidExpiration_showError() {
        controller.navigate("http://localhost:" + port + "/cart");
        controller.waitForLoad("firstLoad");

        controller.writeInput("oof", "cardExpiration");
        controller.writeInput("1234", "cardNumber");
        controller.writeInput("oof", "cardName");
        controller.writeInput("123", "cardCVC");


        controller.clickButton("buyWithNewCard");
        controller.waitForLoad("firstLoad");

        assertTrue(controller.checkExistance("errorCardExpiration"));
        assertTrue(controller.checkText("errorCardExpiration", "Please use a valid expiration date..."));
    }

    @Test
    void whenBuyWithWallet_andNoFunds_showError() {
        controller.navigate("http://localhost:" + port + "/cart");
        controller.waitForLoad("firstLoad");

        controller.clickButton("buyWithWallet");
        controller.waitForLoad("firstLoad");

        assertTrue(controller.checkExistance("errorFunds"));
        assertTrue(controller.checkText("errorFunds", "Oops, you don't have enough funds in your wallet! Either add more funds or pick another payment method!"));
    }

    @Test
    void whenSuccessfulBuy_goToReceipt() {
        controller.navigate("http://localhost:" + port + "/cart");
        controller.waitForLoad("firstLoad");

        controller.writeInput("05/30/2020", "cardExpiration");
        controller.writeInput("1234", "cardNumber");
        controller.writeInput("oof", "cardName");
        controller.writeInput("123", "cardCVC");


        controller.clickButton("buyWithNewCard");
        controller.waitForLoad("firstLoad");

        String url = "http://localhost:" + port + "/cart/receipt";
        assertTrue(controller.checkURL(url));
    }


    private void createSellListing(){
        controller.navigate("http://localhost:" + port + "/sell-game");
        controller.waitForLoad("firstLoad");

        String randomString = this.randomString(10);

        controller.writeInput(randomString, "key");
        controller.writeInput("10", "price");

        controller.pickSelect(1, "game");
        controller.clickPlatform();

        controller.clickButton("confirm");

        controller.waitForLoad("firstLoad");
        controller.waitSeconds(2);
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