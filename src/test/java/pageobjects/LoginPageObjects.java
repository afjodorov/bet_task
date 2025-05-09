package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPageObjects {
    private WebDriver driver;

    private By phoneNumInputLocator = By.id("login-form-phoneNumber");
    private By pswInputLocator = By.id("login-form-password-input");
    private By loginSubmitBtnLocator = By.cssSelector("[data-test-id='logInButton']");

    public LoginPageObjects(WebDriver driver) {
        this.driver = driver;
    }

    public void enterPhoneNumber(String phoneNum) {    
        driver.findElement(phoneNumInputLocator).sendKeys(phoneNum);
    }

    public void enterPassword(String password) {
        driver.findElement(pswInputLocator).sendKeys(password);
    }

    public void clickLoginButton() {
        driver.findElement(loginSubmitBtnLocator).click();
    }

    public void fullLogin(String phoneNum, String password) {
        enterPhoneNumber(phoneNum);
        enterPassword(password);
        clickLoginButton();
    }

}

