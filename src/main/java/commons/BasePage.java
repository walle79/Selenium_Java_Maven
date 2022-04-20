package commons;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import pageUIs.nopcommerce.BasePageUI;
import pageUIs.nopcommerce.NopCommerceBasePageUI;
import pageUIs.nopcommerce.SortPageUI;

public class BasePage {
	
	public void openPageUrl(WebDriver driver, String pageUrl) {
		driver.get(pageUrl);
	}
	
	public String getPageTitle(WebDriver driver) {
		return driver.getTitle();
	}
	
	public String getPageUrl(WebDriver driver) {
		return driver.getCurrentUrl();
	}
	
	public String getPageSource(WebDriver driver) {
		return driver.getPageSource();
	}
	
	public Alert waitForAlertPresence(WebDriver driver) {
		explicitWait = new WebDriverWait(driver, timeout);
		return explicitWait.until(ExpectedConditions.alertIsPresent());
	}
	
	public void acceptAlert(WebDriver driver) {
		alert = waitForAlertPresence(driver);
		alert.accept();
	}
	
	public void cancelAlert(WebDriver driver) {
		alert = waitForAlertPresence(driver);
		alert.dismiss();
	}
	
	public void sendKeyToAlert(WebDriver driver, String value) {
		alert = waitForAlertPresence(driver);
		alert.sendKeys(value);
	}
	
	public String getAlertText(WebDriver driver) {
		alert = waitForAlertPresence(driver);
		return alert.getText();
	}
	
	public void switchToWindowByID(WebDriver driver, String parentID) {
		Set<String> allWindowIDs = driver.getWindowHandles();
		for (String windowID : allWindowIDs) {
			if (!windowID.equals(parentID)) {
				driver.switchTo().window(windowID);
				break;
			}
		}
	}
	
	public void switchToWindowByTitle(WebDriver driver, String expectedWindowTitle) {
		Set<String> allWindowIDs = driver.getWindowHandles();
		for (String windowID : allWindowIDs) {
			driver.switchTo().window(windowID);
			String actualWindowTitle = driver.getTitle();
			if (actualWindowTitle.equals(expectedWindowTitle)) {
				break;
			}
		}
	}
	
	public void closeAllWindowExceptParent(WebDriver driver, String parentID) {
		Set<String> allWindowIDs = driver.getWindowHandles();
		for (String windowID : allWindowIDs) {
			if (!windowID.equals(parentID)) {
				driver.switchTo().window(windowID);
				driver.close();
				sleepInSecond(1);
			}
			
			if (driver.getWindowHandles().size() == 1) {
				driver.switchTo().window(parentID);
				break;
			}
		}
	}
	
	public void backToPage(WebDriver driver) {
		driver.navigate().back();
	}
	
	public void refreshCurrentPage(WebDriver driver) {
		driver.navigate().refresh();
	}
	
	public void forwardToPage(WebDriver driver) {
		driver.navigate().forward();
	}
	
