package com.nopcommerce;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.BaseTest;
import pageObjects.nopcommerce.CategoryPageObject;
import pageObjects.nopcommerce.HomePageObject;
import pageObjects.nopcommerce.LoginPageObject;
import pageObjects.nopcommerce.MyAccountPageObject;
import pageObjects.nopcommerce.PageGeneratorManager;
import pageObjects.nopcommerce.RegisterPageObject;

public class MyAccount extends BaseTest {
	WebDriver driver;
	String editFirstName, editLastName, editEmail, editCompanyName, editGenderName, emailAddress, password;
	String editDateOfBirthDay, editDateOfBirthMonth, editDateOfBirthYear, country, state, city;
	String address1, address2, zipcode, phone_number, fax, new_password;
	String review_title, review_text;

	@Parameters({ "browser", "url" })
	@BeforeClass
	public void beforeClass(String browserName, String appUrl) {
		driver = getBrowserDriver(browserName, appUrl);

		// Data test
		editFirstName = "Automation";
		editLastName = "FC";
		editEmail = "automationfc.vn@gmail.com";
		editCompanyName = "Automation FC";
		editGenderName = "F";
		editDateOfBirthDay = "1";
		editDateOfBirthMonth = "January";
		editDateOfBirthYear = "1999";
		country = "Viet Nam";
		state = "Other";
		city = "Da Nang";
		address1 = "123/04 Le Lai";
		address2 = "234/05 Hai Phong";
		zipcode = "550000";
		phone_number = "0123456789";
		fax = "6666666666";
		review_title = "Very Good";
		review_text = "The best mackbook ever seen";
		emailAddress = "tt@gmail.com";
		password = "123456";
		new_password = "Son@181295";

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
		
		// Navigate to 'My account' page
		homePage.clickToLinkByName(driver, "My account");
		myAccountPage = PageGeneratorManager.getMyAccountPage(driver);
	}
	
	@Test
	public void TC_01_Customer_Info() {
		// Open 'Customer info' page
		myAccountPage.openMyAccountPageByName(driver, "Customer info");
		// Verify email registered is correct
		Assert.assertEquals(myAccountPage.getElementAttributeValue(driver, "Email", "value"), emailAddress);
		
		// Update information
		myAccountPage.clickToRadioGenderButton(driver, "gender-female");
		myAccountPage.sendKeyToTextboxByID(driver, "FirstName", editFirstName);
		myAccountPage.sendKeyToTextboxByID(driver, "LastName", editLastName);
		myAccountPage.selecNopCommerceDropdownByText(driver, "DateOfBirthDay", editDateOfBirthDay);
		myAccountPage.selecNopCommerceDropdownByText(driver, "DateOfBirthMonth", editDateOfBirthMonth);
		myAccountPage.selecNopCommerceDropdownByText(driver, "DateOfBirthYear", editDateOfBirthYear);
		// myAccountPage.sendKeyToTextboxByID(driver, "Email", editEmail);
		myAccountPage.sendKeyToTextboxByID(driver, "Company", editCompanyName);
		
		// Click 'Save' button
		myAccountPage.clickToButtonByName(driver, "Save");
		
		// Verify information updated
		Assert.assertEquals(myAccountPage.getElementAttributeValue(driver, "gender-female", "value"), editGenderName);
		Assert.assertEquals(myAccountPage.getElementAttributeValue(driver, "FirstName", "value"), editFirstName);
		Assert.assertEquals(myAccountPage.getElementAttributeValue(driver, "LastName", "value"), editLastName);
		Assert.assertEquals(myAccountPage.getSelectedItemDropdownByText(driver, "DateOfBirthDay"), editDateOfBirthDay);
		Assert.assertEquals(myAccountPage.getSelectedItemDropdownByText(driver, "DateOfBirthMonth"), editDateOfBirthMonth);
		Assert.assertEquals(myAccountPage.getSelectedItemDropdownByText(driver, "DateOfBirthYear"), editDateOfBirthYear);
		// Assert.assertEquals(myAccountPage.getElementAttributeValue(driver, "Email", "value"), editEmail);
		Assert.assertEquals(myAccountPage.getElementAttributeValue(driver, "Company", "value"), editCompanyName);
	}
	
