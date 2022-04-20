package com.nopcommerce;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.BaseTest;
import pageObjects.nopcommerce.HomePageObject;
import pageObjects.nopcommerce.LoginPageObject;
import pageObjects.nopcommerce.PageGeneratorManager;
import pageObjects.nopcommerce.SearchPageObject;

public class Search extends BaseTest {
	WebDriver driver;
	String emailAddress, password, itemNotExist, relativeProductName, absoluteProductName, searchKeyword;

	@Parameters({ "browser", "url" })
	@BeforeClass
	public void beforeClass(String browserName, String appUrl) {
		driver = getBrowserDriver(browserName, appUrl);

		// Data test
		emailAddress = "ta@gmail.com";
		password = "123456";
		itemNotExist = "Macbook Pro 2050";
		relativeProductName = "Lenovo";
		absoluteProductName = "ThinkPad X1 Carbon";
		searchKeyword = "Apple MacBook Pro";

		homePage = PageGeneratorManager.getHomePage(driver);
		Assert.assertTrue(homePage.isHomePageSliderDisplayed());

		// Navigate to 'Log in' page
		homePage.clickToLinkByName(driver, "Log in");
		loginPage = PageGeneratorManager.getLoginPage(driver);

		// Login with email and password registered
		loginPage.sendKeyToTextboxByID(driver, "Email", emailAddress);
		loginPage.sendKeyToTextboxByID(driver, "Password", password);
		loginPage.clickToButtonByName(driver, "Log in");
		homePage = PageGeneratorManager.getHomePage(driver);
		homePage.isHomePageSliderDisplayed();

		// Navigate to 'Search' page
		homePage.clickFooterLinkByName(driver, "Search");
		searchPage = PageGeneratorManager.getSearchPage(driver);
	}

	@Test
	public void TC_01_Search_With_Empty_Data() {
		// Click 'Search' button
		searchPage.clickToButtonByName(driver, "Search");

		// Verify error message empty data
		Assert.assertEquals(searchPage.getNotificationMessage(), "Search term minimum length is 3 characters");
	}

	@Test
	public void TC_02_Search_With_Data_Not_Exist() {
		// Search item not exist
		searchPage.sendKeyToTextboxByID(driver, "q", itemNotExist);

		// Click 'Search' button
		searchPage.clickToButtonByName(driver, "Search");

		// Verify message displayed
		Assert.assertEquals(searchPage.getNotificationMessage(), "No products were found that matched your criteria.");
	}

	@Test
	public void TC_03_Search_With_Product_Name_Relative() {
		// Search product name relative
		searchPage.sendKeyToTextboxByID(driver, "q", relativeProductName);

		// Click 'Search' button
		searchPage.clickToButtonByName(driver, "Search");

		// Verify size of list product
		Assert.assertEquals(searchPage.getSizeProductSearchList(), 2);

		// Verify product name
		List<WebElement> productNameRelativeElements = searchPage.getListNameProductSearchResult();
		List<String> productNameRelativeText = new ArrayList<String>();
		for (WebElement productName : productNameRelativeElements) {
			productNameRelativeText.add(productName.getText());
		}
		Assert.assertEquals(productNameRelativeText.get(0), "Lenovo IdeaCentre 600 All-in-One PC");
		Assert.assertEquals(productNameRelativeText.get(1), "Lenovo Thinkpad X1 Carbon Laptop");
	}

	@Test
	public void TC_04_Search_With_Product_Name_Absolute() {
		// Search product name absolute
		searchPage.sendKeyToTextboxByID(driver, "q", absoluteProductName);

		// Click 'Search' button
		searchPage.clickToButtonByName(driver, "Search");

		// Verify size of list product
		Assert.assertEquals(searchPage.getSizeProductSearchList(), 1);

		// Verify product name
		List<WebElement> productNameAbsoluteElements = searchPage.getListNameProductSearchResult();
		List<String> productNameAbsoluteText = new ArrayList<String>();
		for (WebElement productName : productNameAbsoluteElements) {
			productNameAbsoluteText.add(productName.getText());
		}
		Assert.assertEquals(productNameAbsoluteText.get(0), "Lenovo Thinkpad X1 Carbon Laptop");
	}

