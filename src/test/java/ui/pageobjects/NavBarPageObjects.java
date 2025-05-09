package ui.pageobjects;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class NavBarPageObjects {
    private WebDriver driver;
    private WebDriverWait wait;

    private By loginButtonLocator = By.xpath("//a[contains(@href, '/login')]");
    private By upcomingNavBtnLocator = By.cssSelector("[data-test-id='tabs-Upcoming']");
    private By totalBalanceLocator = By.cssSelector("span.button.balance");
    private By depositButtonLocator = By.xpath("//span[contains(@class, 'button-deposit') and text()='Deposit']");

    
    public NavBarPageObjects(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
    }

    public void clickLoginNavButton() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginButtonLocator));
        driver.findElement(loginButtonLocator).click();
    }

    public void clickUpcomingNavButton() {
        driver.findElement(upcomingNavBtnLocator).click();
    }

    public void clickDepositButton() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(depositButtonLocator));
        driver.findElement(depositButtonLocator).click();
    }

    public void updatePage() {
        driver.navigate().refresh(); // Refresh the page to ensure the balance is loaded # Takes too much time to load
    }

    public double getTotalBalance() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(totalBalanceLocator));
    
        String balanceText = driver.findElement(totalBalanceLocator).getText();
        String cleaned = balanceText.replace("UGX", "").replace(",", "").trim();
    
        return Double.parseDouble(cleaned);
    }

}
