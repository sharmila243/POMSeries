package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class RegisterPageTest extends BaseTest {
	
	@BeforeClass
	public void regSetup() {
		registerPage = loginPage.navigateToRegisterPage();
	}
	
	public String getRandomEmailId() {
		return "openauto"+System.currentTimeMillis()+"@open.com"; 
	}

	
	@DataProvider
	public Object[][] getUserRegistrationData() {
		return new Object[][] {
			{"Bhargav", "K", "9876543210", "Test@123", "yes"},
			{"Veda", "K", "99887766655", "abcd@123", "no"},
			{"Surya", "B", "9090909090", "xyza@123", "no"}
		};
	}
	
	
	@Test(dataProvider = "getUserRegistrationData")
	public void userRegistrationTest(String fn, String ln, String ph, String pwd, String sub) {
		Assert.assertTrue(registerPage.registerUser(fn, ln, getRandomEmailId(), ph, pwd, sub));
	}
	
	
}
