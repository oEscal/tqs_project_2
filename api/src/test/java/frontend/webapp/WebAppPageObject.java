package frontend.webapp;

import com.api.demo.DemoApplication;
import com.api.demo.grid.dtos.UserDTO;
import com.api.demo.grid.pojos.DeveloperPOJO;
import com.api.demo.grid.pojos.GameGenrePOJO;
import com.api.demo.grid.pojos.GamePOJO;
import com.api.demo.grid.pojos.PublisherPOJO;
import com.api.demo.grid.service.GridService;
import com.api.demo.grid.service.UserService;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.SneakyThrows;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.lang.Nullable;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;


class WebAppPageObject {

    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;


    @SneakyThrows
    WebAppPageObject(UserService userService, GridService gridService) {

        // Chrome headless Driver
        
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--headless");

        driver = new ChromeDriver(options);

        // Chrome Driver
        /*
        System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver"); //Linux Style
        driver = new ChromeDriver();
         */

        // Firefox Driver
        /*
        System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver"); //Linux Style
        driver = new FirefoxDriver();
        */

        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();

        driver.manage().window().setSize(new Dimension(1920,1080));
        driver.manage().timeouts().implicitlyWait(120, TimeUnit.MILLISECONDS);


        // set user
        UserDTO mSimpleUserDTO = new UserDTO("admin", "admin", "ola@adeus.com", "hm", "admin",
                new SimpleDateFormat("dd/MM/yyyy").parse("10/10/2019"));
        userService.saveUser(mSimpleUserDTO);

        mSimpleUserDTO = new UserDTO("oof", "oof", "oof@adeus.com", "hmm", "oof",
                new SimpleDateFormat("dd/MM/yyyy").parse("10/10/2019"));
        userService.saveUser(mSimpleUserDTO);

        // set games
        for (int id = 0; id < 10; id++) {
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

    void tear() {
        driver.quit();
    }


    // Navigate to expected url page
    void navigate(String url) {
        driver.get(url);
    }

    // Check if element by given id is visible
    boolean checkVisibility(String id) {
        return driver.findElement(By.id(id)).isDisplayed();
    }

    // Check if element by given id is enabled
    boolean checkEnabled(String id) {
        return driver.findElement(By.id(id)).isEnabled();
    }

    // Check if the current URL is the expected one
    boolean checkURL(String url) {
        return driver.getCurrentUrl().equals(url);
    }

    // Return the number of options of a select dropdown
    int getNumberOfSelectOptions(String id) {
        Select se = new Select(driver.findElement(By.id(id)));
        List<WebElement> l = se.getOptions();
        return l.size();
    }

    // Check if element by id has the specified text
    boolean checkText(String id, String text) {
        System.out.println(driver.findElement(By.id(id)).getText());
        return driver.findElement(By.id(id)).getText().equals(text);
    }

    String getText(String id) {
        return driver.findElement(By.id(id)).getText();
    }

    // Check if an element exists
    boolean checkExistance(String id) {
        return driver.findElements(By.id(id)).size() != 0;
    }

    // Wait for a specified amount of seconds
    void waitSeconds(int seconds) {
        driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
    }

    // Write value into an iScreenshot from 2020-05-28 11-23-58nput box
    void writeInput(@Nullable String val, String id) {
        if (val != null) {
            driver.findElement(By.id(id)).click();
            if (val.equals("")) {
                driver.findElement(By.id(id)).clear();
            } else {
                driver.findElement(By.id(id)).clear();
                driver.findElement(By.id(id)).sendKeys(val);
            }
        }
    }

    // Pick the selected value from a select box
    void pickSelect(int val, String id) {
        driver.findElement(By.id(id)).click();
        driver.findElement(By.id("react-select-2-option-" + val)).click();
    }

    // Click button given by id
    void clickButton(String id) {
        driver.findElement(By.id(id)).sendKeys(Keys.SPACE);
    }

    void clickCard() {
        driver.findElement(By.cssSelector(".MuiGrid-root:nth-child(1) > a .MuiCardMedia-root")).click();
    }

    void scrollToTop() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0,0)");
    }

    void scrollToElement(String id) {
        WebElement element = driver.findElement(By.id(id));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    // Wait for an element to disappear
    void waitForLoad(String id) {
        // Had to add these exceptions due to the fact that the preloader gets destroyed sometimes faster than the page is created
        try {
            if (driver.findElements(By.id(id)).size() > 0 && driver.findElement(By.id(id)).isDisplayed()) {
                WebDriverWait wait2 = new WebDriverWait(driver, 120);
                wait2.until(ExpectedConditions.invisibilityOf(driver.findElement(By.id(id))));
            }
        } catch (StaleElementReferenceException e) {
            return;
        } catch (NoSuchElementException e) {
            return;
        }
    }

    //Somewhy the platform select refused to behave soooo...
    void clickPlatform(){
        driver.findElement(By.cssSelector(".select__value-container")).click();
        driver.findElement(By.id("react-select-3-option-0")).click();
    }

    void clickStars(){
        driver.findElement(By.className("star-container")).click();
    }

    void waitForVisibility(String id){
        driver.findElement(By.cssSelector(".ProfilePage-main-4")).click();

    }

    void login(int port){
        //Login
        this.navigate("http://localhost:" + port + "/login-page");
        String username = "admin";
        String password = "admin";
        this.writeInput(username,"username");
        this.writeInput(password,"password");

        this.clickButton("confirm");
        this.waitForLoad("processing");
    }

    void loginAlt(int port){
        //Login
        this.navigate("http://localhost:" + port + "/login-page");
        String username = "oof";
        String password = "oof";
        this.writeInput(username,"username");
        this.writeInput(password,"password");

        this.clickButton("confirm");
        this.waitForLoad("processing");
    }
}