	public void sleepInSecond(long timeInSecond) {
		try {
			Thread.sleep(timeInSecond * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public WebElement getElement(WebDriver driver, String locator) {
		return driver.findElement(getByXpath(locator));
	}
	
	public List<WebElement> getElements(WebDriver driver, String locator) {
		return driver.findElements(getByXpath(locator));
	}
	
	public By getByXpath(String locator) {
		return By.xpath(locator);
	}
	
	public String getDynamicLocator(String locator, String... params) {
		return String.format(locator, (Object[]) params);
	}
	
	public void clickToElement(WebDriver driver, String locator) {
		getElement(driver, locator).click();
	}
	
	public void clickToElement(WebDriver driver, String locator, String... params) {
		getElement(driver, getDynamicLocator(locator, params)).click();
	}
	
	public void sendKeyToElement(WebDriver driver, String locator, String value) {
		getElement(driver, locator).clear();
		getElement(driver, locator).sendKeys(value);
	}
	
	public void sendKeyToElement(WebDriver driver, String locator, String value, String... params) {
		locator = getDynamicLocator(locator, params);
		getElement(driver, locator).clear();
		getElement(driver, locator).sendKeys(value);
	}
	
	public int getElementSize(WebDriver driver, String locator) {
		return getElements(driver, locator).size();
	}
	
	public int getElementSize(WebDriver driver, String locator, String... params) {
		locator = getDynamicLocator(locator, params);
		return getElements(driver, locator).size();
	}
	
	public void selectDropdownByText(WebDriver driver, String locator, String itemText) {
		select = new Select(getElement(driver, locator));
		select.selectByVisibleText(itemText);
	}
	
	public void selectDropdownByText(WebDriver driver, String locator, String itemText, String... params) {
		locator = getDynamicLocator(locator, params);
		select = new Select(getElement(driver, locator));
		select.selectByVisibleText(itemText);
	}
	
	public String getSelectedItemDropdown(WebDriver driver, String locator) {
		select = new Select(getElement(driver, locator));
		return select.getFirstSelectedOption().getText();
	}
	
	public String getSelectedItemDropdown(WebDriver driver, String locator, String... params) {
		locator = getDynamicLocator(locator, params);
		select = new Select(getElement(driver, locator));
		return select.getFirstSelectedOption().getText();
	}
	 
	public boolean isMultipleDropdown(WebDriver driver, String locator) {
		select = new Select(getElement(driver, locator));
		return select.isMultiple();
	}
	
	public void selectItemCustomDropdown(WebDriver driver, String parentLocator, String childLocator, String expectedItem) {
		getElement(driver, parentLocator).click();
		sleepInSecond(1);
		
		explicitWait = new WebDriverWait(driver, timeout);
		List<WebElement> allItems = explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getByXpath(childLocator)));
		
		for (WebElement item : allItems) {
			if (item.getText().trim().equals(expectedItem)) {
				jsExecutor = (JavascriptExecutor) driver;
				jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
				sleepInSecond(1);
				
				item.click();
				sleepInSecond(1);
				break;
			}
		}
	}
	
	public String getElementAttribute(WebDriver driver, String locator, String attributeName) {
		return getElement(driver, locator).getAttribute(attributeName);
	}
	
	public String getElementAttribute(WebDriver driver, String locator, String attributeName, String... params) {
		return getElement(driver, getDynamicLocator(locator, params)).getAttribute(attributeName);
	}
	
	public String getElementText(WebDriver driver, String locator) {
		return getElement(driver, locator).getText();
	}
	
	public String getElementText(WebDriver driver, String locator, String... params) {
		return getElement(driver, getDynamicLocator(locator, params)).getText().trim();
	}
	
	public void checkToCheckBoxOrRadio(WebDriver driver, String locator) {
		if (!getElement(driver, locator).isSelected()) {
			getElement(driver, locator).click();
		}
	}
	
	public void checkToCheckBoxOrRadio(WebDriver driver, String locator, String... params) {
		locator = getDynamicLocator(locator, params);
		if (!getElement(driver, locator).isSelected()) {
			getElement(driver, locator).click();
		}
	}
	
	public void uncheckToCheckBox(WebDriver driver, String locator, String... params) {
		locator = getDynamicLocator(locator, params);
		if (getElement(driver, locator).isSelected()) {
			getElement(driver, locator).click();
		}
	}
	
	public boolean isElementDisplayed(WebDriver driver, String locator) {
		return getElement(driver, locator).isDisplayed();
	}
	
	public boolean isElementDisplayed(WebDriver driver, String locator, String... params) {
		return getElement(driver, getDynamicLocator(locator, params)).isDisplayed();
	}
	
	public boolean isElementSelected(WebDriver driver, String locator) {
		return getElement(driver, locator).isSelected();
	}
	
	public boolean isElementSelected(WebDriver driver, String locator, String... params) {
		return getElement(driver, getDynamicLocator(locator, params)).isSelected();
	}
	
	public boolean isElementEnabled(WebDriver driver, String locator) {
		return getElement(driver, locator).isEnabled();
	}
	
	public boolean isElementEnabled(WebDriver driver, String locator, String... params) {
		return getElement(driver, getDynamicLocator(locator, params)).isEnabled();
	}
	
	public WebDriver switchToIframeByElement(WebDriver driver, String locator) {
		return driver.switchTo().frame(getElement(driver, locator));
	}
	
	public WebDriver switchToDefaultContent(WebDriver driver) {
		return driver.switchTo().defaultContent();
	}
	
	public void hoverToElement(WebDriver driver, String locator) {
		action = new Actions(driver);
		action.moveToElement(getElement(driver, locator)).perform();
	}
	
	public void hoverToElement(WebDriver driver, String locator, String... params) {
		action = new Actions(driver);
		action.moveToElement(getElement(driver, getDynamicLocator(locator, params))).perform();
	}
	
	public void doubleClickToElement(WebDriver driver, String locator) {
		action = new Actions(driver);
		action.doubleClick(getElement(driver, locator)).perform();
	}
	
	public void rightClickToElement(WebDriver driver, String locator) {
		action = new Actions(driver);
		action.contextClick(getElement(driver, locator)).perform();
	}
	
	public void doubleClickToElement(WebDriver driver, String sourceLocator, String targetLocator) {
		action = new Actions(driver);
		action.dragAndDrop(getElement(driver, sourceLocator), getElement(driver, targetLocator)).perform();
	}
	
	public void pressKeyToElement(WebDriver driver, String locator, Keys key) {
		action = new Actions(driver);
		action.sendKeys(getElement(driver, locator), key).perform();
	}
	
	public Object executeForBrowser(WebDriver driver, String javascript) {
		jsExecutor = (JavascriptExecutor) driver;
		return jsExecutor.executeScript(javascript);
	}
	
	public String getInnerText(WebDriver driver) {
		jsExecutor = (JavascriptExecutor) driver;
		return (String) jsExecutor.executeScript("return document.documentElement.innerText;");
	}
	
	public void waitForElementVisible(WebDriver driver, String locator) {
		explicitWait = new WebDriverWait(driver, timeout);
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByXpath(locator)));
	}
	
	public void waitForElementVisible(WebDriver driver, String locator, String... params) {
		explicitWait = new WebDriverWait(driver, timeout);
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByXpath(getDynamicLocator(locator, params))));
	}
	
	public void waitForAllElementVisible(WebDriver driver, String locator) {
		explicitWait = new WebDriverWait(driver, timeout);
		explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getByXpath(locator)));
	}
	
	public void waitForElementClickable(WebDriver driver, String locator) {
		explicitWait = new WebDriverWait(driver, timeout);
		explicitWait.until(ExpectedConditions.elementToBeClickable(getByXpath(locator)));
	}
	
	public void waitForElementClickable(WebDriver driver, String locator, String... params) {
		explicitWait = new WebDriverWait(driver, timeout);
		explicitWait.until(ExpectedConditions.elementToBeClickable(getByXpath(getDynamicLocator(locator, params))));
	}
	
	public void waitForElementInvisible(WebDriver driver, String locator) {
		explicitWait = new WebDriverWait(driver, timeout);
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByXpath(locator)));
	}
	
	public void waitForElementInvisible(WebDriver driver, String locator, String... params) {
		explicitWait = new WebDriverWait(driver, timeout);
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByXpath(getDynamicLocator(locator, params))));
	}
	
	public void openFooterPageByName(WebDriver driver, String pageName) {
		waitForElementClickable(driver, BasePageUI.DYNAMIC_PAGE_FOOTER, pageName);
		clickToElement(driver, BasePageUI.DYNAMIC_PAGE_FOOTER, pageName);
	}
	
	// HRM - Open Menu/ Submenu/ Childmenu
