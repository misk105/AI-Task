package frontgate;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class ValidLoginTest {

    WebDriver driver;
    WebDriverWait wait;
    public static String url = "https://certwcs.frontgate.com/?aka_bypass=5C73514EE7A609054D81DE61DD9CA3D6";
    public static String email = "m.hneef@student.aaup.edu";
    public static String password = "Mi@12345678";

    @BeforeTest
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        driver.get(url);
    }

    @Test
    public void validLoginTest() {

        // 1. Hover "Account" button
        WebElement accountBtn = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button.c-button.t-header__my-account"))
        );

        Actions actions = new Actions(driver);
        actions.moveToElement(accountBtn).perform();

        // 2. Click "Sign In/Register"
        WebElement signInRegister = wait.until(
                ExpectedConditions.elementToBeClickable(By.linkText("Sign In / Register"))
        );
        signInRegister.click();

        // 3. Enter valid Email and Password
        WebElement emailField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input#email"))
        );
        emailField.sendKeys(email);

        WebElement passwordField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input#password"))
        );
        passwordField.sendKeys(password);

        // 4. Click "Sign In"
        WebElement signInBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.login-button.btn.btn-primary")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", signInBtn);

        // ✅ Assertion (verify login success)
        WebElement myAccount = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class,'welcome')]"))
        );

        Assert.assertTrue(myAccount.isDisplayed(), "Login failed!");
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}