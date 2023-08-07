package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

import io.qameta.allure.Epic;
import io.qameta.allure.Story;


@Epic("EPIC - 102: Design of Product Information Page for OpenCart Application")
@Story("US - 202: implement ProductInforPage features for OpenCart Application")

public class ProductInfoPageTest extends BaseTest{
	
	@BeforeClass
	public void productInfoPageSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@DataProvider
	public Object[][] getProductTestData() {
		return new Object[][] {
			{"macbook", "MacBook Pro"},
			{"macbook", "MacBook Air" },
			{"imac", "iMac"}
		};
	}
	
	@Test(dataProvider = "getProductTestData")
	public void productHeaderTest(String searchKey, String expProductHeader) {
		searchResPage = accPage.doSearch(searchKey);
		productInfoPage = searchResPage.selectProduct(expProductHeader);
		String actProductHeader = productInfoPage.getProductHeader();
		Assert.assertEquals(actProductHeader, expProductHeader);
	}
	
	
	
	@DataProvider
	public Object[][] getProductImagesCountTestData() {
		return new Object[][] {
			{"macbook", "MacBook Pro", 4},
			{"macbook", "MacBook Air", 4 },
			{"imac", "iMac", 3}
		};
	}
	
	@Test(dataProvider = "getProductImagesCountTestData")
	public void productImagesCountTest(String searchKey, String header, int expProductImagesCount) {
		searchResPage = accPage.doSearch(searchKey);
		productInfoPage = searchResPage.selectProduct(header);
		int actProductImagesCount = productInfoPage.getProductImagesCount();
		Assert.assertEquals(actProductImagesCount, expProductImagesCount);
		
	}
	
	
	@Test
	public void productMasterDataInfoTest() {
		searchResPage = accPage.doSearch("macbook");
		productInfoPage = searchResPage.selectProduct("MacBook Pro");
		Map<String, String> productMasterActualData = productInfoPage.getProductMasterData();
		System.out.println(productMasterActualData);
		
		//Hard Assertions - If one FAILS we cannot check the next ones 
//		Assert.assertEquals(productMasterActualData.get("Brand"), "Apple");
//		Assert.assertEquals(productMasterActualData.get("Availability"), "In Stock");
//		Assert.assertEquals(productMasterActualData.get("header"), "MacBook Pro");
//		Assert.assertEquals(productMasterActualData.get("actualprice"), "$2,000.00");
//		Assert.assertEquals(productMasterActualData.get("ImagesCount"), "4");
		
		
		//softAssertions - If one FAILS, test will FAIL but it will check all the assertions present in the method 
		softAssert.assertEquals(productMasterActualData.get("Brand"), "Apple");
		softAssert.assertEquals(productMasterActualData.get("Availability"), "In Stock");
		softAssert.assertEquals(productMasterActualData.get("header"), "MacBook Pro");
		softAssert.assertEquals(productMasterActualData.get("actualprice"), "$2,000.00");
		softAssert.assertEquals(productMasterActualData.get("ImagesCount"), "4");
		softAssert.assertAll();
		//without the above statement --> All the assertions will be checked but will not show any FAILED assertions --> False Result 
	}

}
