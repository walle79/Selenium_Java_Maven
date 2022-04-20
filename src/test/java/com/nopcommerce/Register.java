package com.nopcommerce;

import org.testng.annotations.Test;

import commons.BaseTest;
import pageObjects.nopcommerce.HomePageObject;
import pageObjects.nopcommerce.LoginPageObject;
import pageObjects.nopcommerce.PageGeneratorManager;
import pageObjects.nopcommerce.RegisterPageObject;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.util.Random;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class Register extends BaseTest {
	WebDriver driver;
	String firstName, lastName, emailAddress, password;

	@Parameters({ "browser", "url" })
	@BeforeClass
	public void beforeClass(String browserName, String appUrl) {
		driver = getBrowserDriver(browserName, appUrl);

		firstName = "hong";
		lastName = "son";
		emailAddress = "son" + getRandomEmail();
		password = "123456";

		homePage = PageGeneratorManager.getHomePage(driver);
		Assert.assertTrue(homePage.isHomePageSliderDisplayed());

		homePage.clickToLinkByName(driver, "Register");
		registerPage = PageGeneratorManager.getRegisterPage(driver);
	}

	@Test
	public void TC_01_Register_With_Empty_Data() {
		registerPage.clickToButtonByName(driver, "Register");
		Assert.assertTrue(
				registerPage.isValidateMessageDisplayed(driver, "FirstName-error", "First name is required."));
		Assert.assertTrue(registerPage.isValidateMessageDisplayed(driver, "LastName-error", "Last name is required."));
		Assert.assertTrue(registerPage.isValidateMessageDisplayed(driver, "Email-error", "Email is required."));
		Assert.assertTrue(registerPage.isValidateMessageDisplayed(driver, "Password-error", "Password is required."));
		Assert.assertTrue(
				registerPage.isValidateMessageDisplayed(driver, "ConfirmPassword-error", "Password is required."));
	}

	@Test
	public void TC_02_Register_With_Invalid_Email() {
		registerPage.refreshCurrentPage(driver);
		registerPage.clickToRadioGenderButton(driver, "gender-male");
		registerPage.sendKeyToTextboxByID(driver, "FirstName", firstName);
		registerPage.sendKeyToTextboxByID(driver, "LastName", lastName);
		registerPage.sendKeyToTextboxByID(driver, "Email", "ta@@gmail.com");
		registerPage.sendKeyToTextboxByID(driver, "Password", password);
		registerPage.sendKeyToTextboxByID(driver, "ConfirmPassword", password);
		Assert.assertTrue(registerPage.isValidateMessageDisplayed(driver, "Email-error", "Wrong email"));
	}

	@Test
	public void TC_03_Register_Less_Than_6_Characters_Password() {
		registerPage.refreshCurrentPage(driver);
		registerPage.clickToRadioGenderButton(driver, "gender-male");
		registerPage.sendKeyToTextboxByID(driver, "FirstName", firstName);
		registerPage.sendKeyToTextboxByID(driver, "LastName", lastName);
		registerPage.sendKeyToTextboxByID(driver, "Email", emailAddress);
		registerPage.sendKeyToTextboxByID(driver, "Password", "1234");
		registerPage.sendKeyToTextboxByID(driver, "ConfirmPassword", password);
		//Assert.assertTrue(registerPage.isValidateMessageDisplayed(driver, "Password-error", "Password must meet the following rules:\n"
		//		+ "\n"
		//		+ "must have at least 6 characters"));

	}

	@Test
	public void TC_04_Register_Confirm_Password_Incorrect() {
		registerPage.refreshCurrentPage(driver);
		registerPage.clickToRadioGenderButton(driver, "gender-male");
		registerPage.sendKeyToTextboxByID(driver, "FirstName", firstName);
		registerPage.sendKeyToTextboxByID(driver, "LastName", lastName);
		registerPage.sendKeyToTextboxByID(driver, "Email", emailAddress);
		registerPage.sendKeyToTextboxByID(driver, "Password", password);
		registerPage.sendKeyToTextboxByID(driver, "ConfirmPassword", "1234556");
		registerPage.clickToButtonByName(driver, "Register");
		Assert.assertTrue(registerPage.isValidateMessageDisplayed(driver, "ConfirmPassword-error", "The password and confirmation password do not match."));

	}
	
	@Test
	public void TC_05_Register_With_Exist_Email() {
		registerPage.refreshCurrentPage(driver);
		registerPage.clickToRadioGenderButton(driver, "gender-male");
		registerPage.sendKeyToTextboxByID(driver, "FirstName", firstName);
		registerPage.sendKeyToTextboxByID(driver, "LastName", lastName);
		registerPage.sendKeyToTextboxByID(driver, "Email", "ta@gmail.com");
		registerPage.sendKeyToTextboxByID(driver, "Password", password);
		registerPage.sendKeyToTextboxByID(driver, "ConfirmPassword", password);
		registerPage.clickToButtonByName(driver, "Register");
		Assert.assertTrue(registerPage.isErrorEmailMsgDisplayed(driver, "message-error validation-summary-errors", "The specified email already exists"));

	}

	@Test
	public void TC_06_Register_With_Valid_Data() {
		registerPage.refreshCurrentPage(driver);
		registerPage.clickToRadioGenderButton(driver, "gender-male");
		registerPage.sendKeyToTextboxByID(driver, "FirstName", firstName);
		registerPage.sendKeyToTextboxByID(driver, "LastName", lastName);
		registerPage.sendKeyToTextboxByID(driver, "Email", emailAddress);
		registerPage.sendKeyToTextboxByID(driver, "Password", password);
		registerPage.sendKeyToTextboxByID(driver, "ConfirmPassword", password);
		registerPage.clickToButtonByName(driver, "Register");
		Assert.assertTrue(registerPage.isSuccessMsgDisplayed());
	}


	@AfterClass(alwaysRun = true)
	public void afterClass() {
		driver.quit();
	}

	public String getRandomEmail() {
		Random random = new Random();
		return "testing" + random.nextInt(99999) + "@gmail.com";
	}

	HomePageObject homePage;
	RegisterPageObject registerPage;
	LoginPageObject loginPage;

}
