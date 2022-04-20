package com.nopcommerce;

import java.util.ArrayList;
import java.util.Collections;
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
import pageObjects.nopcommerce.PageGeneratorManager;
import pageObjects.nopcommerce.SortPageObject;

public class Sort_Display_Paging extends BaseTest {
	WebDriver driver;

	@Parameters({ "browser", "url" })
	@BeforeClass
	public void beforeClass(String browserName, String appUrl) {
		driver = getBrowserDriver(browserName, appUrl);

		homePage = PageGeneratorManager.getHomePage(driver);
		Assert.assertTrue(homePage.isHomePageSliderDisplayed());

		// Navigate to 'Sort' page
		homePage.hoverToMenuLinkByName(driver, "Computers ");
		homePage.clickToMenuLinkByName(driver, "Notebooks ");
		sortPage = PageGeneratorManager.getSortPage(driver);
	}

	@Test
	public void TC_01_Sort_With_A_To_Z() {
		// Select 'Name: A to Z' in sort dropdown
		sortPage.selectItemInDropdownByID(driver, "products-orderby", "Name: A to Z");
		sortPage.sleepInSecond(2);
		List<WebElement> productNameSortAscElements = sortPage.getListNameProductSortResult();
		List<String> productNameSortAscText = new ArrayList<String>();
		for (WebElement productName : productNameSortAscElements) {
			productNameSortAscText.add(productName.getText());
		}

		// Clone a new list
		List<String> productNameSortAscTextClone = new ArrayList<String>(productNameSortAscText);
		Collections.sort(productNameSortAscTextClone);

		// Compare list after sort
		Assert.assertTrue(productNameSortAscText.equals(productNameSortAscTextClone));
	}

	@Test
	public void TC_02_Sort_With_Z_To_A() {
		// Select 'Name: Z to A' in sort dropdown
		sortPage.selectItemInDropdownByID(driver, "products-orderby", "Name: Z to A");
		sortPage.sleepInSecond(2);
		List<WebElement> productNameSortDescElements = sortPage.getListNameProductSortResult();
		List<String> productNameSortDescText = new ArrayList<String>();
		for (WebElement productName : productNameSortDescElements) {
			productNameSortDescText.add(productName.getText());
		}

		// Clone a new list
		List<String> productNameSortDescTextClone = new ArrayList<String>(productNameSortDescText);
		Collections.sort(productNameSortDescTextClone);
		Collections.reverse(productNameSortDescTextClone);

		// Compare list after sort
		Assert.assertTrue(productNameSortDescText.equals(productNameSortDescTextClone));
	}

	@Test
	public void TC_03_Sort_With_Price_Low_To_High() {
		// Select 'Price: Low to High' in sort dropdown
		sortPage.selectItemInDropdownByID(driver, "products-orderby", "Price: Low to High");
		sortPage.sleepInSecond(2);
		List<WebElement> productPriceSortAscElements = sortPage.getListPriceProductSortResult();
		List<String> productPriceSortAscText = new ArrayList<String>();
		for (WebElement productName : productPriceSortAscElements) {
			productPriceSortAscText.add(productName.getText());
		}

		// Clone a new list
		List<String> productPriceSortAscTextClone = new ArrayList<String>(productPriceSortAscText);
		Collections.sort(productPriceSortAscTextClone);

		// Compare list after sort
		Assert.assertTrue(productPriceSortAscText.equals(productPriceSortAscTextClone));
	}

	@Test
	public void TC_04_Sort_With_Price_High_To_Low() {
		// Select 'Price: High to Low' in sort dropdown
		sortPage.selectItemInDropdownByID(driver, "products-orderby", "Price: High to Low");
		sortPage.sleepInSecond(2);
		List<WebElement> productPriceSortDescElements = sortPage.getListPriceProductSortResult();
		List<String> productPriceSortDescText = new ArrayList<String>();
		for (WebElement productName : productPriceSortDescElements) {
			productPriceSortDescText.add(productName.getText());
		}

		// Clone a new list
		List<String> productPriceSortDescTextClone = new ArrayList<String>(productPriceSortDescText);
		Collections.sort(productPriceSortDescTextClone);
		Collections.reverse(productPriceSortDescTextClone);

		// Compare list after sort
		Assert.assertTrue(productPriceSortDescText.equals(productPriceSortDescTextClone));
	}

	@Test
	public void TC_05_Display_With_3_Products() {
		// Select '3' in display dropdown
		sortPage.selectItemInDropdownByID(driver, "products-pagesize", "3");
		sortPage.sleepInSecond(2);

		// Verify <=3 product/ page
		Assert.assertTrue(sortPage.getSizeProductDisplayList() <= 3);

		// Verify paging display
		Assert.assertTrue(sortPage.isPagingDisplay());

		// Verify if current page = 1, 'Next Page' link display
		// Verify if current page = 2, 'Previous Page' link display
		sortPage.clickToNavigatePage(driver, "current-page");
		if (sortPage.getCurrentPageDisplay(driver, "current-page") == 1) {
			Assert.assertTrue(sortPage.isNavigateLinkDisplay(driver, "next-page"));
		}
		sortPage.clickToNavigatePage(driver, "individual-page");
		sortPage.sleepInSecond(2);
		if (sortPage.getCurrentPageDisplay(driver, "individual-page") == 1) {
			Assert.assertTrue(sortPage.isNavigateLinkDisplay(driver, "previous-page"));
		} else {
			Assert.assertTrue(false);
		}
	}

	@Test
	public void TC_06_Display_With_6_Products() {
		// Select '6' in display dropdown
		sortPage.selectItemInDropdownByID(driver, "products-pagesize", "6");
		sortPage.sleepInSecond(2);

		// Verify <=3 product/ page
		Assert.assertTrue(sortPage.getSizeProductDisplayList() <= 6);

		// Verify paging display
		Assert.assertFalse(sortPage.isPagingDisplay());
	}

	@Test
	public void TC_07_Display_With_9_Products() {
		// Select '9' in display dropdown
		sortPage.selectItemInDropdownByID(driver, "products-pagesize", "9");
		sortPage.sleepInSecond(2);

		// Verify <=3 product/ page
		Assert.assertTrue(sortPage.getSizeProductDisplayList() <= 9);

		// Verify paging display
		Assert.assertFalse(sortPage.isPagingDisplay());
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		driver.quit();
	}

	HomePageObject homePage;
	SortPageObject sortPage;
}
