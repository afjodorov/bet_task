package utils;

import java.beans.Statement;
import java.lang.Thread.State;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;
import pageobjects.BetslipPageObjects;
import pageobjects.DepositPageObject;
import pageobjects.LoginPageObjects;
import pageobjects.NavBarPageObjects;
import pageobjects.UpcomingPageObjects;
import pageobjects.StatementPageObjects;

public abstract class BaseUITest {
    protected WebDriver driver;
    protected final static String baseUrl = "https://ug.staging.fe.verekuu.com";

    public NavBarPageObjects navBar;
    public LoginPageObjects loginPage;
    public UpcomingPageObjects upcomingPage;
    public BetslipPageObjects betslipPage;
    public DepositPageObject depositPage;
    public StatementPageObjects statementPageObjects;
    
    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito"); // To block 'Change your password' pop-up

        driver = WebDriverManager.chromedriver().capabilities(options).create();
        driver.get(baseUrl);

        navBar = new NavBarPageObjects(driver);
        loginPage = new LoginPageObjects(driver);
        upcomingPage = new UpcomingPageObjects(driver);
        betslipPage = new BetslipPageObjects(driver);
        depositPage = new DepositPageObject(driver);
        statementPageObjects = new StatementPageObjects(driver);

        // Login to the application before each test
        navBar.clickLoginNavButton();
        loginPage.fullLogin("778899003", "123456");

    }
    
    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
