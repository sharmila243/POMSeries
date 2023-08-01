package com.qa.opencart.driverfactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.safari.SafariDriver;

/**
 * This is the Java class to initialize driver and properties
 * 
 * @author guddu
 *
 */

public class DriverFactory {

	WebDriver driver;
	Properties prop;
	OptionsManager optionsManager;

	public static String highlight;

	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

	/**
	 * This method is used to initialize the driver
	 * 
	 * @param browserName
	 * @return it returns browser
	 */
	public WebDriver initDriver(Properties prop) {
		String browserName = prop.getProperty("browser");
		System.out.println("browserName is: " + browserName);

		highlight = prop.getProperty("highlight");
		optionsManager = new OptionsManager(prop);

		switch(browserName.toLowerCase()) {
		case "chrome":
			// driver = new ChromeDriver(optionsManager.getChromeOptions());

			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions())); // Generate - ThreadLocal copy of the driver
			break;
		case "firefox":
			// driver = new FirefoxDriver(optionsManager.getFirefoxOptions());

			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			break;
		case "edge":
			// driver = new EdgeDriver(optionsManager.getEdgeOptions());

			tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			break;
		case "safari":
			// driver = new SafariDriver();

			tlDriver.set(new SafariDriver());
			break;
		default:
			System.out.println("Please pass the right browser: " + browserName);
			break;
		}

		getDriver().manage().window().maximize();
		getDriver().manage().deleteAllCookies();
		getDriver().get(prop.getProperty("url"));

		return getDriver();

	}

	public static WebDriver getDriver() {
		return tlDriver.get();
	}

	/**
	 * This method is used to initialize the properties
	 * 
	 * @return - returns Properties reference
	 */
	public Properties initProperties() {

		FileInputStream ip = null;
		prop = new Properties();

		// mvn clean install -Denv="qa"

		// -D is the option with the maven command and -D is the env variable
		// env is the variable name and qa is the value

		// In java whenever we have to read the env variables System class is
		// responsible for reading the above line L92

		String envName = System.getProperty("env");
		System.out.println("Executing in: " + envName + " environment");

		try {
			if (envName == null) {
				System.out.println("No env is given.. hence running it on QA env..");
			
					ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
			} 
			else {
				System.out.println("Executing in: " + envName);
				switch (envName.toLowerCase().trim()) {
				case "qa":
					ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
					break;
				case "stag":
					ip = new FileInputStream("./src/test/resources/config/stag.config.properties");
					break;
				case "dev":
					ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
					break;
				case "uat":
					ip = new FileInputStream("./src/test/resources/config/uat.config.properties");
					break;
				case "prod":
					ip = new FileInputStream("./src/test/resources/config/config.properties");
					break;

				default:
					System.out.println("Please pass the right env: " + envName);
					break;
				}
			}
		}catch(FileNotFoundException e) {
				e.printStackTrace(); 
			
			
		}
		
		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return prop;
	}
	
	/**
	 * TakesScreenshot - is the interface
	 * RemoteWebDriver - class is responsible in implementing the TakesScreenshot Interface method
	 * Overridden method is getScreenshotAs() - responsible for taking screenshot
	 * Take Screenshot
	 */
	public static String getScreenshot(String methodName) {
		File srcFile = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir")+"/screenshot/"+methodName+"_"+System.currentTimeMillis()+".png";
		//Move the screenshot from srcFile --> String Path location, possible?
		//NO, both are of different types and is not possible
		
		File destinationFile = new File(path); //destinationFile is now pointing to String path
		
		try {
			FileHandler.copy(srcFile, destinationFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return path;
	}

}
