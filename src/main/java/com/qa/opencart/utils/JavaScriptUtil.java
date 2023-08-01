package com.qa.opencart.utils;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptUtil {

	private WebDriver driver;
	private JavascriptExecutor js;
	
	
	
	public JavaScriptUtil(WebDriver driver) {
		this.driver = driver;
		js = (JavascriptExecutor)this.driver;
	}
	




	public String getTitleByJS() {
		return js.executeScript("return document.title;").toString();
	}
	
	
	
	
	public void goBackWithJS() {
		js.executeScript("history.go(-1);");
	}
	
	public void goForwardWithJS() {
		js.executeScript("history.go(+1);");
	}
	
	public void pageRefreshWithJS() {
		js.executeScript("history.go(0);");
	}
	
	
	
	
	public void generateJSAlert(String alertMsg) {
		js.executeScript("alert('"+alertMsg+"')");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.switchTo().alert().accept(); //or Dismiss
	}
	
	public void generateConfirmAlert(String alertMsg) {
		js.executeScript("confirm('"+alertMsg+"')");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.switchTo().alert().accept(); //or Dismiss
	}
	
	public void generatePromptAlert(String alertMsg, String value) {
		js.executeScript("prompt('"+alertMsg+"')");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Alert alert = driver.switchTo().alert();
		alert.sendKeys(value); //or Dismiss
		alert.accept();
	}
	
	
	
	//to get the text of the ENTIRE PAGE - 'Content Testing'
	public String getPageInnerText() {
		return js.executeScript("return document.documentElement.innerText;").toString();
	}
	
	
	
	
	//Scroll - Page which has footer (Static) 
		//scrollTo - Vertical Scrolling
		//scrollBy - Horizontal Scrolling
	public void scrollPageDown() {
		js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
	}
	
	public void scollPageUp() {
		js.executeScript("window.scrollTo(document.body.scrollHeight, 0);");
	}
	
	public void scrollPageDownToMiddle() {
		js.executeScript("window.scrollTo(0, document.body.scrollHeight/2);");
	}
	
	public void scrollPageDown(String height) {
		js.executeScript("window.scrollTo(0, '"+height+"')");
	}
	
	public void scrollIntoView(WebElement element) {
		js.executeScript("arguments[0].scrollIntoView(true);", element);
			//arguments[0] - WebElement element
			//arguments[1] - If we pass any other parameter next then it becomes arguments[1] - we can supply multiple parameters
			//We'll give you the element and arguments[0].scrollIntoView should be true
			//This method will scroll till it finds the webElement
	}
	
	
	
	
	//Zoom-in and Zoom-out - for Responsive Testing/Accessibility testing - elements are properly visible or not
	public void zoomChromeEdgeSafari(String zoomPercentage) {
		js.executeScript("document.body.style.zoom = '"+zoomPercentage+"%'");
	}
	
	public void zoomFirefox(String zoomPercentage) {
		js.executeScript("document.body.style.MozTransform = 'scale("+zoomPercentage+")'");
	}
	
	
	
	
	//Draw Border
		//To a WebElement - we'll draw a border with some color 
	//In MT - To raise a bug in JIRA, we take screenshot -- paint -- highlight and post it 
	public void drawBorder(WebElement element) {
		js.executeScript("arguments[0].style.border = '3px solid red'", element); 
	}
	
	
	
	
	//Flash
	public void flash(WebElement element) {
		String bgColor = element.getCssValue("backgroundColor");
			for(int i = 0; i<100; i++) {
				changeColor("rgb(0, 200, 0)", element);
				//changeColor("rgb(0, 255, 255)", element); //aqua blue
				//changeColor("rgb(0, 0, 255)", element); //blue
				//changeColor("rgb(255, 87, 51)", element); //orange
				changeColor(bgColor, element);
			}
	}
	
	private void changeColor(String color, WebElement element) {
		js.executeScript("arguments[0].style.backgroundColor = '"+color+"'", element);
		
		try {
			Thread.sleep(20);
		}catch(InterruptedException e){
			
		}
	}
	
}
