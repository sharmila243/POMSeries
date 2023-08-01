package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.driverfactory.DriverFactory;

//import CustomException.FrameworkException;

public class ElementUtil {

	
	private WebDriver driver;
	private Actions action;
	
	private JavaScriptUtil jsUtil;
	
	
	public ElementUtil(WebDriver driver) { //give me the driver
		this.driver = driver;
		action = new Actions(driver); 
		//When someone is creating the object of ElementUtil along with that Actions class will also be initialized with the same driver session ID
		
		jsUtil = new JavaScriptUtil(driver);
	}
	
	
	
	
	
	
	//if the methods are static in nature, only a single copy will be given to CMA 
		//So driver also will be static here 
		//driver also goes to CMA
		//We cannot use it for parallel execution
	public void doSendkeys(By locator, String value) {
		
		if(value == null) {
			System.out.println("Null Values Not Allowed in SendKeys");
			//throw new FrameworkException("ValueCannotBeNull");
		}
		
		getElement(locator).sendKeys(value);;
	}
	
	
	public void doClick(By locator) {
		getElement(locator).click();
	}
	
	
	public String doElementGetText(By locator) {
		String eleText = getElement(locator).getText();
		System.out.println("Element text is: " + eleText);
		return eleText;
	}
	
	
	public boolean checkElementIsDisplayed(By locator) {
		return getElement(locator).isDisplayed();
	}
	
	
	public String getElementAttribute(By locator, String attributeName) {
		return getElement(locator).getAttribute(attributeName);
	}
	
	
	public boolean checkElementIsDisabled(By locator) {
//		boolean flag = getElement(locator).isEnabled(); 
//			if(flag == true) {
//				System.out.println("Element is not disabled");
//				return true;
//			}else {
//				System.out.println("Element is disabled");
//				return false;
//			}
		
		String disabled_val = getElement(locator).getAttribute("disabled");
			if(disabled_val.equals("true")) {
				return true;
			}else {
				return false;
			}
	}
	
	
	public WebElement getElement(By locator) {
		
		WebElement element = driver.findElement(locator);
		
		if(Boolean.parseBoolean(DriverFactory.highlight)) {
			jsUtil.flash(element);
		}
		
//		WebElement element = null;
		
		//Initially element is null, if the locator is correct and element is found, then return the element 
			//if not, if the locator passed is wrong, catch it in the catch block and give the Exception
		
		//Advantage of try catch block is that, element may not be available immediately, we might add thread.sleep() 
			//If element is not found immediately it will throw the exception that is why we add thread.sleep, asking page to wait till it loads 
			//If the element is found then, we are giving another opportunity to find the element
		
//		try {
//			element = driver.findElement(locator); //how do we supple driver to this class? - Create a Constructor
//		}
//		catch(NoSuchElementException e) {
//			System.out.println("Exception Found.. Check the locator again");
//			e.printStackTrace();
//			
//			try {
//				Thread.sleep(5000);
//			} catch (InterruptedException e1) {
//				e1.printStackTrace();
//			}
//			element = driver.findElement(locator); //If the element is found after waiting for 5 sec, then it's fine
													//If not?? this line throws the exception, we need to mention in a try catch block, otherwise we will not be able to proceed further 
															//- which is nothing but a chain
													//We'll solve this with explicitly wait
		
		
		return element;
	}
	
	
	public List<String> getElementsTextList(By locator) { //not applicable for images
		List<WebElement> eleList = getElements(locator);
		List<String> eleTextList = new ArrayList<String>();
			for(WebElement e: eleList) {
				String text = e.getText();
				if(!text.isEmpty()) {
					eleTextList.add(text);	
				}
			}
			return eleTextList;
			//we're returning this because we can validate it
	}
	
	
	public void clickOnElement(By locator, String linkText, String attrName) {
		List<WebElement> links = getElements(locator);
		System.out.println("Total no. of Links: " +links.size());
		
		for(WebElement e: links) {
			String text = e.getText();
			String href_attr = e.getAttribute(attrName);
			System.out.println(href_attr +" : "+ text);
				if(text.equals(linkText)) {
					e.click();
					break;
				}	
			}
	}

	
	public void doSearch(By searchSuggLocator, By searchLocator, String searchKey, String suggName) {
		
		doSendkeys(searchLocator, searchKey);
		
		List<WebElement> suggList = getElements(searchSuggLocator);
		System.out.println(suggList.size());
		
		
		for(WebElement e: suggList) {
			String text = e.getText();
			System.out.println(text);
			
				if(text.contains(suggName)) {
					e.click();
					break;	
				}
		}
				
	}

	
	public int getElementsCount(By locator) {
		return getElements(locator).size();
	}
	
	
	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}
	
	//***********************************************DropDown Utils***********************************************//
	
	public void doSelectDropDownByIndex(By locator, int index) {
		
		if(index < 0) {
			System.out.println("Please pass a positive number");
			return;
		}
		
		Select select = new Select(getElement(locator));		
		select.selectByIndex(index);
	}
	
	
	public void doSelectDropDownByVisibleText(By locator, String visibleText) {
		
		if(visibleText == null) {
			System.out.println("visibleText cannot be null");
			return;
		}
		
		Select select = new Select(getElement(locator));		
		select.selectByVisibleText(visibleText);
	}
	

	public void doSelectDropDownByValue(By locator, String value) {
		
		if(value == null) {
			System.out.println("value cannot be null");
			return;
		}
		
		Select select = new Select(getElement(locator));		
		select.deselectByValue(value);
	}
	
	
	public int getDropDownOptionCount(By locator) {
		Select select = new Select(getElement(locator));
			return select.getOptions().size();
	}
	
	
	public List<String> getDropDownTextList(By locator) {
		Select select = new Select(getElement(locator));
		List<WebElement> optionsList = select.getOptions();
		
		List<String> optionsTextList = new ArrayList<String>();
		
		for(WebElement e: optionsList) {
			String text = e.getText();
			System.out.println(text);
			optionsTextList.add(text);
		}
		
		return optionsTextList;
		
	}
	
	
	public void doSelectDropDownValue(By locator, String value) {
		Select select = new Select(getElement(locator));
		List<WebElement> optionsList = select.getOptions();

		for(WebElement e: optionsList) {
			String text = e.getText();
			System.out.println(text);
				if(text.equals(value)) {
					e.click();
					break;
					}
		}
	}

	
	public void doSelectDropDownUsingLocator(By locator, String dropDownValue) {
		List<WebElement> optionsList = getElements(locator);
	
		for(WebElement e: optionsList) {
			String text = e.getText();
			System.out.println(text);
		
				if(text.equals(dropDownValue)) {
					e.click();
					break;
				}
		}
		
	}
	
	//***********************************************Actions Utils***********************************************//
	
	public WebElement getLinkElementByText(String linkText) {
		return driver.findElement(By.linkText(linkText));
	}
	
	public void doActionsSendkeys(By locator, String value) {
		action.sendKeys(getElement(locator), value).perform();
	}
	
	public void doActionsClick(By locator) {
		action.click(driver.findElement(locator)).perform();
	}
	
	public void selectRightClickOption(By contextMenuLocator, String optionValueToClick) {
		action.contextClick(getElement(contextMenuLocator)).perform();
		
		By optionLocator = By.xpath("//*[text() = '"+optionValueToClick+"']"); 
		//getElement(optionLocator).click();
		doClick(optionLocator);
	
	}
	
	public void twoLevelMenuHandling(By level1MenuLocator, By level2MenuLocator) throws InterruptedException {
		action.moveToElement(getElement(level1MenuLocator)).perform();
		
		Thread.sleep(1000);
		
		doClick(level2MenuLocator);
	}
	
	public void multiLevelMenuHandling(By level1MenuLocator, String level2Option, String level3Option) throws InterruptedException {
		action.moveToElement(getElement(level1MenuLocator)).build().perform();
		Thread.sleep(1000);
		
		action.moveToElement(getLinkElementByText(level2Option)).build().perform();
		Thread.sleep(1000);
		
		
		getLinkElementByText(level3Option).click();
		
	}
	
	public void multiLevelMenuHandling(By level1MenuLocator, String level2Option, String level3Option, String level4Option) throws InterruptedException {
		action.moveToElement(getElement(level1MenuLocator)).build().perform();
		Thread.sleep(1000);
		
		action.moveToElement(getLinkElementByText(level2Option)).build().perform();
		Thread.sleep(1000);
		
		action.moveToElement(getLinkElementByText(level3Option)).build().perform();
		Thread.sleep(1000);
		
		getLinkElementByText(level4Option).click();
		
	}
	
	//***********************************************Wait Utils***********************************************//
	
	/**
	 * An expectation for checking that an element is present on the DOM of a page. 
	 * This does not necessarily mean that the element is visible.
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public WebElement waitForElementPresence(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	
	public WebElement waitForElementPresence(By locator, int timeOut, int pollingTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(pollingTime));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	/**
	 * An expectation for checking that an element is present on the DOM of a page and visible. 
	 * Visibility means that the element is not only displayed but also has a height and width that is greater than 0.
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public WebElement waitForElementVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	public WebElement waitForElementVisible(By locator, int timeOut, int pollingTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(pollingTime));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	
	
	/**
	 * An expectation for checking an element is visible and enabled such that you can click it.
	 * @param timeOut
	 * @param locator
	 */
	public void waitForElementToBeClickable(int timeOut, By locator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();;
	}
	
	
	
	/**
	 * An expectation for checking that the title contains a case-sensitive substring
	 * @param timeOut
	 * @param titleFraction
	 * @return
	 */
	public String waitForTitleContains(int timeOut, String titleFraction) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
		try {
			if(wait.until(ExpectedConditions.titleContains(titleFraction))) {
				return driver.getTitle(); //if true, it will return driver.getTitle()
			}else {
				System.out.println(titleFraction + "Title value is not present");
				return null; //if false
			}
		}catch(Exception e) {
			System.out.println(titleFraction + "Title value is not present");
			e.printStackTrace();
			return null; //If if() condition gives exception, it comes to catch block
		}
	}
	
	/**
	 * An expectation for checking the title of a page.
	 * @param timeOut
	 * @param titleValue
	 * @return
	 */
	public String waitForTitleIs(String titleValue, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
		try {
			if(wait.until(ExpectedConditions.titleIs(titleValue))) {
				return driver.getTitle(); 
			}else {
				System.out.println(titleValue + "Title value is not present");
				return null; 
			}
		}catch(Exception e) {
			System.out.println(titleValue + "Title value is not present");
			e.printStackTrace();
			return null; 
		}
	}
	/**
	 * An expectation for the URL of the current page to contain specific text.
	 * @param timeOut
	 * @param urlFraction
	 * @return
	 */
	public String waitForURLContains(int timeOut, String urlFraction) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut)) ;
		
		try {
			if(wait.until(ExpectedConditions.urlContains(urlFraction))) {
				return driver.getCurrentUrl();
			}else {
				System.out.println(urlFraction + "Url Value is not present");
				return "-1";
			}
		}catch(Exception e) {
			System.out.println(urlFraction + "Url Value is not present");
			e.printStackTrace();
			return "-1";
		}
	}
	
	/**
	 * An expectation for the URL of the current page to contain specific text.
	 * @param timeOut
	 * @param urlValue
	 * @return
	 */
	public String waitForURLToBe(int timeOut, String urlValue) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut)) ;
		
		try {
			if(wait.until(ExpectedConditions.urlContains(urlValue))) {
				return driver.getCurrentUrl();
			}else {
				System.out.println(urlValue + "Url Value is not present");
				return "-1";
			}
		}catch(Exception e) {
			System.out.println(urlValue + "Url Value is not present");
			e.printStackTrace();
			return "-1";
		}
	}
	
	
	
	/**
	 * An expectation for an Alert (JS) to be appeared on the Page.
	 * Check if the alert is present
	 * And Also switches to the alert
	 * Need not write driver.switch().alert();
	 * @param timeOut
	 * @return
	 */
	public Alert waitForJSAlert(int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.alertIsPresent());
	}
	
	public Alert waitForJSAlert(int timeOut, int pollingTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(pollingTime));
		return wait.until(ExpectedConditions.alertIsPresent());
	}
	
	
	
	/**
	 * Checks the Number of Windows
	 * @param timeOut
	 * @param numberOfWindowsToBe
	 * @return 
	 */
	public Boolean waitForNumberOfBrowserWindow(int timeOut, int numberOfWindowsToBe) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.numberOfWindowsToBe(numberOfWindowsToBe));
	}


	
	
	public void waitForFrameByLocator(int timeOut, By frameLocator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
	}
	
	public void waitForFrameByIndex(int timeOut, int frameIndex) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
	}
	
	public void waitForFrameByFrameNameOrId(int timeOut, String nameOrId) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(nameOrId));
	}
	
	public void waitForFrameByFrameElement(int timeOut, WebElement frameElement) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
	}
	
	
	
	/**
	 * An expectation for checking that there is at least one element present on a web page.
	 * @param timeOut
	 * @param locator
	 * @return
	 */
	public List<WebElement> waitForPresenceOfElementsLocated(int timeOut, By locator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}
	
	/**
	 * An expectation for checking that all elements present on the web page that match the locator are visible. 
	 * Visibility means that the elements are not only displayed but also have a height and width that is greater than 0.
	 * @param timeOut
	 * @param locator
	 * @return
	 */
	public List<WebElement> waitForVisibilityOfElementsLocated(int timeOut, By locator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}
	
	//***********************************************FluentWait Utils***********************************************//
	
	/**
	 * presenceOfElementLocated() using FluentWait
	 * @param locator
	 * @param timeOut
	 * @param pollingTime
	 * @return
	 */
	public WebElement waitForPresenceOfElementWithFluentWait(By locator, int timeOut, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
								.withTimeout(Duration.ofSeconds(timeOut))
									.pollingEvery(Duration.ofSeconds(pollingTime))	
										.ignoring(NoSuchElementException.class)
											.ignoring(StaleElementReferenceException.class)
												.withMessage("TimeOut -- Element not found" + locator);
		
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
											
	}

	
	/**
	 * visibilityOfElementLocated() using FluentWait
	 * @param locator
	 * @param timeOut
	 * @param pollingTime
	 * @return
	 */
	public WebElement waitForVisibilityOfElementWithFluentWait(By locator, int timeOut, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
								.withTimeout(Duration.ofSeconds(timeOut))
									.pollingEvery(Duration.ofSeconds(pollingTime))	
										.ignoring(NoSuchElementException.class)
											.ignoring(StaleElementReferenceException.class)
												.withMessage("TimeOut -- Element not found" + locator);
		
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
											
	}
	
	
	/**
	 * alertIsPresent() using FluentWait
	 * @param timeOut
	 * @param pollingTime
	 * @return
	 */
	public Alert waitForJSAlertWithFluentWait(int timeOut, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
								.withTimeout(Duration.ofSeconds(timeOut))
									.pollingEvery(Duration.ofSeconds(pollingTime))
										.ignoring(NoAlertPresentException.class)
											.withMessage("TimeOut -- Alert is not present");
		
		return wait.until(ExpectedConditions.alertIsPresent());
		
	}

	
	/**
	 * frameToBeAvailableAndSwitchToIt() using FluentWait
	 * @param timeOut
	 * @param nameOrId
	 * @param pollingTime
	 */
	public void waitForFrameByFrameNameOrIdWithFluentWait(int timeOut, String nameOrId, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
								.withTimeout(Duration.ofSeconds(timeOut))
									.pollingEvery(Duration.ofSeconds(pollingTime))
										.ignoring(NoSuchFrameException.class)
											.withMessage("TimeOut -- No such frame found");
		
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(nameOrId));
	
	}
	
	
	
	
	/**
	 * visibilityOfElementLocated() and sendKeys() WebDriverWait with FluentWait Features
	 * @param locator
	 * @param timeOut
	 * @param pollingTime
	 * @param value
	 */
	public void waitForElementAndEnterValue(By locator, int timeOut, int pollingTime, String value) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait
			.pollingEvery(Duration.ofSeconds(pollingTime))
				.ignoring(NoSuchElementException.class)
					.withMessage("TimeOut -- Element not found" + locator)
						.until(ExpectedConditions.visibilityOfElementLocated(locator))
							.sendKeys(value);
	
	}
	
	/**
	 * visibilityOfElementLocated() and click() WebDriverWait with FluentWait Features
	 * @param locator
	 * @param timeOut
	 * @param pollingTime
	 */
	public void waitForElementAndClick(By locator, int timeOut, int pollingTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait
			.pollingEvery(Duration.ofSeconds(pollingTime))
				.ignoring(NoSuchElementException.class)
					.withMessage("TimeOut -- Element not found" + locator)
						.until(ExpectedConditions.visibilityOfElementLocated(locator))
							.click();
	
	}
	
	
	//***********************************************CustomWait Utils***********************************************//
	
	public WebElement retryingElementWithCustomWait(By locator, int timeOut) {
		
		WebElement element = null;
		int attempts = 0;
		
			while(attempts < timeOut) {
				try {
					element = getElement(locator);
					System.out.println(locator + "Element found in: " + attempts + "secs");
					break;
				}
				catch(Exception e) {
					System.out.println(locator + "Element not found in: " + attempts + "secs");
					try {
						Thread.sleep(500); //Thread.sleep( milli seconds ) --> default - Thread.sleep(500);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
				attempts++;
			}
			if(element == null) {
				System.out.println("Tried to find element in " +timeOut+ "secs with 500 milli secs interval");
			}
			return element;
	}
	
	
	public WebElement retryingElementWithCustomWait(By locator, int timeOut, long pollingTime) {
		
		WebElement element = null;
		int attempts = 0;
		
			while(attempts < timeOut) {
				try {
					element = getElement(locator);
					System.out.println(locator + "Element found in: " + attempts + "secs");
					break;
				}
				catch(Exception e) {
					System.out.println(locator + "Element not found in: " + attempts + "secs");
					try {
						Thread.sleep(pollingTime); 
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
				attempts++;
			}
			if(element == null) {
				System.out.println("Tried to find element in " +timeOut+ "secs with " +pollingTime+ " milli secs interval");
			}
			return element;
	}
	

}
