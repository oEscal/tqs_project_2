package frontend.webapp;

import com.api.demo.DemoApplication;
import com.api.demo.grid.exception.ExceptionDetails;
import com.api.demo.grid.service.GridService;
import com.api.demo.grid.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(classes= DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class SearchPageTest {
    WebAppPageObject controller;

    private final int port = 3000;

    @Autowired
    private UserService mUserService;

    @Autowired
    private GridService mGridService;


    @BeforeEach
    void setUp() throws ParseException, ExceptionDetails {
        controller = new WebAppPageObject(mUserService, mGridService);
        controller.navigate("http://localhost:" + port + "/games");
    }

    @AfterEach
    void tearDown() {
        controller.tear();
    }

    @Test
    void whenClickingGame_goToGamePage() {
        controller.login(port);
        controller.waitForLoad("processing");

        controller.navigate("http://localhost:" + port + "/games");
        controller.waitForLoad("firstLoad");

        controller.clickCard();

        controller.waitForLoad("firstLoad");
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
        controller.waitForLoad("firstLoad");
        controller.waitSeconds(10);

        controller.scrollToTop();
        assertTrue(controller.checkText("numberOfProducts", noProducts));
    }
}