package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstant;

public class LoginPageTest extends BaseTest{
	
	@Test
	public void loginPageTitleTest() {
		String actTitle = loginPage.getLoginPageTitle();
		Assert.assertEquals(actTitle, AppConstant.LOGIN_PAGE_TITLE);
	}
	
	@Test
	public void URLTest() {
		String actURL = loginPage.getLoginPageURL();
		Assert.assertTrue(actURL.contains(AppConstant.LOGIN_PAGE_URL_FRACTION));
	}
	
	@Test
	public void forgotPwdLinkExistenceTest() {
		Assert.assertTrue(loginPage.isForgotPwdLinkExist());
	}
	
	@Test
	public void loginTest() {
//		String accPageTitle = loginPage.doLogin("janautomation@gmail.com", "Selenium@12345");
//		Assert.assertEquals(accPageTitle, "My Account");
		
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		Assert.assertEquals(accPage.getAccPageTitle(), AppConstant.ACCOUNTS_PAGE_TITLE);
	}

}
