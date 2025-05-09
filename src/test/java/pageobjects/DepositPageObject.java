package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DepositPageObject {
    private WebDriver driver;
    private WebDriverWait wait;

    private By depositAmountInputLocator = By.xpath("//input[@id='deposit-form-amount-input']");
    private By depositSubmitBtnLocator = By.xpath("//button[@data-test-id='depositButton' and span[text()='Deposit']]");


    public DepositPageObject(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
    }

    public void enterDepositAmount(int amount) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(depositAmountInputLocator));
        driver.findElement(depositAmountInputLocator).sendKeys(String.valueOf(amount));
    }

    public void clickDepositButton() {
        driver.findElement(depositSubmitBtnLocator).click();
    }

}
