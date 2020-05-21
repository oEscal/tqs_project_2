package frontend.webapp;

import com.api.demo.DemoApplication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SignUpPageTest {
    /*
    WebAppPageObject controller;

    private final int port = 3000;

    @BeforeEach
    public void setUp() {
        controller = new WebAppPageObject();
        controller.navigate("http://localhost:" + port + "/signup-page");
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

        assertTrue(controller.checkVisibility("errorMinimum"));
        assertTrue(controller.checkText("errorMinimum","Oops, you've got to specify, at least, a name, username, birthday, email and password!"));
    }

    @Test
    //Check that when an invalid birthdate is given an error is displayed
    public void whenInvalidBirthdate_thenErrorMessage() {
        controller.writeInput("oof","birthday");

        controller.clickButton("confirm");

        controller.waitForLoad("processing");

        assertTrue(controller.checkVisibility("errorBirthday"));
        assertTrue(controller.checkText("errorBirthday","Please use a valid birthday..."));
    }

    @Test
    //Check that when an invalid email an error is shown
    public void whenInvalidEmail_thenErrorMessage() {
        controller.writeInput("oof","email");

        controller.clickButton("confirm");

        controller.waitForLoad("processing");

        assertTrue(controller.checkVisibility("errorEmail"));
        assertTrue(controller.checkText("errorEmail","Please use a valid email..."));

        //////////////
        controller.scrollToTop();

        controller.writeInput("oof@","email");

        controller.clickButton("confirm");

        controller.waitForLoad("processing");

        assertTrue(controller.checkVisibility("errorEmail"));
        assertTrue(controller.checkText("errorEmail","Please use a valid email..."));

        //////////////
        controller.scrollToTop();

        controller.writeInput("oof@ass","email");

        controller.clickButton("confirm");

        controller.waitForLoad("processing");

        assertTrue(controller.checkVisibility("errorEmail"));
        assertTrue(controller.checkText("errorEmail","Please use a valid email..."));

        //////////////
        controller.scrollToTop();

        controller.writeInput("oof@ass.","email");

        controller.clickButton("confirm");

        controller.waitForLoad("processing");

        assertTrue(controller.checkVisibility("errorEmail"));
        assertTrue(controller.checkText("errorEmail","Please use a valid email..."));
    }

    @Test
    //Check that when the passwords don't match an error is shown
    public void whenDIfferentPasswords_thenErrorMessage() {
        controller.writeInput("oof","pass");
        controller.writeInput("oops","passConfirm");

        controller.clickButton("confirm");

        controller.waitForLoad("processing");

        assertTrue(controller.checkVisibility("errorPass"));
        assertTrue(controller.checkText("errorPass","Oops, your passwords don't match!"));
    }

    // TODO: Test Not all Card Fields ; Test Card CVC Length ; Test Card CVC Non Digits ; Test Success ; Test user already exists
    */
}