package pageObjects.nopcommerce;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import commons.BasePage;
import pageUIs.nopcommerce.SearchPageUI;

public class SearchPageObject extends BasePage {
	private WebDriver driver;
	
	public SearchPageObject(WebDriver driver) {
		this.driver = driver;
	}

	public String getNotificationMessage() {
		waitForElementVisible(driver, SearchPageUI.notificationMessage);
		return getElementText(driver, SearchPageUI.notificationMessage);
	}

	public int getSizeProductSearchList() {
		waitForElementVisible(driver, SearchPageUI.resultSearchList);
		return getElementSize(driver, SearchPageUI.resultSearchList);
		
	}

	public List<WebElement> getListNameProductSearchResult() {
		waitForAllElementVisible(driver, SearchPageUI.listNameProductSearchResult);
		return getElements(driver, SearchPageUI.listNameProductSearchResult);
	}

}
