package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstant;
import com.qa.opencart.utils.ElementUtil;

public class RegisterPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;

	public RegisterPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	private By firstName = By.id("input-firstname");
	private By lastName = By.id("input-lastname");
	private By email = By.id("input-email");
	private By telephone = By.id("input-telephone");
	private By password = By.id("input-password");
	private By confirmPassword = By.id("input-confirm");

	
	private By subscribeYes = By.xpath("//label[normalize-space()='Yes']");
	private By subscribeNo = By.xpath("//label[normalize-space()='No']");
	
	
	private By privacyPolicy = By.xpath("//input[@type = 'checkbox']");
	private By continueBtn = By.xpath("//input[@value = 'Continue']");
	
	
	private By successMsg = By.cssSelector("div#content h1");
	private By logoutLink = By.linkText("Logout");
	private By registerLink = By.linkText("Register");

	
	public boolean registerUser(String firstName, String lastName, String email, String telephone, String password, String subscribe) {
		eleUtil.waitForElementVisible(this.firstName, AppConstant.MEDIUM_TIME_OUT).sendKeys(firstName);;
		eleUtil.doSendkeys(this.lastName, lastName);
		eleUtil.doSendkeys(this.email, email);
		eleUtil.doSendkeys(this.telephone, telephone);
		eleUtil.doSendkeys(this.password, password);
		eleUtil.doSendkeys(this.confirmPassword, password);
		
			if(subscribe.equalsIgnoreCase("yes")) {
				eleUtil.doClick(subscribeYes);
			}
			else {
				eleUtil.doClick(subscribeNo);
			}
			
		eleUtil.doActionsClick(privacyPolicy);
		eleUtil.doClick(continueBtn);
		
		String successMsg = eleUtil.waitForElementVisible(this.successMsg, AppConstant.MEDIUM_TIME_OUT).getText();
		System.out.println(successMsg);
			if(successMsg.contains(AppConstant.USER_REGISTER_SUCCESS_MSG)) {
				eleUtil.doClick(logoutLink);
				eleUtil.doClick(registerLink);
				return true;
			}
		
			return false;	
	}
	
	

}