//	public void openMenuPage(WebDriver driver, String menuPageName) {
//		waitForElementClickable(driver, HRMBasePageUI.DYNAMIC_MENU_PAGE, menuPageName);
//		clickToElement(driver, HRMBasePageUI.DYNAMIC_MENU_PAGE, menuPageName);
//	}
	
//	public void openSubMenuPage(WebDriver driver, String menuPageName, String subMenuPageName) {
//		waitForElementClickable(driver, HRMBasePageUI.DYNAMIC_MENU_PAGE, menuPageName);
//		clickToElement(driver, HRMBasePageUI.DYNAMIC_MENU_PAGE, menuPageName);
//
//		waitForElementClickable(driver, HRMBasePageUI.DYNAMIC_MENU_PAGE, subMenuPageName);
//		clickToElement(driver, HRMBasePageUI.DYNAMIC_MENU_PAGE, subMenuPageName);
//	}
	
//	public void openChildSubMenuPage(WebDriver driver, String menuPageName, String subMenuPageName, String childSubMenuPageName) {
//		waitForElementClickable(driver, HRMBasePageUI.DYNAMIC_MENU_PAGE, menuPageName);
//		clickToElement(driver, HRMBasePageUI.DYNAMIC_MENU_PAGE, menuPageName);
//
//		waitForElementInvisible(driver, HRMBasePageUI.DYNAMIC_MENU_PAGE, subMenuPageName);
//		hoverToElement(driver, HRMBasePageUI.DYNAMIC_MENU_PAGE, subMenuPageName);
//
//		waitForElementClickable(driver, HRMBasePageUI.DYNAMIC_MENU_PAGE, childSubMenuPageName);
//		clickToElement(driver, HRMBasePageUI.DYNAMIC_MENU_PAGE, childSubMenuPageName);
//	}
//
//	public void clickToBtnByID(WebDriver driver, String buttonIDName) {
//		waitForElementClickable(driver, HRMBasePageUI.DYNAMIC_BTN_BY_ID, buttonIDName);
//		clickToElement(driver, HRMBasePageUI.DYNAMIC_BTN_BY_ID, buttonIDName);
//	}
	
