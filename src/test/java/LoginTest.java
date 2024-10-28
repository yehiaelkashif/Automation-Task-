import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LoginTest {
    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        // Set the path of the WebDriver
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
    }

    @Test
    public void testElementPresence() {
        // Check if username, password fields, and login button are present
        Assert.assertTrue(isElementPresent(By.id("user-name")), "Username field is not present");
        Assert.assertTrue(isElementPresent(By.id("password")), "Password field is not present");
        Assert.assertTrue(isElementPresent(By.id("login-button")), "Login button is not present");
    }

    @Test
    public void testValidLogin() {
        // Valid login credentials
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // Verify successful login
        WebElement swagLabsText = driver.findElement(By.className("title"));
        Assert.assertTrue(swagLabsText.isDisplayed(), "Login was not successful. 'Swag Labs' text is not visible.");
    }

    @Test
    public void testInvalidLogin() {
        // Invalid login credentials
        driver.findElement(By.id("user-name")).sendKeys("invalid_user");
        driver.findElement(By.id("password")).sendKeys("invalid_password");
        driver.findElement(By.id("login-button")).click();

        // Verify error message for invalid credentials
        WebElement errorMessage = driver.findElement(By.cssSelector(".error-message-container.error"));
        Assert.assertTrue(errorMessage.isDisplayed(), "Error message is not displayed for invalid credentials.");
        Assert.assertTrue(errorMessage.getText().contains("Epic sadface: Username and password do not match any user in this service"), "Error message text does not match.");
    }

    @Test
    public void testEmptyCredentials() {
        // Check for empty username
        driver.findElement(By.id("login-button")).click();
        WebElement usernameErrorMessage = driver.findElement(By.cssSelector(".error-message-container.error"));
        Assert.assertTrue(usernameErrorMessage.isDisplayed(), "Error message for empty username is not displayed.");
        Assert.assertTrue(usernameErrorMessage.getText().contains("Epic sadface: Username is required"), "Error message text for empty username does not match.");

        // Check for empty password
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("login-button")).click();
        WebElement passwordErrorMessage = driver.findElement(By.cssSelector(".error-message-container.error"));
        Assert.assertTrue(passwordErrorMessage.isDisplayed(), "Error message for empty password is not displayed.");
        Assert.assertTrue(passwordErrorMessage.getText().contains("Epic sadface: Password is required"), "Error message text for empty password does not match.");
    }

    @AfterClass
    public void tearDown() {
        // Close the browser
        if (driver != null) {
            driver.quit();
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }
}