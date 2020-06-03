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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(classes= DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ProfilePageTest {
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


    }

    @AfterEach
    void tearDown() {
        controller.tear();
    }

    @Test
    void whenPrivateProfile_showAllInfo() {
        controller.navigate("http://localhost:" + port + "/user/admin");
        controller.waitForLoad("firstLoad");
        assertTrue(controller.checkExistance("0"));
        assertTrue(controller.checkExistance("1"));
        assertTrue(controller.checkExistance("2"));
        assertTrue(controller.checkExistance("3"));
        assertTrue(controller.checkExistance("4"));
        assertTrue(controller.checkExistance("5"));
        assertTrue(controller.checkExistance("6"));

        assertTrue(controller.checkExistance("editButton"));
    }

    @Test
    void whenPublicProfile_showOnlyReviews_andSales(){
        controller.navigate("http://localhost:" + port + "/user/oof");
        controller.waitForLoad("firstLoad");
        assertTrue(controller.checkExistance("0"));
        assertTrue(controller.checkExistance("1"));
        assertFalse(controller.checkExistance("2"));
        assertFalse(controller.checkExistance("3"));
        assertFalse(controller.checkExistance("4"));
        assertFalse(controller.checkExistance("5"));
        assertFalse(controller.checkExistance("6"));

        assertFalse(controller.checkExistance("editButton"));
    }

    /*
    @Test
    void whenLoadsProfile_showGenericInfo(){
        controller.navigate("http://localhost:" + port + "/user/admin");
        controller.waitForLoad("firstLoad");

        assertTrue(controller.checkText("name", "(admin)"));
        assertTrue(controller.checkText("username", "admin"));
        assertTrue(controller.checkText("description", "This user hasn't written a description yet!"));
        assertTrue(controller.checkText("country", "hm"));
        assertTrue(controller.checkText("birthday", "09/10/2019"));
    }
    */
    
    @Test
    void whenClickingEditProfile_goToEditProfilePage(){
        controller.navigate("http://localhost:" + port + "/user/admin");
        controller.waitForLoad("firstLoad");

        controller.clickButton("editButton");

        controller.waitSeconds(2);
        controller.waitForLoad("firstLoad");
        String url = "http://localhost:" + port + "/user/admin/edit";
        assertTrue(controller.checkURL(url));
    }

    @Test
    void whenProfileNotFound_showErrorMessage(){
        controller.navigate("http://localhost:" + port + "/user/lmao");
        controller.waitForLoad("firstLoad");
        assertTrue(controller.checkExistance("errorMessage"));
        assertTrue(controller.checkText("errorMessage", "Sorry, there was an error retrieving this user's information..."));
    }


}