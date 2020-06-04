package frontend.webapp;

import com.api.demo.DemoApplication;
import com.api.demo.grid.exception.ExceptionDetails;
import com.api.demo.grid.service.AuctionService;
import com.api.demo.grid.service.GridService;
import com.api.demo.grid.service.UserService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.util.List;

@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureTestDatabase
public class ListAuctionsSteps {
    private final int port = 3000;
    WebAppPageObject controller;


    @Autowired
    private UserService mUserService;

    @Autowired
    private GridService mGridService;

    @Autowired
    private AuctionService mAuctionService;

    private static int id = 0;

    @Given("The web application is launched")
    public void startController() {

        controller = new WebAppPageObject();

        controller.login(port);

        controller.waitForLoad("processing");
        controller.navigate("http://localhost:" + port + "/games/info/" + ListAuctionsSteps.id);
        controller.waitForLoad("firstLoad");
    }

    @Given("There are {int} auctions related to game {int}")
    public void addAuctionsToGame(int auctions_size, int id) throws ParseException, ExceptionDetails {
        ListAuctionsSteps.id +=1;

        if (ListAuctionsSteps.id == 1){
            ServiceInterface.addUser(mUserService);
            ServiceInterface.addGames(mGridService, 3);
            List<String> keys = ServiceInterface.addGameKeys(mGridService, id, auctions_size);
            ServiceInterface.addAuctions(mAuctionService, keys, "admin");
        }
    }

    @Then("Related auctions should be {int}")
    public void checkAuctionsListCounter(int auctions_size) {
        assertTrue(controller.checkText("auctionsCounter", auctions_size + " Auctions"));
    }

    @Then("Related auctions table should be displayed")
    public void checkAuctionsListTableExists() {
        assertTrue(controller.checkExistance("auctionsTable"));
    }

    @Then("Related auctions table should not be displayed")
    public void checkAuctionsListTableNotExists() {
        assertFalse(controller.checkExistance("auctionsTable"));
    }

    @Then("Extra message should appear")
    public void checkNoAuctionMessage() {
        assertTrue(controller.checkText("emptyAuctionsMessage","It seems like no auctions exists related to this game at the moment :("));
    }

}
