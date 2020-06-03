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

import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(classes= DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class WalletPageTest {
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
        controller.navigate("http://localhost:" + port + "/wallet");
        controller.waitForLoad("firstLoad");
    }

    @AfterEach
    void tearDown() {
        controller.tear();
    }

    @Test
    void whenNoFundsSpecified_showError() {
        controller.writeInput("05/30/2020", "cardExpiration");
        controller.writeInput("1234", "cardNumber");
        controller.writeInput("oof", "cardName");
        controller.writeInput("123", "cardCVC");
        controller.writeInput("", "fundsAmount");

        controller.clickButton("buyWithNewCard");

        controller.waitForLoad("firstLoad");

        assertTrue(controller.checkExistance("errorFundsOne"));
        assertTrue(controller.checkText("errorFundsOne", "Oops, you have to specify how much money you want to add to your wallet!"));
    }

    @Test
    void whenBuyingWithCard_andNoCardRegistered() {
        controller.clickButton("buyWithCard");

        controller.waitForLoad("firstLoad");

        assertTrue(controller.checkExistance("errorCardNon"));
        assertTrue(controller.checkText("errorCardNon", "Oops, seems like you haven't registered a credit card to your account!"));
    }

    @Test
    void whenNegativeNumberSpecified_showError() {
        controller.writeInput("05/30/2020", "cardExpiration");
        controller.writeInput("1234", "cardNumber");
        controller.writeInput("oof", "cardName");
        controller.writeInput("123", "cardCVC");
        controller.writeInput("", "fundsAmount");

        controller.writeInput("-5", "fundsAmount");

        controller.clickButton("buyWithNewCard");

        controller.waitForLoad("firstLoad");

        assertTrue(controller.checkExistance("errorFundsTwo"));
        assertTrue(controller.checkText("errorFundsTwo", "Oops, you have to specify a valid number bigger than 0 and with, at most, 2 decimal houses!"));
    }

    @Test
    void whenInvalidNumberSpecified_showError() {
        controller.writeInput("05/30/2020", "cardExpiration");
        controller.writeInput("1234", "cardNumber");
        controller.writeInput("oof", "cardName");
        controller.writeInput("123", "cardCVC");

        controller.writeInput("4.2.1", "fundsAmount");

        controller.clickButton("buyWithNewCard");

        controller.waitForLoad("firstLoad");

        assertTrue(controller.checkExistance("errorFundsTwo"));
        assertTrue(controller.checkText("errorFundsTwo", "Oops, you have to specify a valid number bigger than 0 and with, at most, 2 decimal houses!"));
    }

    @Test
    void whenLetterInNumber_showError() {
        controller.writeInput("05/30/2020", "cardExpiration");
        controller.writeInput("1234", "cardNumber");
        controller.writeInput("oof", "cardName");
        controller.writeInput("123", "cardCVC");

        controller.writeInput("oof69oof", "fundsAmount");

        controller.clickButton("buyWithNewCard");

        controller.waitForLoad("firstLoad");

        assertTrue(controller.checkExistance("errorFundsTwo"));
        assertTrue(controller.checkText("errorFundsTwo", "Oops, you have to specify a valid number bigger than 0 and with, at most, 2 decimal houses!"));
    }

    @Test
    void whenValidNumber_andCardDefined_updateFunds() {
        controller.writeInput("05/30/2020", "cardExpiration");
        controller.writeInput("1234", "cardNumber");
        controller.writeInput("oof", "cardName");
        controller.writeInput("123", "cardCVC");

        controller.writeInput("5", "fundsAmount");

        controller.clickButton("buyWithNewCard");

        controller.waitForLoad("firstLoad");

        controller.waitSeconds(6);

        controller.waitForLoad("firstLoad");

        assertTrue(controller.checkText("totalFunds", "5€"));

    }

}