//	public void enterToTextboxByID(WebDriver driver, String textboxIDName, String value) {
//		waitForElementVisible(driver, HRMBasePageUI.DYNAMIC_TEXTBOX_BY_ID, textboxIDName);
//		sendKeyToElement(driver, HRMBasePageUI.DYNAMIC_TEXTBOX_BY_ID, value, textboxIDName);
//	}
//
//	public String getTextboxValueByID(WebDriver driver, String textboxIDName) {
//		waitForElementVisible(driver, HRMBasePageUI.DYNAMIC_TEXTBOX_BY_ID, textboxIDName);
//		return getElementAttribute(driver, HRMBasePageUI.DYNAMIC_TEXTBOX_BY_ID, "value", textboxIDName);
//	}
//
	public void selectItemInDropdownByID(WebDriver driver, String dropdownID, String valueItem) {
		waitForElementClickable(driver, NopCommerceBasePageUI.DYNAMIC_DROPDOWN_BY_TEXT, dropdownID);
		selectDropdownByText(driver, NopCommerceBasePageUI.DYNAMIC_DROPDOWN_BY_TEXT, valueItem, dropdownID);
	}
	
//	public String getSelectedItemInDropdownByID(WebDriver driver, String dropdownID, String valueItem) {
//		waitForElementVisible(driver, HRMBasePageUI.DYNAMIC_DROPDOWN_BY_ID, dropdownID);
//		return getSelectedItemDropdown(driver, HRMBasePageUI.DYNAMIC_DROPDOWN_BY_ID, dropdownID);
//	}
//
//	public void clickToCheckboxByLabel(WebDriver driver, String checkboxLabelName) {
//		waitForElementClickable(driver, HRMBasePageUI.DYNAMIC_CHECKBOX_BY_LABEL, checkboxLabelName);
//		checkToCheckBoxOrRadio(driver, HRMBasePageUI.DYNAMIC_CHECKBOX_BY_LABEL, checkboxLabelName);
//	}
//
//	public void clickToRadioByLabel(WebDriver driver, String radioLabelName) {
//		waitForElementClickable(driver, HRMBasePageUI.DYNAMIC_RADIO_BY_LABEL, radioLabelName);
//		checkToCheckBoxOrRadio(driver, HRMBasePageUI.DYNAMIC_RADIO_BY_LABEL, radioLabelName);
//	}
	
//	public String getValueDisplayedInTableIDAtColumnNameAndRowIndex(WebDriver driver, String tableID, String headerName, String rowIndex) {
//		int columnIndex = getElementSize(driver, HRMBasePageUI.DYNAMIC_TABLE_HEADER_BY_ID_AND_NAME, tableID, headerName) + 1;
//		waitForElementVisible(driver, HRMBasePageUI.DYNAMIC_TABLE_ROW_BY_COLUMN_INDEX_AND_ROW_INDEX, tableID, rowIndex, String.valueOf(columnIndex));
//		return getElementText(driver, HRMBasePageUI.DYNAMIC_TABLE_ROW_BY_COLUMN_INDEX_AND_ROW_INDEX, tableID, rowIndex, String.valueOf(columnIndex));
//	}
	
	public boolean isJQueryAjaxLoadedSuccess(WebDriver driver) {
		explicitWait = new WebDriverWait(driver, longTimeout);
		jsExecutor = (JavascriptExecutor) driver;
		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() { 
			@Override
			public Boolean apply(WebDriver driver) {
				return (Boolean) jsExecutor.executeScript("return (window.jQuery != null) && (jQuery.active === 0);");
			}
		};
		return explicitWait.until(jQueryLoad);
	}
	