	@Test
	public void TC_02_Addresses() {
		// Open 'Addresses' page
		myAccountPage.openMyAccountPageByName(driver, "Addresses");
		
		// Click 'Add new' button
		myAccountPage.clickToButtonByName(driver, "Add new");
		
		// Input data
		myAccountPage.sendKeyToTextboxByID(driver, "Address_FirstName", editFirstName);
		myAccountPage.sendKeyToTextboxByID(driver, "Address_LastName", editLastName);
		myAccountPage.sendKeyToTextboxByID(driver, "Address_Email", editEmail);
		myAccountPage.sendKeyToTextboxByID(driver, "Address_Company", editCompanyName);
		myAccountPage.selecNopCommerceDropdownByText(driver, "Address.CountryId", country);
		myAccountPage.selecNopCommerceDropdownByText(driver, "Address.StateProvinceId", state);
		myAccountPage.sendKeyToTextboxByID(driver, "Address_City", city);
		myAccountPage.sendKeyToTextboxByID(driver, "Address_Address1", address1);
		myAccountPage.sendKeyToTextboxByID(driver, "Address_Address2", address2);
		myAccountPage.sendKeyToTextboxByID(driver, "Address_ZipPostalCode", zipcode);
		myAccountPage.sendKeyToTextboxByID(driver, "Address_PhoneNumber", phone_number);
		myAccountPage.sendKeyToTextboxByID(driver, "Address_FaxNumber", fax);
		
		// Click 'Save' button
		myAccountPage.clickToButtonByName(driver, "Save");
		
		// Verify information added
		myAccountPage.isUpdatedInformationDiplayedCorrect(driver, "name", editFirstName + " " + editLastName);
		myAccountPage.isUpdatedInformationDiplayedCorrect(driver, "email", editEmail);
		myAccountPage.isUpdatedInformationDiplayedCorrect(driver, "phone", phone_number);
		myAccountPage.isUpdatedInformationDiplayedCorrect(driver, "fax", fax);
		myAccountPage.isUpdatedInformationDiplayedCorrect(driver, "company", editCompanyName);
		myAccountPage.isUpdatedInformationDiplayedCorrect(driver, "address1", address1);
		myAccountPage.isUpdatedInformationDiplayedCorrect(driver, "address2", address2);
		myAccountPage.isUpdatedInformationDiplayedCorrect(driver, "city-state-zip", city + ", " + zipcode);
		myAccountPage.isUpdatedInformationDiplayedCorrect(driver, "country", country);
	}
	
	@Test
	public void TC_03_Change_Password() {
		// Open 'Change password' page
		myAccountPage.openMyAccountPageByName(driver, "Change password");
		
		// Enter old and new password
		myAccountPage.sendKeyToTextboxByID(driver, "OldPassword", password);
		myAccountPage.sendKeyToTextboxByID(driver, "NewPassword", new_password);
		myAccountPage.sendKeyToTextboxByID(driver, "ConfirmNewPassword", new_password);
		
		// Click 'Change password' button
		myAccountPage.clickToButtonByName(driver, "Change password");
		
		// Verify notification success displayed
		myAccountPage.isNotificationSuccessDisplayed();
		myAccountPage.clickToCloseNotification();
		
		// Logout
		myAccountPage.clickToLinkByName(driver, "Log out");
		homePage = PageGeneratorManager.getHomePage(driver);
		
		// Login again
		homePage.clickToLinkByName(driver, "Log in");
		loginPage = PageGeneratorManager.getLoginPage(driver);
		
		// Login with old password
		loginPage.sendKeyToTextboxByID(driver, "Email", emailAddress);
		loginPage.sendKeyToTextboxByID(driver, "Password", password);
		loginPage.clickToButtonByName(driver, "Log in");
		
		// Verify error message
		Assert.assertTrue(loginPage.isErrorEmailMsgDisplayed(driver, "message-error validation-summary-errors", "Login was unsuccessful. Please correct the errors and try again.The credentials provided are incorrect"));

		// Login with new password
		loginPage.sendKeyToTextboxByID(driver, "Password", new_password);
		loginPage.clickToButtonByName(driver, "Log in");
		
		// Login successed
		homePage = PageGeneratorManager.getHomePage(driver);
		homePage.isHomePageSliderDisplayed();
	}
	
	@Test
	public void TC_04_My_Product_Reviews() {
		// Open 'Category' page
		homePage.clickToProductByName(driver, "Apple MacBook Pro 13-inch");
		categoryPage = PageGeneratorManager.getCategoryPage(driver);
		
		// Click to 'Add your review' link
		categoryPage.clickToLinkByName(driver, "Add your review");
		
		// Input review information
		categoryPage.sendKeyToTextboxByID(driver, "AddProductReview_Title", review_title);
		categoryPage.sendKeyToReviewTextTextarea(review_text);
		
		// Click 'Submit review' button
		categoryPage.clickToButtonByName(driver, "Submit review");
		
		// Verify review result
		categoryPage.isReviewMessageResultDisplayed();
		
		// Open 'My account' page
		categoryPage.clickToLinkByName(driver, "My account");
		myAccountPage = PageGeneratorManager.getMyAccountPage(driver);
		
		// Open 'My product reviews' page
		myAccountPage.openMyAccountPageByName(driver, "My product reviews");
		
		// Verify review information
		myAccountPage.isReviewInformationDisplayedCorrect(driver, "review-title", review_title);
		myAccountPage.isReviewInformationDisplayedCorrect(driver, "review-text", review_text);
	}

	
	@AfterClass(alwaysRun = true)
	public void afterClass() {
		driver.quit();
	}

	HomePageObject homePage;
	RegisterPageObject registerPage;
	LoginPageObject loginPage;
	MyAccountPageObject myAccountPage;
	CategoryPageObject categoryPage;
}

