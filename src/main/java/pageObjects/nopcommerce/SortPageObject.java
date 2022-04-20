package pageObjects.nopcommerce;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import commons.BasePage;
import pageUIs.nopcommerce.SortPageUI;

public class SortPageObject extends BasePage {
	private WebDriver driver;
	
	public SortPageObject(WebDriver driver) {
		this.driver = driver;
	}
	
	public List<WebElement> getListNameProductSortResult() {
		waitForAllElementVisible(driver, SortPageUI.listNameProductSortResult);
		return getElements(driver, SortPageUI.listNameProductSortResult);
	}
	
	public List<WebElement> getListPriceProductSortResult() {
		waitForAllElementVisible(driver, SortPageUI.listPriceProductSortResult);
		return getElements(driver, SortPageUI.listPriceProductSortResult);
	}
	
	public int getSizeProductDisplayList() {
		waitForElementVisible(driver, SortPageUI.listNameProductSortResult);
		return getElementSize(driver, SortPageUI.listNameProductSortResult);	
	}
	
	public boolean isNavigateLinkDisplay(WebDriver driver, String navigateClass) {
		waitForElementVisible(driver, SortPageUI.NAVIGATE_PAGE_LI, navigateClass);
		return isElementDisplayed(driver, SortPageUI.NAVIGATE_PAGE_LI, navigateClass);
	}
	
	public int getCurrentPageDisplay(WebDriver driver, String currentPageClass) {
		waitForElementVisible(driver, SortPageUI.CURRENT_PAGE_LI, currentPageClass);
		return Integer.parseInt(getElementText(driver, SortPageUI.CURRENT_PAGE_LI, currentPageClass));
	}
	
	public void clickToNavigatePage(WebDriver driver, String pageClass) {
		waitForElementVisible(driver, SortPageUI.CURRENT_PAGE_LI, pageClass);
		clickToElement(driver, SortPageUI.CURRENT_PAGE_LI, pageClass);
	}
	
	public boolean isPagingDisplay() {
		return isElementDisplayed(driver, SortPageUI.PAGING);
	}
}
