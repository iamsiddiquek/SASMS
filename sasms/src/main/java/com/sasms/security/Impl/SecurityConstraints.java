package com.sasms.security.Impl;

public class SecurityConstraints {

	
	public static final Long EXPIRATION_TIME = 864000000L; // 10 days
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String SIGN_UP_URL = "/users";
	public static final String TOKEN_SECRET = "jf9i4jgu83nfl0";



}