//	public DashboardPO loginToSystem(WebDriver driver, String username, String password) {
//		waitForElementVisible(driver, HRMBasePageUI.USERNAME_TEXTBOX);
//		sendKeyToElement(driver, HRMBasePageUI.USERNAME_TEXTBOX, username);
//		sendKeyToElement(driver, HRMBasePageUI.PASSWORD_TEXTBOX, password);
//		clickToElement(driver, HRMBasePageUI.LOGIN_BTN);
//		return PageGenerator.getDashboardPage(driver);
//	}
//
//	public LoginPO logoutToSystem(WebDriver driver) {
//		waitForElementClickable(driver, HRMBasePageUI.WELCOME_USER_LINK);
//		clickToElement(driver, HRMBasePageUI.WELCOME_USER_LINK);
//		waitForElementClickable(driver, HRMBasePageUI.LOGOUT_LINK);
//		clickToElement(driver, HRMBasePageUI.LOGOUT_LINK);
//		return PageGenerator.getLoginPage(driver);
//	}
	
//	public boolean isSuccessMessageDisplayed(WebDriver driver, String messageValue) {
//		waitForElementVisible(driver, HRMBasePageUI.DYNAMIC_SUCCESS_MESSAGE_VALUE, messageValue);
//		return isElementDisplayed(driver, HRMBasePageUI.DYNAMIC_SUCCESS_MESSAGE_VALUE, messageValue);
//	}
//
//	public boolean isFieldEnabledByID(WebDriver driver, String fieldID) {
//		waitForElementVisible(driver, HRMBasePageUI.FIELD_BY_ID, fieldID);
//		return isElementEnabled(driver, HRMBasePageUI.FIELD_BY_ID, fieldID);
//	}
//
//	public boolean isRadioBtnSelectedByLabel(WebDriver driver, String labelName) {
//		waitForElementVisible(driver, HRMBasePageUI.DYNAMIC_RADIO_BY_LABEL, labelName);
//		return isElementSelected(driver, HRMBasePageUI.DYNAMIC_RADIO_BY_LABEL, labelName);
//	}
	
	// NopCommerce
	public void clickToButtonByName(WebDriver driver, String buttonName) {
		waitForElementClickable(driver, NopCommerceBasePageUI.DYNAMIC_BUTTON_BY_NAME, buttonName);
		clickToElement(driver, NopCommerceBasePageUI.DYNAMIC_BUTTON_BY_NAME, buttonName);
	}
	
	public void clickToLinkByName(WebDriver driver, String linkName) {
		waitForElementClickable(driver, NopCommerceBasePageUI.DYNAMIC_LINK_BY_NAME, linkName);
		clickToElement(driver, NopCommerceBasePageUI.DYNAMIC_LINK_BY_NAME, linkName);
	}
	
	public boolean isValidateMessageDisplayed(WebDriver driver, String messageID, String messageValue) {
		waitForElementVisible(driver, NopCommerceBasePageUI.DYNAMIC_MESSAGE_BY_TEXT, messageID, messageValue);
		return isElementDisplayed(driver, NopCommerceBasePageUI.DYNAMIC_MESSAGE_BY_TEXT, messageID, messageValue);
	}
	
	public void sendKeyToTextboxByID(WebDriver driver, String textboxID, String value) {
		waitForElementVisible(driver, NopCommerceBasePageUI.DYNAMIC_TEXTBOX_BY_ID, textboxID);
		sendKeyToElement(driver, NopCommerceBasePageUI.DYNAMIC_TEXTBOX_BY_ID, value, textboxID);
	}
	
	public boolean isErrorEmailMsgDisplayed(WebDriver driver, String messageClass, String emailMessage) {
		waitForElementVisible(driver, NopCommerceBasePageUI.EMAIL_ERROR_MSG, messageClass, emailMessage);
		return isElementDisplayed(driver, NopCommerceBasePageUI.EMAIL_ERROR_MSG, messageClass, emailMessage);
	}
	
	public void openMyAccountPageByName(WebDriver driver, String pageName) {
		waitForElementClickable(driver, NopCommerceBasePageUI.DYNAMIC_MY_ACCOUNT_PAGE_BY_NAME, pageName);
		clickToElement(driver, NopCommerceBasePageUI.DYNAMIC_MY_ACCOUNT_PAGE_BY_NAME, pageName);
	}
	
	public String getElementAttributeValue(WebDriver driver, String elementID, String attributeName) {
		waitForElementVisible(driver, NopCommerceBasePageUI.DYNAMIC_TEXTBOX_BY_ID, elementID);
		return getElementAttribute(driver, NopCommerceBasePageUI.DYNAMIC_TEXTBOX_BY_ID, attributeName, elementID);
	}
	
	public void clickToRadioGenderButton(WebDriver driver, String genderName) {
		waitForElementClickable(driver, NopCommerceBasePageUI.DYNAMIC_RADIO_BTN, genderName);
		clickToElement(driver, NopCommerceBasePageUI.DYNAMIC_RADIO_BTN, genderName);
	}
	
	public void selecNopCommerceDropdownByText(WebDriver driver, String dropdownName, String itemValue) {
		waitForElementVisible(driver, NopCommerceBasePageUI.DYNAMIC_DROPDOWN_BY_TEXT, dropdownName);
		selectDropdownByText(driver, NopCommerceBasePageUI.DYNAMIC_DROPDOWN_BY_TEXT, itemValue, dropdownName);
	}
	
	public String getSelectedItemDropdownByText(WebDriver driver, String dropdownName) {
		waitForElementVisible(driver, NopCommerceBasePageUI.DYNAMIC_DROPDOWN_BY_TEXT, dropdownName);
		return getSelectedItemDropdown(driver, NopCommerceBasePageUI.DYNAMIC_DROPDOWN_BY_TEXT, dropdownName);
	}
	
	public boolean isUpdatedInformationDiplayedCorrect(WebDriver driver, String classInfo, String textDisplayed) {
		waitForElementVisible(driver, NopCommerceBasePageUI.DYNAMIC_INFO_BY_CLASS_AND_TEXT, classInfo, textDisplayed);
		return isElementDisplayed(driver, NopCommerceBasePageUI.DYNAMIC_INFO_BY_CLASS_AND_TEXT, classInfo, textDisplayed);
	}
	
	public void clickToProductByName(WebDriver driver, String productName) {
		waitForElementClickable(driver, NopCommerceBasePageUI.DYNAMIC_PRODUCT_BY_NAME, productName);
		clickToElement(driver, NopCommerceBasePageUI.DYNAMIC_PRODUCT_BY_NAME, productName);
	}
	
	public boolean isReviewInformationDisplayedCorrect(WebDriver driver, String reviewClass, String reviewText) {
		waitForElementVisible(driver, NopCommerceBasePageUI.DYNAMIC_REVIEW_TITLE_CONTENT, reviewClass, reviewText);
		return isElementDisplayed(driver, NopCommerceBasePageUI.DYNAMIC_REVIEW_TITLE_CONTENT, reviewClass, reviewText);
	}
	
	public void clickFooterLinkByName(WebDriver driver, String footerLinkName) {
		waitForElementVisible(driver, NopCommerceBasePageUI.DYNAMIC_FOOTER_LINK_BY_NAME, footerLinkName);
		clickToElement(driver, NopCommerceBasePageUI.DYNAMIC_FOOTER_LINK_BY_NAME, footerLinkName);
	}
	
	public void checkToSearchCheckboxByID(WebDriver driver, String checkboxID) {
		waitForElementClickable(driver, NopCommerceBasePageUI.DYNAMIC_SEARCH_CHECKBOX, checkboxID);
		checkToCheckBoxOrRadio(driver, NopCommerceBasePageUI.DYNAMIC_SEARCH_CHECKBOX, checkboxID);
	}
	
	public void unCheckToSearchCheckboxByID(WebDriver driver, String checkboxID) {
		waitForElementClickable(driver, NopCommerceBasePageUI.DYNAMIC_SEARCH_CHECKBOX, checkboxID);
		uncheckToCheckBox(driver, NopCommerceBasePageUI.DYNAMIC_SEARCH_CHECKBOX, checkboxID);
	}
	
	public void hoverToMenuLinkByName(WebDriver driver, String menuLinkName) {
		waitForElementVisible(driver, NopCommerceBasePageUI.DYNAMIC_HEADER_LINK_BY_NAME, menuLinkName);
		hoverToElement(driver, NopCommerceBasePageUI.DYNAMIC_HEADER_LINK_BY_NAME, menuLinkName);
	}
	
	public void clickToMenuLinkByName(WebDriver driver, String menuLinkName) {
		waitForElementVisible(driver, NopCommerceBasePageUI.DYNAMIC_HEADER_LINK_BY_NAME, menuLinkName);
		clickToElement(driver, NopCommerceBasePageUI.DYNAMIC_HEADER_LINK_BY_NAME, menuLinkName);
	}
	
	private Alert alert;
	private WebDriverWait explicitWait;
	private long longTimeout = 10;
	private long timeout = 30;
	private Select select;
	private JavascriptExecutor jsExecutor;
	private Actions action;

}
