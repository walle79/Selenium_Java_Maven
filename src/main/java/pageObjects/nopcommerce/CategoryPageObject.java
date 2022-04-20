package pageObjects.nopcommerce;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import pageUIs.nopcommerce.CategoryPageUI;
import pageUIs.nopcommerce.HomePageUI;

public class CategoryPageObject extends BasePage {
	private WebDriver driver;
	
	public CategoryPageObject(WebDriver driver) {
		this.driver = driver;
	}

	public boolean isReviewMessageResultDisplayed() {
		waitForElementVisible(driver, CategoryPageUI.reviewResult);
		return isElementDisplayed(driver, CategoryPageUI.reviewResult);
	}

	public void sendKeyToReviewTextTextarea(String review_text) {
		waitForElementVisible(driver, CategoryPageUI.reviewTextTextarea);
		sendKeyToElement(driver, CategoryPageUI.reviewTextTextarea, review_text);
	}

}