	@Test
	public void TC_05_Advanced_Search_Parent_Categories() {
		// Search product name
		searchPage.sendKeyToTextboxByID(driver, "q", searchKeyword);

		// Click to 'Advanced search' checkbox
		searchPage.checkToSearchCheckboxByID(driver, "advs");

		// Choose 'Computers' in Category dropdown
		searchPage.selecNopCommerceDropdownByText(driver, "cid", "Computers");

		// UnCheck 'Automatically search sub categories' checkbox
		searchPage.unCheckToSearchCheckboxByID(driver, "isc");

		// Click 'Search' button
		searchPage.clickToButtonByName(driver, "Search");

		// Verify message displayed
		Assert.assertEquals(searchPage.getNotificationMessage(), "No products were found that matched your criteria.");
	}

	@Test
	public void TC_06_Advanced_Search_Sub_Categories() {
		// Refresh current page
		searchPage.refreshCurrentPage(driver);

		// Search product name
		searchPage.sendKeyToTextboxByID(driver, "q", searchKeyword);

		// Click to 'Advanced search' checkbox
		searchPage.checkToSearchCheckboxByID(driver, "advs");

		// Choose 'Computers' in Category dropdown
		searchPage.selecNopCommerceDropdownByText(driver, "cid", "Computers");

		// Check 'Automatically search sub categories' checkbox
		searchPage.checkToSearchCheckboxByID(driver, "isc");

		// Click 'Search' button
		searchPage.clickToButtonByName(driver, "Search");

		// Verify size of list product
		Assert.assertEquals(searchPage.getSizeProductSearchList(), 1);

		// Verify product name
		List<WebElement> productNameElements = searchPage.getListNameProductSearchResult();
		Assert.assertEquals(productNameElements.get(0).getText(), "Apple MacBook Pro 13-inch");
	}

	@Test
	public void TC_07_Advanced_Search_Incorrect_Manufacturer() {
		// Refresh current page
		searchPage.refreshCurrentPage(driver);

		// Search product name
		searchPage.sendKeyToTextboxByID(driver, "q", searchKeyword);

		// Click to 'Advanced search' checkbox
		searchPage.checkToSearchCheckboxByID(driver, "advs");

		// Choose 'Computers' in Category dropdown
		searchPage.selecNopCommerceDropdownByText(driver, "cid", "Computers");

		// Check 'Automatically search sub categories' checkbox
		searchPage.checkToSearchCheckboxByID(driver, "isc");

		// Choose 'HP' in Manufacturer dropdown
		searchPage.selecNopCommerceDropdownByText(driver, "mid", "HP");

		// Click 'Search' button
		searchPage.clickToButtonByName(driver, "Search");

		// Verify message displayed
		Assert.assertEquals(searchPage.getNotificationMessage(), "No products were found that matched your criteria.");
	}

	@Test
	public void TC_08_Advanced_Search_Correct_Manufacturer() {
		// Refresh current page
		searchPage.refreshCurrentPage(driver);

		// Search product name
		searchPage.sendKeyToTextboxByID(driver, "q", searchKeyword);

		// Click to 'Advanced search' checkbox
		searchPage.checkToSearchCheckboxByID(driver, "advs");

		// Choose 'Computers' in Category dropdown
		searchPage.selecNopCommerceDropdownByText(driver, "cid", "Computers");

		// Check 'Automatically search sub categories' checkbox
		searchPage.checkToSearchCheckboxByID(driver, "isc");

		// Choose 'Apple' in Manufacturer dropdown
		searchPage.selecNopCommerceDropdownByText(driver, "mid", "Apple");

		// Click 'Search' button
		searchPage.clickToButtonByName(driver, "Search");

		// Verify size of list product
		Assert.assertEquals(searchPage.getSizeProductSearchList(), 1);

		// Verify product name
		List<WebElement> productNameCorrectManufacturerElements = searchPage.getListNameProductSearchResult();
		Assert.assertEquals(productNameCorrectManufacturerElements.get(0).getText(), "Apple MacBook Pro 13-inch");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		driver.quit();
	}

	HomePageObject homePage;
	LoginPageObject loginPage;
	SearchPageObject searchPage;
}
