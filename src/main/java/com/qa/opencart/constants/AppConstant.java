package com.qa.opencart.constants;

import java.util.Arrays;
import java.util.List;

public class AppConstant {
	
	
	public static final String LOGIN_PAGE_TITLE = "Account Login";
	public static final String LOGIN_PAGE_URL_FRACTION = "route=account/login";
	
	public static final String ACCOUNTS_PAGE_TITLE = "My Account";
	public static final List<String> EXPECTED_ACC_PAGE_HEADERS = Arrays.asList("My Account", "My Orders", "My Affiliate Account", "Newsletter");
	public static final int EXPECTED_ACC_PAGE_HEADERS_COUNT = 4; 
	
	public static final String USER_REGISTER_SUCCESS_MSG = "Your Account Has Been Created";

	//**********************************************default TimeOut Values**********************************************//
	
	public static final int SHORT_TIME_OUT = 5;
	 public static final int MEDIUM_TIME_OUT = 10;
	 public static final int LONG_TIME_OUT = 15;
	 public static final int BIG_TIME_OUT = 20;
}
