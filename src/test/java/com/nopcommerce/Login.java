package com.nopcommerce;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import commons.BaseTest;
import pageObjects.nopcommerce.HomePageObject;
import pageObjects.nopcommerce.LoginPageObject;
import pageObjects.nopcommerce.PageGeneratorManager;
import pageObjects.nopcommerce.RegisterPageObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

public class Login extends BaseTest {
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

		homePage.clickToLinkByName(driver, "Log in");
		loginPage = PageGeneratorManager.getLoginPage(driver);
	}

	// Nó sẽ thực thi sau mỗi lần thực thi testcase (@Test)
	@AfterMethod
	public void takeScreenshot(ITestResult result) {
		// Khởi tạo đối tượng result thuộc ITestResult để lấy trạng thái và tên của từng Step
		// Ở đây sẽ so sánh điều kiện nếu testcase passed hoặc failed
		// passed = SUCCESS và failed = FAILURE
		if (ITestResult.FAILURE == result.getStatus()) {
			try {
				// Tạo tham chiếu của TakesScreenshot
				TakesScreenshot ts = (TakesScreenshot) driver;
				// Gọi hàm capture screenshot - getScreenshotAs
				File source = ts.getScreenshotAs(OutputType.FILE);
				//Kiểm tra folder tồn tại. Nêu không thì tạo mới folder
				File theDir = new File("./Screenshots/");
				if (!theDir.exists()) {
					theDir.mkdirs();
				}
				// result.getName() lấy tên của test case xong gán cho tên File chụp màn hình
				FileHandler.copy(source, new File("./Screenshots/" + result.getName() + ".png"));
				System.out.println("Đã chụp màn hình: " + result.getName());
			} catch (Exception e) {
				System.out.println("Exception while taking screenshot " + e.getMessage());
			}
		}
	}
	
	@Test
	public void TC_01_Login_With_Empty_Data() {
		loginPage.clickToButtonByName(driver, "Log in");
		Assert.assertTrue(loginPage.isValidateMessageDisplayed(driver, "Email-error", "Please enter your email"));
	}

	@Test
	public void TC_02_Login_With_Invalid_Email() {
		loginPage.refreshCurrentPage(driver);
		loginPage.sendKeyToTextboxByID(driver, "Email", "abcd1234");
		loginPage.sendKeyToTextboxByID(driver, "Password", password);
		loginPage.clickToButtonByName(driver, "Log in");
		Assert.assertTrue(loginPage.isValidateMessageDisplayed(driver, "Email-error", "Wrong email"));
	}
	
	@Test
	public void TC_03_Login_With_Not_Registed_Email() {
		loginPage.refreshCurrentPage(driver);
		loginPage.sendKeyToTextboxByID(driver, "Email", "asfa@gmail.com");
		loginPage.sendKeyToTextboxByID(driver, "Password", password);
		loginPage.clickToButtonByName(driver, "Log in");
		Assert.assertTrue(loginPage.isErrorEmailMsgDisplayed(driver, "message-error validation-summary-errors", "Login was unsuccessful. Please correct the errors and try again.No customer account found"));
	}
	
	@Test
	public void TC_04_Login_With_Registed_Email_And_Blank_Password() {
		loginPage.refreshCurrentPage(driver);
		loginPage.sendKeyToTextboxByID(driver, "Email", emailAddress);
		loginPage.sendKeyToTextboxByID(driver, "Password", "");
		loginPage.clickToButtonByName(driver, "Log in");
		Assert.assertTrue(loginPage.isErrorEmailMsgDisplayed(driver, "message-error validation-summary-errors", "Login was unsuccessful. Please correct the errors and try again.The credentials provided are incorrect"));
	}
	
	@Test
	public void TC_05_Login_With_Registed_Email_And_Incorrect_Password() {
		loginPage.refreshCurrentPage(driver);
		loginPage.sendKeyToTextboxByID(driver, "Email", emailAddress);
		loginPage.sendKeyToTextboxByID(driver, "Password", "11111111111");
		loginPage.clickToButtonByName(driver, "Log in");
		Assert.assertTrue(loginPage.isErrorEmailMsgDisplayed(driver, "message-error validation-summary-errors", "Login was unsuccessful. Please correct the errors and try again.The credentials provided are incorrect"));
	}
	
	@Test
	public void TC_06_Login_With_Valid_Data() {
		loginPage.refreshCurrentPage(driver);
		loginPage.sendKeyToTextboxByID(driver, "Email", emailAddress);
		loginPage.sendKeyToTextboxByID(driver, "Password", password);
		loginPage.clickToButtonByName(driver, "Log in");
		homePage = PageGeneratorManager.getHomePage(driver);
		homePage.isHomePageSliderDisplayed();
	}
	
	@AfterClass(alwaysRun = true)
	public void afterClass() {
		driver.quit();
	}

	HomePageObject homePage;
	RegisterPageObject registerPage;
	LoginPageObject loginPage;
}
