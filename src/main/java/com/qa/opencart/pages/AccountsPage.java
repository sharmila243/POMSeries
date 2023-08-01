package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstant;
import com.qa.opencart.utils.ElementUtil;

public class AccountsPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private By isLogoutLinkExist = By.linkText("Logout");
	private By accHeaders = By.cssSelector("div#content h2");
	private By search = By.name("search");
	private By searchBtn = By.cssSelector("div#search button");
	
	
	public AccountsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	
	public String getAccPageTitle() {
		return eleUtil.waitForTitleIs(AppConstant.ACCOUNTS_PAGE_TITLE, AppConstant.SHORT_TIME_OUT);
	}
	
	public boolean isLogoutLinkExist() {
		return eleUtil.waitForElementVisible(isLogoutLinkExist, AppConstant.SHORT_TIME_OUT).isDisplayed();
	}
	
	public int accPageHeadersCount() {
		return eleUtil.waitForVisibilityOfElementsLocated(AppConstant.MEDIUM_TIME_OUT, accHeaders).size();
	}
	
	public List<String> getAccPageHeaders() {
		List<WebElement> headersList = eleUtil.waitForVisibilityOfElementsLocated(AppConstant.MEDIUM_TIME_OUT, accHeaders);
		List<String> headersValueList = new ArrayList<String>();
		for(WebElement e: headersList) {
			String header = e.getText();
			headersValueList.add(header);
		}
		return headersValueList;
	}
	
	
	public SearchResultsPage doSearch(String searchKey) {
		WebElement searchField = eleUtil.waitForElementVisible(search, AppConstant.SHORT_TIME_OUT);
		searchField.clear();
		searchField.sendKeys(searchKey);
		eleUtil.doClick(searchBtn);
		//
		return new SearchResultsPage(driver);
	}

}	

