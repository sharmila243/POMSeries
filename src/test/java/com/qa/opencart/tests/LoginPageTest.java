package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstant;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;


@Epic("EPIC - 100: Design of the LoginPage for OpenCart Application")
@Story("US - 200: implement login Page features with OpenCart Application")

public class LoginPageTest extends BaseTest{
	
	@Description("Login Page title test...")
	@Severity(SeverityLevel.MINOR)
	@Test
	public void loginPageTitleTest() {
		String actTitle = loginPage.getLoginPageTitle();
		Assert.assertEquals(actTitle, AppConstant.LOGIN_PAGE_TITLE);
	}
	
	@Description("Login Page URL test...")
	@Severity(SeverityLevel.NORMAL)
	@Test
	public void URLTest() {
		String actURL = loginPage.getLoginPageURL();
		Assert.assertTrue(actURL.contains(AppConstant.LOGIN_PAGE_URL_FRACTION));
	}
	
	@Description("Checks if forgot password link exists or not")
	@Severity(SeverityLevel.CRITICAL)
	@Test
	public void forgotPwdLinkExistenceTest() {	
		Assert.assertTrue(loginPage.isForgotPwdLinkExist());
	}
	
	@Description("Checks user could be able to login with valid credentials")
	@Severity(SeverityLevel.BLOCKER)
	@Test
	public void loginTest() {
//		String accPageTitle = loginPage.doLogin("janautomation@gmail.com", "Selenium@12345");
//		Assert.assertEquals(accPageTitle, "My Account");
		
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		Assert.assertEquals(accPage.getAccPageTitle(), AppConstant.ACCOUNTS_PAGE_TITLE);
	}

}
