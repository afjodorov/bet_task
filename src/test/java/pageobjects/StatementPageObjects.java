package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class StatementPageObjects {
    private WebDriver driver;
    private WebDriverWait wait;

    private By myAccountLocator = By.xpath("//div[contains(@class, 'menu-button') and .//span[text()='Account']]");
    private By statementLinkLocator = By.xpath("//a[@id='301056']");
    private By statementHeader = By.xpath("//h2[text()='STATEMENT']");

    public StatementPageObjects(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
    }

    public void goToStatementPage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(myAccountLocator));
        driver.findElement(myAccountLocator).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(statementLinkLocator));
        driver.findElement(statementLinkLocator).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(statementHeader));
    }
}
