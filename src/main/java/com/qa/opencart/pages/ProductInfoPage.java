package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstant;
import com.qa.opencart.utils.ElementUtil;

public class ProductInfoPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil; 
	
	
	private By productHeader = By.cssSelector("div#content h1");
	private By totalProductImages = By.cssSelector("ul.thumbnails img");
	private By quantity = By.name("quantity");
	private By addToCart = By.id("button-cart");
	private By productMetaData = By.xpath("(//div[@id = 'content']//ul[@class = 'list-unstyled'])[1]/li");
	private By productPricingData = By.xpath("(//div[@id = 'content']//ul[@class = 'list-unstyled'])[2]/li");
	
	
	private Map<String, String> productMap;
	
	
	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	
	public String getProductHeader() {
		return eleUtil.doElementGetText(productHeader);
	}
	
	public int getProductImagesCount() {
		int actProductImagesCount =  eleUtil.waitForVisibilityOfElementsLocated(AppConstant.MEDIUM_TIME_OUT, totalProductImages).size();
		System.out.println("Total selected product Images: " + actProductImagesCount + "for product" + getProductHeader());
		return actProductImagesCount;
	}
	
	
//	Brand: Apple
//	Product Code: Product 18
//	Reward Points: 800
//	Availability: In Stock
	public void getProductMetaData() {
		List<WebElement> metaList = eleUtil.waitForVisibilityOfElementsLocated(AppConstant.MEDIUM_TIME_OUT, productMetaData);
		
		//Collection - key and value 
		//Map --> parent of --> HashMap 
		//put() is the method used in MAP 
		//Map<String, String> metaMap = new HashMap<String, String>();
		
		for(WebElement e: metaList) {
			String metaText = e.getText();
			String key = metaText.split(":")[0].trim();
			String value = metaText.split(":")[1].trim();
			
			productMap.put(key, value);
		}
	}
	
//	$2,000.00
//	Ex Tax: $2,000.00
	public void getProductPricingData() {
		List<WebElement> priceList = eleUtil.waitForVisibilityOfElementsLocated(AppConstant.SHORT_TIME_OUT, productPricingData);

//		Map<String, String> priceMap = new HashMap<String, String>();

		String actPrice = priceList.get(0).getText().trim();
		String exTax = priceList.get(1).getText().split(":")[0].trim();
		String exTaxValue = priceList.get(1).getText().split(":")[1].trim();
		
		productMap.put("actualprice", actPrice);
		productMap.put(exTax, exTaxValue);
		
	}
	
	
	public Map<String, String> getProductMasterData() {
		productMap = new HashMap<String, String>(); //non-order based collection - HashMap
		
//		productMap = new LinkedHashMap<String, String>(); //sorted collection - LinkedHashMap

//		productMap = new TreeMap<String, String>(); //Alphabetcial order - TreeMap - ABC..abc..
		
		
		productMap.put("header", getProductHeader());
		productMap.put("ImagesCount", String.valueOf(getProductImagesCount()));
		getProductMetaData();
		getProductPricingData();
		
		return productMap;
	}
	
	
	

}
