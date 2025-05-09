package ui;

import org.junit.jupiter.api.Test;
import utils.BaseUITest;

public class TestImplementation extends BaseUITest {

    @Test
    public void TC_1() {
        
        double balanceBeforeBetting = 0;
        int betAmount = 1000;
        
        navBar.clickUpcomingNavButton();
        
        upcomingPage.clickRandomOdd(); 

        betslipPage.enterBetAmount(betAmount);

        navBar.updatePage(); // Refresh the page to ensure the balance is loaded # Takes too much time to load

        balanceBeforeBetting = navBar.getTotalBalance();

        betslipPage.clickPlaceBetButton();

        betslipPage.checkBalanceisReduced(balanceBeforeBetting, betAmount);
        
        String betSlipId = betslipPage.checkBetIsInMyBetsPage();

        betslipPage.checkBetIsInStatementPage(betSlipId);

    }

    // @Test
    // public void TC_2() {
    //     int betAmount = 1000;
    //     double balanceBeforeBetting = 0;

    //     navBar.clickUpcomingNavButton();

    //     upcomingPage.clickRandomOdds(5); 
            
    //     betslipPage.enterBetAmount(betAmount);

    //     navBar.updatePage();

    //     balanceBeforeBetting = navBar.getTotalBalance();

    //     betslipPage.check10PercentBonus(betAmount);

    //     betslipPage.clickPlaceBetButton();

    //     betslipPage.checkBalanceisReduced(balanceBeforeBetting, betAmount);
        
    //     String betSlipId = betslipPage.checkBetIsInMyBetsPage();

    //     betslipPage.checkBetIsInStatementPage(betSlipId);

    // }

    // @Test
    // public void TC_3() {

    //     navBar.clickDepositButton();

    //     depositPage.enterDepositAmount(1000);

    //     depositPage.clickDepositButton(); // BUG: Sorry, a technical issue prevented us from processing your deposit. Please try again.

    //     try {
    //         Thread.sleep(5000);
    //     } catch (Exception e) {}
    // }

}