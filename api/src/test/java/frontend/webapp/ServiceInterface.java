package frontend.webapp;

import com.api.demo.grid.dtos.UserDTO;
import com.api.demo.grid.exception.ExceptionDetails;
import com.api.demo.grid.models.Game;
import com.api.demo.grid.pojos.*;
import com.api.demo.grid.service.AuctionService;
import com.api.demo.grid.service.GridService;
import com.api.demo.grid.service.UserService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ServiceInterface {


    public static void addUser(UserService userService) throws ParseException, ExceptionDetails {
        UserDTO mSimpleUserDTO = new UserDTO("admin", "admin", "ola@adeus.com", "hm", "admin",
                new SimpleDateFormat("dd/MM/yyyy").parse("10/10/2019"));
        userService.saveUser(mSimpleUserDTO);
        mSimpleUserDTO = new UserDTO("oof", "oof", "oof@adeus.com", "hmm", "oof",
                new SimpleDateFormat("dd/MM/yyyy").parse("10/10/2019"));
        userService.saveUser(mSimpleUserDTO);
    }

    public static void addGames(GridService gridService, int size) {
        for (int id = 0; id < size; id++) {
            GamePOJO gamePOJO = new GamePOJO("game" + id, "", null, null, null,
                    null, "");

            GameGenrePOJO gameGenrePOJO = new GameGenrePOJO("genre" + id, "");
            gridService.saveGameGenre(gameGenrePOJO);

            PublisherPOJO publisherPOJO = new PublisherPOJO("publisher" + id, "");
            gridService.savePublisher(publisherPOJO);

            DeveloperPOJO developerPOJO = new DeveloperPOJO("developer" + id);
            gridService.saveDeveloper(developerPOJO);

            gamePOJO.setDevelopers(new HashSet<>(Arrays.asList("developer" + id)));
            gamePOJO.setGameGenres(new HashSet<>(Arrays.asList("genre" + id)));
            gamePOJO.setPublisher("publisher" + id);
            gridService.saveGame(gamePOJO);
        }
    }

    public static List<String> addGameKeys(GridService gridService, int gameID, int size) {
        List<String> keys = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            String key = ServiceInterface.randomString(50);
            String retailer = ServiceInterface.randomString(10);
            String platform = ServiceInterface.randomString(10);
            GameKeyPOJO gameKeyPOJO = new GameKeyPOJO(key, gameID, retailer, platform);
            keys.add(key);
            gridService.saveGameKey(gameKeyPOJO);
        }

        return keys;
    }

    public static void addAuctions(AuctionService auctionService, List<String> keys, String auctioneer) throws ParseException, ExceptionDetails {
        for (String key : keys) {
            Date endDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/10/2020");
            int price = 10;
            AuctionPOJO auctionPOJO = new AuctionPOJO(auctioneer, key, price, endDate);
            auctionService.addAuction(auctionPOJO);
        }
    }


    private static String randomString(int n) {

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
                    = (int) (AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }


}
