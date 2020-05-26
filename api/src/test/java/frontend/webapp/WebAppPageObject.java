package frontend.webapp;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class WebAppPageObject {
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;

    public WebAppPageObject() {
        System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver"); //Linux Style
        driver = new FirefoxDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
    }

    public void tear() {
        driver.quit();
    }


    // Navigate to expected url page
    public void navigate(String url) {
        driver.get(url);
    }

    // Check if element by given id is visible
    public boolean checkVisibility(String id) {
        return driver.findElement(By.id(id)).isDisplayed();
    }

    // Check if element by given id is enabled
    public boolean checkEnabled(String id) {
        return driver.findElement(By.id(id)).isEnabled();
    }

    // Check if the current URL is the expected one
    public boolean checkURL(String url) {
        return driver.getCurrentUrl().equals(url);
    }

    // Return the number of options of a select dropdown
    public int getNumberOfSelectOptions(String id) {
        Select se = new Select(driver.findElement(By.id(id)));
        List<WebElement> l = se.getOptions();
        return l.size();
    }

    // Check if element by id has the specified text
    public boolean checkText(String id, String text) {
        System.out.println(driver.findElement(By.id(id)).getText());
        return driver.findElement(By.id(id)).getText().equals(text);
    }

    // Check if an element exists
    public boolean checkExistance(String id){
        return driver.findElements( By.id(id) ).size() != 0;
    }

    // Wait for a specified amount of seconds
    public void waitSeconds(int seconds){
        driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
    }

    // Write value into an input box
    public void writeInput(@Nullable String val, String id) {
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
    public void pickSelect(int val, String id) {
        driver.findElement(By.id(id)).click();
        driver.findElement(By.id("react-select-2-option-"+val)).click();
    }

    // Click button given by id
    public void clickButton(String id) {
        driver.findElement(By.id(id)).sendKeys(Keys.SPACE);
    }

    // Scroll to element
    public void scrollToTop(){
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("window.scrollTo(0,0)");
    }


    // Wait for an element to disappear
    public void waitForLoad(String id) {
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
}
