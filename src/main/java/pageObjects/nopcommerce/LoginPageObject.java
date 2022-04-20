package pageObjects.nopcommerce;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import pageUIs.nopcommerce.LoginPageUI;

public class LoginPageObject extends BasePage {
	private WebDriver driver;
	
	public LoginPageObject(WebDriver driver) {
		this.driver = driver;
	}

	public void inputToEmailTxt(String email) {
		waitForElementVisible(driver, LoginPageUI.EMAIL_TXT);
		sendKeyToElement(driver, LoginPageUI.EMAIL_TXT, email);
	}

	public void inputToPasswordTxt(String password) {
		waitForElementVisible(driver, LoginPageUI.PASSWORD_TXT);
		sendKeyToElement(driver, LoginPageUI.PASSWORD_TXT, password);
	}

	public HomePageObject clickToLoginBtn() {
		waitForElementClickable(driver, LoginPageUI.LOGIN_BTN);
		clickToElement(driver, LoginPageUI.LOGIN_BTN);
		return PageGeneratorManager.getHomePage(driver);
	}

}
