package frontend.webapp;

import com.api.demo.DemoApplication;
import com.api.demo.grid.dtos.UserDTO;
import com.api.demo.grid.repository.UserRepository;
import com.api.demo.grid.service.GridService;
import com.api.demo.grid.service.UserService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(classes= DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class LoginPageTest {
    WebAppPageObject controller;

    private final int port = 3000;

    @Autowired
    private UserService mUserService;

    @Autowired
    private UserRepository mUserRepository;

    @Autowired
    private GridService mGridService;


    @BeforeEach
    void setUp() {
        controller = new WebAppPageObject(mUserService, mGridService);
        controller.navigate("http://localhost:" + port + "/login-page");
    }

    @AfterEach
    void tearDown() {
        controller.tear();
    }

    @Test
    //Check that when no input is fed, an error is displayed
    void whenNoInput_thenErrorMessage() {
        controller.clickButton("confirm");

        assertTrue(controller.checkVisibility("errorOne"));
        assertTrue(controller.checkText("errorOne","Please fill both fields before submitting!"));

        assertTrue(controller.checkVisibility("errorOneToast"));
        assertTrue(controller.checkText("errorOneToast","Please fill both fields!"));
    }

    @Test
    //Check that when no input is fed, an error is displayed
    void whenInvalidCredentials_thenErrorMessage() {

        controller.writeInput("jonas","username");
        controller.writeInput("jonas","password");

        controller.clickButton("confirm");

        controller.waitForLoad("processing");

        assertTrue(controller.checkVisibility("errorTwo"));
        assertTrue(controller.checkText("errorTwo","Oops, wrong credentials!"));

        assertTrue(controller.checkVisibility("errorTwoToast"));
        assertTrue(controller.checkText("errorTwoToast","Sorry, those credentials seem to be incorrect!"));
    }

    @Test
    @SneakyThrows
    //Check that when a correct input is fed, we get moved to the homepage and are logged in
    // NOTE: This requires the BD to have this user...
    void whenValidCredentials_thenRedirectToHome_andLogin() {

        String username = "admin";
        String password = "admin";

        controller.writeInput(username,"username");
        controller.writeInput(password,"password");

        controller.clickButton("confirm");

        controller.waitForLoad("processing");

        String url = "http://localhost:" + port + "/";
        assertTrue(controller.checkURL(url));
        assertTrue(controller.checkText("logged",username.toUpperCase()));
    }
}