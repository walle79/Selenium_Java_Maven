package com.nopcommerce;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.BaseTest;
import pageObjects.nopcommerce.HomePageObject;
import pageObjects.nopcommerce.LoginPageObject;
import pageObjects.nopcommerce.PageGeneratorManager;
import pageObjects.nopcommerce.WishlistPageObject;

public class Wishlist_Compare_RecentView extends BaseTest {
	WebDriver driver;
	String emailAddress, password;

	@Parameters({ "browser", "url" })
	@BeforeClass
	public void beforeClass(String browserName, String appUrl) {
		driver = getBrowserDriver(browserName, appUrl);
		
		emailAddress = "ta@gmail.com";
		password = "123456";

		homePage = PageGeneratorManager.getHomePage(driver);
		Assert.assertTrue(homePage.isHomePageSliderDisplayed());

		// Navigate to 'Log in' page
		homePage.clickToLinkByName(driver, "Log in");
		loginPage = PageGeneratorManager.getLoginPage(driver);
		loginPage.sendKeyToTextboxByID(driver, "Email", emailAddress);
		loginPage.sendKeyToTextboxByID(driver, "Password", password);
		loginPage.clickToButtonByName(driver, "Log in");
		homePage = PageGeneratorManager.getHomePage(driver);
		homePage.isHomePageSliderDisplayed();
	}

	@Test
	public void TC_01_Add_To_Wishlist() {
		// Navigate to 'Wishlist' page
		homePage.clickToProductByName(driver, "Build your own computer");
		wishlistPage = PageGeneratorManager.getWishlistPage(driver);
		wishlistPage.sleepInSecond(2);
	}

	@Test
	public void TC_02_Add_Product_To_Cart_From_Wishlist_Page() {

	}

	@Test
	public void TC_03_Remove_Product_In_Wishlist_Page() {

	}

	@Test
	public void TC_04_Add_Product_To_Compare() {

	}

	@Test
	public void TC_05_Recently_Viewed_Product() {

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		driver.quit();
	}

	HomePageObject homePage;
	LoginPageObject loginPage;
	WishlistPageObject wishlistPage;
}
