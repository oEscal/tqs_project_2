package frontend.webapp;

import com.api.demo.grid.service.GridService;
import com.api.demo.grid.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SignUpPageTest {
    WebAppPageObject controller;

    private final int port = 3000;

    @Autowired
    private UserService mUserService;

    @Autowired
    private GridService mGridService;


    @BeforeEach
    void setUp() {
        controller = new WebAppPageObject(mUserService, mGridService);
        controller.navigate("http://localhost:" + port + "/signup-page");
    }

    @AfterEach
    void tearDown() {
        controller.tear();
    }

    @Test
    //Check that when the necessary input isn't fed, an error is displayed
    void whenNoMinimumInput_thenErrorMessage() {
        controller.clickButton("confirm");

        controller.waitForLoad("processing");
        controller.waitSeconds(2);

        assertTrue(controller.checkVisibility("errorMinimum"));
        assertTrue(controller.checkText("errorMinimum", "Oops, you've got to specify, at least, a name, username, birthday, email, password and country!"));
    }

    @Test
    //Check that when an invalid birthdate is given an error is displayed
    void whenInvalidBirthdate_thenErrorMessage() {
        controller.writeInput("oof", "birthday");

        controller.clickButton("confirm");

        controller.waitForLoad("processing");
        controller.waitSeconds(2);

        assertTrue(controller.checkExistance("errorBirthday"));
        assertTrue(controller.checkText("errorBirthday", "Please use a valid birthday..."));
    }

    @Test
    //Check that when an invalid email an error is shown
    void whenInvalidEmail_thenErrorMessage() {
        controller.writeInput("oof", "email");

        controller.waitSeconds(5);
        controller.clickButton("confirm");

        controller.waitForLoad("processing");
        controller.waitSeconds(2);


        assertTrue(controller.checkExistance("errorEmail"));
        assertTrue(controller.checkText("errorEmail", "Please use a valid email..."));

        //////////////
        controller.scrollToTop();

        controller.writeInput("oof@", "email");

        controller.waitSeconds(5);
        controller.clickButton("confirm");

        controller.waitForLoad("processing");
        controller.waitSeconds(2);


        assertTrue(controller.checkExistance("errorEmail"));
        assertTrue(controller.checkText("errorEmail", "Please use a valid email..."));

        //////////////
        controller.scrollToTop();

        controller.writeInput("oof@ass", "email");

        controller.waitSeconds(5);
        controller.clickButton("confirm");

        controller.waitForLoad("processing");
        controller.waitSeconds(2);


        assertTrue(controller.checkExistance("errorEmail"));
        assertTrue(controller.checkText("errorEmail", "Please use a valid email..."));


        //////////////
        controller.scrollToTop();

        controller.writeInput("oof@ass.", "email");

        controller.waitSeconds(5);
        controller.clickButton("confirm");

        controller.waitForLoad("processing");
        controller.waitSeconds(2);


        assertTrue(controller.checkExistance("errorEmail"));
        assertTrue(controller.checkText("errorEmail", "Please use a valid email..."));
    }

    @Test
    //Check that when the passwords don't match an error is shown
    void whenDifferentPasswords_thenErrorMessage() {
        controller.writeInput("oof", "pass");
        controller.writeInput("oops", "passConfirm");

        controller.clickButton("confirm");

        controller.waitForLoad("processing");
        controller.waitSeconds(2);

        assertTrue(controller.checkExistance("errorPass"));
        assertTrue(controller.checkText("errorPass", "Oops, your passwords don't match!"));
    }

    @Test
    //Check that when filling one of the card fields all fields are filled
    void whenNotFillingAllCardFields_thenErrorMessage() {
        controller.writeInput("55555555555555", "cardNumber");

        controller.waitSeconds(5);
        controller.clickButton("confirm");

        controller.waitForLoad("processing");
        controller.waitSeconds(2);

        assertTrue(controller.checkExistance("errorCardAll"));
        assertTrue(controller.checkText("errorCardAll","Oops, if you want to add your payment info, you've got to specify all four card information fields!"));

        //////////
        controller.writeInput("Jonas Pistolas","cardName");

        controller.waitSeconds(5);
        controller.clickButton("confirm");

        controller.waitForLoad("processing");
        controller.waitSeconds(2);

        assertTrue(controller.checkExistance("errorCardAll"));
        assertTrue(controller.checkText("errorCardAll","Oops, if you want to add your payment info, you've got to specify all four card information fields!"));

        //////////
        controller.writeInput("420","cardCVC");

        controller.waitSeconds(5);
        controller.clickButton("confirm");

        controller.waitForLoad("processing");
        controller.waitSeconds(2);

        assertTrue(controller.checkExistance("errorCardAll"));
        assertTrue(controller.checkText("errorCardAll","Oops, if you want to add your payment info, you've got to specify all four card information fields!"));

        //////////
        controller.writeInput("05/29/2069","cardExpiration");

        controller.waitSeconds(5);
        controller.clickButton("confirm");

        controller.waitForLoad("processing");
        controller.waitSeconds(2);


        assertFalse(controller.checkExistance("errorCardAll"));
    }

    @Test
    //Check that when the CVC is less than 3 characters an error is thrown and when its non digits an error is also thrown
    void whenBadCVC_thenErrorMessage() {
        controller.writeInput("o4f", "cardCVC");

        controller.waitSeconds(5);
        controller.clickButton("confirm");

        controller.waitForLoad("processing");
        controller.waitSeconds(2);

        assertTrue(controller.checkExistance("errorCardCVC"));
        assertTrue(controller.checkText("errorCardCVC", "Oops, the CVC must contain only numbers and have 3 digits!"));

        //////////
        controller.writeInput("of", "cardCVC");

        controller.waitSeconds(5);
        controller.clickButton("confirm");

        controller.waitForLoad("processing");
        controller.waitSeconds(2);

        assertTrue(controller.checkExistance("errorCardCVC"));
        assertTrue(controller.checkText("errorCardCVC", "Oops, the CVC must contain only numbers and have 3 digits!"));
    }


    @Test
    void whenCardNumberHasNumbersNLetters_thenErrorMessage() {
        controller.writeInput("o4f", "cardNumber");

        controller.waitSeconds(5);
        controller.clickButton("confirm");

        controller.waitForLoad("processing");
        controller.waitSeconds(2);

        assertTrue(controller.checkExistance("errorCardNumber"));
        assertTrue(controller.checkText("errorCardNumber", "Oops, the credit card number must contain only numbers and have at least 9 digits!"));
    }

    @Test
    void whenCardNumberHasLetters_thenErrorMessage() {
        //////////
        controller.writeInput("of", "cardNumber");

        controller.waitSeconds(5);
        controller.clickButton("confirm");

        controller.waitForLoad("processing");
        controller.waitSeconds(2);

        assertTrue(controller.checkExistance("errorCardNumber"));
        assertTrue(controller.checkText("errorCardNumber", "Oops, the credit card number must contain only numbers and have at least 9 digits!"));
    }

    @Test
    void whenCardNumberHasBadLength_thenErrorMessage() {
        //////////
        controller.writeInput("123", "cardNumber");

        controller.waitSeconds(5);
        controller.clickButton("confirm");

        controller.waitForLoad("processing");
        controller.waitSeconds(2);

        assertTrue(controller.checkExistance("errorCardNumber"));
        assertTrue(controller.checkText("errorCardNumber", "Oops, the credit card number must contain only numbers and have at least 9 digits!"));

        //////////
    }

    @Test
    void whenGoodCardNumber_thenNoErrorMessage() {
        controller.writeInput("1234567890", "cardNumber");

        controller.waitSeconds(5);
        controller.clickButton("confirm");

        controller.waitForLoad("processing");
        controller.waitSeconds(2);

        assertFalse(controller.checkExistance("errorCardNumber"));

    }

    @Test
    //Check that when an invalid expiration date is given an error is displayed
    void whenInvalidExpirationDate_thenErrorMessage() {
        controller.writeInput("oof", "cardExpiration");

        controller.clickButton("confirm");

        controller.waitForLoad("processing");
        controller.waitSeconds(1);

        assertTrue(controller.checkExistance("errorCardExpiration"));
        assertTrue(controller.checkText("errorCardExpiration", "Please use a valid expiration date..."));
    }

    @Test
    //Needs the user ola adeus to exist in the DB
    void whenUserAlreadyExists_thenError() {
        controller.writeInput("admin", "name");
        controller.writeInput("admin", "username");
        controller.pickSelect(52, "country");
        controller.writeInput("04/04/1999", "birthday");
        controller.writeInput("ola@oof.com", "email");
        controller.writeInput("adeus", "pass");
        controller.writeInput("adeus", "passConfirm");


        controller.clickButton("confirm");

        controller.waitForLoad("processing");
        controller.waitSeconds(2);

        controller.waitSeconds(1);
        assertTrue(controller.checkExistance("errorTwoToast"));
        assertTrue(controller.checkText("errorTwoToast", "There is already a user with that username"));
    }

    //Test when everything goes right
    @Test
    void whenValidInput_thenRedirectToHome_andLogin() {
        String randomString = this.randomString(10);

        controller.writeInput(randomString, "name");
        controller.writeInput(randomString, "username");
        controller.pickSelect(52, "country");
        controller.writeInput("04/04/1999", "birthday");
        controller.writeInput(randomString+"@"+randomString+".com", "email");
        controller.writeInput(randomString, "pass");
        controller.writeInput(randomString, "passConfirm");


        controller.clickButton("confirm");

        controller.waitForLoad("processing");
        controller.waitSeconds(2);

        String url = "http://localhost:" + port + "/";
        assertTrue(controller.checkURL(url));
        assertTrue(controller.checkText("logged",randomString.toUpperCase()));
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