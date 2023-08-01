package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.driverfactory.DriverFactory;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegisterPage;
import com.qa.opencart.pages.SearchResultsPage;

public class BaseTest {
	
	protected WebDriver driver;
	protected Properties prop;
	protected LoginPage loginPage; //Access Modifier - default [we cannot access beyond this package] so change it to protected
	protected AccountsPage accPage;
	protected SearchResultsPage searchResPage;
	protected ProductInfoPage productInfoPage;
	protected RegisterPage registerPage;
	DriverFactory df;
	
	protected SoftAssert softAssert; //SoftAssert - non-Static, so created the object 
	
	@BeforeTest
	public void setup() {
//		driver = new ChromeDriver();
//		driver.manage().window().maximize();
//		driver.manage().deleteAllCookies();
//		driver.get("https://naveenautomationlabs.com/opencart/index.php?route=account/login");
		
		df = new DriverFactory();		
		prop = df.initProperties();
		//to get the access to the initProperties method, df.initProperties and as it is returning prop, assign it to Properties reference prop
		
		driver = df.initDriver(prop); //call by Reference - calling the method by passing the reference
		//Instead of passing chrome here, we can directly pass the Properties reference
		//because we might have lots of properties, like username, password, url, highlight, headless, incognito etc 
				//In the initDriver method in driverFactory we cannot pass all the properties String browser, String url etc as comma separated
				//Instead of doing that passing all the properties using comma separated we can directly pass the properties reference variable 
				//and access whatever property required by using getProperty() method.
		
		loginPage = new LoginPage(driver);//session id = 123
		
		softAssert = new SoftAssert();
	}
	
	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}

}
