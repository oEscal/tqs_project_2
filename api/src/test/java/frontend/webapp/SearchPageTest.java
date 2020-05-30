package frontend.webapp;

import com.api.demo.DemoApplication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;


class SearchPageTest {
    WebAppPageObject controller;

    private final int port = 3000;

    @BeforeEach
    void setUp() {
        controller = new WebAppPageObject();
        controller.navigate("http://localhost:" + port + "/games");
    }

    @AfterEach
    void tearDown() {
        controller.tear();
    }

    @Test
    void whenClickingGame_goToGamePage() {
        controller.waitForLoad("firstLoad");
        controller.waitSeconds(2);

        controller.clickCard();

        controller.waitForLoad("processing");
        controller.waitSeconds(2);

        String url = "http://localhost:" + port + "/games/info/1";
        assertTrue(controller.checkURL(url));
    }


    @Test
    void whenInvalidPriceSearch_showError() {
        controller.waitForLoad("firstLoad");
        controller.scrollToTop();
        controller.writeInput("oof", "priceFrom");

        controller.scrollToElement("confirm");
        controller.clickButton("confirm");

        controller.waitForLoad("processing");
        controller.waitSeconds(2);

        controller.scrollToTop();
        assertTrue(controller.checkExistance("errorRangeNumber"));
        assertTrue(controller.checkText("errorRangeNumber", "Oops, the specified price ranges must be numbers!"));
    }

    @Test
    void whenSearchingNothing_sameAsAll() {
        String noProducts = controller.getText("numberOfProducts");

        controller.scrollToElement("confirm");
        controller.clickButton("confirm");

        controller.waitForLoad("processing");
        controller.waitSeconds(2);

        controller.scrollToTop();
        assertTrue(controller.checkText("numberOfProducts", noProducts));
    }
}