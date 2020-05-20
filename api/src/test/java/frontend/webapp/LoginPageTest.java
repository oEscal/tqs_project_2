package frontend.webapp;

import com.api.demo.DemoApplication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

/*
@SpringBootTest(classes= DemoApplication.class)
public class LoginPageTest {
    WebAppPageObject controller;

    private final int port = 3000;

    @BeforeEach
    public void setUp() {
        controller = new WebAppPageObject();
        controller.navigate("http://localhost:" + port + "/login-page");
    }

    @AfterEach
    public void tearDown() {
        controller.tear();
    }

    @Test
    //Check that when no input is fed, an error is displayed
    public void whenNoInput_thenErrorMessage() {
        controller.clickButton("confirm");

        assertTrue(controller.checkVisibility("errorOne"));
        assertTrue(controller.checkText("errorOne","Please fill both fields before submitting!"));

        assertTrue(controller.checkVisibility("errorOneToast"));
        assertTrue(controller.checkText("errorOneToast","Please fill both fields!"));
    }

    @Test
    //Check that when no input is fed, an error is displayed
    public void whenInvalidCredentials_thenErrorMessage() {
        controller.writeInput("jonas","username");
        controller.writeInput("jonas","password");

        controller.clickButton("confirm");

        controller.waitForLoad("processing");

        assertTrue(controller.checkVisibility("errorTwo"));
        assertTrue(controller.checkText("errorTwo","Oops, wrong credentials!"));

        assertTrue(controller.checkVisibility("errorTwoToast"));
        assertTrue(controller.checkText("errorTwoToast","Sorry, those credentials seem to be incorrect!"));
    }
}

 */