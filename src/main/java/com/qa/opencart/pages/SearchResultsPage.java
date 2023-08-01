package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstant;
import com.qa.opencart.utils.ElementUtil;

public class SearchResultsPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	
	
	private By productResults = By.cssSelector("div.product-layout");
	

	
	public SearchResultsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	
	public int getSearchResultsCount() {
		return eleUtil.waitForVisibilityOfElementsLocated(AppConstant.MEDIUM_TIME_OUT, productResults).size();
	}
	
	
	public ProductInfoPage selectProduct(String productName) {
		eleUtil.waitForElementToBeClickable(AppConstant.MEDIUM_TIME_OUT, By.linkText(productName));
		return new ProductInfoPage(driver);
	}
	

}
