package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstant;

public class AccountsPageTest extends BaseTest{
	
	@BeforeClass
	public void accPageSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	
	@Test
	public void accPageTitleTest() {
		String actAccPageTitle = accPage.getAccPageTitle();
		Assert.assertEquals(actAccPageTitle, AppConstant.ACCOUNTS_PAGE_TITLE);
	}
	
	@Test
	public void isLogoutLinkExistTest() {
		Assert.assertTrue(accPage.isLogoutLinkExist());
	}
	
	@Test
	public void accPageHeadersCountTest() {
		int actPageHeadersCount = accPage.accPageHeadersCount();
		System.out.println("Headers size: " + actPageHeadersCount);
		Assert.assertEquals(actPageHeadersCount, AppConstant.EXPECTED_ACC_PAGE_HEADERS_COUNT);
	}
	
	@Test
	public void accPageHeadersTest() {
		List<String> actHeadersList = accPage.getAccPageHeaders();
		System.out.println("Headers List: " + actHeadersList);
		Assert.assertEquals(actHeadersList, AppConstant.EXPECTED_ACC_PAGE_HEADERS);
	}
	
	
	@DataProvider
	public Object[][] getSearchKey() {
		return new Object[][] {
			{"macbook", 3},
			{"iMac", 1},
			{"Samsung", 2}
		};
	}
	
	@Test(dataProvider = "getSearchKey")
	public void searchTest(String searchKey, int expResultsCount) {
		searchResPage = accPage.doSearch(searchKey);
		int actResultsCount = searchResPage.getSearchResultsCount();
		Assert.assertEquals(actResultsCount, expResultsCount);
	}
	

}
