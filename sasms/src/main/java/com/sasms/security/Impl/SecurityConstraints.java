package com.sasms.security.Impl;

import java.util.concurrent.TimeUnit;

import com.sasms.SpringApplicationContext;
import com.sasms.security.AppProperties;

public class SecurityConstraints {

	
//	public static final Long EXPIRATION_TIME = 864000000L; // 10 days
	public static final Long EXPIRATION_TIME = TimeUnit.DAYS.toMillis(10);
//	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String SIGN_UP_URL = "/users";
	
	// Symmetric Signature/Algorithm Secrete Token use to be parse data.
//	public static final String TOKEN_SECRET = "j3k2l4h4v6bb72l1o3"; 
	

	public static String getTokenSecret() {

		AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("appPropertiesImpl");
		return appProperties.getTokenSecret();
	}	



}
