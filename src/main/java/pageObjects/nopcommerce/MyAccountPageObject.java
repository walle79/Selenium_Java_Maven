package pageObjects.nopcommerce;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import pageUIs.nopcommerce.MyAccountPageUI;

public class MyAccountPageObject extends BasePage {
	private WebDriver driver;

	public MyAccountPageObject(WebDriver driver) {
		this.driver = driver;
	}

	public boolean isNotificationSuccessDisplayed() {
		waitForElementVisible(driver, MyAccountPageUI.notification_success);
		return isElementDisplayed(driver, MyAccountPageUI.notification_success);
	}

	public void clickToCloseNotification() {
		waitForElementClickable(driver, MyAccountPageUI.close_icon);
		clickToElement(driver, MyAccountPageUI.close_icon);
	}
}
