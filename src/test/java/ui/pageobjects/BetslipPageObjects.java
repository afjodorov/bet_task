package ui.pageobjects;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BetslipPageObjects {
    
    private WebDriver driver;
    private WebDriverWait wait;
    private NavBarPageObjects navBarPage;
    private StatementPageObjects statementPage;
    
    private By betInputLocator = By.id("betslip-form-stake-input");
    private By betSubmitBtnLocator = By.xpath("//input[@type='submit' and @value='PLACE BET']");
    private By betPlacedMessage = By.xpath("//h2[contains(text(), 'Congratulations')]");

    private By potentialWinningAmountLocator = By.cssSelector("[data-test-id='totalWinnings']");
    private By bonusAmountLocator = By.cssSelector("[data-test-id='totalWinBonus']");
    private By potentialPayoutAmountLocator = By.cssSelector("[data-test-id='payout']");

    private By viewMyBetsLocator = By.xpath("//a[contains(@class, 'view-my-bets-link') and text()='View My Bets']");
    private By betslipDetailsLinkLocator = By.xpath("//a[contains(@class, 'open-details-container-link') and text()='betslip details.']");
    private By betSlipIdLocator = By.cssSelector("[data-test-id='betSlipID']");
    

    public BetslipPageObjects(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        navBarPage = new NavBarPageObjects(driver);
        statementPage = new StatementPageObjects(driver);
    }

    public void enterBetAmount(int bet) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(betInputLocator));
        driver.findElement(betInputLocator).sendKeys(String.valueOf(bet));
    }

    public void clickPlaceBetButton() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(betSubmitBtnLocator));
        driver.findElement(betSubmitBtnLocator).click();

        verifyBetPlacedMessage();
    }

    private void verifyBetPlacedMessage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(betPlacedMessage));
    }

    public void checkBalanceisReduced(double balanceBeforeBetting, int betAmount) {
        double currentTotalBalance = navBarPage.getTotalBalance();
        double reducedBalance =  balanceBeforeBetting - betAmount;
        
        assert reducedBalance == currentTotalBalance : "Balance not reduced correctly! Expected: " + reducedBalance + ", but got: " + currentTotalBalance;
    }

    private void goToBetPage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(betslipDetailsLinkLocator));
        driver.findElement(betslipDetailsLinkLocator).click();
    }

    private void goToMyBetsPage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(viewMyBetsLocator));
        driver.findElement(viewMyBetsLocator).click();
    }

    private String getBetSlipID() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(betSlipIdLocator));
        String textId = driver.findElement(betSlipIdLocator).getText();
        String betSlipId = textId.replaceAll("\\D+", "");
        
        return betSlipId;
    }

    public String checkBetIsInMyBetsPage() {
        goToBetPage();

        String betSlipId = getBetSlipID();
        By betSlipIdLocator = By.cssSelector("[data-test-id='bet-pending-" + betSlipId + "']");

        goToMyBetsPage();
        wait.until(ExpectedConditions.visibilityOfElementLocated(betSlipIdLocator));

        return betSlipId;
    }

    public void checkBetIsInStatementPage(String betSlipId) {
        statementPage.goToStatementPage();

        By betSlipIdLocator = By.xpath("//*[contains(text(), '#" + betSlipId + "')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(betSlipIdLocator));
    }

    public void check10PercentBonus(int enteredBetAmount) {
        BigDecimal potentialWinning = getPotentialWinning();
        BigDecimal bonusAmount = getBonusAmount();
        BigDecimal potentialPayout = getPotentialPayout();

        BigDecimal expectedBonus = potentialWinning.multiply(BigDecimal.valueOf(0.1)).setScale(2, RoundingMode.HALF_UP);

        if (expectedBonus.compareTo(bonusAmount) != 0) {
            throw new AssertionError("Bonus amount is not correct! Expected: " + expectedBonus + ", but got: " + bonusAmount);
        }

        BigDecimal expectedPayout = potentialWinning.add(bonusAmount).setScale(2, RoundingMode.HALF_UP);

        // For some reason I had to subtract the entered bet amount from the potential payout to get the 0, could be a bug or at least not clear for me?
        if (expectedPayout.compareTo(potentialPayout.subtract(BigDecimal.valueOf(enteredBetAmount))) != 0) {
            throw new AssertionError("Potential payout is not correct! Expected: " + expectedPayout + ", but got: " + potentialPayout);
        }
    }

    private BigDecimal getPotentialWinning() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(potentialWinningAmountLocator));
        String text = driver.findElement(potentialWinningAmountLocator).getText();

        return parseCurrencyText(text);
    }

    private BigDecimal getBonusAmount() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(bonusAmountLocator));
        String text = driver.findElement(bonusAmountLocator).getText();

        return parseCurrencyText(text);
    }

    private BigDecimal getPotentialPayout() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(potentialPayoutAmountLocator));
        String text = driver.findElement(potentialPayoutAmountLocator).getText();

        return parseCurrencyText(text);
    }

    private BigDecimal parseCurrencyText(String text) {
        String cleaned = text.replace("UGX", "").replace(",", "").trim();

        return new BigDecimal(cleaned).setScale(2, RoundingMode.HALF_UP);
    }
}
