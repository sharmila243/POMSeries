package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstant;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage { //Example of Encapsulation
		//PageClass/ PageLibrary/ PageObject for the Login Page
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	//1. private By locators - page locators/page elements
	
	private By emailId = By.id("input-email");
	private By password = By.id("input-password");
	private By loginBtn = By.xpath("//input[contains(@class, 'btn-primary')]");
	private By forgotPwdLink = By.linkText("Forgotten Password11");
	private By registerLink = By.linkText("Register");
	
	
	//2. public Page Constructor - we'll get the driver using the constructor
	//To perform any kind of actions or create a webElement - we require driver, which constructor helps us to initialize
		//LoginPage is not responsible for initializing the driver, driver = new ChromeDriver();
		//Whenever we're creating the object of the LoginPage, we're supposed to give the driver, session id = 123
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	
	//3. public Page Actions - behavior/methods
	@Step("...Getting loginPage title...")
	public String getLoginPageTitle() {
		String title = eleUtil.waitForTitleIs(AppConstant.LOGIN_PAGE_TITLE, AppConstant.SHORT_TIME_OUT);
		System.out.println(title);
		return title;
	}
	
	@Step("...Getting loginPage URL...")
	public String getLoginPageURL() {
		String url = eleUtil.waitForURLContains(AppConstant.SHORT_TIME_OUT, AppConstant.LOGIN_PAGE_URL_FRACTION);
		System.out.println(url);
		return url;
	}	
	
	@Step("...Existence of forgot password link...")	
	public boolean isForgotPwdLinkExist() {	
		return eleUtil.waitForElementVisible(forgotPwdLink, AppConstant.MEDIUM_TIME_OUT).isDisplayed();
	}
	
	@Step("...Logging in with valid credentials- username:{0} and password:{1}...")
	public AccountsPage doLogin(String userName, String pwd) {
		System.out.println("App Credentials are: " +userName +":"+ pwd );
		
		eleUtil.waitForElementVisible(emailId, AppConstant.MEDIUM_TIME_OUT).sendKeys(userName);
		eleUtil.doSendkeys(password, pwd);
		eleUtil.doClick(loginBtn);
		
//		String resultTitle = eleUtil.waitForTitleIs(AppConstant.ACCOUNTS_PAGE_TITLE, AppConstant.LONG_TIME_OUT);
//		System.out.println(resultTitle);
//		return resultTitle;
		
		return new AccountsPage(driver);
	}
	
	@Step("...Navigating to Registration Page...")
	public RegisterPage navigateToRegisterPage() {
		eleUtil.waitForElementToBeClickable(AppConstant.SHORT_TIME_OUT, registerLink);
		
		return new RegisterPage(driver);
	}
	
}
	