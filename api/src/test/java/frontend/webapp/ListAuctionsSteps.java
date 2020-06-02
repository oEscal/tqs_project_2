package frontend.webapp;

import com.api.demo.DemoApplication;
import com.api.demo.grid.pojos.AuctionPOJO;
import io.cucumber.java.en.Given;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.Date;

@SpringBootTest(classes= DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ListAuctionsSteps {
    private final int port = 3000;
    WebAppPageObject controller;


    @Given("The web application is launched")
    public void startController() {
        controller.login(port);

        controller.waitForLoad("processing");
        controller.navigate("http://localhost:" + port + "/sell-game");
        controller.waitForLoad("firstLoad");
    }

    @Given("There are <auctions> related to game with id {int}")
    public void addAuctionsToGame(String auctions, int id) {
        AuctionPOJO auctionPOJO = new AuctionPOJO("auctioneer",ListAuctionsSteps.randomString(10), 10, new Date());
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
