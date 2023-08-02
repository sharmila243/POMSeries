package com.qa.opencart.pages;

import org.openqa.selenium.By;

public class CartPage {
	
	String product = "macbook";
	
	By prd = By.id("product");
	
	public void cartPage() {
		System.out.println("clicking on product");
	}

}
